# Git

https://blog.csdn.net/stone_yw/article/details/80795669#commentBox

## Tips

### 添加空目录

git 不能提交一个空的文件夹，因此可以在空目录下创建 .gitkeep 文件或 README 文件。

eclipse 会自动忽略了后缀为gitkepp的文件。

.gitkeep 中可添加如下内容。

```
# Ignore everything in this directory 
* 
# Except this file 
!.gitkeep 
```



## Question

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