package co.wangming.adminserver.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Project Name: mercedesbenz
 * Package Name: com.finup.settlement.cloud.filter
 * Author:       liujie
 * Date:         2019-04-10 16:30
 * Description:  用来统一业务线请求方式，
 * 有的业务线使用 contextType:application/www-url-encoded
 * 有的业务线使用 contextType:application/json;
 * 该过滤器主要将上述两种请求方式统一
 * Revision history: 1、fanpu created at 2019-04-10
 */
@Component
public class LogHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogHandlerInterceptor.class);

    private ThreadLocal<CostTimeData> threadLocal = new ThreadLocal<>();

    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;

        String handlerName = "";
        try {
            handlerName = method.getBeanType().toGenericString() + "-" + method.getMethod().getName();
        } catch (Exception e) {
            logger.error("[统一解码器] 获取handler name异常", e);
        }

        setCostTimeData(request, handlerName);

        return true;
    }

    private void setCostTimeData(HttpServletRequest request, String handlerName) {
        try {
            String traceId = MDC.get("e_tid");

            CostTimeData costTimeData = new CostTimeData();
            costTimeData.handlerName = handlerName;
            costTimeData.startTime = System.currentTimeMillis();
            costTimeData.traceId = traceId;

            threadLocal.set(costTimeData);

            logger.info("[统一解码器-记录方法耗时] 设置costTimeData完成:{}", JSON.toJSONString(costTimeData));
        } catch (Exception e) {
            logger.error("[统一解码器-记录方法耗时] 设置costTimeData异常:", e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        try {
            CostTimeData costTimeData = threadLocal.get();
            if (costTimeData == null) {
                logger.warn("[统一解码器-记录方法耗时] 记录失败, 找不到CostTimeData");
                return;
            }

            long costTime = System.currentTimeMillis() - costTimeData.startTime;
            logger.info("[统一解码器-记录方法耗时] 记录完成 traceId:{}, costTime:{}(毫秒), handlerName:{}, startTime:{}", costTimeData.traceId, costTime, costTimeData.handlerName, costTimeData.startTime);

            if (costTime > 1000) {
                logger.warn("[统一解码器-记录方法耗时] 耗时时间过长 traceId:{}, costTime:{}(毫秒), handlerName:{}, startTime:{}", costTimeData.traceId, costTime, costTimeData.handlerName, costTimeData.startTime);
            }
        } catch (Exception e) {
            logger.error("[统一解码器-记录方法耗时] 记录耗时异常", e);
        } finally {
            threadLocal.remove();
        }

        super.afterCompletion(request, response, handler, ex);
    }

    public static class CostTimeData {
        public long startTime;
        public String handlerName;
        public String traceId;
    }

}
