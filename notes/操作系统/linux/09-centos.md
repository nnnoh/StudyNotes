## Installation

### 运行级别

> CentOS minimal 没有桌面程序，正常启动应该是以等级3来运行。

**Linux下有7个运行级别：**
0	系统停机模式，系统默认运行级别不能设置为0，否则不能正常启动，机器关闭。
1	单用户模式，root权限，用于系统维护，禁止远程登陆，就像Windows下的安全模式登录
2	多用户模式，没有NFS网络支持
3	完整的多用户文本模式，有NFS，登陆后进入控制台命令行模式
4	系统未使用，保留一般不用，在一些特殊情况下可以用它来做一些事情。例如在笔记本电脑的电池用尽时，可以切换到这个模式来做一些设置
5	图形化模式，登陆后进入图形GUI模式
6	重启模式，默认运行级别不能设为6，否则不能正常启动。运行init 6机器就会重启

**运行级别的切换：**

**init** +想要切换的运行级别

**查看运行级别：**

**who -r**	（返回当前运行级别、切换时间、上一次的运行级别）

**runlevel**	（返回的第一个值是前一运行级别，第二个值为当前运行级别）

### 网络配置

> q: connect: Network is unreachable.
>
> a:配置参数
>
> - BOOTPROTO选项设置为dhcp（动态获取）。如果想采用指定ip，则应该设置为static，另外IPADDR/NETMASK/BROADCAST几个参数也需要指定对应值。
> - 设置HWADDR属性为网卡MAC地址。
> - ONBOOT 设置为yes。
> 

**网络配置文件**：/etc/sysconfig/network-script/ifcfg- (tab)

> 网卡名设为eth0 ，这样更符合习惯
>
> 修改方式：
>
> 1. 重命名配置文件为 ifcfg-eth0
> 2. 修改NAME [和DEVICE]字段为eth0
> 3. 确认MAC地址（ip addr 或 ifconfig 查看）无误
> 4. 编辑/etc/default/grup文件，在GRUB_CMDLINE_LINUX=""项中，插入"net.ifnames=0 biosdevname=0"（与原有项之间用空格隔开），保存退出。
> 5. 调用命令grub2-mkconfig -o /boot/grub2/grub.cfg更新配置。
> 6. reboot。

**文件参数**

DEVICE     接口名（设备,网卡）
USERCTL    [yes|no]（非root用户是否可以控制该设备）
BOOTPROTO  IP的配置方法[none|static|bootp|dhcp]（引导时不使用协议|静态分配IP|BOOTP协议|DHCP协议）
HWADDR     MAC地址 
ONBOOT     系统启动的时候网络接口是否有效（yes/no） 
TYPE       网络类型（通常是Ethemet） 
NETMASK    网络掩码 
IPADDR     IP地址 
IPV6INIT   IPV6是否有效（yes/no） 
GATEWAY    默认网关IP地址
BROADCAST  广播地址
NETWORK    网络地址

## 服务

### ssh

**开启ssh服务**：

1. 检查有没有安装ssh服务：rpm -qa | grep ssh；如果没有安装ssh服务就安装 ： yum install openssh-server

2. 安装好后在ssh配置文件里进行配置 : vim /etc/ssh/sshd_config

   Port 22

   PermitRootLogin yes   # 或 no

3.  /bin/systemctl start sshd.service 开启ssh服务

4. 开启后用 ps -e | grep sshd 检查一下ssh服务是否开启

5. 用netstat -an | grep 22检查一下22端口是否开启

6. 将ssh服务添加到自启动列表中：systemctl enable sshd.service

### iptables

- [CentOS7安装iptables防火墙 - 太清 - 博客园](https://www.cnblogs.com/kreo/p/4368811.html)

## 软件包

#### kernel-devel

如果某个程序需要内核提供的一些功能，它就需要内核的 C header 来编译程序，这个时候 linux-devel 里面的东西就用上了。

比如 nvidia 和 ati 的官方显卡驱动，alsa-driver 声卡驱动，他们都需要编译一个放在内核里面运行的模块，编译这个模块就需要内核的 header 文件才能顺利编译。

当然，kernel-devel 不光是 C Header 文件，它还有内核的配置文件，以及其他的开发用的资料  

**kernel devel和kernel source的区别**

- kernel-devel包只包含用于内核开发环境所需的内核头文件以及Makefile，而kernel-souce包含所有内核源代码。

- 如果仅仅是用于你自己编写的模块开发的话，因为只需引用相应的内核头文件，所以只有devel包即可，如果你要修改现有的内核源代码并重新编译，那必须是kernel-souce。

- kernel-devel是用做内核一般开发的，比如编写内核模块，原则上，可以不需要内核的原代码。

- kernel则是专指内核本身的开发，因此需要内核的原代码。
- kernel-souce在RH某些版本之后不再附带在发行版中了，必须自己通过kernel-XXX.src.rpm做出来。

- 关于kernel source的有kernel和kernel-devel两个rpm，其中kernel rpm包含源文件和头文件（就像2.4下的kernel-source rpm），而kernel-devel则主要是头文件。

## 网络

### 相关文件

/proc/net/dev

/sys/devices/virtual/net/
