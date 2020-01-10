## OAuth 2.0

[OAuth 2.0](https://www.baidu.com/link?url=JMWEEQpXxa2MY_A4ns2tKt2FkH5EcEjYqVmfgjKeRZi&wd=&eqid=8ad7befc00147d66000000045e182548)

OAuth（Open standard for Authorization）是一个网络开放协议。为保证用户资源的安全授权提供了简易的标准。

oauth的好处：

- 允许用户授权第三方网站或应用，访问用户存储在其它网站上的资源，而不需要将用户名和密码提供给第三方网站或分享他们数据的内容
- **对于用户**：免去了繁琐的注册过程,降低了注册成本,提高了用户体验。
- **对于消费方**：简化自身会员系统的同时又能够带来更多的用户和流量。 
- **对于服务提供者**：围绕自身进行开发,增加用户粘性。

> oauth版本2.0即oauth2.0，不向下兼容。

### 协议流程

#### 角色

**resource owner**：资源所有者，这里可以理解为用户。

 **client**：客户端，可以理解为一个第三方的应用程序。

 **resource server**：资源服务器，它存储用户或其它资源。

 **authorization server**：授权服务器，它认证resource owner的身份，为 resource owner提供授权审批流程，并最终颁发授权令牌(Access Token)。

 **user-agent**：用户代理，这里可以理解为“浏览器”。

#### 抽象协议流程

![img](D:\GitHub\StudyNotes\notes\network\oauth.assets\963904-20160904103628678-329605467.png)

（A）用户打开客户端以后，客户端要求用户给予授权。

（B）用户同意给予客户端授权。

（C）客户端使用上一步获得的授权，向认证服务器申请令牌。

（D）认证服务器对客户端进行认证以后，确认无误，同意发放令牌。

（E）客户端使用令牌，向资源服务器申请获取资源。

（F）资源服务器确认令牌无误，同意向客户端开放资源。

### 应用程序注册

对于一个应用程序来说，如果它想要使用OAuth，那么首先它要在服务提供商那里注册(Application Registration)。

应用程序要提供：

- 应用程序名称(Application Name)
- 应用程序网站(Application Website)
- 回调URL(Redirect URI or Callback URL)

在用户同意授权(或者拒绝)之后，服务提供商会将用户重新导向这个Callback URL，这个Callback URL要来负责处理授权码或者访问令牌。

应用程序注册完成之后，服务提供商会颁发给应用程序一个“客户端认证信息(client credentials)”。Client Credential包括：

- **Client ID**
  - 提供给服务提供商，用于**识别**应用程序
  - 用于构建提供给用户请求授权的URL
- **Client Secret**
  - 提供给服务提供商，用于**验证**应用程序
  - 只有应用程序和服务提供商两者可知

### 授权模式

oauth2.0提供了四种授权模式：

- 授权码授权模式（Authorization Code Grant）
- 隐式授权模式（Implicit Grant）
- 密码授权模式（Resource Owner Password Credentials Grant）
- 客户端凭证授权模式（Client Credentials Grant）

#### 授权码授权模式（Authorization Code Grant）

##### 流程

![img](D:\GitHub\StudyNotes\notes\network\oauth.assets\963904-20160904103730949-1759423169.png) 

（A）用户访问客户端，客户端将用户引导向认证服务器。

（B）用户选择是否给予客户端授权。

（C）如用户给予授权，认证服务器将用户引导向客户端指定的redirection uri，同时加上授权码code。

（D）客户端收到code后，通过后台的服务器向认证服务器发送code和redirection uri。

（E）认证服务器验证code和redirection uri，确认无误后，响应客户端访问令牌（access token）和刷新令牌（refresh token）。

##### 请求示例

（A）步骤：客户端申请认证的URI　　

```
https://www.authorization.com/oauth/authorize?response_type=code&client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=read&state=xxx
```

- response_type：授权类型，必选项，此处的值固定为"code"
- client_id：客户端的ID，必选项
- redirect_uri：重定向URI，必选项
- scope：申请的权限范围，可选项
- state：任意值，认证服务器会原样返回，用于抵制CSRF(跨站请求伪造)攻击。

（C）步骤：认证服务器重定向跳转URI

```
https://CALLBACK_URL?code=SplxlOBeZQQYbYS6WxSbIA&state=xxx
```

- code：授权码，必选项。授权码有效期通常设为10分钟，一次性使用。该码与客户端ID、重定向URI以及用户，是一一对应关系。
- state：原样返回客户端传的该参数的值。

（D）步骤：客户端向认证服务器申请令牌

```
https://www.authorization.com/oauth/token?client_id=CLIENT_ID&grant_type=authorization_code&code=AUTHORIZATION_CODE&redirect_uri=CALLBACK_URL
```

- client_id：表示客户端ID，必选项。
- grant_type：表示使用的授权模式，必选项，此处的值固定为"authorization_code"。
- code：表示上一步获得的授权码，必选项。
- redirect_uri：表示重定向URI，必选项，且必须与A步骤中的该参数值保持一致。

（E）步骤：响应（D）步骤的数据

```json
{
       "access_token":"2YotnFZFEjr1zCsicMWpAA",
       "token_type":"example",
       "expires_in":3600,
       "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
       "example_parameter":"example_value"
}
```

- access_token：访问令牌，必选项。
- token_type：令牌类型，该值大小写不敏感，必选项。
- expires_in：过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
- refresh_token：更新令牌，用来获取下一次的访问令牌，可选项。
- scope：权限范围，如果与客户端申请的范围一致，此项可省略。

##### 使用场景

- 授权码模式是最常见的一种授权模式，在oauth2.0内是最安全和最完善的。
- 适用于所有有Server端的应用，如Web站点、有Server端的手机客户端。 
- 可以得到较长期限授权。

#### 隐式授权模式（Implicit Grant）

##### 流程

![img](D:\GitHub\StudyNotes\notes\network\oauth.assets\963904-20160904105328094-1969461112.png)

（A）客户端将用户引导向认证服务器。

（B）用户决定是否给于客户端授权。

（C）假设用户给予授权，认证服务器将用户导向客户端指定的”重定向URI"，并在URI的Hash部分包含了访问令牌。

（D）浏览器向资源服务器发出请求，其中不包括上一步收到的Hash值。

（E）资源服务器返回一个网页，其中包含的代码可以获取Hash值中的令牌。

（F）浏览器执行上一步获得的脚本，提取出令牌。

（G）浏览器将令牌发给客户端。

##### 请求示例

（A）步骤：客户端发出请求

```
https://www.authorization.com/authorize?response_type=token&client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=read&state=xxx
```

- response_type：授权类型，此处的值固定为"token"，必选项。
- client_id：客户端的ID，必选项。
- redirect_uri：重定向的URI，必选项。
- scope：权限范围，可选项。
- state: 任意值，认证服务器会原样返回,用于抵制CSRF(跨站请求伪造)攻击。

（C）步骤：认证服务器响应客户端的请求url

```
https://CALLBACK_URL#access_token =ACCESS_TOKEN&state=xyz&token_type=example&expires_in=3600&state=xxx
```

- access_token：访问令牌，必选项。
- token_type：令牌类型，该值大小写不敏感，必选项。
- expires_in：过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
- scope：权限范围，如果与客户端申请的范围一致，此项可省略。
- state：如果客户端的请求中包含这个参数，认证服务器的回应也必须一模一样包含这个参数。

##### 使用场景

- 适用于所有无Server端配合的应用
- 如手机/桌面客户端程序、浏览器插件。
- 基于JavaScript等脚本客户端脚本语言实现的应用。

注意：因为Access token是附着在 redirect_uri 上面被返回的，所以这个 Access token就可能会暴露给资源所有者或者设置内的其它方（对资源所有者来说，可以看到redirect_uri，对其它方来说，可以通过监测浏览器的地址变化来得到 Access token）。

#### 密码模式（Resource Owner Password Credentials Grant）

##### 流程

![img](D:\GitHub\StudyNotes\notes\network\oauth.assets\963904-20160904113855317-2042330087.png)

（A）用户向客户端提供用户名和密码。

（B）客户端将用户名和密码发给认证服务器，向后者请求令牌。

（C）认证服务器确认无误后，向客户端提供访问令牌。

##### 请求示例

（B）步骤：客户端发出https请求

```
https://www.authorization.com/token?grant_type=password&username=USERNAME&password=PASSWORD&client_id=CLIENT_ID
```

- grant_type：授权类型，此处的值固定为"password"，必选项。
- username：用户名，必选项。
- password：用户的密码，必选项。
- scope：权限范围，可选项。

（C）步骤：向客户端响应（B）步骤的数据

```json
    {
       "access_token":"2YotnFZFEjr1zCsicMWpAA",
       "token_type":"example",
       "expires_in":3600,
       "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA"
     }
```

- access_token：访问令牌，必选项。
- token_type：令牌类型，该值大小写不敏感，必选项。
- expires_in：过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
- refresh_token：更新令牌，用来获取下一次的访问令牌，可选项。

##### 使用场景

- 这种模式适用于用户对应用程序高度信任的情况。比如是用户操作系统的一部分。
- 认证服务器只有在其他授权模式无法执行的情况下，才能考虑使用这种模式。

#### 客户端凭证模式（Client Credentials Grant）

##### 流程介绍

![img](D:\GitHub\StudyNotes\notes\network\oauth.assets\963904-20160904114645553-50785995.png)　　

（A）客户端向认证服务器进行身份认证，并要求一个访问令牌。

（B）认证服务器确认无误后，向客户端提供访问令牌。

##### 请求示例

（A）步骤：客户端发送https请求

```
https://www.authorization.com/token?grant_type=client_credentials&client_id=CLIENT_ID
```

- granttype：表示授权类型，此处的值固定为"client_credentials"，必选项。
- scope：表示权限范围，可选项。

（B）步骤：向客户端响应（A）步骤的数据

```json
     {
       "access_token":"2YotnFZFEjr1zCsicMWpAA",
       "token_type":"example",
       "expires_in":3600,
       "example_parameter":"example_value"
     }
```

- access_token：访问令牌，必选项。
- token_type：令牌类型，该值大小写不敏感，必选项。
- expires_in：过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
- example_parameter：其它参数，可选项。 

##### 使用场景

- 客户端模式应用于应用程序想要以自己的名义与授权服务器以及资源服务器进行互动。
- 例如使用了第三方的静态文件服务

#### 刷新TOKEN

授权流程最终的目的是要获取用户的授权令牌（access_token）。而且授权令牌（access_token）的权限也非常之大，

所以在协议中明确表示要设置授权令牌（access_token）的有效期。那么当授权令牌（access_token）过期时，就需要刷新token。

##### 流程介绍

![img](D:\GitHub\StudyNotes\notes\network\oauth.assets\963904-20160904115728036-456177447.png)

（A）--（D）通过授权流程获取access_token，并调用业务api接口。

（F）当调用业务api接口时响应“Invalid Token Error”时。

（G）调用刷新access_token接口，使用参数refresh_token（如果平台方提供，否则需要用户重新进行授权流程）。

（H）响应最新的access_token及refresh_token。

##### 请求示例

（G）步骤：客户端调用刷新token接口

```
https://www.authorization.com/oauth/token?grant_type=refresh_token&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&refresh_token=REFRESH_TOKEN
```

- client_id：客户端的ID，必选项。
- client_secret：客户端的密钥，必选项。
- grant_type：表示使用的授权模式，此处的值固定为"refreshtoken"，必选项。
- refresh_token：表示早前收到的更新令牌，必选项。

（H）步骤：响应客户端数据

```json
{
       "access_token":"2YotnFZFEjr1zCsicMWpAA",
       "token_type":"example",
       "expires_in":3600,
       "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
       "example_parameter":"example_value"
     }
```

- access_token：访问令牌，必选项。
- token_type：令牌类型，该值大小写不敏感，必选项。
- expires_in：过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
- refresh_token：更新令牌，用来获取下一次的访问令牌，可选项。
- scope：权限范围，如果与客户端申请的范围一致，此项可省略。

说明：建议将access_token和refresh_token的过期时间保存下来，每次调用平台方的业务api前先对access_token和refresh_token进行一下时间判断，如果过期则执行刷新access_token或重新授权操作。refersh_token如果过期就只能让用户重新授权。

### OAuth2.0安全性

oauth2.0协议本身设计是安全的，但在开发过程中没有严格按照协议进行开发，或对流程中的参数校验不完整，就会造成一些流程上的漏洞。

#### 可能的安全漏洞

##### 授权码授权模式

漏洞：针对应用方csrf劫持第三方账号

**漏洞成因**：

主要的漏洞原因是redirect_uri中的code参数没有和当前客户端的状态绑定，攻击者可以通过发送预先获取好的code参数到受害者电脑，导致导致受害者当前登录的应用方账号被绑定到攻击者指定的平台方（如微博）帐号上。

**解决方案**：

应用方要预防这种csrf劫持账号，加入state参数是比较简单的通行方法。根据rfc6749 章节10.12，该值既不可预测，又必须可以证明应用（client）和和当前第三方网站的登录认证状态存在关联（如果存在过期时间更好）。一种简单的方法是：随机算一个字符串，然后保存在session，回调时检查state参数和session里面的值。

而平台方也要在回调时，支持应用方的state参数（当然如果允许redirect_uri参数中带应用方自己的防csrf参数，其实也可以）。

同时在使用code获取access_token成功后，进行access_token与授权用户绑定时，尽量不要直接与当前登录用户进行绑定。

但严格来讲，仅有state参数，其实还不够，还需要结合rfc6749 章节3.3（发放有业务接口仅限范围的令牌）提到的防御手段。

##### 隐式授权模式

漏洞：针对存储型xss劫持用户的access_token

**漏洞成因**：

隐式授权模式场景的特点是授权验证时只需要client_id和redirect_uri这两个参数作应用标识，返回的时候也就直接在uri片段中返回access token。那就是只要合作方网站、平台方网站甚至是浏览器三者之一有任何一个xss，就很容易通过xss获取到access token，然后以此攻击受害者的平台方账号。

**解决方案**：

解决这个问题，rfc6749第10.16小节就只是语焉不详的说需要额外的安全措施，这也反映了这种场景的防御难处。

有一种方案，平台方必须对应用方的应用强制进行分类，即应用方在申请client_id（即应用appkey）时，需要选择属于哪类应用；平台方再根据分类开放对应权限，并且进行特定的防御和监控措施。

##### 密码模式

威胁：应用方代码本身存在后门，或应用方服务器被入侵。难以保证应用方所在环境在传输过程中不被嗅探。

**解决方案**：

（1）对于外部合作方，最大限度取消且不使用密码授权的授权。对于因为合同原因而导致无法取消的情况，需要加强监控。

（2）内部应用一样要划分等级，无必要的应用一律禁止使用密码授权模式。

#### 安全性建议

##### 资源提供方

- 对client_id和回调地址做严格校验 
- 获取access token的code仅能使用一次，且与授权用户关联
- 尽量避免直接读取当前用户session进行绑定 
- 有效使用client_secret参数

##### 资源使用方

- 使用Authorization Code方式进行授权 
- 授权过程使用state随机哈希,并在服务端进行判断 
- 尽量使用HTTPS保证授权过程的安全性 