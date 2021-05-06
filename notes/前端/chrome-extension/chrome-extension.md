# Chrome Extension

## Reference

- [【干货】Chrome插件(扩展)开发全攻略 - 我是小茗同学 - 博客园](https://www.cnblogs.com/liuxianan/p/chrome-plugin-develop.html)
- [综述--扩展开发文档](http://open.chrome.360.cn/extension_dev/overview.html)
- https://developer.chrome.com/extensions/tut_migration_to_manifest_v2.html

## Question

- [json - Chrome extension "Refused to evaluate a string as JavaScript because 'unsafe-eval' - Stack Overflow](https://stackoverflow.com/questions/24798669/chrome-extension-refused-to-evaluate-a-string-as-javascript-because-unsafe-eva)

  拒绝eval求值问题，这是浏览器自带的"网页安全政策"（Content Security Policy）导致的；浏览器默认的Content-Security-Policy的安全政策时“ default-src ‘self’ ”。我们可以通过manifest.json重新配置Content Security Policy的配置开启eval功能。

  ```json
  {
      ...
      "content_security_policy": "script-src 'self' 'unsafe-eval'; object-src 'self'"
  }
  ```

- [解决Chrome extension 报错 The message port closed before a response was received_m0_37729058的博客-CSDN博客](https://blog.csdn.net/m0_37729058/article/details/89186257)

