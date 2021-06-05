## 账户与群组

每个登陆的使用者至少都会取得两个 ID ，一个是使用者 ID (User ID ，简称 UID)、一个是群组 ID (Group ID ，简称 GID)。

每一个文件都会有所谓的拥有者 ID 与拥有群组 ID ，当我们有要显示文件属性的需求时，系统会依据 /etc/passwd 与 /etc/group 的内容， 找到 UID / GID 对应的账号与组名再显示出来。

- /etc/passwd 账户 UID/GID 等重要参数
- /etc/shadow 口令相关数据
- /etc/group 群组信息
- /etc/gshadow 群组口令

### /etc/passwd

```bash
账号名称:口令:UID:GID:用户信息说明栏:home目录:shell
```

**口令**

早期 Unix 系统的口令就是放在这字段上！但是因为这个文件的特性是所有的程序都能够读取，这样一来很容易造成口令数据被窃取， 因此后来就将这个字段的口令数据给他改放到 /etc/shadow 中了。所以这里你会看到一个『 x 』

**UID**

| id 范围                | 该 ID 使用者特性                                             |
| ---------------------- | ------------------------------------------------------------ |
| 0 (系统管理员)         | 当 UID 是 0 时，代表这个账号是『系统管理员』！ 所以当你要让其他的账号名称也具有 root 的权限时，将该账号的 UID 改为 0 即可。 这也就是说，一部系统上面的系统管理员不见得只有 root 喔！ 不过，很不建议有多个账号的 UID 是 0 啦～ |
| 1~499 (系统账号)       | 保留给系统使用的 ID，其实除了 0 之外，其他的 UID 权限与特性并没有不一样。默认 500 以下的数字让给系统作为保留账号只是一个习惯。  由于系统上面启动的服务希望使用较小的权限去运行，因此不希望使用 root 的身份去运行这些服务， 所以我们就得要提供这些运行中程序的拥有者账号才行。这些系统账号通常是不可登陆的， 所以才会有我们在[第十一章](http://cn.linux.vbird.org/linux_basic/0320bash.php)提到的 /sbin/nologin 这个特殊的 shell 存在。  根据系统账号的由来，通常系统账号又约略被区分为两种： 1~99：由 distributions 自行创建的系统账号； 100~499：若用户有系统账号需求时，可以使用的账号 UID。 |
| 500~65535 (可登陆账号) | 给一般使用者用的。事实上，目前的 linux 核心 (2.6.x 版)已经可以支持到 4294967295 (2^32-1) 这么大的 UID 号码喔！ |

**Shell**

当用户登陆系统后就会取得一个 Shell 来与系统的核心沟通以进行用户的操作任务。

 /sbin/nologin 可以让账号无法登陆 shell 环境。一般设置这样的帐号是给启动服务的账号所用的，这只是让服务启动起来，但是不能登录系统。

如果想要让某个具有 /sbin/nologin 的用户知道，他们不能登陆主机时，可以新建 /etc/nologin.txt 这个文件，在文件内面写上不能登陆的原因，当用户登录时，屏幕上就会出现这个文件里面的内容。

`touch /etc/nologin` 禁止除root以外的用户登录。

### /etc/shadow

````bash
账号名称:口令:最近更动口令的日期:口令不可被更动的天数:口令需要重新变更的天数:口令需要变更期限前的警告天数:口令过期后的账号宽限时间(口令失效日):账号失效日期:保留
````

将口令字段清空， 再重新启动后相应账户将不用口令即可登陆。

### /etc/group

```bash
组名:群组口令[x]:GID:此群组支持的账号名称
```

**此群组支持的账号名称**
我们知道一个账号可以加入多个群组，那某个账号想要加入此群组时，将该账号填入这个字段即可，如，"root,dmtsai"。

![账号相关文件之间的 UID/GID 与口令相关性示意图](https://gitee.com/nnnoh/image-hosting-service/raw/master/img/2021/id_link.gif)

### /etc/gshadow

```bash
组名:口令:群组管理员的账号:该群组的所属账号
```

gshadow 最大的功能就是创建群组管理员。不过，由于目前有类似 `sudo`  之类的工具， 所以这个群组管理员的功能已经很少使用了。

- 口令：开头为 ! 表示该群组不具有群组管理员（无合法口令）。

### 有效群组与初始群组

**初始群组**（initial group），即 /etc/passwd 里面的第四栏的 GID，使用者一登陆就会主动取得，不需要在 /etc/group 的第四个字段写入该账号的。

非 initial group 的其他群组，即 /etc/group 的第四个字段包含使用者账户的群组。

`groups` 命令输出使用者所有支持的群组，而且， 第一个输出的群组即为**有效群组**（effective group）。

当创建一个新的文件或者是新的目录时，新文件的群组是使用者的有效群组。

`newgrp 群组名 `  命令用于变更有效群组，想要切换的群组必须是你已经有支持的群组。

这个命令可以变更目前用户的有效群组， 而且是另外以一个 shell 来提供这个功能的。

![newgrp 的运行示意图](https://gitee.com/nnnoh/image-hosting-service/raw/master/img/2021/newgrp.gif)

查看某群组下所有用户：

1. 该群组为次要群组的用户：

   ```bash
   $ cat /etc/group | grep gname
   ```

2. 该群组为初始群组（主要群组）的用户：

   ```bash
   awk   -F":"   '{print $1"\t\t"$4}'   /etc/passwd | grep '[^0-9]gid$'
   ```

## 账户管理（系统管理员）

### useradd

```bash
[root@www ~]# useradd [-u UID] [-g 初始群组] [-G 次要群组] [-mM]\
>  [-c 说明栏] [-d 家目录绝对路径] [-s shell] 使用者账号名
选项与参数：
-u  ：后面接的是 UID ，是一组数字。直接指定一个特定的 UID 给这个账号；
-g  ：后面接的那个组名就是我们上面提到的 initial group 啦～
      该群组的 GID 会被放置到 /etc/passwd 的第四个字段内。
-G  ：后面接的组名则是这个账号还可以加入的群组。
      这个选项与参数会修改 /etc/group 内的相关数据喔！
-M  ：强制！不要创建用户家目录！(系统账号默认值)
-m  ：强制！要创建用户家目录！(一般账号默认值)
-c  ：这个就是 /etc/passwd 的第五栏的说明内容啦～可以随便我们配置的啦～
-d  ：指定某个目录成为家目录，而不要使用默认值。务必使用绝对路径！
-r  ：创建一个系统的账号，这个账号的 UID 会有限制 (参考 /etc/login.defs)
-s  ：后面接一个 shell ，若没有指定则默认是 /bin/bash 的啦～
-e  ：后面接一个日期，格式为『YYYY-MM-DD』此项目可写入 shadow 第八字段，
      亦即账号失效日的配置项目啰；
-f  ：后面接 shadow 的第七字段项目，指定口令是否会失效。0为立刻失效，
      -1 为永远不失效(口令只会过期而强制于登陆时重新配置而已。)

范例一：完全参考默认值创建一个用户，名称为 vbird1
[root@www ~]# useradd vbird1
[root@www ~]# ll -d /home/vbird1
drwx------ 4 vbird1 vbird1 4096 Feb 25 09:38 /home/vbird1
# 默认会创建用户家目录，且权限为 700 ！这是重点！

[root@www ~]# grep vbird1 /etc/passwd /etc/shadow /etc/group
/etc/passwd:vbird1:x:504:505::/home/vbird1:/bin/bash
/etc/shadow:vbird1:!!:14300:0:99999:7:::
/etc/group:vbird1:x:505:    <==默认会创建一个与账号一模一样的群组名
```

CentOS 这些默认值主要会帮我们处理几个项目：

- 在 /etc/passwd 里面创建一行与账号相关的数据，包括创建 UID/GID/家目录等；
- 在 /etc/shadow 里面将此账号的口令相关参数填入，但是尚未有口令；
  - 需要使用『 passwd 账号 』来设置口令。
- 在 /etc/group 里面加入一个与账号名称一模一样的组名；
  - 如果了指定一个已经存在的群组作为使用者的初始群组，则不会主动的创建与账号同名的群组。
- 在 /home 底下创建一个与账号同名的目录作为用户家目录，且权限为 700
  - 系统账号（-r 选项）主要是用来进行运行系统所需服务的权限配置， 所以系统账号默认都不会主动创建家目录的。

#### useradd 参考档

`useradd -D` 可查看 useradd 的默认值，这些数据存储在 /etc/default/useradd。

```bash
[root@www ~]# useradd -D
GROUP=100		<==默认的群组
HOME=/home		<==默认的家目录所在目录
INACTIVE=-1		<==口令失效日，在 shadow 内的第 7 栏
EXPIRE=			<==账号失效日，在 shadow 内的第 8 栏
SHELL=/bin/bash		<==默认的 shell
SKEL=/etc/skel		<==用户家目录的内容数据参考目录
CREATE_MAIL_SPOOL=yes   <==是否主动帮使用者创建邮件信箱(mailbox)
```

针对群组的角度有两种不同的机制， 这两种机制分别是：

- 私有群组机制：系统会创建一个与账号一样的群组给使用者作为初始群组。 这种群组的配置机制会比较有保密性，这是因为使用者都有自己的群组，而且家目录权限将会配置为 700 (仅有自己可进入自己的家目录) 之故。使用这种机制将不会参考 GROUP=100 这个配置值。代表性的 distributions 有 RHEL, Fedora, CentOS 等；
- 公共群组机制：就是以 GROUP=100 这个配置值作为新建账号的初始群组，因此每个账号都属于 users 这个群组， 且默认家目录通常的权限会是『 drwxr-xr-x ... username users ... 』，由于每个账号都属于 users 群组，因此大家都可以互相分享家目录内的数据之故。代表 distributions 如 SuSE等。

此外，UID/GID 还有口令参数参考的是 /etc/login.defs 文件。

```bash
MAIL_DIR        /var/spool/mail	<==用户默认邮件信箱放置目录

PASS_MAX_DAYS   99999	<==/etc/shadow 内的第 5 栏，多久需变更口令日数
PASS_MIN_DAYS   0	<==/etc/shadow 内的第 4 栏，多久不可重新配置口令日数
PASS_MIN_LEN    5	<==口令最短的字符长度，已被 pam 模块取代，失去效用！
PASS_WARN_AGE   7	<==/etc/shadow 内的第 6 栏，过期前会警告的日数

UID_MIN         500	<==使用者最小的 UID，意即小于 500 的 UID 为系统保留
UID_MAX       60000	<==使用者能够用的最大 UID
GID_MIN         500	<==使用者自定义组的最小 GID，小于 500 为系统保留
GID_MAX       60000	<==使用者自定义组的最大 GID

CREATE_HOME     yes	<==在不加 -M 及 -m 时，是否主动创建用户家目录？
UMASK           077     <==用户家目录创建的 umask ，因此权限会是 700
USERGROUPS_ENAB yes     <==使用 userdel 删除时，是否会删除初始群组
MD5_CRYPT_ENAB yes      <==口令是否经过 MD5 的加密机制处理
```

### passwd

```bash
[root@www ~]# passwd [--stdin]  <==所有人均可使用来改自己的口令
[root@www ~]# passwd [-l] [-u] [--stdin] [-S] \
>  [-n 日数] [-x 日数] [-w 日数] [-i 日期] 账号 <==root 功能
选项与参数：
--stdin ：可以透过来自前一个管线的数据，作为口令输入，对 shell script 有帮助！
-l  ：是 Lock 的意思，会将 /etc/shadow 第二栏最前面加上 ! 使口令失效；
-u  ：与 -l 相对，是 Unlock 的意思！
-S  ：列出口令相关参数，亦即 shadow 文件内的大部分信息。
-n  ：后面接天数，shadow 的第 4 字段，多久不可修改口令天数
-x  ：后面接天数，shadow 的第 5 字段，多久内必须要更动口令
-w  ：后面接天数，shadow 的第 6 字段，口令过期前的警告天数
-i  ：后面接『日期』，shadow 的第 7 字段，口令失效日期
```

- 『 passwd 』修改自己的口令
- 『 passwd 账号 』修改指定账户的口令

root 用户使用 passwd 命令修改 root 或其他账户的口令时，无需先输入账号的旧密码，并且 root 配置口令无限制，如果口令不符合要求，系统虽然会提示 Bad password，但通常还是会接受。

口令的规范是非常严格的，尤其新的 distributions 大多使用 PAM 模块来进行口令的检验，包括太短、 口令与账号相同、口令为字典常见字符串等，都会被 PAM 模块检查出来而拒绝修改口令。

PAM 模块管理的机制写在 /etc/pam.d/passwd 当中。而该文件与口令有关的测试模块就是使用：pam_cracklib.so，这个模块会检验口令相关的信息， 并且取代 /etc/login.defs 内的 PASS_MIN_LEN 的配置。

```bash
范例：让 vbird2 的账号失效，观察完毕后再让她失效
[root@www ~]# passwd -l vbird2
[root@www ~]# passwd -S vbird2
vbird2 LK 2009-02-26 0 60 7 10 (Password locked.)
# 嘿嘿！状态变成『 LK, Lock 』了啦！无法登陆喔！
[root@www ~]# grep vbird2 /etc/shadow
vbird2:!!$1$50MnwNFq$oChX.0TPanCq7ecE4HYEi.:14301:0:60:7:10::
# 其实只是在这里加上 !! 而已！
```

### chage

除了使用 passwd -S 之外，chage 可以显示更详细的口令参数。

```bash
[root@www ~]# chage [-ldEImMW] 账号名
选项与参数：
-l ：列出该账号的详细口令参数；
-d ：后面接日期，修改 shadow 第三字段(最近一次更改口令的日期)，格式 YYYY-MM-DD
-E ：后面接日期，修改 shadow 第八字段(账号失效日)，格式 YYYY-MM-DD
-I ：后面接天数，修改 shadow 第七字段(口令失效日期)
-m ：后面接天数，修改 shadow 第四字段(口令最短保留天数)
-M ：后面接天数，修改 shadow 第五字段(口令多久需要进行变更)
-W ：后面接天数，修改 shadow 第六字段(口令过期前警告日期)

范例一：列出 vbird2 的详细口令参数
[root@www ~]# chage -l vbird2
Last password change                               : Feb 26, 2009
Password expires                                   : Apr 27, 2009
Password inactive                                  : May 07, 2009
Account expires                                    : never
Minimum number of days between password change     : 0
Maximum number of days between password change     : 60
Number of days of warning before password expires  : 7
```

```bash
范例二：创建一个名为 agetest 的账号，该账号第一次登陆后使用默认口令，
       但必须要更改过口令后，使用新口令才能够登陆系统使用 bash 环境
[root@www ~]# useradd agetest
[root@www ~]# echo "agetest" | passwd --stdin agetest
[root@www ~]# chage -d 0 agetest
# 此时此账号的口令创建时间会被改为 1970/1/1 ，所以会有问题！

# 尝试以 agetest 登陆的情况
You are required to change your password immediately (root enforced)
WARNING: Your password has expired.
You must change your password now and login again!
Changing password for user agetest.
Changing password for agetest
(current) UNIX password:  <==这个账号被强制要求必须要改口令！
```

### usermod

在使用 useradd 后，发现某些地方还可以进行细部修改。 此时，当然我们可以直接到 /etc/passwd 或 /etc/shadow 去修改相对应字段的数据， 不过，Linux 也有提供 usermod 命令让大家来进行账号相关数据的微调。

```bash
[root@www ~]# usermod [-cdegGlsuLU] username
选项与参数：
-c  ：后面接账号的说明，即 /etc/passwd 第五栏的说明栏，可以加入一些账号的说明。
-d  ：后面接账号的家目录，即修改 /etc/passwd 的第六栏；
-e  ：后面接日期，格式是 YYYY-MM-DD 也就是在 /etc/shadow 内的第八个字段数据啦！
-f  ：后面接天数，为 shadow 的第七字段。
-g  ：后面接初始群组，修改 /etc/passwd 的第四个字段，亦即是 GID 的字段！
-G  ：后面接次要群组，修改这个使用者能够支持的群组，修改的是 /etc/group 啰～
-a  ：与 -G 合用，可『添加次要群组的支持』而非『配置』喔！
-l  ：后面接账号名称。亦即是修改账号名称， /etc/passwd 的第一栏！
-s  ：后面接 Shell 的实际文件，例如 /bin/bash 或 /bin/csh 等等。
-u  ：后面接 UID 数字啦！即 /etc/passwd 第三栏的数据；
-L  ：暂时将用户的口令冻结，让他无法登陆。其实仅改 /etc/shadow 的口令栏。
-U  ：将 /etc/shadow 口令栏的 ! 拿掉，解冻啦！
```

### userdel

userdel 目的在删除用户的相关数据，而用户的数据有：

- 用户账号/口令相关参数：/etc/passwd, /etc/shadow
- 使用者群组相关参数：/etc/group, /etc/gshadow
- 用户个人文件数据： /home/username, /var/spool/mail/username..

```bash
[root@www ~]# userdel [-r] username
选项与参数：
-r  ：连同用户的家目录也一起删除

范例一：删除 vbird2 ，连同家目录一起删除
[root@www ~]# userdel -r vbird2
```

如果该账号只是『暂时不激活』的话，那么将 /etc/shadow 里头账号失效日期 (第八字段) 配置为 0 就可以让该账号无法使用，但是所有跟该账号相关的数据都会留下来。

如果想要完整的将某个账号完整的移除，最好可以在下达 userdel -r username 之前， 先以『 find / -user username 』查出整个系统内属于 username 的文件，然后再加以删除。

## 账户管理（用户功能）

useradd/usermod/userdel 都是系统管理员所能够使用的命令， 如果是一般身份使用者，那么一般使用下面的账号数据变更与查询命令。

### finger

finger 可以查阅用户相关的信息。

```bash
[root@www ~]# finger [-s] username
选项与参数：
-s  ：仅列出用户的账号、全名、终端机代号与登陆时间等等；
-m  ：列出与后面接的账号相同者，而不是利用部分比对 (包括全名部分)

范例一：观察 vbird1 的用户相关账号属性
[root@www ~]# finger vbird1
Login: vbird1                           Name: (null)
Directory: /home/vbird1                 Shell: /bin/bash
Never logged in.
No mail.
No Plan.

范例二：找出目前在系统上面登陆的用户与登陆时间
[vbird1@www ~]$ finger
Login     Name       Tty      Idle  Login Time   Office     Office Phone
root      root       tty1           Feb 26 09:53
vbird1               tty2           Feb 26 15:21
```

- Login：为使用者账号，亦即 /etc/passwd 内的第一字段；
- Name：为全名，亦即 /etc/passwd 内的第五字段(或称为批注)；
- Directory：就是家目录了；
- Shell：就是使用的 Shell 文件所在；
- Never logged in.：figner 还会调查用户登陆主机的情况喔！
- No mail.：调查 /var/spool/mail 当中的信箱数据；
- No Plan.：调查 ~vbird1/.plan 文件，并将该文件取出来说明！

### chfn

chfn 设置账户『个人属性』数据。

```bash
[root@www ~]# chfn [-foph] [账号名]
选项与参数：
-f  ：后面接完整的大名；
-o  ：您办公室的房间号码；
-p  ：办公室的电话号码；
-h  ：家里的电话号码！

范例一：vbird1 自己更改一下自己的相关信息！
[vbird1@www ~]$ chfn
Changing finger information for vbird1.
Password:                        <==确认身份，所以输入自己的口令
Name []: VBird Tsai test         <==输入你想要呈现的全名
Office []: Dic in Ksu. Tainan    <==办公室号码
Office Phone []: 06-2727175#356  <==办公室电话
Home Phone []: 06-1234567        <==家里电话号码

Finger information changed.
[vbird1@www ~]$ grep vbird1 /etc/passwd
vbird1:x:504:505:VBird Tsai test,Dic in Ksu. Tainan,06-2727175#356,06-1234567:
/home/vbird1:/bin/bash
# 其实就是改到第五个字段，该字段里面用多个『 , 』分隔就是了！
```

### chsh

chsh，change shell 的简写。不论是 chfn 与 chsh ，都是能够让一般用户修改 /etc/passwd 这个系统文件。

```bash

[vbird1@www ~]$ chsh [-ls]
选项与参数：
-l  ：列出目前系统上面可用的 shell ，其实就是 /etc/shells 的内容！
-s  ：配置修改自己的 Shell 啰

范例一：用 vbird1 的身份列出系统上所有合法的 shell，并且指定 csh 为自己的 shell
[vbird1@www ~]$ chsh -l
/bin/sh
/bin/bash
/sbin/nologin  <==所谓：合法不可登陆的 Shell 就是这玩意！
/bin/tcsh
/bin/csh       <==这就是 C shell 啦！
/bin/ksh
# 其实上面的信息就是我们在 bash 中谈到的 /etc/shells 啦！

[vbird1@www ~]$ chsh -s /bin/csh; grep vbird1 /etc/passwd
Changing shell for vbird1.
Password:  <==确认身份，请输入 vbird1 的口令
Shell changed.
vbird1:x:504:505:VBird Tsai test,Dic in Ksu. Tainan,06-2727175#356,06-1234567:
/home/vbird1:/bin/csh
```

### id

id 这个命令则可以查询某人或自己的相关 UID/GID 等等的信息

```bash

[root@www ~]# id [username]

范例一：查阅 root 自己的相关 ID 信息！
[root@www ~]# id
uid=0(root) gid=0(root) groups=0(root),1(bin),2(daemon),3(sys),4(adm),6(disk),
10(wheel) context=root:system_r:unconfined_t:SystemLow-SystemHigh
# 上面信息其实是同一行的数据！包括会显示 UID/GID 以及支持的所有群组！
# 至于后面那个 context=... 则是 SELinux 的内容，先不要理会他！

[root@www ~]# id vbird100
id: vbird100: No such user  <== id 这个命令也可以用来判断系统上面有无某账号！
```

## 群组管理

 基本上，群组的内容都与这两个文件有关：/etc/group, /etc/gshadow。 群组管理，都是上面两个文件的新增、修改与移除。此外，有效群组的概念涉及 newgrp 与 gpasswd 命令。

### groupadd

```bash
[root@www ~]# groupadd [-g gid] [-r] 组名
选项与参数：
-g  ：后面接某个特定的 GID ，用来直接给予某个 GID ～
-r  ：创建系统群组啦！与 /etc/login.defs 内的 GID_MIN 有关。

范例一：新建一个群组，名称为 group1
[root@www ~]# groupadd group1
[root@www ~]# grep group1 /etc/group /etc/gshadow
/etc/group:group1:x:702:
/etc/gshadow:group1:!::
# 群组的 GID 也是会由 500 以上最大 GID+1 来决定！
```

### groupmod

```bash

[root@www ~]# groupmod [-g gid] [-n group_name] 群组名
选项与参数：
-g  ：修改既有的 GID 数字；
-n  ：修改既有的组名

范例一：将刚刚上个命令创建的 group1 名称改为 mygroup ， GID 为 201
[root@www ~]# groupmod -g 201 -n mygroup group1
[root@www ~]# grep mygroup /etc/group /etc/gshadow
/etc/group:mygroup:x:201:
/etc/gshadow:mygroup:!::
```

不要随意的更动 GID ，容易造成系统资源的错乱。

### groupdel

```bash
[root@www ~]# groupdel [groupname]

范例一：将刚刚的 mygroup 删除！
[root@www ~]# groupdel mygroup

范例二：若要删除 vbird1 这个群组的话？
[root@www ~]# groupdel vbird1
groupdel: cannot remove user's primary group.
```

如果要删除某个群组，必须要确认 /etc/passwd 内的账号没有任何人使用该群组作为 initial group。如果存在这样的账号，则需要

- 修改 vbird1 的 GID ，或者是：
- 删除 vbird1 这个使用者。

### gpasswd

gpasswd 用于设置群组管理员，管理哪些账号可以加入/移出该群组。

```bash
# 关于系统管理员(root)做的动作：
[root@www ~]# gpasswd groupname
[root@www ~]# gpasswd [-A user1,...] [-M user3,...] groupname
[root@www ~]# gpasswd [-rR] groupname
选项与参数：
    ：若没有任何参数时，表示给予 groupname 一个口令(/etc/gshadow)
-A  ：将 groupname 的主控权交由后面的使用者管理(该群组的管理员)
-M  ：将某些账号加入这个群组当中！
-r  ：将 groupname 的口令移除
-R  ：让 groupname 的口令栏失效

# 关于群组管理员(Group administrator)做的动作：
[someone@www ~]$ gpasswd [-ad] user groupname
选项与参数：
-a  ：将某位使用者加入到 groupname 这个群组当中！
-d  ：将某位使用者移除出 groupname 这个群组当中。

范例一：创建一个新群组，名称为 testgroup 且群组交由 vbird1 管理：
[root@www ~]# groupadd testgroup  <==先创建群组
[root@www ~]# gpasswd testgroup   <==给这个群组一个口令吧！
Changing the password for group testgroup
New Password:
Re-enter new password:
# 输入两次口令就对了！
[root@www ~]# gpasswd -A vbird1 testgroup  <==加入群组管理员为 vbird1
[root@www ~]# grep testgroup /etc/group /etc/gshadow
/etc/group:testgroup:x:702:
/etc/gshadow:testgroup:$1$I5ukIY1.$o5fmW.cOsc8.K.FHAFLWg0:vbird1:
# 很有趣吧！此时 vbird1 则拥有 testgroup 的主控权喔！身份有点像板主啦！

范例二：以 vbird1 登陆系统，并且让他加入 vbird1, vbird3 成为 testgroup 成员
[vbird1@www ~]$ id
uid=504(vbird1) gid=505(vbird1) groups=505(vbird1) ....
# 看得出来，vbird1 尚未加入 testgroup 群组喔！

[vbird1@www ~]$ gpasswd -a vbird1 testgroup
[vbird1@www ~]$ gpasswd -a vbird3 testgroup
[vbird1@www ~]$ grep testgroup /etc/group
testgroup:x:702:vbird1,vbird3
```

