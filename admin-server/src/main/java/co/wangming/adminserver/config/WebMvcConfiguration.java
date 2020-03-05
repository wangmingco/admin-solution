package co.wangming.adminserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created By WangMing On 2020-03-05
 **/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Autowired
    RequestCostTimeInterceptor requestCostTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestCostTimeInterceptor());
        registry.addWebRequestInterceptor(new HttpRequestLogInterceptor());
        logger.info("添加 HandlerInterceptor 完成");
    }
}
