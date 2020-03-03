package co.wangming.adminserver.shiro;

import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.logger.LoggerLocalCache;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static org.apache.shiro.subject.support.DefaultSubjectContext.PRINCIPALS_SESSION_KEY;

/**
 * Created By WangMing On 2020-03-02
 **/
public class ApiAccessControlFilter extends AccessControlFilter {

    private static final Logger LOGGER = LoggerFactory.getSystemLogger(ApiAccessControlFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        String requestToken = httpServletRequest.getHeader("X-Token");

        Subject subject = SecurityUtils.getSubject();
        trySetLogger(subject, uri);

        Object sessionToken = subject.getSession().getAttribute("X-Token");
        if (StringUtils.isEmpty(requestToken) || StringUtils.isEmpty(sessionToken) || !(requestToken.equals(sessionToken))) {
            LOGGER.info("token验证失败  requestToken:{}, sessionToken:{}", requestToken, sessionToken);
            return false;
        }

        LOGGER.info("开始进行鉴权");

        boolean isAuthenticated = subject.isAuthenticated();
        boolean isPermitted = subject.isPermitted(uri);

        LOGGER.info("鉴权完成 isPermitted:{}, isAuthenticated:{}", isPermitted, isAuthenticated);

        return isPermitted && isAuthenticated;
    }

    private void trySetLogger(Subject subject, String uri) {
        try {
            LoggerLocalCache.INSTANCE.setPath(uri);

            Session session = subject.getSession();
            Object user = session.getAttribute(PRINCIPALS_SESSION_KEY);
            LoggerLocalCache.INSTANCE.setUser(user == null ? null : user.toString());
        } catch (Exception e) {
            LOGGER.error("", e);
        }

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        return true;
    }


}
