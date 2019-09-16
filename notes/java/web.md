## HTML

文件名后缀：.htm or .html

### HTML 标签

#### HTML 文档结构

HTML 文档由 4 个主要标记组成，

> 没有标签结尾的，默认标签结尾在下一个标签开头前
>
> HTML 标签 不区分大小写





IFrame, object, include

target

label span 区别



\<form> botton 会跳转 get 

name id 区别

herf 路径 相对

编码规范

> " ' ' "

w3school 知识补充

## CSS

CSS 指层叠样式表 (Cascading Style Sheets)。

- 样式定义**如何显示** HTML 元素；样式通常存储在**样式表**中
- 把样式添加到 HTML 中，是为了**解决内容与表现分离的问题**
- **外部样式表**可以极大提高工作效率，外部样式表存储在 **.css 文件**中
- 多个样式定义可**层叠**为一
- 样式对网页中元素位置的排版进行像素级精确控制

### 样式表

- 外部样式表

  当样式需要应用于很多页面时，外部样式表将是理想的选择。样式表以 .css 扩展名进行保存。

  每个页面使用标签链接到样式表。

  `<head> <link rel="stylesheet" type="text/css" href="mystyle.css"> </head>` 

- 内部样式表

  当单个文档需要特殊的样式时，就应该使用内部样式表。可以使用 `<style>` 标签在文档头部 `<head>` 定义内部样式表

- 内联样式

  当样式仅需要在一个元素上应用一次时。将表现和内容混杂在一起，内联样式会损失掉样式表的许多优势。

  要使用内联样式，需在相关的标签内使用样式（style）属性。style 属性可以包含任何 CSS 属性。

  `<p style="color:sienna;margin-left:20px">这是一个段落。</p>`

#### 样式层叠

样式层叠就是对一个元素多次设置同一个样式，将使用**最后一次**设置的属性值。对于不同样式将叠加到元素上。

一般而言，所有的样式会根据下面的规则层叠于一个新的虚拟样式表中，其中数字 4 拥有最高的优先权。

1. 浏览器缺省设置
2. 外部样式表
3. 内部样式表（位于 \<head> 标签内部）
4. 内联样式（在 HTML 元素内部）

#### 多重样式优先级

选择器优先级列表，其中数字 7 拥有最高的优先权：

1. 通用选择器（*）
2. 元素(类型)选择器
3. 类选择器
4. 属性选择器
5. 伪类
6. ID 选择器
7. 内联样式

##### !important 

当 !important 规则被应用在一个样式声明中时，该样式声明会覆盖CSS中任何其他的声明，无论它处在声明列表中的哪里。

使用 !important 不是一个好习惯，因为它改变了样式表本来的级联规则，从而使其难以调试。

###### Tips

- Always 要优化考虑使用样式规则的优先级来解决问题而不是 !important
- Only 只在需要覆盖全站或外部 css（例如引用的 ExtJs 或者 YUI ）的特定页面中使用 !important
- Never 永远不要在全站范围的 css 上使用 !important
- Never 永远不要在你的插件中使用 !important

##### 权重计算

- 内联样式表的权值最高 1000
- ID 选择器的权值为 100
- Class 类选择器的权值为 10
- HTML 标签选择器的权值为 1

示例：

```css
<html>    
<head>    
<style type="text/css">    
#redP p {    
/* 权值 = 100+1=101 */    
color:#F00;  /* 红色 */    
}    
#redP .red em {    
/* 权值 = 100+10+1=111 */    
color:#00F; /* 蓝色 */    
}    
#redP p span em {    
/* 权值 = 100+1+1+1=103 */    
color:#FF0;/*黄色*/    
}    
</style>    
</head>    
<body>    
<div id="redP">   
<p class="red">red   
<span><em>em red</em></span>    <!-- em 显示蓝色 -->
</p>    
<p>red</p>    
</div>    
</body>   
</html>
```

##### CSS 优先级法则

- 选择器都有一个权值，权值越大越优先；
- 当权值相等时，后出现的样式表设置要优于先出现的样式表设置；
- 创作者的规则高于浏览者：即网页编写者设置的CSS 样式的优先权高于浏览器所设置的样式；
- 继承的CSS 样式不如后来指定的CSS 样式；
- 在同一组属性设置中标有"!important"规则的优先级最大；

### 语法

CSS 规则由两个主要的部分构成：选择器，以及一条或多条声明:

![selector](.\web.assets\selector.gif)

- 选择器通常是需要改变样式的 HTML 元素。
- 每条声明由一个属性和一个值组成。

CSS 注释以 "/\*" 开始, 以 "*/" 结束。

### 选择器

要在 HTML 元素中设置 CSS 样式，需要在元素中设置选择器。

