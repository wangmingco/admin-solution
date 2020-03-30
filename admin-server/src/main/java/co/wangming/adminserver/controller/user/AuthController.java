package co.wangming.adminserver.controller.user;

import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.service.UserAuthService;
import co.wangming.adminserver.vo.Response;
import co.wangming.adminserver.vo.auth.LoginRequest;
import org.slf4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created By WangMing On 2020-03-02
 **/
@RestController
@RequestMapping("/api/user/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getUserLogger(AuthController.class);

    @Resource
    private UserAuthService userAuthService;

    @PostMapping("login")
    public Response login(@Validated @RequestBody LoginRequest loginRequest) {
        LOGGER.info("接收到用户登录请求");
        Response response = userAuthService.login(loginRequest);
        LOGGER.info("用户登录处理完成:{}", response);

        return response;
    }

    @GetMapping("info")
    public Response login() {

        return userAuthService.info();
    }

    @PostMapping("logout")
    public Response logout() {

        return userAuthService.logout();
    }
}
