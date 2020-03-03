package co.wangming.adminserver.logger;

import org.slf4j.Logger;

/**
 * Created By WangMing On 2020-03-02
 **/
public class LoggerFactory {

    public static Logger getUserLogger(Class<?> clazz) {

        UserLogger userLogger = new UserLogger();
        Logger logger = org.slf4j.LoggerFactory.getLogger(clazz);
        userLogger.setLogger(logger);

        return userLogger;
    }

    public static Logger getSystemLogger(Class<?> clazz) {

        SystemLogger systemLogger = new SystemLogger();
        Logger logger = org.slf4j.LoggerFactory.getLogger(clazz);
        systemLogger.setLogger(logger);

        return systemLogger;
    }
}
