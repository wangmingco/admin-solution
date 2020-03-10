package co.wangming.adminserver.config;

import co.wangming.adminserver.logger.LoggerLocalCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 日志过滤器
 * 在业务打印日志的时候统一打印出当前请求路径
 * <p>
 * filterName : ahttpTraceLogFilter, 以a开头进行过滤器排序, 确保 HttpTraceLogFilter 第一个运行
 * urlPatterns: 过滤 /api/* 所有的api请求
 * <p>
 * Created By WangMing On 2020-03-02
 **/
@WebFilter(filterName = "ahttpTraceLogFilter", urlPatterns = "/api/*")
public class HttpTraceLogFilter extends OncePerRequestFilter implements Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpTraceLogFilter.class);

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();
        LoggerLocalCache.INSTANCE.setPath(requestUri);

        // 继续执行其他过滤器(shiro中还有很多过滤器要执行)
        filterChain.doFilter(request, response);
    }

}

