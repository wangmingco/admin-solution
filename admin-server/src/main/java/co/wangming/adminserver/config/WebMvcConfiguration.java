package co.wangming.adminserver.config;

import co.wangming.adminserver.logger.LoggerFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created By WangMing On 2020-03-05
 **/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getSystemLogger(WebMvcConfiguration.class);

    @Autowired
    RequestCostTimeInterceptor requestCostTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestCostTimeInterceptor());
        registry.addWebRequestInterceptor(new HttpRequestLogInterceptor());
        logger.info("将RequestCostTimeInterceptor 添加进 InterceptorRegistry");
        logger.info("将HttpRequestLogInterceptor 添加进 InterceptorRegistry");
    }

    @Bean
    @ConditionalOnProperty(name = "enable.trace.http.log", havingValue = "true")
    public HttpTraceLogFilter httpTraceLogFilter(MeterRegistry registry) {
        logger.info("开启http trace log");
        return new HttpTraceLogFilter();
    }

}
