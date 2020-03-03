package co.wangming.adminserver.vo.auth;

import javax.validation.constraints.NotNull;

/**
 * Created By WangMing On 2020-03-02
 **/
public class LoginRequest {

    @NotNull(message = "用户名不能为空")
//    @Pattern(regexp = "/^[a-zA-Z0-9_-]{4,16}$/", message = "用户名不符合规则")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
