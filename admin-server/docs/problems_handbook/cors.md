服务已经对开发环境开启了cors访问, 
```java
@Configuration
public class ShiroConfig {

    // ... 省略其他配置

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        if (!SpringUtil.isInProduction(applicationContext)) {
            LOGGER.info("进行非生产模式CORS配置");
            config.addAllowedOrigin("*");
            config.setAllowCredentials(true);
            config.addAllowedMethod("*");
            config.addAllowedHeader("*");
            config.addExposedHeader("Set-Cookie");
//        config.setMaxAge();
        }

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }
}

```
前端也开启了cookie访问
```javascript
if (process.env.NODE_ENV === 'development') {
  service.defaults.baseURL = 'http://localhost:9900/'
  service.defaults.withCredentials = true
}
```

但是在某些接口仍然报错
```
2020-03-31 14:49:22.044 [http-nio-9900-exec-6] ERROR c.wangming.adminserver.util.UserUtil(277) - [admin, /api/user/authority/updateRoleBackendPermission]  获取UserName失败, 没找到session
2020-03-31 14:49:22.044 [http-nio-9900-exec-6] INFO  c.w.a.shiro.ApiAccessControlFilter(155) - [, /api/user/authority/updateRoleBackendPermission]  开始进行鉴权
2020-03-31 14:49:22.044 [http-nio-9900-exec-6] INFO  c.w.a.shiro.ApiAccessControlFilter(167) - [, /api/user/authority/updateRoleBackendPermission] 鉴权完成, isPermitted:false, isAuthenticated:false
2020-03-31 14:49:22.044 [http-nio-9900-exec-6] INFO  c.w.a.shiro.ApiAccessControlFilter(155) - [, /api/user/authority/updateRoleBackendPermission]  访问被拒绝
2020-03-31 14:49:22.044 [http-nio-9900-exec-6] INFO  c.w.a.config.HttpTraceLogFilter(162) - [, /api/user/authority/updateRoleBackendPermission] request wrapper CharacterEncoding: UTF-8
2020-03-31 14:49:22.045 [http-nio-9900-exec-6] INFO  c.w.a.config.HttpTraceLogFilter(162) - [, /api/user/authority/updateRoleBackendPermission] response wrapper CharacterEncoding: ISO-8859-1
2020-03-31 14:49:22.045 [http-nio-9900-exec-6] INFO  c.w.a.config.HttpTraceLogFilter(162) - [, /api/user/authority/updateRoleBackendPermission] Http 请求日志: HttpTraceLog{
     method='OPTIONS',
     path='/api/user/authority/updateRoleBackendPermission',
     parameterMap='{}',
     timeTaken=1,
     time='2020-03-31T14:49:22.044',
     status=200,
     requestHeaders='
            host: localhost:9900
            connection: keep-alive
            accept: */*
            access-control-request-method: POST
            access-control-request-headers: content-type
            origin: http://localhost:9528
            sec-fetch-mode: cors
            sec-fetch-site: same-site
            referer: http://localhost:9528/
            user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36
            accept-encoding: gzip, deflate, br
            accept-language: zh-CN,zh;q=0.9,en;q=0.8',
     requestBody='',
     responseHeaders='',
     responseBody='{"code":1002,"message":"登录验证失败"}',}
```
从日志中看到是针对跨域POST请求发起的预检请求OPTIONS中没有 cookie header.

目前推测应该是预检请求中不带有cookie相关信息. 解决办法 TODO
1. OPTIONS预检请求带上 cookie信息
2. 后端将OPTIONS 预检请求放过检查