[CSS3选择器归类整理](https://www.w3cschool.cn/css3/css3-selector.html)

#### 基本选择器

##### id 选择器

id 选择器可以为标有特定 id 的 HTML 元素指定特定的样式。

HTML元素以 id 属性来设置 id 选择器，CSS 中 id 选择器以 "#" 来定义。

```css
#para1
{
	text-align:center;
	color:red;
}
```

- id 属性不要以数字开头，数字开头的 id 在 Mozilla/Firefox 浏览器中不起作用。
- id 属性只能在每个 HTML 文档中出现一次。

##### class 选择器

class 选择器用于描述一组元素的样式。class 选择器有别于 id 选择器，class可以在多个元素中使用。

class 选择器在 HTML 中以 class 属性表示, 在 CSS 中，类选择器以一个点"."号显示。

```css
.center {text-align:center;}
/*可以指定特定的HTML元素使用class*/
p.center {text-align:center;}
```

- 类名的第一个字符不能使用数字，它无法在 Mozilla 或 Firefox 中起作用。

### 属性

> 不要在属性值与单位之间留有空格。

#### 颜色值

color 属性定义元素的字体颜色。

CSS 中颜色值表示方式：

- 使用十六进制的颜色值： `p { color: #ff0000;}`

  使用 CSS 的缩写形式：`p { color: #f00;}`

- 使用 RGB 值。

  像素形式（不需要 px 单位）：`p { color: rgb(255,0,0); }`

  百分比形式（必须要 %）： `p { color: rgb(100%,0%,0%); }`

- 使用颜色名称：`p { color: red;}`

#### 背景

使用简写属性时，属性值的顺序为：:

- background-color
- background-image
- background-repeat
- background-attachment
- background-position

以上属性无需全部使用，可以按照页面的实际需要使用。

示例：`body {background:#ffffff url('img_tree.png') no-repeat right top;}`

##### background-color

background-color 属性定义了元素的背景颜色。

background-color 不能继承，其默认值是 transparent。即，如果一个元素没有指定背景色，那么背景就是透明的，这样其父元素的背景才可见。

##### background-image

background-image 属性描述了元素的背景图像.

默认情况下，背景图像进行平铺重复显示，以覆盖整个元素实体.

`body {background-image:url('path');}`

##### background-repeat

默认情况下 background-image 属性会在页面的水平/垂直方向平铺。

background-repeat 使图片只在水平方向平铺 （repeat-x）或垂直方向（repeat-y）回不平铺（no-repeat）

```css
body { 
background-image:url('/path/logo.png'); /* www.xxx.com/path/logo.png */
background-repeat:repeat-x; 
}
```

##### background-position

background-position 属性改变图像在背景中的位置:

background-position 属性值：

- 关键字 top、bottom、left、right 和 center

  使用两个关键字设置位置：一个对应水平方向，另一个对应垂直方向。如果只有一个关键字，则会默认另一个关键字为 center。

- 百分数值

  一个对应水平方向，另一个对应垂直方向。默认 50%（居中）。以图像中心为偏移点，在元素范围内。如，放在水平方向 2/3、垂直方向 1/3 处。`background-position:66% 33%;`

- 长度值

  长度值是元素内边距区左上角的偏移，偏移点是图像的左上角。

  单位可以是 px、cm（通过 ppi 换算）

#### 文本

##### color

color 属性设置文字的颜色。

对于W3C标准的CSS：如果定义了颜色属性，还必须定义背景色属性。

##### text-align

设置文本的水平对齐方式。

值：left、center、right、justify

justify 使每一行宽度相等，左，右外边距对齐。

> 如果想把一个行内元素的第一行“缩进”，可以用左内边距或外边距创造这种效果。

##### text-decoration 

设置或删除文本的装饰。

值：overline（上划线）、line-through（删除线）、underline（下划线）

##### text-transform

指定在一个文本中字母的大小写。

值：uppercase（大写）、lowercase（小写）、capitalize（首字母大写，其他不变）

##### text-indent

指定文本的第一行的缩进。

值：长度值（px）、百分数

##### word-spacing 

改变字（单词）之间的标准间隔。其默认值 normal 相当于 0px。

#### 表格

##### border

设置边框属性。按如下顺序，可跳过某个值的设置。

1. border-width
2. border-style
3. border-color

对于这些属性，当属性值的数量大于 2 时，按上右下左顺序指定各边框宽度。

或直接单独设置某边边框。

- border-top 
- border-right
- border-bottom
- border-left

##### border-width 

指定所有边框或各边边框宽度。只有当边框样式不是 none 时才起作用。

- 长度值
- 关键字 thick 、medium（默认值）、thin（5px、3px、2px）

##### border-style

设置边框样式。

| 值      | 描述                                                         |
| :------ | :----------------------------------------------------------- |
| none    | 定义无边框。                                                 |
| hidden  | 与 "none" 相同。不过应用于表时除外，对于表，hidden 用于解决边框冲突。 |
| dotted  | 定义点状边框。在大多数浏览器中呈现为实线。                   |
| dashed  | 定义虚线。在大多数浏览器中呈现为实线。                       |
| solid   | 定义实线。                                                   |
| double  | 定义双线。双线的宽度等于 border-width 的值。                 |
| groove  | 定义 3D 凹槽边框。其效果取决于 border-color 的值。           |
| ridge   | 定义 3D 垄状边框。其效果取决于 border-color 的值。           |
| inset   | 定义 3D inset 边框。其效果取决于 border-color 的值。         |
| outset  | 定义 3D outset 边框。其效果取决于 border-color 的值。        |
| inherit | 规定应该从父元素继承边框样式。                               |

##### border-color

设置边框的颜色。只有当边框样式不是 none 时才起作用。

### 布局

- 流动模型（Flow）
- 浮动模型 (Float)
- 层模型（Layer）





## JavaScript

- **HTML** 定义了网页的内容
- **CSS** 描述了网页的布局
- **JavaScript** 网页的行为

[JavaScript 知识图谱](https://www.w3cschool.cn/javascript/javascript-skillmap.html)

JavaScript 脚本位置

- HTML 中的脚本必须位于 `<script>` 与 `</script>` 标签之间，可放置在 `<body>` 或 `<head>` 部分中。
  
  浏览器解释 html 时是按先后顺序的，因此 script 的执行也是按先后顺序的。

- 保存到外部文件中，扩展名为 .js。
  
  JS 文件不能直接运行，需嵌入到 HTML 文件中执行。HTML 使用 `<script>` 标签的 src 属性设置 js 文件
  
  `<script src="/statics/demosource/myscript.js"></script>`
  
  在 JS 文件中可直接编写 JavaScript 代码不需要\<script>标签。

JavaScript 语句会在页面加载时执行。

通常在语句的结尾后加上一个分号`";"`来表示语句的结束。

单行注释，在注释内容前加符号 “//”；多行注释以"/\*"开始，以"\*/"结束。

### 语法

#### 变量

定义变量：`var num1,num2;`

1. 变量必须使用字母、下划线(_)或者美元符($)开始。_
2. 然后可以使用任意多个英文字母、数字、下划线(_)或者美元符($)组成。字母区分大小写。
3. 不能使用 JavaScript 关键词与 JavaScript 保留字。

> 变量虽然也可以不声明，直接使用，但不规范，需要先声明，后使用。
>
> 变量可以存储数值，字符串，布尔值等。

#### 操作符

**操作符之间的优先级（高到低）:**

算术操作符 → 比较操作符 → 逻辑操作符 → "="赋值符号

#### 数组<a name="Array"></a>

**创建数组语法：**

```javascript
var myarray=new Array();	 
```

1. 创建的新数组是空数组，没有值，如输出，则显示undefined。
2. 虽然创建数组时，可指定长度 `new Array(i)`，但实际上数组都是变长的，也就是说即使指定了长度，仍然可以将元素存储在规定长度以外。
3. 数组索引号从0开始。
4. 数组存储的数据可以是任何类型（数字、字符、布尔值等）

**数组赋值：**

```javascript
var myarray = new Array(66,80,90,77,59);//创建数组同时赋值
var myarray = [66,80,90,77,59];//直接输入一个字面量数组
```

**数组属性 length**

length 属性表示数组的长度，即数组中元素的个数。

JavaScript 数组的 length 属性是可变的。

```javascript
arr.length=10; //增大数组的长度
```

**二维数组的定义**

```javascript
var Myarr = [[0 , 1 , 2 ],[1 , 2 , 3]];	//直接赋值
var myarr=new Array();  //先声明一维 
myarr[i]=new Array(); //再创建第二维
```

#### 分支语句

```javascript
if(条件1) { 
    条件1成立时执行的代码
} else if(条件n) { 
    条件n成立时执行的代码
} else { 
    条件1、n不成立时执行的代码
}
```

```javascript
//switch必须赋初始值，值与每个case值匹配。满足执行该 case 后的所有语句
switch(表达式)	{
case值1:
  执行代码块 1
  break;
case值n:
  执行代码块 n
  break;
default:
  与 case值1 、case值n 不同时执行的代码
}
```

#### 循环语句

```javascript
for(初始化变量;循环条件;循环迭代) {     
    循环语句 
}
```

```javascript
while(判断条件){
    循环语句
}
do {
    循环语句
} while(判断条件)
```

- **break**  退出当前循环。
- **continue**  仅仅跳过本次循环，而整个循环体继续执行

#### 函数

```javascript
function 函数名(参数1,参数2){
    函数代码;
    return 值;
}
```

##### 匿名函数

```javascript

```

调用局部变量

字符串作为函数调用。

this

可变长参数 

arguments

eval

Function

window[]

#### 作用域

局部变量：在函数中通过var声明的变量，只能在函数内部访问，在函数执行完毕后销毁。

全局变量：在函数外通过var声明的变量，网页中所有脚本和函数均可使用，在页面关闭后销毁。

没有声明就使用的变量，默认为全局变量，不论这个变量在哪被使用。

在 HTML 中, 全局变量是 window 对象，所有数据变量都属于 window 对象。

### 事件

| 事件        | 说明             |
| ----------- | ---------------- |
| onclick     | 鼠标单击         |
| onmouseover | 鼠标经过         |
| onmouseout  | 鼠标移开         |
| onchange    | 文本内容改变     |
| onselect    | 文本内容被选中   |
| onfocus     | 光标聚集         |
| onblur      | 光标离开（失焦） |
| onload      | 页面加载         |
| onunload    | 页面关闭         |

#### 特别事件

form

- onsubmit

  示例：`<form onsubmit="return checkForm()">`

#### 事件绑定与解绑

### 常用方法

#### 输出

- 使用 **window.alert()** 弹出警告框显示消息。

- 使用 **document.write()** 方法将内容显示到 HTML 界面中。

  如果在文档已完成加载后执行 document.write，整个 HTML 页面将被覆盖。

- 使用 **innerHTML** 在 HTML 元素上显示信息。

  `document.getElementById("demo").innerHTML = "段落已修改。";`

- 使用 **console.log()** 输出信息到浏览器的控制台。

  浏览器中使用 F12 来启用调试模式， 在调试窗口中点击 Console 菜单查看控制台输出。

#### 消息框

- `alert(str);  ` 警告框，包含一个确定按钮。

- `confirm(str);` 确认框，包含确定和取消按钮。

  点击"确定"按钮时，返回true；点击"取消"按钮时，返回false。

- `prompt(str1, str2);` 提问框，包含一个确定按钮、取消按钮与一个文本输入框。

  str1：要显示在消息对话框中的文本；str2：文本框中的内容，对文本框内容的修改不会改变 str2 的值。

  点击确定按钮，文本框中的内容将作为函数返回值；点击取消按钮，将返回null。

> 这些消息对话框是排它的。

#### 打开/关闭窗口

`window.open([URL], [窗口名称], [参数字符串])`

查找一个已经存在或者新建的浏览器窗口。返回值为对应窗口对象。

- URL：可选参数，在窗口中要显示网页的网址或路径。如果省略这个参数，或者它的值是空字符串，那么窗口就不显示任何文档。

- 窗口名称：可选参数，被打开窗口的名称。

  1. 该名称由字母、数字和下划线字符组成。
  2. "\_top"、"\_blank"、"_self"具有特殊意义的名称。
     _blank：在新窗口显示目标网页
     _self：在当前窗口显示目标网页
     _top：框架网页中在上部窗口中显示目标网页
  3. 相同 name 的窗口只能创建一个，要想创建多个窗口则 name 不能相同。
  4. .name 不能包含有空格。

- 参数字符串：可选参数，设置窗口参数，各参数用逗号隔开。

  | 参数       | 值      | 说明                         |
  | ---------- | ------- | ---------------------------- |
  | top        | Number  | 窗口顶部离开屏幕顶部的像素数 |
  | left       | Number  | 窗口左端离开屏幕左端的像素数 |
  | width      | Number  | 窗口的宽度                   |
  | height     | Number  | 窗口的高度                   |
  | menubar    | yes, no | 窗口有没有菜单               |
  | toolbar    | yes, no | 窗口有没有工具条             |
  | scrollbars | yes, no | 窗口有没有滚动条             |
  | status     | yes, no | 窗口有没有状态栏             |

`window.close();` 关闭本窗口。

`<窗口对象>.close();` 关闭指定的窗口。

#### 类型转换

- **result = parseInt(str)**  解析字符串，返回一个整数。

### 内置对象

> 内置对象可直接输出显示。

#### Date

##### 创建日期对象

```javascript
var nDate=new Date();
var d = new Date(2012, 10, 1);  //2012年10月1日
var d = new Date('Oct 1, 2012');  //2012年10月1日
document.write(nDate+"<br>");
//Sun Sep 08 2019 15:35:50 GMT+0800 (中国标准时间)
```

##### 获取/设置时间日期

- get/setDate()  返回/设置星期。0-6，0 表示星期天。
- get/setFullYear()  返回/设置年份，用四位数表示 
- get/setYear()  返回/设置年份
- get/setMonth()  返回/设置月份。0：一月，...，11：十二月
- get/setHours()  返回/设置小时，24小时制
- get/setMinutes()  返回/设置分钟数
- get/setSeconds()  返回/设置秒钟数
- get/setTime()  返回/设置时间，单位维毫秒 ，计算从 1970 年 1 月 1 日零时到日期对象所指的日期的毫秒数。

#### String

##### 定义字符串

```javascript
var mystr = "Hello World!";
```

##### 属性/方法

- **length** 属性为字符串的长度。

- **charAt(index)** 返回指定位置的字符。返回的字符是长度为 1 的字符串。

  如果参数 index 不在 0 与 string.length-1 之间，该方法将返回一个空字符串。

- **indexOf(substring, startpos)** 返回某个指定的字符串值在字符串中首次出现的位置。可选参数 startpos 指定在字符串中开始查找的位置。

  indexOf() 方法区分大小写。如果要检索的字符串值没有出现，则该方法返回 -1。

- **split(separator, limit)** 将字符串分割为字符串数组，返回此数组。可选参数 limit 设置返回的数组长度。

  空字符串 ("") 用作 separator时，每个字符之间都会被分割。

- **substring(startPos, stopPos)**   提取字符串中介于两个指定下标之间的字符串。可选 stopPos 指定结束位置（返回字符串不包含该位置）。

  返回字符串长度为 stop 减start。如果参数 start 与 stop 相等，那么该方法返回的就是一个空串 ""。

  如果 start 比 stop 大，那么该方法在提取子串之前会先交换这两个参数。

- **substr(startPos,length)** 提取从 startPos位置开始的指定数目的字符串。length 可选。

  此方法 startPos 为负数时，从字符串的尾部开始算起的位置。即 -1 指字符串中最后一个字符。当 startPos 为负数且绝对值大于字符串长度，startPos为0。

- **toUpperCase()** 方法将字符串小写字母转换为大写。

- **toLowerCase()** 方法将字符串所有大写字母都变成小写。

#### Math

Math 对象，提供对数据的数学计算。

Math 对象是一个固有的对象，无需创建它，直接把 Math 作为对象使用就可以调用其所有属性和方法。

##### 方法

- **Math.ceil(x)** 返回的是大于或等于x，并且与x最接近的整数。

- **Math.floor(x)** 返回的是小于或等于x，并且与 x 最接近的整数。

- **Math.round(x)** 四舍五入为最接近的整数。

  如果 x 与两侧整数同等接近，则结果接近 +∞方向的数字值 。

- **Math.random()** 方法可返回大于或等于 0 但小于 1 的一个随机数。

#### Array

[Array](#Array)

##### 方法

- **concat(array1,..., arrayN)**  方法用于连接两个或多个数组。此方法返回一个新数组，不改变原来的数组。

- **join(separator)** 把数组中的所有元素放入一个字符串。可选分隔符。

- **reverse()** 颠倒数组中元素的顺序。该方法会改变原来的数组，而不会创建新的数组。

- **slice(start,end)** 从已有的数组中返回选定的数组。可选 end 指定结束位置（不包含该位置）。

  可使用负值从数组的尾部选取元素。

- **sort(func)**  使数组中的元素按照一定的顺序排列。默认按unicode码顺序排列。

  func 比较函数比较 a, b 两个值。

  若返回值 <=-1，则表示 a 在排序后的序列中出现在 b 之前。
  若返回值 >-1 && <1，则表示 a 和 b 具有相同的排序顺序。
  若返回值 >=1，则表示 a 在排序后的序列中出现在 b 之后。

### window 对象

window 对象是BOM的核心，指当前的浏览器窗口。

BOM，Browser Object Model，即浏览器对象模型。浏览器页面初始化时，会在内存创建一个全局对象，用来描述当前窗口的属性和状态，这个全局对象被称为浏览器对象模型。

使用 window 对象的属性和方法时可省略 window。

![window](D:\GitHub\StudyNotes\notes\java\web.assets\1470710607319368.gif)

#### 计时器

##### 间隔性触发计时器

- **setInterval(代码,交互时间)**  载入页面后每隔指定的时间执行代码，以毫秒计时。返回值可传递给 clearInterval() 从而取消周期性执行。

  ```javascript
  setInterval("clock()",1000)
  setInterval(clock,1000)  //每隔1秒调用一次clock()函数
  ```
  
- **clearInterval(id_of_setInterval)** 取消由 setInterval() 设置的定时任务。

##### 一次性计时器

- **setTimeout(代码,延迟时间)** 在载入后延迟指定时间后执行一次代码，以毫秒为单位。
- **clearTimeout(id_of_setTimeout)** 取消计时器。

#### history 对象

history 对象记录了用户曾经浏览过的页面(URL)，并可以实现浏览器前进与后退相似导航的功能。

从窗口被打开的那一刻开始记录，每个浏览器窗口、每个标签页乃至每个框架，都有自己的 history 对象与特定的 window 对象关联。

##### 属性/方法

- **length** 返回当前窗口历史列表中的 URL 数量。
- **back()** 加载 history 列表中的前一个 URL。等同于点击浏览器的后退按钮。
- **forward()** 加载 history 列表中的下一个 URL。等同于前进按钮。
- **go(number)** 根据当前所处的页面，加载 history 列表中相对当前页面偏移 number 的页面。即，0：当前页面；1：forward()；-1：back()。

#### sessionStorage 对象

#### localStorage 对象

#### location 对象

location 表示窗口中当前显示的文档的 Web 地址。用于获取或设置窗体的URL，并且可以用于解析URL。

##### 属性

![location](.\web.assets\53605c5a0001b26909900216.jpg)

##### 方法

- **assign()** 加载新的文档。
- **reload()** 重新加载当前文档。
- **replace()** 用新的文档代替当前文档。

#### navigator 对象

navigator 对象包含有关浏览器的信息，通常用于检测浏览器与操作系统的版本。

##### 属性

- **appCodeName** 浏览器代码名的字符串表示
- **appName**  返回浏览器的名称
- **appVersion**  返回浏览器的平台和版本信息
- **platform** 返回运行浏览器的操作系统平台
- **userAgent** 返回发送服务器的 user-agent 头部的值，即包括浏览器版本信息等的字符串

#### screen 对象

screen 对象用于获取用户的屏幕信息。

##### 属性

- **avaiHeight**  窗口可以使用的屏幕高度，单位像素；

  即访问者屏幕的宽度，减去界面特性，比如任务栏。

- **avaiWidth** 窗口可以使用的屏幕宽度，单位像素；

- **colorDepth** 用户浏览器表示的颜色位数，通常为32位（每像素的位数）;

- **pixelDepth** 用户浏览器表示的颜色位数，通常为32位（每像素的位数）（IE不支持此属性)

- **height** 屏幕分辨率的高度，单位像素；

- **width** 屏幕分辨率的宽度，单位像素；

### DOM

文档对象模型 DOM（Document Object Model）定义访问和处理 HTML 文档的标准方法。DOM 将 HTML 文档呈现为带有元素、属性和文本的节点树结构（节点树）。

HTML 文档可以说由节点构成的集合，DOM节点有：

1. 元素节点：即标签。
2. 文本节点：向用户展示的内容。
3. 属性节点：元素属性。

#### 获取元素节点

- **document.body** html dom中的body节点 即\<body>
- **document.documentElement** html dom中的root 节点 即\<html>

- **document.getElementById("id")**  

  返回 null 或 [object HTMLParagraphElement]。

  通过元素对象的属性或方法对元素进行操作。

- **document.getElementsByName(name)**

  返回带有指定名称的节点对象的数组。

  直接输出这个数组显示 [object NodeList]，即使 length 为 0。

- **document.getElementsByTagName(Tagname)** 

  返回带有指定标签名（如 p、a、img 等标签名）的节点对象的数组。返回元素的顺序是它们在文档中的顺序。

#### 节点属性

- **nodeName** 节点的名称，只读

  1. 元素节点的 nodeName 与标签名相同
  2. 属性节点的 nodeName 是属性的名称
  3. 文本节点的 nodeName 永远是 #text
  4. 文档节点的 nodeName 永远是 #document

- **nodeValue** 节点的值

  1. 元素节点的 nodeValue 是 undefined 或 null
  2. 文本节点的 nodeValue 是文本自身
  3. 属性节点的 nodeValue 是属性的值

- **nodeType** 节点的类型，只读

  元素：1；属性：2；文本：3；注释：8；文档：9。

#### 节点遍历

- **childNodes** 属性为该元素节点下的所有子节点的数组。

  如果该节点没有子节点，则该属性返回不包含节点的 NodeList。

  节点之间的空白符（换行），在firefox、chrome、opera、safari浏览器都是（空白）文本节点，而 IE 会忽略。

- **firstChild** 属性返回 childNodes 属性数组的第一个子节点。如果选定的节点没有子节点，则该属性返回 NULL。

-  **lastChild** 属性返回 childNodes 属性数组的最后一个子节点。如果选定的节点没有子节点，则该属性返回 NULL。

- **parentNode **获取节点的父节点。

- **nextSibling** 属性可返回某个节点之后紧跟的节点（处于同一树层级中）。

  如果无此节点，则该属性返回 null。

- **previousSibling** 属性可返回某个节点之前紧跟的节点（处于同一树层级中）。

#### 节点操作

##### document 方法

- **document.createElement(tagName)** 方法可创建元素类型为 tagName 的节点。此方法可返回一个 Element 对象。
- **document.createTextNode(data)** 方法创建新的文本节点，返回新创建的 Text 节点。

##### 节点方法

- **appendChild(newnode)** 在节点的最后一个子节点列表之后添加一个新的子节点。

- **insertBefore(newnode,node)** 方法可在已有的子节点 node 前插入一个新的子节点。

- **removeChild(node)** 方法从子节点列表中删除某个节点。如删除成功，此方法可返回被删除的节点，如失败，则返回 NULL。

  把删除的子节点赋值给 x，这个子节点不在DOM树中，但是还存在内存中，可通过 x 操作。如果要完全删除对象，给 x 赋 null 值。

- **replaceChild (newnode,oldnew)**  实现子节点对象的替换。返回被替换对象的引用。 newnode 必须先被建立。 

  当 oldnode 被替换时，所有与之相关的属性内容都将被移除。

#### 元素节点对象

##### 元素方法

- **getAttribute(name)** 通过元素节点的属性名称获取属性的值。
- **setAttribute(name,value)** 增加一个指定名称和值的新属性，或者把一个现有的属性设定为指定的值。

- **removeAttribute(name)** 移除属性

  示例：element.removeAttribute("style"); 移除元素的样式属性。

##### 元素属性

> Object.attribute

- **innerHTML** 属性用于获取或替换 HTML 元素的内容（标签头尾间的字符串）。

- **style.[property]** HTML 元素的 CSS 样式属性。赋的值须使用 "" 。 

  使用示例：objmychar.style.fontSize="20"; 

  obj.style="color:red;background-color:#CCC";

- **style.display** 控制元素显示或隐藏。

  值："none" 隐藏；"block"显示为块级元素。

- **classNam** 属性设置或返回元素的 class 属性。

  可为网页内的某个元素指定一个css样式（类选择器）来更改该元素的外观。

value & innerHTML 区别

> radio： check 属性

Cookie

原型链

Boolean

herf="javascript:;" 之类的

execCommand

debugger 关键字

闭包

https://www.w3cschool.cn/javascript/js-cookies.html

### 正则表达式

#### 语法

格式：`/正则表达式主体/修饰符[可选]`

#### 修饰符

- i：执行对大小写不敏感的匹配。
- g：执行全局匹配（查找所有匹配而非在找到第一个匹配后停止）。
- m：执行多行匹配。

#### 正则表达式模式

https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Guide/Regular_Expressions

#### 字符串方法

##### search

##### replace

#### RegExp

##### test

##### exec

#### 使用示例

##### Cookie

```javascript
var str=" token=-1223249887; username=user".match(/(^| )username=([^;]*)(;|$)/);
		for(var i=0;i<str.length;i++)
			document.write(str[i]+"</br>");
```

> 使用Cookie似乎不比使用字符串方法直接分析字符串性能好。

### Tips

#### JS 输出空格

1. 使用 html 标签`"&nbsp;"`
2. 使用CSS样式 `"<span style='white-space:pre;'>"`

#### 浏览器窗口可视区域大小

获得浏览器窗口的尺寸（浏览器的窗口可视区域，不包括工具栏和滚动条）的方法:

1. 对于IE9+、Chrome、Firefox、Opera 以及 Safari：
   - window.innerHeight - 浏览器窗口的内部高度
   - window.innerWidth - 浏览器窗口的内部宽度
   
2. 对于 Internet Explorer 8、7、6、5：

   - document.documentElement.clientHeight表示HTML文档所在窗口的当前高度。

   - document.documentElement.clientWidth表示HTML文档所在窗口的当前宽度。

   或者，document对象的body属性对应HTML文档的\<body>标签

   - document.body.clientHeight

   - document.body.clientWidth

在不同浏览器都实用的 JavaScript 方案：

```javascript
var w= document.documentElement.clientWidth
      || document.body.clientWidth;
var h= document.documentElement.clientHeight
      || document.body.clientHeight;
```

#### 网页尺寸

##### 网页尺寸 scrollHeight 

scrollHeight 和 scrollWidth，获取网页内容高度和宽度。

scrollHeight和scrollWidth还可获取Dom元素中内容实际占用的高度和宽度。

**一、针对IE、Opera:**

scrollHeight 是网页内容实际高度，可以小于 clientHeight。

**二、针对NS、FF:**

scrollHeight 是网页内容高度，不过最小值是 clientHeight。也就是说网页内容实际高度小于 clientHeight 时，scrollHeight 返回 clientHeight 。

**三、浏览器兼容性**

```javascript
var w=document.documentElement.scrollWidth
   || document.body.scrollWidth;
var h=document.documentElement.scrollHeight
   || document.body.scrollHeight;
```

##### 网页尺寸offsetHeight

offsetHeight 和 offsetWidth，获取网页内容高度和宽度(包括滚动条等边线，会随窗口的显示大小改变)。

**一、值**

offsetHeight = clientHeight + 滚动条 + 边框。

**二、浏览器兼容性**

```javascript
var w= document.documentElement.offsetWidth
    || document.body.offsetWidth;
var h= document.documentElement.offsetHeight
    || document.body.offsetHeight;
```

#### 网页卷去的距离与偏移量

![scroll](D:\GitHub\StudyNotes\notes\java\web.assets\5347b2b10001e1a307520686.jpg)

scrollLeft：设置或获取位于给定对象左边界与窗口中目前可见内容的最左端之间的距离 ，即左边灰色的内容。

scrollTop：设置或获取位于对象最顶端与窗口中可见内容的最顶端之间的距离 ，即上边灰色的内容。

> scrollLeft 和 scrollTop 会随着滑块的滑动而改变。

offsetLeft：获取指定对象相对于版面或由 offsetParent 属性指定的父坐标的计算左侧位置 。

offsetTop：获取指定对象相对于版面或由 offsetParent 属性指定的父坐标的计算顶端位置 。

> offsetParent：布局中设置postion属性(Relative、Absolute、fixed)的父容器，从最近的父节点开始，一层层向上找，直到HTML的body。

## jQuery

### 选择器

## Bootstrap

Bootstrap 是一个用于快速开发 Web 应用程序和网站的前端框架。Bootstrap 是基于 HTML、CSS、JavaScript 的工具集。

**优点：**

- 移动设备优先：自 Bootstrap 3 起，框架包含了贯穿于整个库的移动设备优先的样式。
- 浏览器支持：所有的主流浏览器都支持 Bootstrap。
- 响应式设计：Bootstrap 的响应式 CSS 能够自适应于台式机、平板电脑和手机。
- 为开发人员创建接口提供了一个简洁统一的解决方案。
- 包含了功能强大的内置组件，易于定制。
- 提供了基于 Web 的定制。
- 开源。

**Bootstrap 包的内容**

- **基本结构**：Bootstrap 提供了一个带有网格系统、链接样式、背景的基本结构。
- **CSS**：Bootstrap 自带以下特性：全局的 CSS 设置、定义基本的 HTML 元素样式、可扩展的 class，以及一个先进的网格系统。
- **组件**：Bootstrap 包含了十几个可重用的组件，用于创建图像、下拉菜单、导航、警告框、弹出框等等。
- **JavaScript 插件**：Bootstrap 包含了十几个自定义的 jQuery 插件。您可以直接包含所有的插件，也可以逐个包含这些插件。
- **定制**：可以定制 Bootstrap 的组件、LESS 变量和 jQuery 插件来得到自己的版本。

**CDN**

```html
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
 
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
 
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
```

### Basic template

```html
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <h1>Hello, world!</h1>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery-1.12.4.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ" crossorigin="anonymous"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
  </body>
</html>
```

bootstrap模板为使IE6、7、8版本（IE9以下版本）浏览器兼容html5新增的标签，引入下面代码文件即可。

```xml
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
```

同理为使IE6、7、8版本浏览器兼容css3样式，引入下面代码：

```xml
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
```

了解每句的作用

全局样式 用处

### 导航条



使用示例

## JSP

JSP，全名为Java Server Pages，其根本就是一个简化的 Servlet 设计，实现了在 Java 当中使用 HTML 标签，JSP 是一种动态网页技术标准也是 JavaEE 标准，JSP 与 Servlet 一样是在服务器端执行的。

**常见动态网站开发技术**

Jsp: java平台，安全性高，适合大型开发，企业级、分布式的Web应用程序

Asp.net: .NET平台，简单易学。但是安全性以及跨平台性差

PHP: 简单，高效，成本低，开发周期短，特别适合中小型企业的Web应用开发(LAMP:Linux+Apache+MySQL+PHP)

JSP等模板引擎作为伪前端技术（实际上是服务器端技术）有如下弊端：

1. 标签库没有统一标准，各大厂商完成不一，常使开发者晕头转向

2. 本身不是一种前端与后端分离的技术，不能实现前端与后端各自的语言独立

3. 不是好的富客户端技术，GWT虽然可以用为富客户端前端技术，但是也是基于Java

4. 前端与后端数据交换XML/JSON支持不够灵活，不是天生支持Ajax

页面跳转



<%@page isELIgnored="false"%>

${pageContext.request.contextPath}



```jsp
<%@ page import="servlet.CountServlet"%>
```

引入 servlet 类使用其提供的功能。

## Servlet

Servlet 是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和 HTTP 服务器上的数据库或应用程序之间的中间层。

相比于 CGI（Common Gateway Interface，公共网关接口），Servlet 有以下几点优势：

- 性能明显更好。
- Servlet 在 Web 服务器的地址空间内执行。这样它就没有必要再创建一个单独的进程来处理每个客户端请求。
- Servlet 是独立于平台的，因为它们是用 Java 编写的。
- 服务器上的 Java 安全管理器执行了一系列限制，以保护服务器计算机上的资源。因此，Servlet 是可信的。
- Java 类库的全部功能对 Servlet 来说都是可用的。它可以通过 sockets 和 RMI 机制与 applets、数据库或其他软件进行交互。

Servlet 执行的主要任务：

- 读取客户端（浏览器）发送的显式的数据。这包括网页上的 HTML 表单，或者也可以是来自 applet 或自定义的 HTTP 客户端程序的表单。
- 读取客户端（浏览器）发送的隐式的 HTTP 请求数据。这包括 cookies、媒体类型和浏览器能理解的压缩格式等等。
- 处理数据并生成结果。这个过程可能需要访问数据库，执行 RMI 或 CORBA 调用，调用 Web 服务，或者直接计算得出对应的响应。
- 发送显式的数据（即文档）到客户端（浏览器）。该文档的格式可以是多种多样的，包括文本文件（HTML 或 XML）、二进制文件（GIF 图像）、Excel 等。
- 发送隐式的 HTTP 响应到客户端（浏览器）。这包括告诉浏览器或其他客户端被返回的文档类型（例如 HTML），设置 cookies 和缓存参数，以及其他类似的任务。

Java Servlet 是运行在带有支持 Java Servlet 规范的解释器的 web 服务器上的 Java 类。

Servlet 可以使用 **javax.servlet** 和 **javax.servlet.http** 包创建，它是 Java 企业版的标准组成部分。这些类实现 Java Servlet 和 JSP 规范。

### Session

HTTP 是一种无状态协议，这意味着每次客户端检索网页时，客户端打开一个单独的连接到 Web 服务器，服务器会自动不保留之前客户端请求的任何记录。

但仍有以下三种方式来维持 Web 客户端和 Web 服务器之间的 session 会话：

- Cookies

  Web 服务器分配一个唯一的 session 会话 ID 作为每个 Web 客户端的 cookie，对于客户端的后续请求可以使用接收到的 cookie 来识别。

- 隐藏的表单字段

  Web 服务器可以发送一个隐藏的 HTML 表单字段，以及一个唯一的 session 会话 ID。当表单被提交时，指定的名称和值会被自动包含在 GET 或 POST 数据中。浏览器发送请求时，session_id 值可以用于保持不同的浏览器的跟踪。

  `<input type="hidden" name="sessionid" value="12345">`

  但是点击常规的超文本链接（\<A HREF...>）不会导致表单提交，因此隐藏的表单字段也不支持常规的 session 会话跟踪。

- URL 重写

  在每个 URL 末尾追加一些额外的数据来标识 session 会话。如，`http://w3cschool.cc/file.htm;sessionid=12345`

  URL 重写在浏览器不支持 cookie 时也能够很好地工作，但是它的缺点是会动态生成每个 URL 来为页面分配一个 session 会话 ID。

#### HttpSession

Servlet 使用 HttpSession 接口创建一个 HTTP 客户端和 HTTP 服务器之间的 session 会话。会话持续一个指定的时间段，跨多个连接或页面请求。

获取 HttpSession对象：`HttpSession session = request.getSession();`

getSession() 返回当前的 Session 会话，如果不存在则新建一个。

getSession(boolen isNew)

- true : 等同于 getSession()
- false : 如存在会话，则返回该会话，否则返回 null

#### HttpSession 常用方法

移除特定的属性：`public void removeAttribute(String name) `

删除整个 session 会话：`public void invalidate()`

设置 session 会话过期时间：`public void setMaxInactiveInterval(int interval) `



https://www.jianshu.com/p/2f7031a69f43

https://blog.csdn.net/weixin_41910244/article/details/80287527

cookie应用场景：①判断用户是否登录过网站； ②用来记录购物车或者记录用户使用偏好来制定推送；

session应用场景：①登录验证信息。

session cookie 有效期



forward sendRedirect

https://www.cnblogs.com/lesleysbw/p/6246546.html

https://www.cnblogs.com/wangshen31/p/8335343.html 注意分辨对错

servlet 监听器

https://blog.csdn.net/hbtj_1216/article/details/83015670

补充 https://www.cnblogs.com/zhangyanran/p/10082180.html

servlet 容器

servlet jsp 区别

https://www.w3cschool.cn/servlet/servlet-sxoy2p19.html

各对象生命周期

### Tips

#### 统计在线人数

1. 使用 HttpSessionListener

   HttpSession 创建/删除时更新人数。

   使用 ServletContext 存储在线人数属性值。

   `event.getSession().getServletContext()`

2. 使用 ServletContextListener, HttpSessionAttributeListener, HttpSessionListener

   HttpSession 创建/删除时设置/移除用户名属性。

   HttpSessionAttributeListener 中维护用户列表。
   
   ServletContextListener 创建用户列表。



```java
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            Properties properties=new Properties();
            properties.load(getServletContext().getResourceAsStream("/WEB-INF/dbcp.properties"));
            DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
            Connection conn = dataSource.getConnection();
            System.out.println(conn.toString());
            String sql = "select 1+1 as result;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int result = rs.getInt("result");
                out.println("result: " + result);
                System.out.println("result: " + result);
            }

            rs.close();
            pstmt.close();
            conn.close();
        }catch (Exception ex) {
            out.println(ex.getMessage());
        }
    	
    	// DispatcherServlet
//    	response.setContentType("text/plain;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            out.println("context: " + request.getContextPath());
//            out.println("request uri: " + request.getRequestURI());
//            out.println("params: " + request.getParameterMap());
//        }
    	
//    	response.setContentType("text/html;charset=UTF-8");
        // use jsp file
//        request.setAttribute("title", "Hello Servlet");
//        request.setAttribute("content", "你好");
//        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/hello.jsp");
//        rd.forward(request, response);
        
        // try-with-resource
//        try (PrintWriter out = response.getWriter()) {
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet HelloServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet HelloServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }
```

## web.xml

web.xml 文件是用来初始化配置信息（非必须）。比如 Welcome 页面、servlet、servlet-mapping、filter、listener、启动加载级别等。

> XML 标签 大小写敏感。

https://www.cnblogs.com/yqskj/articles/2233061.html

### 路径

#### classpath

classpath 指 **WEB-INF/classes/** 这个目录的路径。

classpath 目录下文件：

- src/main/resources 目录下的所有文件。
- 按包名存放的 class 文件。

示例：

`classpath*:**/mapper/mapping/*Mapper.xml`

`classpath:` 这种前缀，只能代表一个文件。`classpath*:` 则可以代表多个匹配的文件；

双星号 `** `表示在任意目录下，也就是说在 `WEB-INF/classes/` 下任意层的目录，只要符合后面的文件路径，都会被作为资源文件找到。



```xml
<welcome-file-list>
  <welcome-file>/haha.jsp</welcome-file>
</welcome-file-list>
```
version 3.1 -- Tomcat8.5, Servlet 3.1 ok

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
         http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
</web-app>
```

### 标签

#### load-on-startup

1. load-on-startup 元素标记容器是否应该在web应用程序启动的时候就加载这个servlet，(实例化并调用其init()方法)。
2. 它的值必须是一个整数，表示servlet被加载的先后顺序。
3. 如果该元素的值为负数或者没有设置，则容器会当Servlet被请求时再加载。
4. 如果值为正整数或者0时，表示容器在应用启动时就加载并初始化这个servlet，值越小，servlet的优先级越高，就越先被加载。值相同时，容器就会自己选择顺序来加载。



## Tomcat

### Install

> Tomcat 有安装版和解压版两种

#### 配置环境变量

1. 新建变量名：CATALINA_HOME，变量值：D:\WorkSpaceByJava\DevtTools\Apache-Tomcat-8.0.23

2. 打开 PATH，添加变量值：%CATALINA_HOME%\lib;%CATALINA_HOME%\bin

3. CMD 转入到 Tomcat 的 bin 目录。（CMD cd 到其他盘: cd /d e:\）

   执行 service.bat install

service.bat remove 可以移除注册服务

启动服务 net start Tomcat8	管理员身份运行
关闭服务 net stop  Tomcat8

> Tomcat8 即刚刚注册的 Tomcat 服务名称

[Eclipse开发JavaWeb项目配置Tomcat](https://blog.csdn.net/zs20082012/article/details/79138204)

### 目录结构

bin：目录存放一些启动运行Tomcat的可执行程序和相关内容。

conf：存放关于Tomcat服务器的全局配置。

lib：目录存放Tomcat运行或者站点运行所需的jar包，所有在此Tomcat上的站点共享这些jar包。

logs： 存放日志文件。

temp:  存放临时文件。

wabapps：目默认的站点根目录，可以更改。当服务器启动时，会加载所有这个目录下的应用。

work：目录用于在服务器运行时过度资源，简单来说，就是存储jsp、servlet翻译、编译后的结果。



WEB-INF目录结构

1，是Java的WEB应用的安全目录。客户端无法访问，只有服务端可以访问。

2，web.xml，项目部署文件

3，classes文件夹，用以放置 *.class文件

4，lib文件夹，用于存放需要的jar包



wabapp


web 项目路径

localhost:8080/项目名

即在 Tomcat 下部署的文件名

修改

- 在 Server 视图打开 Tomcat，Modules 中修改部署的项目信息。

> 或 项目右键 properties -> Web Project Settings -> Context root。

目录结构

WebRoot/WebContent	Web应用的根 "/"

![JavaWeb目录结构](.\web.assets\172304056712920.png)

浏览器或页面直接访问的资源不能放在 WEB-INF 内。

Web 项目中的相对路径


### 部署

Tomcat 部署 Java Web 应用程序有两种方式：静态部署和动态部署。

https://www.cnblogs.com/purplestone/p/3964207.html

### Tips

#### 修改 Tomcat 服务器默认端口

修改 `conf/server.xml` 文件如下 `port` 值。

```xml
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443" />
```



CATALINA_HOME与CATALINA_BASE

## HTTP

URI URL

### HTTP 报文首部

首部内容为客户端和服务器分别处理请求和响应提供的所需要的信息。

#### 请求报文

在请求中，HTTP 报文由方法、URI、HTTP 版本、HTTP首部字段等部分组成。

```http
GET / HTTP/1.1
Host: www.baidu.com
Connection: keep-alive
Cache-Control: max-age=0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36
Sec-Fetch-Mode: navigate
Sec-Fetch-User: ?1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3
Sec-Fetch-Site: none
Accept-Encoding: gzip, deflate, br
Accept-Language: zh-CN,zh;q=0.9
Cookie: BAIDUID=D11CD3FFD082C12C66B3B81ED5E9A029:FG=1; BIDUPSID=D11CD3FFD082C12C66B3B81ED5E9A029; PSTM=1562305725; BD_UPN=12314753; sugstore=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=1436_21116_29523_29518_29099_29567_29221_29460_22160; delPer=0; BD_CK_SAM=1; BD_HOME=0; pgv_pvi=1048900608; pgv_si=s1115573248; PSINO=5; COOKIE_SESSION=3677_0_9_9_27_113_0_4_9_8_967_23_0_0_2896_0_1566290217_0_1566352161%7C9%230_2_1564737807%7C1; H_PS_645EC=716d8pfa%2FmJsNN0HQRU3LkAqYBRNphh5R8TWKaiuLdTTtMKVf%2FjD1ojQJIM
```

#### 响应报文

在响应中，HTTP 报文由 HTTP 版本、状态码（数字和原因短语）、HTTP首部字段 3 部分构成。

```http
HTTP/1.1 200 OK
Bdpagetype: 1
Bdqid: 0xcbec3da800039cd1
Cache-Control: private
Connection: Keep-Alive
Content-Encoding: gzip
Content-Type: text/html
Cxy_all: baidu+9ad5c3ed247641f2cfbbd9665600887b
Date: Wed, 21 Aug 2019 02:21:20 GMT
Expires: Wed, 21 Aug 2019 02:20:53 GMT
Server: BWS/1.1
Set-Cookie: delPer=0; path=/; domain=.baidu.com
Set-Cookie: BDSVRTM=0; path=/
Set-Cookie: BD_HOME=0; path=/
Set-Cookie: H_PS_PSSID=1436_21116_29523_29518_29099_29567_29221_29460_22160; path=/; domain=.baidu.com
Strict-Transport-Security: max-age=172800
Vary: Accept-Encoding
X-Ua-Compatible: IE=Edge,chrome=1
Transfer-Encoding: chunked
```

#### HTTP 首部字段

> HTTP/1.1 及常用的首部字段

##### 首部字段结构

`首部字段名: 字段值`

当 HTTP 首部字段重复时，会根据浏览器内部处理逻辑优先处理第一次或最后出现的首部字段。

##### 首部字段类型

1. 通用首部字段（General Header Fields）

   请求和响应报文都会使用的首部。

2. 请求首部字段（Request Header Fields）

   补充请求的附加内容、客户端信息、响应内容相关优先级等信息。

3. 响应首部字段（Response Header Fields）

   补充响应的附加内容，也会要求客户端附加额外的内容信息。

4. 实体首部字段（Entity Header Fields）

   针对请求和响应报文的实体部分使用的首部，补充资源内容更新时间等郁实体有关的信息。



### Cookie

生命周期

Http请求时，URL中作为参数值的中文字符等会被编码

URLEncode

## Tips

### F12 

https://blog.csdn.net/weixin_41819731/article/details/80472232#commentBox

https://www.jianshu.com/p/d01eb74bf06c



ctrl+F5 刷新，不使用缓存