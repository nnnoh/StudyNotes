# Electron-vue

## Electron 介绍

electron由Node.js+Chromium+Native APIs构成。你可以理解成，它是一个得到了Node.js和基于不同平台的Native APIs加强的Chromium浏览器，可以用来开发跨平台的桌面级应用。

它的开发主要涉及到两个进程的协作——Main（主）进程和Renderer（渲染）进程。简单的理解两个进程的作用：

1. Main进程主要通过Node.js、Chromium和Native APIs来实现一些系统以及底层的操作，比如创建系统级别的菜单，操作剪贴板，创建APP的窗口等。
2. Renderer进程主要通过Chromium来实现APP的图形界面——就是平时我们熟悉的前端开发的部分，不过得到了electron给予的加强，一些Node的模块（比如fs）和一些在Main进程里能用的东西（比如Clipboard）也能在Render进程里使用。
3. Main进程和Renderer进程通过`ipcMain`和`ipcRenderer`来进行通信。通过事件监听和事件派发来实现两个进程通信，从而实现Main或者Renderer进程里不能实现的某些功能。

![electron](https://blog-1251750343.cos.ap-beijing.myqcloud.com/8700af19ly1fncq342rk8j20cs0d63zd)

**优点**：

1. 从上述介绍可以发现，除了不同平台Native APIs不同以外，Node.js和Chromium都是跨平台的工具，这也为electron生来就能做跨平台的应用开发打下基础。
2. 开发图形界面前所未有的容易——比起C#\QT\MFC等传统图形界面开发技术，通过前端的图形化界面开发明显更加容易和方便。得益于Chromium，这种开发模式得以实现。
3. 成熟的社区、活跃的核心团队，大部分electron相关的问题你可以在社区、github issues、Stack Overflow里得到答案。开发的障碍进一步降低。

**缺点**：

1. 应用体积过大。由于内部包装了Chromium和Node.js，使得打包体积（使用`electron-builder`）在mac上至少是45M+起步，在windows上稍微好一点，不过也要35M+起步。不过相比早期打包体积100M+起步来说，已经好了不少。不过解压后安装依然是100M+起步。
2. 受限于Node.js和Native APIs的一些支持度的问题，它依然有所局限。一些功能依然无法实现。比如无法获取在系统文件夹里选中的文件，而必须调用web的File或者node的fs接口才可以访问系统文件。
3. 应用性能依旧是个问题。所以做轻量级应用没问题，重量级应用尤其是CPU密集型应用的话很是问题。

## electron-vue 介绍

vue只是在renderer进程里使用的框架，不涉及到main进程。因此其他的前端开发框架同样适用。

[electron-vue](https://github.com/SimulatedGREG/electron-vue)，这个是开发者[SimulatedGREG](https://github.com/SimulatedGREG)参考vue-cli的webpack模板骨架搭建的electron和vue结合的开发脚手架。

## 参考

- [Tag: Electron-vue | MARKSZのBlog](https://molunerfinn.com/tags/Electron-vue/page/2/)

