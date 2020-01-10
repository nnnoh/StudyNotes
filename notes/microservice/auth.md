## 微服务框架下身份认证

目前实现统一身份认证和授权的技术手段较多，总体可以归纳为以下两类：

1. 传统的 Cookie + Session 解决方案，有状态会话模式；
2. 基于令牌(token)的解决方案，无状态交互模式。

具体有：

- 分布式 Session
- OAuth2.0
- JWT
- CAS

### 示例

<div class="model">
    <div class="inner">
        桌面
    </div>
    <div class="inner">
        桌面
    </div>
</div>
<style>
    .inner{
        float:left;
        border:1px solid;
        margin: 5px;
    }
    .model{
        border:1px dashed;
        margin: 5px;
    }
</style>

https://blog.csdn.net/weixin_33829657/article/details/91645738

