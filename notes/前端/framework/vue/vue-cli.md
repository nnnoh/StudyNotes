# Getting Started

官网：[Vue CLI](https://cli.vuejs.org/zh/)

## 创建项目

可以使用下列任一命令安装 Vue CLI 包：

```bash
npm install -g @vue/cli
# OR
yarn global add @vue/cli
```

运行以下命令来创建一个新项目：

```bash
vue create hello-world
```

### 基本目录结构

```
./
│  .gitignore
│  babel.config.js
│  package-lock.json
│  package.json
│  README.md
│
├─node——modules
│      ...
│
├─public  //静态文件放置位置
│      favicon.ico
│      index.html
│
└─src
    │  App.vue
    │  main.js  // 入口文件
    │
    ├─assets
    │      logo.png
    │
    └─components
            HelloWorld.vue
```

### 运行

```
npm run start
```

### 编译打包

```
npm run build
```

