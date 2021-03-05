# Docker

## 介绍

### Docker

Docker 是一个开源的应用容器引擎，可以让开发者打包他们的应用以及依赖包到一个轻量级、可移植的容器中，然后发布到任何流行的 Linux 机器上，也可以实现虚拟化。

Docker 从 17.03 版本之后分为 CE（Community Edition: 社区版） 和 EE（Enterprise Edition: 企业版）。

**Docker** 使用 `Google` 公司推出的 [Go 语言](https://golang.google.cn/) 进行开发实现，基于 `Linux` 内核的 [cgroup](https://zh.wikipedia.org/wiki/Cgroups)，[namespace](https://en.wikipedia.org/wiki/Linux_namespaces)，以及 [OverlayFS](https://docs.docker.com/storage/storagedriver/overlayfs-driver/) 类的 [Union FS](https://en.wikipedia.org/wiki/Union_mount) 等技术，对进程进行封装隔离，属于 [操作系统层面的虚拟化技术](https://en.wikipedia.org/wiki/Operating-system-level_virtualization)。由于隔离的进程独立于宿主和其它的隔离的进程，因此也称其为容器。最初实现是基于 [LXC](https://linuxcontainers.org/lxc/introduction/)，从 `0.7` 版本以后开始去除 `LXC`，转而使用自行开发的 [libcontainer](https://github.com/docker/libcontainer)，从 `1.11` 版本开始，则进一步演进为使用 [runC](https://github.com/opencontainers/runc) 和 [containerd](https://github.com/containerd/containerd)。

![Docker 架构](https://gitee.com/nnnoh/image-hosting-service/raw/master/img/2021/docker-on-linux.png)

`runc` 是一个 Linux 命令行工具，用于根据 [OCI容器运行时规范](https://github.com/opencontainers/runtime-spec) 创建和运行容器。

`containerd` 是一个守护程序，它管理容器生命周期，提供了在一个节点上执行容器和管理镜像的最小功能集。

- 传统虚拟机技术是虚拟出一套硬件后，在其上运行一个完整操作系统，在该系统上再运行所需应用进程；
- Docker 容器内的应用进程直接运行于宿主的内核，容器内没有自己的内核，而且也没有进行硬件虚拟。因此容器要比传统虚拟机更为轻便。

![传统虚拟化](https://gitee.com/nnnoh/image-hosting-service/raw/master/img/2021/traditional-virtualization.png)

![Docker](https://gitee.com/nnnoh/image-hosting-service/raw/master/img/2021/docker-diff.png)

| 特性       | 容器               | 虚拟机      |
| ---------- | ------------------ | ----------- |
| 启动       | 秒级               | 分钟级      |
| 硬盘使用   | 一般为 `MB`        | 一般为 `GB` |
| 性能       | 接近原生           | 弱于        |
| 系统支持量 | 单机支持上千个容器 | 一般几十个  |

### 镜像（Image）

**Docker 镜像** 是一个特殊的文件系统，除了提供容器运行时所需的程序、库、资源、配置等文件外，还包含了一些为运行时准备的一些配置参数（如匿名卷、环境变量、用户等）。镜像 **不包含** 任何动态数据，其内容在构建之后也不会被改变。

#### 分层存储

因为镜像包含操作系统完整的 `root` 文件系统，其体积往往是庞大的，因此在 Docker 设计时，就充分利用 [Union FS](https://en.wikipedia.org/wiki/Union_mount) 的技术，将其设计为分层存储的架构。

所以严格来说，镜像并非是像一个 `ISO` 那样的打包文件，镜像只是一个虚拟的概念，其实际体现并非由一个文件组成，而是由一组文件系统组成，或者说，由多层文件系统联合组成。

镜像构建时，会一层层构建，前一层是后一层的基础。每一层构建完就不会再发生改变，后一层上的任何改变只发生在自己这一层。比如，删除前一层文件的操作，实际不是真的删除前一层的文件，而是仅在当前层标记为该文件已删除。在最终容器运行的时候，虽然不会看到这个文件，但是实际上该文件会一直跟随镜像。因此，在构建镜像的时候，需要额外小心，每一层尽量只包含该层需要添加的东西，任何额外的东西应该在该层构建结束前清理掉。

分层存储的特征还使得镜像的复用、定制变的更为容易。甚至可以用之前构建好的镜像作为基础层，然后进一步添加新的层，以定制自己所需的内容，构建新的镜像。

### 容器（Container）

容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。

容器的实质是进程，但与直接在宿主执行的进程不同，容器进程运行于属于自己的独立的 [命名空间](https://en.wikipedia.org/wiki/Linux_namespaces)。因此容器可以拥有自己的 `root` 文件系统、自己的网络配置、自己的进程空间，甚至自己的用户 ID 空间。

每一个容器运行时，是以镜像为基础层，在其上创建一个当前容器的存储层，我们可以称这个为容器运行时读写而准备的存储层为 **容器存储层**。

容器存储层的生存周期和容器一样，容器消亡时，容器存储层也随之消亡。

按照 Docker 最佳实践的要求，容器不应该向其存储层内写入任何数据，容器存储层要保持无状态化。所有的文件写入操作，都应该使用 [数据卷（Volume）]()、或者 [绑定宿主目录]()，在这些位置的读写会跳过容器存储层，直接对宿主（或网络存储）发生读写，其性能和稳定性更高。

数据卷的生存周期独立于容器，容器消亡，数据卷不会消亡。因此，使用数据卷后，容器删除或者重新运行之后，数据却不会丢失。

### 仓库（Repository）

[Docker Registry ]()是一个集中的存储、分发镜像的服务。

一个 **Docker Registry** 中可以包含多个 **仓库**（`Repository`）；每个仓库可以包含多个 **标签**（`Tag`）；每个标签对应一个镜像。

通常，一个仓库会包含同一个软件不同版本的镜像，而标签就常用于对应该软件的各个版本。我们可以通过 `<仓库名>:<标签>` 的格式来指定具体是这个软件哪个版本的镜像。如果不给出标签，将以 `latest` 作为默认标签。

以 [Ubuntu 镜像](https://hub.docker.com/_/ubuntu) 为例，`ubuntu` 是仓库的名字，其内包含有不同的版本标签，如，`16.04`, `18.04`。我们可以通过 `ubuntu:16.04`，或者 `ubuntu:18.04` 来具体指定所需哪个版本的镜像。如果忽略了标签，比如 `ubuntu`，那将视为 `ubuntu:latest`。

仓库名经常以 *两段式路径* 形式出现，比如 `jwilder/nginx-proxy`，前者往往意味着 Docker Registry 多用户环境下的用户名，后者则往往是对应的软件名。但这并非绝对，取决于所使用的具体 Docker Registry 的软件或服务。

## 基本使用

### 安装

Centos 7上 yum install docker，默认安装的 docker 版本号为1.13.1。

docker是 在1.13.1后正式推出企业版，版本号也从此发生了变化，由原来的 1.13.1 升级到了 17.03（即表示17年3月），从此 docker 有了 docker-ee 和 docker-ce 之分。

docker-ce 安装步骤参考：[docker-ce | 镜像站使用帮助 | Tsinghua Open Source Mirror](https://mirror.tuna.tsinghua.edu.cn/help/docker-ce/)

安装完成后，运行下面的命令，验证是否安装成功。

```bash
$ docker version
# 或者
$ docker info
```

Docker 需要用户具有 sudo 权限，为了避免每次命令都输入`sudo`，可以把用户加入 docker 用户组（[官方文档](https://docs.docker.com/install/linux/linux-postinstall/#manage-docker-as-a-non-root-user)）。

```bash
$ sudo groupadd docker
$ sudo usermod -aG docker $USER
// 重新登录后生效
```

> docker进程使用Unix Socket而不是TCP端口。而默认情况下，Unix socket属于root用户，需要root权限才能访问。
>
> docker守护进程启动的时候，会默认赋予名字为docker的用户组读写Unix socket的权限。

Docker 是服务器----客户端架构。命令行运行`docker`命令的时候，需要本机有 Docker 服务。如果这项服务没有启动，可以用下面的命令启动（[官方文档](https://docs.docker.com/config/daemon/systemd/)）。

```bash
# service 命令的用法
$ sudo service docker start

# systemctl 命令的用法
$ sudo systemctl start docker
```

### image 文件

**Docker 把应用程序及其依赖，打包在 image 文件里面。**

image 文件可以看作是容器的模板。Docker 根据 image 文件生成容器的实例。同一个 image 文件，可以生成多个同时运行的容器实例。

image 是二进制文件。实际开发中，一个 image 文件往往通过继承另一个 image 文件，加上一些个性化设置而生成。

```bash
# 列出本机的所有 image 文件。
$ docker image ls
$ docker images
# 删除 image 文件
$ docker image rm [imageName]
```

一个 IMAGE ID 对应一个 REPOSITORY，一个 REPOSITORY 可以有多个 TAG。

为了方便共享，image 文件制作完成后，可以上传到网上的仓库。如，Docker 的官方仓库 [Docker Hub](https://hub.docker.com/)。

国内 Hub 源：

- [Docker Hub 源使用帮助 — USTC Mirror Help  文档](http://mirrors.ustc.edu.cn/help/dockerhub.html)
- [容器镜像服务 - 阿里云](https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors)

### Hello World

**将 image 文件从仓库抓取到本地。**

```bash
$ docker image pull library/hello-world
```

`docker image pull`是抓取 image 文件的命令。

`library/hello-world`是 image 文件在仓库里面的位置，其中`library`是 image 文件所在的组，`hello-world`是 image 文件的名字。

Docker 官方提供的 image 文件，都放在[`library`](https://hub.docker.com/r/library/)组里面，所以它的是默认组，可以省略。

**查看本地的 image 文件。**

```bash
$ docker image ls
```

**运行指定 image 文件。**

```bash
$ docker container run hello-world
```

`docker container run`命令会从 image 文件，生成一个正在运行的容器实例。

注意，`docker container run`命令具有自动抓取 image 文件的功能。如果发现本地没有指定的 image 文件，就会从仓库自动抓取。因此，前面的`docker image pull`命令并不是必需的步骤。

`hello world` 输出提示之后就会停止运行，容器自动终止。

有些容器不会自动终止，因为提供的是服务。比如，安装运行 Ubuntu 的 image。

```bash
$ docker container run -it ubuntu bash
```

- `bash`：容器启动以后，内部第一个执行的命令。这里是启动 Bash，保证用户可以使用 Shell。

对于那些不会自动终止的容器，可以使用`docker container kill` 命令手动**终止运行**。（也可按下 Ctrl + d 或者输入 exit 退出容器）

```bash
$ docker container kill [containID]
```

### 容器文件

**image 文件生成的容器实例，本身也是一个文件，称为容器文件。**

```bash
# 列出本机正在运行的容器
$ docker container ls
# 列出本机正在运行的容器，功能同上
$ docker ps
# 列出本机所有容器，包括终止运行的容器
$ docker container ls -a
# 列出最近创建的容器(包括所有状态)
$ docker container ls -l
```

> docker container options 是Docker 1.13中的更新，docker container ls 与 docker ps 功能相同，但是语义更明确，简化了Docker的用法，所以更推荐使用新的写法。

通过容器Id或容器NAMES（自动分配）可以对指定容器进行操作。

关闭容器并不会删除容器文件，只是容器停止运行而已。可以使用`docker container rm`命令**删除终止运行的容器**。

```bash
$ docker container rm [containerID]
```

### Dockerfile 文件

Dockerfile 文件是一个文本文件，用来配置 image。Docker 根据 该文件生成二进制的 image 文件。

下面以 [koa-demos](http://www.ruanyifeng.com/blog/2017/08/koa.html) 项目为例，演示 Dockerfile 文件编写，实现让用户在 Docker 容器里面运行 Koa 框架。

```bash
$ git clone https://github.com/ruanyf/koa-demos.git
$ cd koa-demos
```

#### 编写 Dockerfile 文件

首先，在项目的根目录下，新建一个文本文件`.dockerignore`，写入下面的内容。

```
.git
node_modules
npm-debug.log
```

上面代码表示，这三个路径要排除，不要打包进入 image 文件。如果你没有路径要排除，这个文件可以不新建。

然后，在项目的根目录下，新建一个文本文件 `Dockerfile`，写入下面的内容。

```dockerfile
FROM node:8.4
COPY . /app
WORKDIR /app
RUN npm install --registry=https://registry.npm.taobao.org
EXPOSE 3000
CMD node demos/01.js
```

- `FROM node:8.4`：该 image 文件继承官方的 node image，冒号表示标签，这里标签是`8.4`，即8.4版本的 node。
- `COPY . /app`：将当前目录下的所有文件（除了`.dockerignore`排除的路径），都拷贝进入 image 文件的`/app`目录。
- `WORKDIR /app`：指定接下来的工作路径为`/app`。
- `RUN npm install`：在`/app`目录下，运行`npm install`命令安装依赖。注意，安装后所有的依赖，都将打包进入 image 文件。
- `EXPOSE 3000`：将容器 3000 端口暴露出来， 允许外部连接这个端口。
- `CMD node demos/01.js`：容器启动后自动执行该命令。
  - `RUN`命令在 image 文件的构建阶段执行，执行结果都会打包进入 image 文件；
  - `CMD`命令则是在容器启动后执行。
  - 一个 Dockerfile 可以包含多个`RUN`命令，但是最多只能有一个`CMD`命令。
  - 指定了`CMD`命令以后，`docker container run`命令就不能附加命令了（比如前面的`/bin/bash`），否则它会覆盖`CMD`命令。

#### 创建 image 文件

有了 Dockerfile 文件以后，就可以使用`docker image build`命令**创建 image 文件**。

```bash
$ docker image build -t koa-demo .
# 或者
$ docker image build -t koa-demo:0.0.1 .
```

- `-t`参数用来指定 image 文件的名字，后面还可以用冒号指定标签。如果不指定，默认的标签就是`latest`。

- 最后的那个点`.`指定上下文（context）的目录。

  - `docker build` 命令得知上下文路径后，会将路径下的内容打包，然后上传给 Docker 引擎。

    这样 Docker 引擎收到这个上下文包后，展开就会获得构建镜像所需的一切文件。

    上下文路径下不要放无用的文件，如果文件过多会造成传输过程缓慢。

  - `COPY` 这类指令中的源文件的路径都是相对于上下文的*相对路径*。

    `../`等超出了上下文的范围，Docker 引擎无法获得这些位置的文件。
  
  - 如果不额外指定 `Dockerfile` 的话，会将上下文目录下的名为 `Dockerfile` 的文件作为 Dockerfile。

如果运行成功，就可以看到新生成的 image 文件`koa-demo`了。

```bash
$ docker image ls
```

#### 生成容器

`docker container run`命令会**从 image 文件生成容器**。

```bash
$ docker container run --rm -p 8000:3000 -it koa-demo:0.0.1
```

- `-p`参数：容器的 3000 端口映射到本机的 8000 端口。
- `--rm`参数：在容器终止运行后自动删除容器文件。
- `-it`参数：容器的 Shell 映射到当前的 Shell，然后你在本机窗口输入的命令，就会传入容器。
  - `-t`：在新容器内指定一个伪终端。
  - `-i`：允许对容器内的标准输入 (STDIN) 进行交互。
- `koa-demo:0.0.1`：image 文件的名字（如果有标签，还需要提供标签，默认是 latest 标签）。

#### 发布 image 文件

容器运行成功后，就确认了 image 文件的有效性。这时，我们就可以考虑把 image 文件分享到网上，让其他人使用。

首先，去 [hub.docker.com](https://hub.docker.com/) 或 [cloud.docker.com](https://cloud.docker.com/) 注册一个账户。然后，用下面的命令登录。

```bash
$ docker login
```

接着，**为本地的 image 标注用户名和版本**。

```bash
$ docker image tag [imageName] [username]/[repository]:[tag]
# 实例
$ docker image tag koa-demos:0.0.1 ruanyf/koa-demos:0.0.1
```

也可以不标注用户名，重新构建一下 image 文件。

```bash
$ docker image build -t [username]/[repository]:[tag] .
```

最后，发布 image 文件。

```bash
$ docker image push [username]/[repository]:[tag]
```

发布成功以后，登录 hub.docker.com，就可以看到已经发布的 image 文件。

## 容器

### 启动停止查看

#### docker container run

##### 分离模式 （detach）

参数：

- `-d`：后台运行容器，并输出容器Id。

  以下情况需配合 `-it` 使用

  - 当容器的 `ENTRYPOINT` 是 `bash` 或 `sh`。

    否则容器会立即停止运行，因为 `bash` 找不到任何分配的伪终端。

  - 未来需要在容器中使用 `nano` 或 `vim`。

    否则在 `exec -it xxx bash` 终端运行 `nano` 时报错 `Error opening terminal: unknown.`。

退出：

- `exit` 或 `ctrl + D`：退出终端，会关闭容器。
- `ctrl+P ctrl+Q`（连续按）：退出，但不关闭容器。

Docker 容器启动时，默认会把容器内部第一个进程，也就是pid=1的程序，作为docker 容器是否正在运行的依据，如果 docker 容器pid=1的进程挂了，那么docker容器便会直接退出。

如下命令，nginx 程序将后台运行，这个时候 nginx 并不是pid为1的程序，而是执行的 bash，这个 bash 执行了 nginx 指令后就挂了，所以容器也就退出了。

```bash
$ docker run -d -p 80:80 my_image service nginx start
```

如下关闭 nginx 的后台运行，使容器能持续运行。

```bash
$ docker run -d -p 80:80 my_image nginx -g 'daemon off;'
```

#### docker container start

`docker container run`命令是新建容器，每运行一次，就会新建一个容器。`docker container start` 命令，它用来启动已经生成、已经停止运行的容器文件。

```bash
$ docker container start [containerID]
```

- `-i`：交互式运行

#### docker container stop

`docker container kill`命令终止容器运行，相当于向容器里面的主进程发出 SIGKILL 信号。`docker container stop`命令也是用来终止容器运行，相当于向容器里面的主进程发出 SIGTERM 信号，然后过一段时间再发出 SIGKILL 信号。

```bash
$ bash container stop [containerID]
```

这两个信号的差别是，应用程序收到 SIGTERM 信号以后，可以自行进行收尾清理工作，但也可以不理会这个信号。如果收到 SIGKILL 信号，就会强行立即终止，那些正在进行中的操作会全部丢失。

#### docker container logs

`docker container logs`命令用来查看 docker 容器的输出，即容器里面 Shell 的标准输出。如果`docker run`命令运行容器的时候，没有使用`-it`参数，就要用这个命令查看输出。

```bash
$ docker container logs [containerID]
```

#### docker container exec

`docker container exec`命令在正在运行的容器上执行命令，通常用于进入一个正在运行的 docker 容器的`shell`。

如果`docker run`命令运行容器的时候，没有使用`-it`参数，就可以用这个命令进入容器。

此命令进入容器后退出容器终端，不会导致容器的停止。

```bash
$ docker container exec -it [containerID] /bin/bash
```

#### docker attach

连接到正在运行中的容器。可以同时连接上同一个container来共享屏幕。

从这个容器退出，会导致容器的停止。

```bash
$ docker attach 1e560fca3906 
```

#### docker container cp

`docker container cp`命令用于正在运行的 Docker 容器里面的文件和本机之间的文件/目录复制。

```bash
$ docker container cp [OPTIONS] CONTAINER:SRC_PATH DEST_PATH|-
$ docker cp [OPTIONS] SRC_PATH|- CONTAINER:DEST_PATH
# 将b80f4b3ad86a容器中的docs文件夹复制到宿主机的用户目录下
$ docker cp b80f4b3ad86a:/root/docs ~/
```

#### docker container prune

清理所有处于终止状态的容器。

#### docker top

查看容器内部运行的进程

#### docker inspect

查看 Docker 的底层信息，会返回一个 JSON 文件记录着 Docker 容器的配置和状态信息。

### 容器连接

#### 端口映射

容器中可以运行一些网络应用，要让外部也可以访问这些应用，可以通过 **-P** 或 **-p** 参数来指定端口映射。

- **-P :**是容器内部端口**随机**映射到主机的高端口。
- **-p :** 是容器内部端口绑定到**指定**的主机端口。

另外，还可以指定容器绑定的网络地址，比如绑定 127.0.0.1。

默认都是绑定 tcp 端口，如果要绑定 UDP 端口，可以在端口后面加上 **/udp**。

```bash
$ docker run -d -p 127.0.0.1:5000:5000/udp training/webapp python app.py
```

`docker port [containerId]` 查看容器的端口映射。

对于少数几个容器互联可通过在容器运行时使用 `--link` 连接其他容器。示例：

```bash
# 1. 新建并启动 MySQL 容器
$ docker container run \
  -d \
  --rm \
  --name wordpressdb \
  --env MYSQL_ROOT_PASSWORD=123456 \
  --env MYSQL_DATABASE=wordpress \
  mysql:5.7
# 2. 基于官方的 WordPress image，新建并启动 WordPress 容器
 $ docker container run \
  -d \
  -p 127.0.0.2:8080:80 \
  --rm \
  --name wordpress \
  --env WORDPRESS_DB_PASSWORD=123456 \
  --link wordpressdb:mysql \
  --volume "$PWD/wordpress":/var/www/html \
  wordpress
```

- `--link wordpressdb:mysql`，表示 WordPress 容器要连到`wordpressdb`容器，冒号表示该容器的别名是`mysql`。

随着 Docker 网络的完善，强烈建议大家将容器加入自定义的 Docker 网络来连接多个容器，而不是使用 `--link` 参数。

#### 容器互联

docker 有一个连接系统允许将多个容器连接在一起，共享连接信息。

docker 连接会创建一个父子关系，其中父容器可以看到子容器的信息。

创建一个新的 Docker 网络。

```bash
$ docker network create -d bridge test-net
```

- **-d**：参数指定 Docker 网络类型，有 bridge、overlay。
  - 默认为 bridge。
  - overlay 网络类型用于 Swarm mode。

查看网络

```bash
$ docker network ls
```

运行容器并连接到新建的 test-net 网络:

```bash
$ docker run -itd --name test1 --network test-net ubuntu /bin/bash
$ docker run -itd --name test2 --network test-net ubuntu /bin/bash
```

在容器中即可通过 `ping test2` 确认是否能相互连接。

当创建一个容器时，docker 会自动对它进行命名。也可以使用 **--name** 标识来命名容器。

如果有多个容器之间需要互相连接，推荐使用 **Docker Compose**。

#### 配置 DNS

在宿主机的 /etc/docker/daemon.json 文件中增加以下内容来设置全部容器的 DNS：

```json
{
  "dns" : [
    "114.114.114.114",
    "8.8.8.8"
  ]
}
```

配置完，重启 docker 生效。

查看容器的 DNS 信息：

```bash
$ docker run -it --rm  ubuntu  cat etc/resolv.conf
```

如果只想在指定的容器设置 DNS，则可以在运行时手动指定：

```bash
$ docker run -it --rm -h host_ubuntu  --dns=114.114.114.114 --dns-search=test.com ubuntu
```

- **-h HOSTNAME 或者 --hostname=HOSTNAME**： 设定容器的主机名，它会被写到容器内的 /etc/hostname 和 /etc/hosts。

- **--dns=IP_ADDRESS**： 添加 DNS 服务器到容器的 /etc/resolv.conf 中，让容器用这个服务器来解析所有不在 /etc/hosts 中的主机名。

- **--dns-search=DOMAIN**： 设定容器的搜索域，当设定搜索域为 .example.com 时，在搜索一个名为 host 的主机时，DNS 不仅搜索 host，还会搜索 host.example.com。

如果在容器启动时没有指定 **--dns** 和 **--dns-search**，Docker 会默认用宿主主机上的 /etc/resolv.conf 来配置容器的 DNS。

### 导入导出

####  docker export

导出本地某个**容器**，可以使用 **docker export** 命令。

```bash
$ docker export 1e560fca3906 > ubuntu.tar
$ docker export 1e560fca3906 -o ubuntu.tar
```

#### docker import

使用 **docker import** 从容器快照文件中再导入为镜像，以下实例将快照文件 ubuntu.tar 导入到**镜像** test/ubuntu:v1:

```bash
$ cat docker/ubuntu.tar | docker import - test/ubuntu:v1
```

此外，也可以通过指定 URL 或者某个目录来导入，例如：

```bash
$ docker import http://example.com/exampleimage.tgz example/imagerepo
```

## 镜像

### 镜像操作

#### docker system df

`docker image ls` 列表中的镜像体积总和并非是所有镜像实际硬盘消耗。由于 Docker 镜像是多层存储结构，并且可以继承、复用，因此不同镜像可能会因为使用相同的基础镜像，从而拥有共同的层。由于 Docker 使用 Union FS，相同的层只需要保存一份即可，因此实际镜像硬盘占用空间很可能要比这个列表镜像大小的总和要小的多。

通过 `docker system df` 命令来便捷的查看镜像、容器、数据卷所占用的空间。

#### docker image prune

由于新旧镜像同名，旧镜像名称被取消，从而出现仓库名、标签均为 `<none>` 的镜像，`docker pull` 和 `docker build`  可能导致这种情况。这类无标签镜像也被称为 **虚悬镜像(dangling image)** ，可以用下面的命令专门显示这类镜像。

```bash
$ docker image ls -f dangling=true
```

一般来说，虚悬镜像已经失去了存在的价值，是可以随意删除的，可以用下面的命令删除。

```bash
$ docker image prune
```

#### docker image ls

##### 中间层镜像

默认的 `docker image ls` 列表中只会显示顶层镜像，如果希望显示包括中间层镜像在内的所有镜像的话，需要加 `-a` 参数。

```bash
$ docker image ls -a
```

这样会看到很多无标签的镜像，与之前的虚悬镜像不同，这些无标签的镜像很多都是**中间层镜像**，是其它镜像所依赖的镜像。这些无标签镜像不应该删除，否则会导致上层镜像因为依赖丢失而出错。

实际上，这些镜像也没必要删除，因为之前说过，相同的层只会存一遍，而这些镜像是别的镜像的依赖，因此并不会因为它们被列出来而多存了一份，无论如何你也会需要它们。只要删除那些依赖它们的镜像后，这些依赖的中间层镜像也会被连带删除。

##### 以特定格式显示

`--filter` 配合 `-q` 产生出指定范围的 ID 列表，然后送给另一个 `docker` 命令作为参数，从而针对这组实体成批的进行某种操作。

```bash
# 删除exited状态的容器
$ docker rm $(docker ps -q -f status=exited)
```

另外一些时候，我们可能只是对表格的结构不满意，希望自己组织列；或者不希望有标题，这样方便其它程序解析结果等，这就用到了 [Go 的模板语法](https://gohugo.io/templates/introduction/)。

比如，下面的命令会直接列出镜像结果，并且只包含镜像ID和仓库名：

```bash
$ docker image ls --format "{{.ID}}: {{.Repository}}"
```

#### docker search

从 Docker Hub 网站来搜索镜像。

```bash
$  docker search httpd
```

#### docker rmi

镜像删除使用 **docker rmi**  或 **docker image rm** 命令，比如删除 hello-world 镜像：

```bash
$ docker rmi hello-world
$ docker image rm redis:alpine
```

可以使用 `docker image ls -q` 来配合使用 `docker image rm`，这样可以成批的删除希望删除的镜像。

删除所有在 `mongo:3.2` 之前的镜像：

```bash
$ docker image rm $(docker image ls -q -f before=mongo:3.2)
```

#### docker commit

当我们从 docker 镜像仓库中下载的镜像不能满足我们的需求时，我们可以通过以下两种方式对镜像进行更改。

- ~~从已经创建的容器中更新镜像，并且提交这个镜像（**docker commit**）~~ - 不建议
- 使用 Dockerfile 指令来创建一个新的镜像（**docker build**）

`docker commit` 命令除了学习之外，还有一些特殊的应用场合，比如被入侵后保存现场等。

在修改了容器的文件后，也就是改动了容器的存储层。我们可以通过 `docker diff` 命令看到具体的改动。

当我们运行一个容器的时候（如果不使用卷的话），我们做的任何文件修改都会被记录于容器存储层里。`docker commit` 命令，可以将容器的存储层保存下来成为镜像。

定制好了变化后，可以通过命令 docker commit 来提交容器副本。

```bash
$ docker commit -m="has update" -a="runoob" e218edb10161 runoob/ubuntu:v2
```

- **-m:** 提交的描述信息
- **-a:** 指定镜像作者
- **e218edb10161：**容器 ID
- **runoob/ubuntu:v2:** 指定要创建的目标镜像名

可以用 `docker history` 具体查看镜像内的历史记录。

使用 `docker commit` 命令虽然可以比较直观的帮助理解镜像分层存储的概念，但是实际环境中并不会这样使用。

1. `docker diff` 命令可以看出，简单的操作，也会有大量的无关内容被添加进来，将会导致镜像极为臃肿。

2. 使用 `docker commit` 意味着所有对镜像的操作都是黑箱操作，生成的镜像也被称为 **黑箱镜像**。

   除了制作镜像的人知道执行过什么命令、怎么生成的镜像，别人根本无从得知。

   这种黑箱镜像的维护工作是非常痛苦的。

3. 任何修改的结果仅仅是在当前层进行标记、添加、修改，而不会改动上一层。如果使用 `docker commit` 制作镜像，以及后期修改的话，每一次修改都会让镜像更加臃肿一次，所删除的上一层的东西并不会丢失，会一直如影随形的跟着这个镜像，即使根本无法访问到。这会让镜像更加臃肿。

#### 导入导出

镜像导入导出：

```bash
$ docker save ubuntu:load > /root/ubuntu.tar
$ docker load < ubuntu.tar
```

和 export/import 区别：

- export 和 import 导出的是一个容器的快照, 不是镜像本身, 也就是说没有 layer。

  dockerfile 里的 workdir, entrypoint 之类的所有东西都会丢失，commit 过的话也会丢失。

  快照文件将丢弃所有的历史记录和元数据信息（即仅保存容器当时的快照状态），而镜像存储文件将保存完整记录，体积也更大。

-  docker save 保存的是镜像（image），docker export 保存的是容器（container）；
-  docker load 用来载入镜像包，docker import 用来载入容器包，但两者都会恢复为镜像；
-  docker load 不能对载入的镜像重命名，而 docker import 可以为镜像指定新名称。

### Dockerfile

基本使用参见：[Dockerfile 文件](#Dockerfile 文件)。

**原则与建议**

- 容器轻量化。从镜像中产生的容器应该尽量轻量化，能在足够短的时间内停止、销毁、重新生成并替换原来的容器。

- 使用 .dockerignore。在大部分情况下，Dockerfile 会和构建所需的文件放在同一个目录中，为了提高构建的性能，应该使用 .dockerignore来过滤掉不需要的文件和目录。

- 为了减少镜像的大小，减少依赖，仅安装需要的软件包。

- 一个容器只做一件事。解耦复杂的应用，分成多个容器，而不是所有东西都放在一个容器内运行。如一个 Python Web 应用，可能需要 Server、DB、Cache、MQ、Log 等几个容器。一个更加极端的说法：One process per container。

- 减少镜像的图层。不要多个 Label、ENV 等标签。

- 对续行的参数按照字母表排序，特别是使用apt-get install -y安装包的时候。

- 使用构建缓存。如果不想使用缓存，可以在构建的时候使用参数--no-cache=true来强制重新生成中间镜像。

#### 指令

##### FROM

定制镜像，那一定是以一个镜像为基础，在其上进行定制。 `FROM` 就是指定 **基础镜像**，因此一个 `Dockerfile` 中 `FROM` 是必备的指令，并且必须是第一条指令。

除了选择现有镜像为基础镜像外，Docker 还存在一个特殊的镜像，名为 `scratch`。这个镜像是虚拟的概念，并不实际存在，它表示一个空白的镜像。

```dockerfile
FROM scratch
...
```

如果你以 `scratch` 为基础镜像的话，意味着你不以任何镜像为基础，接下来所写的指令将作为镜像第一层开始存在。

不以任何系统为基础，直接将可执行文件复制进镜像的做法并不罕见，对于 Linux 下静态编译的程序来说，并不需要有操作系统提供运行时支持，所需的一切库都已经在可执行文件里了，因此直接 `FROM scratch` 会让镜像体积更加小巧。使用 [Go 语言](https://golang.google.cn/) 开发的应用很多会使用这种方式来制作镜像，这也是为什么有人认为 Go 是特别适合容器微服务架构的语言的原因之一。

##### RUN

格式：

```dockerfile
RUN <命令行命令>
RUN ["可执行文件", "参数1", "参数2"]
```

Dockerfile 的每一个指令都会在 docker 上新建一层。所以过多无意义的层，会造成镜像膨胀过大。例如：

```dockerfile
FROM debian:stretch

RUN apt-get update
RUN apt-get install -y gcc libc6-dev make wget
RUN wget -O redis.tar.gz "http://download.redis.io/releases/redis-5.0.3.tar.gz"
RUN mkdir -p /usr/src/redis
RUN tar -xzf redis.tar.gz -C /usr/src/redis --strip-components=1
RUN make -C /usr/src/redis
RUN make -C /usr/src/redis install
```

以上执行会创建 7 层镜像。可简化为以下格式：

```dockerfile
FROM debian:stretch

RUN set -x; buildDeps='gcc libc6-dev make wget' \
    && apt-get update \
    && apt-get install -y $buildDeps \
    && wget -O redis.tar.gz "http://download.redis.io/releases/redis-5.0.3.tar.gz" \
    && mkdir -p /usr/src/redis \
    && tar -xzf redis.tar.gz -C /usr/src/redis --strip-components=1 \
    && make -C /usr/src/redis \
    && make -C /usr/src/redis install \
    && rm -rf /var/lib/apt/lists/* \
    && rm redis.tar.gz \
    && rm -r /usr/src/redis \
    && apt-get purge -y --auto-remove $buildDeps
```

以 **&&** 符号连接命令，这样执行后，只会创建 1 层镜像。

此外，还可以看到这一组命令的最后添加了清理工作的命令，删除了为了编译构建所需要的软件，清理了所有下载、展开的文件，并且还清理了 `apt` 缓存文件。这是很重要的一步，我们之前说过，镜像是多层存储，每一层的东西并不会在下一层被删除，会一直跟随着镜像。因此镜像构建时，一定要确保每一层只添加真正需要添加的东西，任何无关的东西都应该清理掉。

> Union FS 是有最大层数限制的，比如 AUFS，曾经是最大不得超过 42 层，现在是不得超过 127 层。

##### VOLUMN

格式为：

- `VOLUME ["<路径1>", "<路径2>"...]`
- `VOLUME <路径>`

为了防止运行时用户忘记将动态文件所保存目录挂载为卷，在 `Dockerfile` 中，我们可以事先指定某些目录挂载为匿名卷，这样在运行时如果用户不指定挂载，其应用也可以正常运行，不会向容器存储层写入大量数据。

```dockerfile
VOLUME /data
```

这里的 `/data` 目录就会在容器运行时自动挂载为匿名卷，任何向 `/data` 中写入的信息都不会记录进容器存储层，从而保证了容器存储层的无状态化。当然，运行容器时可以覆盖这个挂载设置。

```bash
$ docker run -d -v mydata:/data xxxx
```

##### ONBUILD

`ONBUILD` 是一个特殊的指令，它后面跟的是其它指令，比如 `RUN`, `COPY` 等，而这些指令，在当前镜像构建时并不会被执行。只有当以当前镜像为基础镜像，去构建下一级镜像的时候才会被执行。

例如，用 `ONBUILD` 写一下Node.js 项目基础镜像的 `Dockerfile`:

```dockerfile
FROM node:slim
RUN mkdir /app
WORKDIR /app
ONBUILD COPY ./package.json /app
ONBUILD RUN [ "npm", "install" ]
ONBUILD COPY . /app/
CMD [ "npm", "start" ]
```

各个项目的 `Dockerfile` 就变成了简单地：

```dockerfile
FROM my-node
```

当在各个项目目录中，用这个只有一行的 `Dockerfile` 构建镜像时，之前基础镜像的那三行 `ONBUILD` 就会开始执行，成功的将当前项目的代码复制进镜像、并且针对本项目执行 `npm install`，生成应用镜像。

##### 参考

- [Docker Dockerfile | 菜鸟教程](https://www.runoob.com/docker/docker-dockerfile.html)
- [dockerfile详解_紫色飞猪-CSDN博客_dockerfile](https://blog.csdn.net/zisefeizhu/article/details/83472190)

#### Dockerfile 多阶段构建

- [Dockerfile 多阶段构建 - Docker —— 从入门到实践](https://yeasy.gitbook.io/docker_practice/container)

### 多种系统架构支持的 Docker 镜像

使用镜像创建一个容器，该镜像必须与 Docker 宿主机系统架构一致，例如 `Linux x86_64` 架构的系统中只能使用 `Linux x86_64` 的镜像创建容器。

> Windows、macOS 除外，其使用了 [binfmt_misc](https://docs.docker.com/docker-for-mac/multi-arch/) 提供了多种架构支持，在 Windows、macOS 系统上 (x86_64) 可以运行 arm 等其他架构的镜像。

- [构建多种系统架构支持的 Docker 镜像 - Docker —— 从入门到实践](https://yeasy.gitbook.io/docker_practice/image/manifest)

## 数据管理

- [Docker数据卷挂载命令volume(-v)与mount的总结_Charles Shih 技术博客-CSDN博客](https://shichen.blog.csdn.net/article/details/106292036)
- [Use volumes | Docker Documentation](https://docs.docker.com/storage/volumes/#choose-the--v-or---mount-flag)
- [Use bind mounts | Docker Documentation](https://docs.docker.com/storage/bind-mounts/)

## 网络配置

### 如何进入 Docker 容器的网络命名空间？

答：Docker 在创建容器后，删除了宿主主机上 `/var/run/netns` 目录中的相关的网络命名空间文件。因此，在宿主主机上是无法看到或访问容器的网络命名空间的。

用户可以通过如下方法来手动恢复它。

首先，使用下面的命令查看容器进程信息，比如这里的 1234。

```
$ docker inspect --format='{{. State.Pid}} ' $container_id
1234
```

接下来，在 `/proc` 目录下，把对应的网络命名空间文件链接到 `/var/run/netns` 目录。

```
$ sudo ln -s /proc/1234/ns/net /var/run/netns/
```

然后，在宿主主机上就可以看到容器的网络命名空间信息。例如

```
$ sudo ip netns show
1234
```

此时，用户可以通过正常的系统命令来查看或操作容器的命名空间了。例如修改容器的 IP 地址信息为 `172.17.0.100/16`。

```
$ sudo ip netns exec 1234 ifconfig eth0 172.17.0.100/16
```

## Docker Compose

- [Docker Compose | 菜鸟教程](https://www.runoob.com/docker/docker-compose.html)

`Compose` 允许用户通过一个单独的 `docker-compose.yml` 模板文件（YAML 格式）来定义一组相关联的应用容器为一个项目（project）。

`Compose` 中有两个重要的概念：

- 服务 (`service`)：一个应用的容器，实际上可以包括若干运行相同镜像的容器实例。
- 项目 (`project`)：由一组关联的应用容器组成的一个完整业务单元，在 `docker-compose.yml` 文件中定义。

`Compose` 的默认管理对象是项目，通过子命令对项目中的一组容器进行便捷地生命周期管理。一个项目可以由多个服务（容器）关联而成。

`Compose` 项目由 Python 编写，实现上调用了 Docker 服务提供的 API 来对容器进行管理。因此，只要所操作的平台支持 Docker API，就可以在其上利用 `Compose` 来进行编排管理。

### 使用示例

最常见的项目是 web 网站。下面用 `Python` 来建立一个能够记录页面访问次数的 web 网站，该项目应该包含 web 应用和缓存。

**web应用**

新建文件夹，在该目录中编写 `app.py` 文件

```python
from flask import Flask
from redis import Redis

app = Flask(__name__)
redis = Redis(host='redis', port=6379)

@app.route('/')
def hello():
    count = redis.incr('hits')
    return 'Hello World! 该页面已被访问 {} 次。\n'.format(count)

if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
```

**Dockerfile**

编写 `Dockerfile` 文件，内容为

```dockerfile
FROM python:3.6-alpine
ADD . /code
WORKDIR /code
RUN pip install redis flask
CMD ["python", "app.py"]
```

**docker-compose.yml**

编写 `docker-compose.yml` 文件，这个是 Compose 使用的主模板文件。

```yml
version: '3'
services:

  web:
    build: .
    ports:
     - "5000:5000"

  redis:
    image: "redis:alpine"
```

**运行 compose 项目**

```
$ docker-compose up
```

此时访问本地 `5000` 端口，每次刷新页面，计数就会加 1。

### 命令说明

对于 Compose 来说，大部分命令的对象既可以是项目本身，也可以指定为项目中的服务或者容器。如果没有特别的说明，命令对象将是项目，这意味着项目中所有的服务都会受到命令影响。

- [命令说明 - Docker —— 从入门到实践](https://yeasy.gitbook.io/docker_practice/compose/install)

### Compose 模板文件

- [Compose 模板文件 - Docker —— 从入门到实践](https://yeasy.gitbook.io/docker_practice/compose/install)

## Swarm mode

Docker 1.12 [Swarm mode](https://docs.docker.com/engine/swarm/) 已经内嵌入 Docker 引擎，成为了 docker 子命令 `docker swarm`。请注意与旧的 `Docker Swarm` 区分开来。

`Swarm mode` 内置 kv 存储功能，提供了众多的新特性，比如：具有容错能力的去中心化设计、内置服务发现、负载均衡、路由网格、动态伸缩、滚动更新、安全传输等。使得 Docker 原生的 `Swarm` 集群具备与 Mesos、Kubernetes 竞争的实力。

### 概念

`Swarm` 是使用 [`SwarmKit`](https://github.com/docker/swarmkit/) 构建的 Docker 引擎内置（原生）的集群管理和编排工具。

### 节点

运行 Docker 的主机可以主动初始化一个 `Swarm` 集群或者加入一个已存在的 `Swarm` 集群，这样这个运行 Docker 的主机就成为一个 `Swarm` 集群的节点 (`node`) 。

节点分为管理 (`manager`) 节点和工作 (`worker`) 节点。

管理节点用于 `Swarm` 集群的管理，`docker swarm` 命令基本只能在管理节点执行（节点退出集群命令 `docker swarm leave` 可以在工作节点执行）。一个 `Swarm` 集群可以有多个管理节点，但只有一个管理节点可以成为 `leader`，`leader` 通过 `raft` 协议实现。

工作节点是任务执行节点，管理节点将服务 (`service`) 下发至工作节点执行。管理节点默认也作为工作节点。你也可以通过配置让服务只运行在管理节点。

来自 Docker 官网的这张图片形象的展示了集群中管理节点与工作节点的关系。

![img](https://gitee.com/nnnoh/image-hosting-service/raw/master/img/2021/swarm-diagram.png)

### 服务和任务

任务 （`Task`）是 `Swarm` 中的最小的调度单位，目前来说就是一个单一的容器。

服务 （`Services`） 是指一组任务的集合，服务定义了任务的属性。服务有两种模式：

- `replicated services` 按照一定规则在各个工作节点上运行指定个数的任务。
- `global services` 每个工作节点上运行一个任务

两种模式通过 `docker service create` 的 `--mode` 参数指定。

来自 Docker 官网的这张图片形象的展示了容器、任务、服务的关系。

![img](https://gitee.com/nnnoh/image-hosting-service/raw/master/img/2021/services-diagram.png)



## 参考

- [Docker--从入门到实践](https://yeasy.gitbook.io/docker_practice/)
- [Docker 入门教程 - 阮一峰的网络日志](http://www.ruanyifeng.com/blog/2018/02/docker-tutorial.html)

### About Docker Machine

- [Docker Machine is now in maintenance mode · Issue #4537 · docker/machine · GitHub](https://github.com/docker/machine/issues/4537)
- [Adding advisory warnings about docker-machine by hamiltont · Pull Request #9239 · docker/docker.github.io · GitHub](https://github.com/docker/docker.github.io/pull/9239)