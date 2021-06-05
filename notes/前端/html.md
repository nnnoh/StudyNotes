## HTML

文件名后缀：.htm or .html

### HTML 标签

#### HTML 文档结构

HTML 文档由 4 个主要标记组成，

> HTML 标签、属性名不区分大小写
>
> 没有标签结尾的，默认标签结尾在下一个标签开头前
>
> 不是所有标签都需要结束标签（body 中的~~基本~~都要，head 中 meta 和 link 不用）

\<!DOCTYPE html>



重利用 方式

焦点 标签

textarea

<code> <pre> 区别

contentediable = "true" 块内所有元素都可被删除

事件 tabindex

img alt

块元素 内联元素

IFrame, object, include

target

label span 区别

https://blog.csdn.net/qq_41511151/article/details/98586981



字符实体（character entities）

\<form> botton 会跳转 get 

form 表单 enctype 属性规定发送到服务器之前应该如何对表单数据进行编码。

- application/x-www-form-urlencoded 
  在发送前编码所有字符（默认） 

- multipart/form-data 
  不对字符编码。 在使用包含文件上传控件的表单时，必须使用该值。

- text/plain 
  空格转换为 "+" 加号，但不对特殊字符编码。

form 默认使用 html 的 charset 编码格式提交数据

中文

html 默认编码 ISO-8859-1 ？

get post servlet req.setCharacterEncoding("") 区别

servlet中设置编码时，注意语句的顺序。

https://blog.csdn.net/jiahao1186/article/details/82026330#commentBox

url中出现中文数据，最好使用URL编码处理



https://www.cnblogs.com/oyer/p/5611508.html

name id 区别

href 路径 相对

href="#id"

编码规范

> " ' ' "

`<input>` 标签的 autocomplete 属性，autocomplete 属性适用于 `<form>`，以及下面的 `<input>` 类型。

w3school 知识补充

实现技巧

HTML5

兼容

语义元素

data-* 自定义属性