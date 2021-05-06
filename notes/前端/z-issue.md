## 窗口间通信

[window.postMessage](https://developer.mozilla.org/zh-CN/docs/Web/API/Window/postMessage)

### 打开新窗口

[Window.open()](https://developer.mozilla.org/zh-CN/docs/Web/API/Window/open)

## 鼠标键盘事件

- [鼠标事件 - Web API 接口参考 | MDN](https://developer.mozilla.org/zh-CN/docs/Web/API/MouseEvent)
- [KeyboardEvent - Web API 接口参考 | MDN](https://developer.mozilla.org/zh-CN/docs/Web/API/KeyboardEvent)
- [js获取鼠标位置的各种方法 - 玲儿灵 - 博客园](https://www.cnblogs.com/jymz/p/3987794.html)
  - [javascript - How to get the mouse position without events (without moving the mouse)? - Stack Overflow](https://stackoverflow.com/questions/2601097/how-to-get-the-mouse-position-without-events-without-moving-the-mouse)
  - [How to get mouse position in jQuery without mouse-events? - Stack Overflow](https://stackoverflow.com/questions/4517198/how-to-get-mouse-position-in-jquery-without-mouse-events)

## exports/require & export/import

- [一看就懂的module.exports/exports与module.export/export default {}  - SegmentFault 思否](https://segmentfault.com/a/1190000019399632)
- [Js模块打包 exports和require 与 export和import 的用法和区别 - 浅笑· - 博客园](https://www.cnblogs.com/qianxiaox/p/14017376.html)
- [import、require、export、module.exports 混合使用详解 - SegmentFault 思否](https://segmentfault.com/a/1190000012386576)
- [require，import区别？ - 知乎](https://www.zhihu.com/question/56820346)
- [exports 和 module.exports 的区别 - CNode技术社区](https://cnodejs.org/topic/5231a630101e574521e45ef8)

## For Loop

## 类型判断

- [如何判断JS类型 - 知乎](https://zhuanlan.zhihu.com/p/89238840)
- [JavaScript学习总结(六)——JavaScript判断数据类型总结 - 孤傲苍狼 - 博客园](https://www.cnblogs.com/xdp-gacl/p/3490065.html)

## Pormise

- [Promise - 廖雪峰的官方网站](https://www.liaoxuefeng.com/wiki/1022910821149312/1023024413276544)
- [使用 Promise - JavaScript | MDN](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Guide/Using_promises)

## 时间格式化

```javascript
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };

    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(
                RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}
```





clearfix



cookie 跨域 

.withCredentials()方法可以激活发送原始cookie的能力,不过只有在Access-Control-Allow-Origin不是一个通配符(*),并且Access-Control-Allow-Credentials为’true’的情况下才行。

SameSite

## Tips

- [浅谈js防抖和节流 - SegmentFault 思否](https://segmentfault.com/a/1190000018428170)
- [js的几种sleep函数](https://juejin.cn/post/6844903808410058760)

