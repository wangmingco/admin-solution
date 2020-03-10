package co.wangming.adminserver.config;

import co.wangming.adminserver.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created By WangMing On 2020-03-02
 **/
@Component
public class RequestCostTimeInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getUserLogger(RequestCostTimeInterceptor.class);

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
            handlerName = method.getBeanType().getCanonicalName() + "#" + method.getMethod().getName() + "()";
        } catch (Exception e) {
            LOGGER.error("[调用计时器] 获取handler name异常", e);
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

            LOGGER.debug("[调用计时器] 设置costTimeData完成:{}", JSON.toJSONString(costTimeData));
        } catch (Exception e) {
            LOGGER.error("[调用计时器] 设置costTimeData异常:", e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        try {
            CostTimeData costTimeData = threadLocal.get();
            if (costTimeData == null) {
                LOGGER.warn("[调用计时器] 记录失败, 找不到CostTimeData");
                return;
            }

            long costTime = System.currentTimeMillis() - costTimeData.startTime;

            if (costTime > 1000) {
                LOGGER.warn("[调用计时器] 耗时时间过长 traceId:{}, costTime:{}(毫秒), handlerName:{}, startTime:{}", costTimeData.traceId, costTime, costTimeData.handlerName, costTimeData.startTime);
            } else {
                LOGGER.info("[调用计时器]  handlerName:{}, costTime:{}(毫秒), traceId:{}, startTime:{}", costTimeData.handlerName, costTime, costTimeData.traceId, costTimeData.startTime);
            }
        } catch (Exception e) {
            LOGGER.error("[调用计时器] 记录耗时异常", e);
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
