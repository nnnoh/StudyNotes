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

  当样式仅bor需要在一个元素上应用一次时。将表现和内容混杂在一起，内联样式会损失掉样式表的许多优势。

  要使用内联样式，需在相关的标签内使用样式（style）属性。style 属性可以包含任何 CSS 属性。

  `<p style="color:sienna;margin-left:20px">这是一个段落。</p>`

**导入样式**

在 .css 文件或 `<style>` 标签中使用 `@import url("css/style.css")` 可引入另一个 css 样式表。

> 属性值不需要 "" 包含。

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

![selector](../../img/css.assets/selector.gif)

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
- 对于`class="a b"`，使用 `.a.b{}` 选中。

##### 元素选择器



#### 层次选择器语法

| **选择器** | **类型**                 | **功能描述**                                                 |
| ---------- | ------------------------ | ------------------------------------------------------------ |
| E  F       | 后代选择器（包含选择器） | 选择匹配的F元素，且匹配的F元素被包含在匹配的E元素内          |
| E>F        | 子选择器                 | 选择匹配的F元素，且匹配的F元素所匹配的E元素的子元素（仅下一个层级） |
| E+F        | 相邻兄弟选择器           | 选择匹配的F元素，且匹配的F元素紧位于匹配的E元素的后面        |
| E~F        | 通用选择器               | 选择匹配的F元素，且位于匹配的E元素后的所有匹配的F元素        |

> 选择器中空格为后代选择器，因此注意不要添加无用空格

#### 动态伪类选择器

| **选择器** | **类型**       | **功能描述**                                                 |
| ---------- | -------------- | ------------------------------------------------------------ |
| E:link     | 链接伪类选择器 | 选择匹配的E元素，而且匹配元素被定义了超链接并未被访问过。常用于链接描点上 |
| E:visited  | 链接伪类选择器 | 选择匹配的E元素，而且匹配元素被定义了超链接并已被访问过。常用于链接描点上 |
| E:active   | 用户行为选择器 | 选择匹配的E元素，且匹配元素被激活。常用于链接描点和按钮上    |
| E:hover    | 用户行为选择器 | 选择匹配的E元素，且用户鼠标停留在元素E上。IE6及以下浏览器仅支持a:hover |
| E:focus    | 用户行为选择器 | 选择匹配的E元素，而且匹配元素获取焦点                        |

#### 否定伪类选择器

- **E:not(F)**  匹配所有除元素F外的E元素

#### 结构伪类选择器

| **选择器**            | **功能描述**                                                 |
| --------------------- | ------------------------------------------------------------ |
| E:first-child         | 作为父元素的第一个子元素的元素E。与E:nth-child(1)等同        |
| E:last-child          | 作为父元素的最后一个子元素的元素E。与E:nth-last-child(1)等同 |
| E:root                | 选择匹配元素E所在文档的根元素。在HTML文档中，根元素始终是html，此时该选择器与html类型选择器匹配的内容相同 |
| E F:nth-child(n)      | 选择父元素E的第n个子元素F。其中n可以是整数（1，2，3）、关键字（even，odd）、可以是公式（2n+1）,而且n值起始值为1，而不是0. |
| E F:nth-last-child(n) | 选择父元素E的倒数第n个子元素F。此选择器与E:nth-child(n)选择器计算顺序刚好相反，但使用方法都是一样的，其中：nth-last-child(1)始终匹配最后一个元素，与last-child等同 |
| E:nth-of-type(n)      | 选择父元素内具有指定类型的第n个E元素                         |
| E:nth-last-of-type(n) | 选择父元素内具有指定类型的倒数第n个E元素                     |
| E:first-of-type       | 选择父元素内具有指定类型的第一个E元素，与E:nth-of-type(1)等同 |
| E:last-of-type        | 选择父元素内具有指定类型的最后一个E元素，与E:nth-last-of-type(1)等同 |
| E:only-child          | 选择父元素只包含一个子元素，且该子元素匹配E元素              |
| E:only-of-type        | 选择父元素只包含一个同类型子元素，且该子元素匹配E元素        |
| E:empty               | 选择没有子元素的元素，而且该元素也不包含任何文本节点         |

- `ul>li:nth-child(3)` 表达的并不是一定选择列表ul元素中的第3个子元素li，仅有列表ul中第3个li元素前不存在其他的元素，命题才有意义，否则不会改变列表第3个li元素的样式。
- `:nth-child(n)`  中参数只能是n，不可以用其他字母代替。
- `:nth-child(odd)` 选择的是奇数项，而使用:nth-last-child(odd) 选择的却是偶数项

::before 

::after

https://www.cnblogs.com/wonyun/p/5807191.html 有错？

