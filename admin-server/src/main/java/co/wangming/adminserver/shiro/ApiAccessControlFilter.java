package co.wangming.adminserver.shiro;

import co.wangming.adminserver.enums.ResponseCode;
import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.logger.LoggerLocalCache;
import co.wangming.adminserver.util.SpringUtil;
import co.wangming.adminserver.util.UserUtil;
import co.wangming.adminserver.vo.Response;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created By WangMing On 2020-03-02
 **/
public class ApiAccessControlFilter extends AccessControlFilter {

    private static final Logger LOGGER = LoggerFactory.getSystemLogger(ApiAccessControlFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        // 开发环境中, 如果是OPTIONS预检请求则直接返回true TODO 这里想办法做的更加优雅些, 目前就是个补丁
        if (!SpringUtil.isInProduction()
                && request instanceof HttpServletRequest
                && "OPTIONS".equals(((HttpServletRequest) request).getMethod())) {
            return true;
        }

        trySetUserLog();

        LOGGER.info("开始进行鉴权");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Subject subject = SecurityUtils.getSubject();

        boolean isAuthenticated = subject.isAuthenticated();
        boolean isPermitted = subject.isPermitted(httpServletRequest.getRequestURI());

        LOGGER.info("鉴权完成, isPermitted:{}, isAuthenticated:{}", isPermitted, isAuthenticated);

        return isPermitted && isAuthenticated;
    }

    private void trySetUserLog() {
        LoggerLocalCache.INSTANCE.setUser(UserUtil.getCurrentUserName());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse servletResponse) throws Exception {
        LOGGER.info("访问被拒绝");
        Response response = ResponseCode.AUTH_FAIL.build();

        String result = JSON.toJSONString(response);
        servletResponse.getOutputStream().write(result.getBytes("UTF8"));
        servletResponse.flushBuffer();

        return false;
    }


}
