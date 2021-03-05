# Layui

## 开始使用

[Layui 开发使用文档](https://www.layui.com/doc/)

Layui 目录结构如下

```
  ├─css //css目录
  │  │─modules //模块css目录（一般如果模块相对较大，我们会单独提取，比如下面三个：）
  │  │  ├─laydate
  │  │  ├─layer
  │  │  └─layim
  │  └─layui.css //核心样式文件
  ├─font  //字体图标目录
  ├─images //图片资源目录（目前只有layim和编辑器用到的GIF表情）
  │─lay //模块核心目录
  │  └─modules //各模块组件
  │─layui.js //基础核心库
  └─layui.all.js //包含layui.js和所有模块的合并文件
```

获得 layui 后，将其完整地部署到你的项目目录（或静态资源服务器），你只需要引入下述两个文件：

- ./layui/css/layui.css
- ./layui/layui.js //提示：如果是采用非模块化方式（最下面有讲解），此处可换成：./layui/layui.all.js

### 模块化

layui模块的定义（入口文件）

```
layui.define(['layer', 'form'], function(exports){
  var layer = layui.layer
  ,form = layui.form;
  layer.msg('Hello World');
  exports('index', {}); //这是模块输出的核心，模块名必须和use时的模块名一致
});     
```
layui模块的使用

```
layui.use(['mod1', 'mod2'], function(args){
  var mod = layui.mod1;
  //……
}); 
```

推荐遵循 layui 的模块规范建立一个入口文件，并通过 layui.use() 方式来加载该入口文件

### 全模块

layui 的「模块化加载」十分适用于开发环境，它方便团队开发和代码调试。但对于「线上环境」，更推荐采用「全模块加载」，即直接引入 `layui.all.js`，它包含了 layui 所有的内置模块，且无需再通过 `layui.use()` 方法加载模块，直接调用即可。


## 基础支撑

### 全局配置
```JavaScript
layui.config({
  dir: '/res/layui/' //layui.js 所在路径（注意，如果是script单独引入layui.js，无需设定该参数。），一般情况下可以无视
  ,version: false //一般用于更新模块缓存，默认不开启。设为true即让浏览器不缓存。也可以设为一个固定的值，如：201610
  ,debug: false //用于开启调试模式，默认false，如果设为true，则JS模块的节点会保留在页面
  ,base: '' //设定扩展的Layui模块的所在目录，一般用于外部模块扩展
});
```

### 定义模块

`layui.define([mods], callback)`

通过该方法可定义一个 Layui模块。参数mods是可选的，用于声明该模块所依赖的模块，可以传一个字符型的模块名，或者数组。callback即为模块加载完毕的回调函数，它返回一个exports参数，用于输出该模块的接口。

exports 是一个函数，它接受两个参数，第一个参数为模块名，第二个参数为模块接口，模块接口可以是函数，或者对象。当使用 exports 声明了一个模块后，该模块就会注册到layui对象下，就可以在外部使用该模块。

### 加载所需模块

`layui.use([mods], callback)`

mods里面必须是一个合法的模块名，不能包含目录。

该方法的函数其实返回了所加载的模块接口，所以也可以不通过 layui 对象赋值获得接口

### 动态加载CSS

`layui.link(href)`

href即为css路径。该方法并非是使用Layui所必须的，它一般只是用于动态加载你的外部CSS文件。

### 本地存储

本地存储是对 localStorage 和 sessionStorage 的友好封装，可更方便地管理本地数据。

- localStorage 持久化存储：*layui.data(table, settings)，数据会永久存在，除非物理删除。*
- sessionStorage 会话性存储：*layui.sessionData(table, settings)*，页面关闭后即失效。注：*layui 2.2.5 新增*

其中参数 *table* 为表名，*settings*是一个对象，用于设置key、value。

使用示例：

```javascript
layui.code
//【增】：向test表插入一个nickname字段，如果该表不存在，则自动建立。
layui.data('test', {
  key: 'nickname'
  ,value: '贤心'
});
 
//【删】：删除test表的nickname字段
layui.data('test', {
  key: 'nickname'
  ,remove: true
});
layui.data('test', null); //删除test表
  
//【改】：同【增】，会覆盖已经存储的数据
  
//【查】：向test表读取全部的数据
var localTest = layui.data('test');
console.log(localTest.nickname); 
```

### 获取设备信息

`layui.device(key)` ，参数key是可选的

由于Layui的一些功能进行了兼容性处理和响应式支持，因此该方法同样发挥了至关重要的作用。尤其是在layui mobile模块中的作用可谓举足轻重。

```javascript
var device = layui.device();	//可根据不同的设备返回下述不同的信息
/*
{  
	os: "windows" //底层操作系统，windows、linux、mac等  
	,ie: false //ie6-11的版本，如果不是ie浏览器，则为false  
	,weixin: false //是否微信环境  
	,android: false //是否安卓系统  
	,ios: false //是否ios系统
}
*/
```

有时你的App可能会对userAgent插入一段特定的标识，譬如：
`Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 myapp/1.8.6 Safari/537.36`

要验证当前的WebView是否在你的App环境，即可通过上述的*myapp*（即为Native给Webview插入的标识，可以随意定义）来判断。

```javascript
var device = layui.device('myapp');
if(device.myapp){
  alert('在我的App环境');
} 
```

### 其他

### 第三方支撑

Layui**部分模块**依赖*jQuery*（比如layer），但是你并不用去额外加载jQuery。Layui已经将jQuery最稳定的一个版本改为Layui的内部模块，当你去使用 layer 之类的模块时，它会首先判断你的页面是否已经引入了jQuery，如果没有，则加载内部的jQuery模块，如果有，则不会加载。

另外，我们的图标取材于阿里巴巴矢量图标库（*iconfont*），构建工具采用 *Gulp* 。除此之外，不依赖于任何第三方工具。

> layui.use 异步加载，不需放置在ready事件回调函数中。

## Tips

layui.extend

## Layui 模板

### nepadmin

nepadmin 单页面后台模版，基于 layui 2.4.0。

遵循原生 HTML/CSS/JS 的书写与组织形式，上手容易，拿来即用。
最低兼容到IE8浏览器。

github: https://github.com/fanjyy/nepadmin


