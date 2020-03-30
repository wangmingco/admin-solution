package co.wangming.adminserver.shiro;

import co.wangming.adminserver.enums.AuthConstant;
import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.util.SpringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

import static co.wangming.adminserver.enums.AuthConstant.TOKEN_NAME;

/**
 * 使用shiro 做权限控制
 * <p>
 * TODO
 * CORS(Cross-Origin Resource Sharing，跨域资源共享) 配置
 * <p>
 * XSS(Cross Site Scripting, 跨站脚本) 攻击
 * CSRF(Cross-site requestforgery, 跨站请求伪造) 攻击
 * SSRF(Server Side RequestForgery, 服务器端请求伪造) 攻击
 *
 *
 * <p>
 * Created By WangMing On 2020-03-02
 **/
@Configuration
public class ShiroConfig {

    private static final Logger LOGGER = LoggerFactory.getSystemLogger(ShiroConfig.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private SecurityManager securityManager;

    @PostConstruct
    private void initStaticSecurityManager() {
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Autowired DatabaseRealm shiroDatabaseRealm) {
        LOGGER.info("start securityManager setting");

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroDatabaseRealm);
        securityManager.setSessionManager(buildSessionManager());
        // TODO
//		securityManager.setRememberMeManager();
        // TODO
//        securityManager.setSubjectDAO();
        // TODO
//        securityManager.setCacheManager();
        // TODO
//        securityManager.setAuthorizer();
        // TODO
//        securityManager.setAuthenticator();
        // TODO
//        securityManager.setSubjectFactory();
        // TODO
//        securityManager.setEventBus();

        LOGGER.info("defaultWebSecurityManager over");
        return securityManager;
    }

    private SessionManager buildSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        //
        sessionManager.setSessionIdCookie(buildCookie());
        //
        sessionManager.setSessionIdCookieEnabled(true);
        //
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        // 全局会话超时时间（单位毫秒），默认30分钟
        sessionManager.setGlobalSessionTimeout(AuthConstant.GlobalSessionTimeout);

        // 是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);

        // 是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);

        // 设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认30分钟
        // 设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler
        // 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        sessionManager.setSessionValidationInterval(AuthConstant.SessionValidationInterval);

        // TODO
//        sessionManager.setSessionDAO();
        // TODO
//        sessionManager.setSessionListeners();
        // TODO
//        sessionManager.setSessionValidationScheduler();
        // TODO


        return sessionManager;
    }

    public SimpleCookie buildCookie() {
        SimpleCookie simpleCookie = new SimpleCookie(TOKEN_NAME);
        simpleCookie.setPath("/");
        // 对服务器生成的TOKEN设置 HttpOnly 属性. 前端无法读写该TOKEN, 提供系统安全, 防止XSS攻击
        simpleCookie.setHttpOnly(true);
        // 设置浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        // TODO
//        simpleCookie.setComment();
        // TODO
//        simpleCookie.setDomain();
        // TODO
//        simpleCookie.setName();
        // TODO
//        simpleCookie.setSecure();
        // TODO
//        simpleCookie.setValue();
        // TODO
//        simpleCookie.setVersion();
        return simpleCookie;
    }

    /**
     * 设置接口权限验证, 目前只针对api接口进行权限验证
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        LOGGER.info("start shiroFilter setting");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setSuccessUrl("/#/dashboard");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("apiAccessControlFilter", new ApiAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/#/login/**", "anon");
        filterChainDefinitionMap.put("/api/user/auth/login", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/api/**", "apiAccessControlFilter");
        filterChainDefinitionMap.put("/**", "logFilter");
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        LOGGER.info("shirFilter config fineshed");
        return shiroFilterFactoryBean;
    }

    @Bean
    public CorsFilter corsFilter() {
        // CORS配置信息
        CorsConfiguration config = new CorsConfiguration();

        if (!SpringUtil.isInProduction(applicationContext)) {
            LOGGER.info("进行非生产模式CORS配置");

            // TODO 放行哪些原始域
            config.addAllowedOrigin("*");

            // TODO  是否发送Cookie信息
            config.setAllowCredentials(true);

            // TODO  放行哪些原始域(请求方式)
            config.addAllowedMethod("*");

            // TODO 放行哪些原始域(头部信息)
            config.addAllowedHeader("*");

            // TODO 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
            config.addExposedHeader("Set-Cookie");

            // TODO
//        config.setMaxAge();

        }

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }
}
