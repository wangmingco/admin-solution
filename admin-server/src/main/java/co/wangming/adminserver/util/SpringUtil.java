package co.wangming.adminserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created By WangMing On 2018/11/13
 **/
@Component
public class SpringUtil implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtil.class);

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    public static boolean isInProduction(ApplicationContext applicationContext) {
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        if (activeProfiles == null || activeProfiles.length == 0 || activeProfiles.length > 1) {
            return true;
        }

        if ("production".equals(activeProfiles[0])) {
            return true;
        } else {
            return false;
        }
    }
}
