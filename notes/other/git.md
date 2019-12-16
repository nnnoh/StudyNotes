## Git

Git 与 SVN 区别点：

1. Git 是分布式的，SVN 不是：这是 Git 和其它非分布式的版本控制系统，例如 SVN，CVS 等，最核心的区别。

2. Git 把内容按元数据方式存储，而 SVN 是按文件：所有的资源控制系统都是把文件的元信息隐藏在一个类似 .svn， .cvs 等的文件夹里。

3. Git 分支和 SVN 的分支不同：分支在 SVN 中一点都不特别，其实它就是版本库中的另外一个目录。

4. Git 没有一个全局的版本号，而 SVN 有：目前为止这是跟 SVN 相比 Git 缺少的最大的一个特征。

5. Git 的内容完整性要优于 SVN：Git 的内容存储使用的是 SHA-1 哈希算法。这能确保代码内容的完整性，确保在遇到磁盘故障和网络问题时降低对版本库的破坏。

### 基本概念



[图解Git](http://marklodato.github.io/visual-git-guide/index-zh-cn.html)

https://blog.csdn.net/stone_yw/article/details/80795669#commentBox

### 修改管理

### 远程仓库

#### 添加远程仓库

`git remote add (shortname) (url)`

将当前仓库连接到某个远程服务器。可以指定一个简单的名字，以便将来引用。

`git remote`

查看当前配置有哪些远程仓库。

-v 参数，可以看到每个别名的实际链接地址。

#### 从远程仓库克隆

Git支持多种协议，默认的`git://`使用ssh，但也可以使用`https`等其他协议。

使用`https`除了速度慢以外，还有个最大的麻烦是每次推送都必须输入口令，但是在某些只开放http端口的公司内部就无法使用`ssh`协议而只能用`https`。

### 分支管理

`git branch [branchname]`

没有参数时，**git branch** 会列出你在本地的分支。

执行 **git init** 的时候，默认情况下 Git 就会为你创建 **master** 分支。（新建的空分支，git branch无显示）

带分支名参数时，创建新分支。

`git branch -d (branchname)` 删除分支

`git checkout (branchname)`

切换分支，Git 会用该分支的最后提交的快照替换你的工作目录的内容。

`git checkout -b (branchname)` 命令创建新分支并立即切换到该分支。

`git merge [branchname] `

合并分支到当前分支。

### 历史记录

`git log`

列出历史提交记录。

--oneline 选项查看历史记录的简洁的版本。

--graph 选项查看历史中什么时候出现了分支、合并。

--reverse 选项来逆向显示所有日志。

--author=xxx 查看指定用户的提交日志

--since / --before , --until / --after ={} 指定时间

--decorate 查看标签

git reflog

列出命令使用记录

### 标签

### Tip

#### 添加空目录

git 不能提交一个空的文件夹，因此可以在空目录下创建 .gitkeep 文件或 README 文件。

eclipse 会自动忽略了后缀为gitkepp的文件。

.gitkeep 中可添加如下内容。

```
# Ignore everything in this directory 
* 
# Except this file 
!.gitkeep 
```

### Question

**Q1:**

warning: LF will be replaced by CRLF in database.md.
The file will have its original line endings in your working directory.

**Q2:**

 ! [rejected]        master -> master (fetch first)
error: failed to push some refs to 'git@github.com:nnnoh/StudyNotes.git'
hint: Updates were rejected because the remote contains work that you do
hint: not have locally. This is usually caused by another repository pushing
hint: to the same ref. You may want to first integrate the remote changes
hint: (e.g., 'git pull ...') before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.