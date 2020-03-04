package co.wangming.adminserver.shiro;

import co.wangming.adminserver.enums.ResponseCode;
import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.logger.LoggerLocalCache;
import co.wangming.adminserver.vo.Response;
import com.alibaba.fastjson.JSON;
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
        LOGGER.info("开始进行鉴权");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String requestToken = httpServletRequest.getHeader("X-Token");
        if (StringUtils.isEmpty(requestToken)) {
            LOGGER.warn("token验证失败  requestToken为空:{}", requestToken);
            return false;
        }

        Subject subject = SecurityUtils.getSubject();
        trySetLogger(subject);

        Object sessionToken = subject.getSession().getAttribute("X-Token");
        if (StringUtils.isEmpty(sessionToken)) {
            LOGGER.warn("token验证失败 sessionToken为空:{}", sessionToken);
            return false;
        }

        if (!(requestToken.equals(sessionToken))) {
            LOGGER.warn("token验证失败requestToken与sessionToken不相等. requestToken:{}, sessionToken:{}", requestToken, sessionToken);
            return false;
        }

        boolean isAuthenticated = subject.isAuthenticated();
        boolean isPermitted = subject.isPermitted(httpServletRequest.getRequestURI());

        LOGGER.info("鉴权完成 isPermitted:{}, isAuthenticated:{}", isPermitted, isAuthenticated);

        return isPermitted && isAuthenticated;
    }

    private void trySetLogger(Subject subject) {
        try {
            Session session = subject.getSession();
            Object user = session.getAttribute(PRINCIPALS_SESSION_KEY);
            LoggerLocalCache.INSTANCE.setUser(user == null ? null : user.toString());
        } catch (Exception e) {
            LOGGER.error("", e);
        }

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse servletResponse) throws Exception {
        LOGGER.info("访问被拒绝");
        Response response = ResponseCode.AUTH_FAIL.build();

        String result = JSON.toJSONString(response);
        servletResponse.getOutputStream().write(result.getBytes());
        servletResponse.flushBuffer();

        return true;
    }


}
