package co.wangming.adminserver.logger;

/**
 * Created By WangMing On 2018/9/20
 **/
public enum LoggerLocalCache {

    INSTANCE;

    private ThreadLocal<String> userThreadLocal = new ThreadLocal<>();
    private ThreadLocal<String> pathThreadLocal = new ThreadLocal<>();
    private ThreadLocal<String> logThreadLocal = new ThreadLocal<>();

    public void setUser(String type) {
        userThreadLocal.set(type);
        setLog();
    }

    public void setPath(String type) {
        pathThreadLocal.set(type);
        setLog();
    }

    private void setLog() {
        StringBuilder stringBuilder = new StringBuilder("[");
        String user = userThreadLocal.get();
        if (user != null) {
            stringBuilder.append(user).append(", ");
        }
        String path = pathThreadLocal.get();
        if (path != null) {
            stringBuilder.append(path);
        }
        stringBuilder.append("] ");

        logThreadLocal.set(stringBuilder.toString());
    }

    public String getPrefixLog() {
        String log = logThreadLocal.get();
        return log == null ? "" : log;
    }

    public void clear() {
        userThreadLocal.remove();
        logThreadLocal.remove();
    }

}
