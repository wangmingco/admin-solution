package co.wangming.adminserver.enums;

import co.wangming.adminserver.vo.Response;

/**
 * Created By WangMing On 2020-03-02
 **/
public enum ResponseCode {

    SUCCESS(20000, "成功"),
    UNKNOW_ERROR(9999, "位置异常"),
    LOGIN_FAIL(1001, "登录失败, 用户名或者密码错误"),
    AUTH_FAIL(1002, "登录验证失败"),
    VALIDATED_TOKEN(50008, "非法的token"),
    OTHER_LOGIN(50012, "其他客户端登录了"),
    TOKEN_EXPIRED(50014, "Token 过期了"),
    ;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    public Response build() {
        Response response = new Response();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }

    public <T> Response build(T t) {
        Response response = new Response();
        response.setCode(code);
        response.setMessage(msg);
        response.setData(t);
        return response;
    }
}
