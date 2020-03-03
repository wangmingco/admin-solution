package co.wangming.adminserver.config;

import co.wangming.adminserver.logger.LoggerLocalCache;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created By WangMing On 2019-12-03
 **/
@WebFilter(filterName = "httpTraceLogFilter", urlPatterns = "/api/*")
public class HttpTraceLogFilter extends OncePerRequestFilter implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        LoggerLocalCache.INSTANCE.setPath(requestUri);
    }

}

