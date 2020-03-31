package co.wangming.adminserver.util;

import co.wangming.adminserver.logger.LoggerFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;

import static org.apache.shiro.subject.support.DefaultSubjectContext.PRINCIPALS_SESSION_KEY;

/**
 * Created By WangMing On 2020-03-11
 **/
public class UserUtil {

    private static final Logger LOGGER = LoggerFactory.getSystemLogger(UserUtil.class);

    public static String getCurrentUserName() {

        Subject subject = SecurityUtils.getSubject();
        try {
            Session session = subject.getSession(false);
            if (session == null) {
                LOGGER.debug("获取UserName失败, 没找到session");
                return "";
            }
            Object user = session.getAttribute(PRINCIPALS_SESSION_KEY);
            return user == null ? "" : user.toString();
        } catch (Exception e) {
            LOGGER.error("获取UserName失败", e);
            return "";
        }
    }
}
