# Install

CentOS 安装 node：

1. 安装gcc

   ```bash
   yum install gcc gcc-c++
   ```

2. 下载node国内镜像（推荐）

   ```bash
   cd /usr/local
   wget https://npm.taobao.org/mirrors/node/latest-v14.x/node-v14.6.0-linux-x64.tar.gz
   ```

3. 解压并重命名文件夹

   ```bash
   tar -xvf  node-v14.6.0-linux-x64.tar.gz
   mv node-v10.14.1node-v14.6.0-linux-x64 node
   ```

4. 添加环境变量

   ```bash
   vi /etc/profile
   ```

   在文件最后添加以下配置：

   ```shell
   export NODE_HOME=/usr/local/node  
   export PATH=$NODE_HOME/bin:$PATH
   ```

5. 刷新配置

   ```bash
   source /etc/profile
   ```

6. 验证结果：

   ```bash
   node -v
   npm -v
   ```

npm阿里云镜像源加速

```bash
npm config set registry "https://registry.npm.taobao.org"
```

验证npm设置阿里云源是否设置成功

```bash
npm config get registry
```

### 依赖安装失败

**安装electron在node install.js卡住**

方法一：在安装的时候直接加上淘宝镜像

```bash
npm install electron@1.4.15 --save-dev --registry=http://registry.npm.taobao.org
```

方法二：配置 .npmrc 文件

windows在`C:\Users\username\`目录下，linux在`~\`目录下
新建 `.npmrc` 文件，复制内容如下：

```bash
registry=https://registry.npm.taobao.org/
electron_mirror="https://npm.taobao.org/mirrors/electron/"
```


方法三：下载安装包

1. 下载所需要的安装包：
   下载链接：https://npm.taobao.org/mirrors/electron

2. 在node_modules\electron\下创建dist文件夹。

3. 将下载的压缩包解压进刚刚创建的dist目录。

4. 在node_modules\electron\中创建path.txt，内容为electron.exe。

## cnpm

```cmd
npm install -g cnpm --registry=https://registry.npm.taobao.org
```

## Version Manager

- [nvm-windows](https://github.com/coreybutler/nvm-windows)



