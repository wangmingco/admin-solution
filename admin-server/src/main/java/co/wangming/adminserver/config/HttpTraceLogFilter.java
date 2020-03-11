package co.wangming.adminserver.config;

import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.logger.LoggerLocalCache;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 日志过滤器
 * 在业务打印日志的时候统一打印出当前请求路径
 * <p>
 * filterName : ahttpTraceLogFilter, 以a开头进行过滤器排序, 确保 HttpTraceLogFilter 第一个运行
 * urlPatterns: 过滤 /api/* 所有的api请求
 * <p>
 * Created By WangMing On 2020-03-02
 **/
public class HttpTraceLogFilter extends OncePerRequestFilter implements Ordered {

    private static final Logger log = LoggerFactory.getUserLogger(HttpTraceLogFilter.class);

    private static final String NEED_TRACE_PATH_PREFIX = "/api/";
    private static final String IGNORE_CONTENT_TYPE = "multipart/form-data";


    public HttpTraceLogFilter() {
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        LoggerLocalCache.INSTANCE.setPath(requestUri);

        if (!isRequestValid(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        /**
         * 将Request和response的请求/应答字节进行拷贝
         */
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }

        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
            status = response.getStatus();
        } finally {
            String path = request.getRequestURI();
            if (path.startsWith(NEED_TRACE_PATH_PREFIX) && !Objects.equals(IGNORE_CONTENT_TYPE, request.getContentType())) {

                String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
                log.info(requestBody);
                //1. 记录日志
                HttpTraceLog traceLog = new HttpTraceLog();
                traceLog.setPath(path);
                traceLog.setMethod(request.getMethod());
                traceLog.setTimeTaken(System.currentTimeMillis() - startTime);
                traceLog.setTime(LocalDateTime.now().toString());
                traceLog.setParameterMap(JSON.toJSONString(request.getParameterMap()));
                traceLog.setStatus(status);
                traceLog.setRequestBody(getRequestBody(request));
                traceLog.setResponseBody(getResponseBody(response));
                log.info("Http 请求日志: {}", traceLog);
            }
            updateResponse(response);
        }
    }

    private boolean isRequestValid(HttpServletRequest request) {
        try {
            new URI(request.getRequestURL().toString());
            return true;
        } catch (URISyntaxException ex) {
            return false;
        }
    }

    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            try {
                requestBody = IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            } catch (IOException e) {
                // NOOP
            }
        }
        return requestBody;
    }

    private String getResponseBody(HttpServletResponse response) {
        String responseBody = "";
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            try {
                responseBody = IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            } catch (IOException e) {
                // NOOP
            }
        }
        return responseBody;
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        Objects.requireNonNull(responseWrapper).copyBodyToResponse();
    }


    private static class HttpTraceLog {

        private String path;
        private String parameterMap;
        private String method;
        private Long timeTaken;
        private String time;
        private Integer status;
        private String requestBody;
        private String responseBody;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getParameterMap() {
            return parameterMap;
        }

        public void setParameterMap(String parameterMap) {
            this.parameterMap = parameterMap;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Long getTimeTaken() {
            return timeTaken;
        }

        public void setTimeTaken(Long timeTaken) {
            this.timeTaken = timeTaken;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getRequestBody() {
            return requestBody;
        }

        public void setRequestBody(String requestBody) {
            this.requestBody = requestBody;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public void setResponseBody(String responseBody) {
            this.responseBody = responseBody;
        }

        @Override
        public String toString() {
            return "HttpTraceLog{" +
                    "\n     method='" + method + "\'," +
                    "\n     path='" + path + "\'," +
                    "\n     parameterMap='" + parameterMap + "\'," +
                    "\n     timeTaken=" + timeTaken + "," +
                    "\n     time='" + time + "\'," +
                    "\n     status=" + status + "," +
                    "\n     requestBody='" + requestBody + "\'," +
                    "\n     responseBody='" + responseBody + "\'," +
                    '}';
        }
    }


}

