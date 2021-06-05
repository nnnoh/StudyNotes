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

#### 多行编辑

**快捷键**

- 在 Settings | Keymap 设置 Clone Caret Above 和 Clone Caret Below 的快捷键。

**多处选中**

- 按住**alt+shift**，然后用鼠标左键点击需要编辑处，可以同时编辑多处。

**选中多个矩形** 

- 按住**alt+鼠标左键**即可实现选中多行编辑；
- 多行选中状态下，按键盘**左右键**可以左右移动光标。

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

### CMD快捷打开

[Win10 Shift+右键菜单打开Cmd窗口](https://www.jianshu.com/p/aada247d22ed)

### .bat

```cmd
@echo off
pause
```

#### cmd 乱码

`chcp 65001` 修改窗口文字编码格式为 utf-8

> jar 包运行时设置显示日志的编码格式：`-Dfile.encoding=utf-8`

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

#### 查找文件中的指定字符串

##### findstr

```bash
# findstr  /?
在文件中寻找字符串。

FINDSTR [/B] [/E] [/L] [/R] [/S] [/I] [/X] [/V] [/N] [/M] [/O] [/P] [/F:file]
        [/C:string] [/G:file] [/D:dir list] [/A:color attributes] [/OFF[LINE]]
        strings [[drive:][path]filename[ ...]]

  /B         在一行的开始配对模式。
  /E         在一行的结尾配对模式。
  /L         按字使用搜索字符串。
  /R         将搜索字符串作为一般表达式使用。
  /S         在当前目录和所有子目录中搜索匹配文件。
  /I         指定搜索不分大小写。
  /X         打印完全匹配的行。
  /V         只打印不包含匹配的行。
  /N         在匹配的每行前打印行数。
  /M         如果文件含有匹配项，只打印其文件名。
  /O         在每个匹配行前打印字符偏移量。
  /P         忽略有不可打印字符的文件。
  /OFF[LINE] 不跳过带有脱机属性集的文件。
  /A:attr    指定有十六进位数字的颜色属性。请见 "color /?"
  /F:file    从指定文件读文件列表 (/ 代表控制台)。
  /C:string  使用指定字符串作为文字搜索字符串。
  /G:file    从指定的文件获得搜索字符串。 (/ 代表控制台)。
  /D:dir     查找以分号为分隔符的目录列表
  strings    要查找的文字。
  [drive:][path]filename
             指定要查找的文件。

除非参数有 /C 前缀，请使用空格隔开搜索字符串。
例如: 'FINDSTR "hello there" x.y' 在文件 x.y 中寻找 "hello" 或
"there"。'FINDSTR /C:"hello there" x.y' 文件 x.y  寻找
"hello there"。

一般表达式的快速参考:
  .        通配符: 任何字符
  *        重复: 以前字符或类出现零或零以上次数
  ^        行位置: 行的开始
  $        行位置: 行的终点
  [class]  字符类: 任何在字符集中的字符
  [^class] 补字符类: 任何不在字符集中的字符
  [x-y]    范围: 在指定范围内的任何字符
  \x       Escape: 元字符 x 的文字用法
  \<xyz    字位置: 字的开始
  xyz\>    字位置: 字的结束

有关 FINDSTR 常见表达法的详细情况，请见联机命令参考。
```

示例：

```bash
# 在当前目录和所有子目录中的后缀为 md 的文件中搜索包含单词 encode （不考虑字母大小写）的行
findstr /s /i /n encode  *.md
```

注意，findstr 匹配的是字节而非字符，查找汉字时只能在936代码下使用，且查找的文件须为中文编码，utf-8格式会导致查找不到。

