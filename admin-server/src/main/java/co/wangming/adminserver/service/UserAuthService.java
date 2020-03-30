package co.wangming.adminserver.service;

import co.wangming.adminserver.controller.user.AuthController;
import co.wangming.adminserver.enums.ResponseCode;
import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.util.MessageDigestUtil;
import co.wangming.adminserver.util.UserUtil;
import co.wangming.adminserver.vo.Response;
import co.wangming.adminserver.vo.auth.InfoResponse;
import co.wangming.adminserver.vo.auth.LoginRequest;
import co.wangming.adminserver.vo.auth.LoginResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created By WangMing On 2020-03-02
 **/
@Service("shiroUserService")
public class UserAuthService {

    private static final Logger LOGGER = LoggerFactory.getUserLogger(AuthController.class);

    public Response login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Subject subject = SecurityUtils.getSubject();

        try {
            LOGGER.info("登录校验开始");

            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password, false);
            subject.login(usernamePasswordToken);

            boolean isAuthenticated = subject.isAuthenticated();

            if (isAuthenticated) {

                /**
                 * 该token只是让前端用来标识已经登录成功了, 并不用来与后端身份交互认证
                 */
                LoginResponse response = new LoginResponse();
                response.setToken(MessageDigestUtil.md5(System.currentTimeMillis() + username));
                LOGGER.info("登录成功, sessionId:{}", subject.getSession(false).getId());
                return ResponseCode.SUCCESS.build(response);
            } else {
                LOGGER.warn("登录失败, 用户名或者密码错误");
                return ResponseCode.LOGIN_FAIL.build();
            }

        } catch (UnknownAccountException e) {
            LOGGER.warn("登录失败, 用户名或者密码错误");
            return ResponseCode.LOGIN_FAIL.build();

        } catch (AuthenticationException e) {
            LOGGER.error("登录失败", e);
            return ResponseCode.AUTH_FAIL.build();
        } catch (Exception e) {
            LOGGER.error("登录失败", e);
            return ResponseCode.AUTH_FAIL.build();
        }
    }

    public Response info() {
        InfoResponse info = new InfoResponse();

        info.setName(UserUtil.getCurrentUserName());
        info.setAvatar("admin");
        info.setIntroduction("admin");
        info.setRoles(new ArrayList() {{
            add("admin");
        }});

        return ResponseCode.SUCCESS.build(info);
    }

    public Response logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            LOGGER.info("have logined");
            return ResponseCode.SUCCESS.build();
        }

        subject.logout();

        LOGGER.info("登出成功");

        return ResponseCode.SUCCESS.build();
    }

}