: :: 区别

分组选择器 嵌套选择器

#### 属性选择器

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

##### background-size

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

#### 边框

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

利用 border 属性可绘制简单图形（标签内容为空）。

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

#### Box Model

- **Margin（外边距）** - 清除边框区域。Margin没有背景颜色，它是完全透明
- **Border（边框）** - 边框周围的填充和内容。边框是受到盒子的背景颜色影响
- **Padding（内边距）** - 清除内容周围的区域。会受到框中填充的背景颜色影响
- **Content（内容）** - 盒子的内容，显示文本和图像

一个盒子实际所占有的宽度（或高度）是由“内容+左右内边距+左右边框+左右外边距”组成的。在CSS中可以通过设置width和height的值来控制内容所占矩形的大小。

##### 浏览器的兼容性问题

根据 W3C 的规范，元素内容占据的空间是由 width 属性设置的，而内容周围的 padding 和 border 值是另外计算的。不幸的是，IE5.X 和 6 在怪异模式中使用自己的非标准模型。这些浏览器的 width 属性不是内容的宽度，而是内容、内边距和边框的宽度的总和。

虽然有方法解决这个问题。但是目前最好的解决方案是回避这个问题。也就是，不要给元素添加具有指定宽度的内边距，而是尝试将内边距或外边距添加到元素的父元素和子元素。

`box-sizing: border-box;`  用处

IE8 及更早IE版本不支持 填充的宽度和边框的宽度属性设。

解决IE8及更早版本不兼容问题可以在HTML页面声明 \<!DOCTYPE html>即可。

list:

top/bottom  left/right

display (block?)

position 是否占据原空间

float

overflow

box-shadow 和 border 区别 border会占空间

cursor

outline

clear

box-sizing

boder-radius

width:auto block

float 对 block 的影响

 width:100% 会独占一行

对齐 参照物

https://zhidao.baidu.com/question/384080665.html

> 只有当该元素的宽度小于父元素的宽度时候，才能看出左对齐，右对齐和居中对齐的效果。?

当该元素被设为浮动时，该元素的width就变成了内容的宽度了，由内容撑开，也就是所谓的有了包裹性。

> 浮动元素会自动生成一个块级框。

overflow |   position:absolute | float:left/right都可以产生包裹性，替换元素也同样具有包裹性。

*|position:relavtive|相对定位      占原来的位置，不能实现模式的转化，即不具有包裹性。

所以在具有包裹性的元素上不可以利用width : auto；来让元素宽度自适应浏览器宽。

https://blog.csdn.net/liangde123/article/details/51974724

https://www.jb51.net/css/594769.html

https://www.jianshu.com/p/091b4ffb10b0

https://blog.csdn.net/tt_twilight/article/details/72804104#commentBox

table 

https://blog.csdn.net/zhw0596/article/details/86063093

https://blog.csdn.net/Yocan_ture/article/details/80698108



z-index

zoom:1

display:inline-block元素间会产生多余空白。解决方法：父元素定义font-size:0 去掉行内块元素水平方向空白；子元素定义vertical-align 属性去掉行内块元素垂直方向空白

### 布局模型

- 流动模型（Flow）
- 浮动模型 (Float)
- 层模型（Layer）

display	
float	
clear	
visibility
overflow	
overflow-x
overflow-y

@media

### 动画

transtion

### 函数

attr(attribute-name)



-moz- 代表firefox浏览器私有属性

-ms- 代表ie浏览器私有属性

-webkit- 代表safari、chrome私有属性

-o- 代表Opera

css 标准文本流

对齐方式

line-height

transform

vertical-align



浏览器默认样式 user agent stylesheet

不同浏览器甚至同一浏览器不同版本的默认样式是不同的 。

解决方法为引入**reset.css**或**normalize.css**
**reset.css**是将**所有**的浏览器样式重置，保证各个浏览器样式渲染的一致性。
地址: https://meyerweb.com/eric/tools/css/reset/
**normalize.css**保留不同浏览器同标签相同的默认值，只重置不同默认样式的差异，可理解为reset.css的升级版。
github地址:https://github.com/necolas/normalize.css

### 响应式web设计



@

### Tips

##### 标签 a

改变 a 标签样式，可实现在鼠标移动到选项时，修改背景颜色。

背景颜色添加到链接中显示链接的区域

注意,整个区域是可点击的链接,而不仅仅是文本

示例：

```css
li a {
    display: block;
    color: #000;
    padding: 8px 16px;
    text-decoration: none;
}
 
/* 鼠标移动到选项上修改背景颜色 */
li a:hover {
    background-color: #555;
    color: white;
}
```

