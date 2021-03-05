## jQuery

jquery.js

jquery.min.js

### 选择器

jQuery 选择器基于元素的 id、类、类型、属性、属性值等"查找"（或选择）HTML 元素。 它基于已经存在的 CSS 选择器，除此之外，它还有一些自定义的选择器。

jQuery 中所有选择器都以美元符号开头：$()。

选择器结果 context 是 document。其最大选择元素为 "html"（即为选择器选择元素的默认父元素）。

"H:first-child" 将选择其父元素及其所有子*n元素的第一个子元素为H的元素。

"H:nth-child(n)"   将选择其父元素及其所有子*n元素的第n个子元素为H（不为H则不选）的元素。

通过 \$(":button") 可以选取所有 type="button" 的 \<input> 元素 和 \<button> 元素。

**:** 即为 jQuery 的过滤选择器，语法类似于 css 中的伪类选择器；其过滤选择器大概可以分为基本过滤（p:first 之类）、内容过滤（:empty）、子元素过滤(:first-child)和属性过滤 [href] 选择器。



选择器效率

firejspt js性能测试工具



事件冒泡 事件捕获 

DOM2级事件规定的事件流包含三个阶段：事件捕获阶段，处于目标阶段和事件冒泡阶段。首先发生的是事件捕获，然后是实际的目标接收到事件，最后阶段是冒泡阶段。

> （在chrome上）事件发生时，目标的 click() 方法绑定的事件会先于其绑定在事件捕获阶段的事件先执行；其他元素的 click() 绑定的事件会在事件冒泡阶段执行。

各种 target 区别

各种事件绑定与解绑方法

js 自定义事件

https://www.cnblogs.com/lyzg/p/5347857.html

js CustomEvent / document.createEvent

keypress 事件不会被某些键（比如 ALT、CTRL、SHIFT、ESC）触发。

事件 命名空间 

e.stopPropagation() 阻止事件冒泡

e.preventDefault() 阻止默认事件



模糊匹配

\$. \$().

jQuery 对象

DOM 事件 load

ready DOM 加载完成事件

当指定的对象已加载时绑定load/ready事件监听器，该方法会立即执行。

onload 所有内容（包括图片）加载完成事件



注意，事件function中使用的$()选择器在每次执行时都会重新选择，可能会选不到想选的元素。在function外层定义变量解决。

$ == jQuery

`$('div').attr('width')` 是静态的，从 html 上读的属性值

`$('div').width()` 是动态的，计算出来的值

attr prop 区别

this

onclick click 区别

this  $(this)

var thisObj = $(obj) //js对象转jquery对象



jQuery.fn.extend();[·](http://caibaojian.com/jquery-extend-and-jquery-fn-extend.html)

jQuery.extend();

https://www.cnblogs.com/douyage/p/8630529.html







### jQuery HTML

![jQuery Dimensions](../../img/jquery.assets/img_jquerydim-1583999822870.gif)

设置了 box-sizing 后，width() 获取的是 css 设置的 width 减去 padding 和 border 的值。



index()

event window.event  diff \__proto__

光标

键盘

execCommand

Event

prepend() 等插入元素方法，如果待插入的 jQuery 对象已在html中存在，其将会移动到新的位置上去。当该元素插入到多个地方时，原 jquery对象将指向最后插入的元素。

scroll 

选中多个元素时，其调用的设置方法将应用在所有元素中，取值方法将取第一个 ? 元素。（如果其中有冲突时）

方法参数中的方法变量可带的参数值意义。 event index

window scroll -> 浏览器窗口滚动条



Tips

jQuery 插件写法

实用方法

移除属性 类

## AJAX

js

XMLHttpRequest

jquery



同源策略

jsonp

json 的一种"使用模式"

跨域失败Orz

`<script type="text/javascript" src=""></script>` 无法跨域。

spring 

ResponseBody  RequestBody

springMVC4  需引入jakson annotations/core/databind 包 



### Tips

https://blog.csdn.net/weixin_42839080/article/details/82833111

XMLHttpRequest cannot loadfile:///E:/webs/extJS/ext-3.3.0/examples/csdn/combobox.txt?_dc=1414738973999.Cross origin requests are only supported for protocol schemes: http, data,chrome-extension, https, chrome-extension-resource.

解决办法是给chrome添加启动参数：**--allow-file-access-from-files** ，这样本地ajax请求就不会报跨域错误了。（注意如果给chrome添加多个启动参数，每个启动参数“--”之前要有空格隔开，如"C:\ProgramFiles\Google\Chrome\Application\chrome.exe" **--allow-file-access-from-files**）

具体方法：在浏览器快捷方式上右键-属性-快捷方式-目标



