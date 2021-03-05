## IDAE

#### 设置鼠标放到类或方法上自动显示文档

File -> Settings -> Editor -> General

勾选 Other -> show quick documentation...

#### 自定义代码折叠

```java
// <editor-fold defaultstate="collapsed" desc="$description$">
$SELECTION$
// </editor-fold>
```

#### 查看类继承关系

顶部菜单栏 Navigate -> Type Hierachy

#### 查看使用

右键菜单 -> Find Usage

#### 代码格式化

Code -> Reformat Code

快捷键：Ctrl + Alt + L

#### 默认设置

File -> Other settings -> Settings for New Projects...

#### IDEA远程debug

[IDEA远程debug](https://blog.csdn.net/qq_37192800/article/details/80761643)

### Question

#### maven项目不识别

指向 pom.xml，右键菜单 add as maven

## Office

光标重置：[ Ctrl + Home ]

修订文档：审阅 -> 修订

Excel 设置的小数点格式只影响显示，而不影响本身的值（进行运算时使用的还是原来的值）。

## Windows

默认WIndows下文件夹和文件不区分大小写（活久知Orz）

win10四月更新后可以通过命令激活文件名区分大小写。

#### CMD快捷打开

[Win10 Shift+右键菜单打开Cmd窗口](https://www.jianshu.com/p/aada247d22ed)

### .bat

```cmd
@echo off
pause
```

### 常用命令

#### 查看端口占用

1. 按下`Win+R`调出命令行窗口，输入`netstat -aon|findstr "8081"`，找到指定行最后一列的数字（PID），我们这里是9548.
2. 输入`tasklist|findstr "9548"`，发现是`javaw.exe`占用了8081端口。
3. 再次输入`taskkill /f /t /im 9548`结束该进程。

#### 计算文件MD5值

```cmd
certutil -hashfile filename MD5  
certutil -hashfile filename SHA1  
certutil -hashfile filename SHA256 
```

> 压缩软件也可查看文件MD5值

