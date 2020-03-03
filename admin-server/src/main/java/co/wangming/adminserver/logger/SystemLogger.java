package co.wangming.adminserver.logger;


/**
 * Created By WangMing 2020-03-02
 **/
public class SystemLogger extends AbstractLogger {

    @Override
    protected String getRoutePrefixLog() {
        return LoggerLocalCache.INSTANCE.getPrefixLog();
    }
}
