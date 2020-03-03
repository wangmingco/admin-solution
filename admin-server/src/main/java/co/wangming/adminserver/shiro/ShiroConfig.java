package co.wangming.adminserver.shiro;

import co.wangming.adminserver.enums.AuthConstant;
import co.wangming.adminserver.logger.LoggerFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * 使用shiro 做权限控制
 * 1. 登录
 * 2. 登出
 * 3. 基于数据库的权限验证
 * 4. 用户/角色/权限 授权
 * <p>
 * Created By WangMing On 2020-03-02
 **/
@Configuration
public class ShiroConfig {

    private static final Logger LOGGER = LoggerFactory.getSystemLogger(ShiroConfig.class);

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
//		securityManager.setRememberMeManager();

        LOGGER.info("defaultWebSecurityManager over");
        return securityManager;
    }

    private SessionManager buildSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        sessionManager.setSessionIdCookie(buildCookie());

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

        return sessionManager;
    }

    public SimpleCookie buildCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("admin-solution");
        simpleCookie.setPath("/");
        // 将 HttpOnly 设为true, 增加对xss防护的安全系数, 只能通过http访问，javascript无法访问, 防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        // 设置浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
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

        // 配置不会被拦截的链接 顺序判断
        // 配置退出 过滤器,其中的具体WebAuthenticatingFilter的退出代码Shiro已经替我们实现了
        // 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        // authc:所有url都必须认证通过才可以访问;

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
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        if (1 != 1) {
            LOGGER.info("生产环境, 不允许跨域访问");
            // 返回新的CorsFilter.
            return new CorsFilter(new UrlBasedCorsConfigurationSource());
        }

        LOGGER.info("非生产环境, 允许跨域访问");
        // 非生产环境, 允许跨域访问
        //放行哪些原始域
        config.addAllowedOrigin("*");
        //是否发送Cookie信息
        config.setAllowCredentials(true);
        //放行哪些原始域(请求方式)
        config.addAllowedMethod("*");
        //放行哪些原始域(头部信息)
        config.addAllowedHeader("*");
        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        config.addExposedHeader("Set-Cookie");

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }
}
