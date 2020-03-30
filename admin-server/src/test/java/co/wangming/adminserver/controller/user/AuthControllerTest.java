package co.wangming.adminserver.controller.user;

import co.wangming.adminserver.AdminServerApplicationTests;
import co.wangming.adminserver.mapper.auth.RolePermissionRelationMapper;
import co.wangming.adminserver.mapper.auth.UserMapper;
import co.wangming.adminserver.mapper.auth.UserRoleRelationMapper;
import co.wangming.adminserver.model.auth.User;
import co.wangming.adminserver.shiro.DatabaseRealm;
import co.wangming.adminserver.vo.Response;
import co.wangming.adminserver.vo.auth.LoginRequest;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

/**
 * Created By WangMing On 2020-03-02
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application-test.properties", classes = AdminServerApplicationTests.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    /***************** 将数据库部分mock掉 ************************/
    @Tested
    private DatabaseRealm shiroDatabaseRealm;
    @Injectable
    private UserMapper userMapper;
    @Injectable
    private UserRoleRelationMapper userRoleRelationMapper;
    @Injectable
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    /*****************web controller 测试设置************************/
    @LocalServerPort
    private int port;
    private URL base;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/api/user/auth", port);
        this.base = new URL(url);
    }

    @Test
    public void testLogin_empoty() throws Exception {
        LoginRequest loginRequest = new LoginRequest();

        ResponseEntity<Response> response = this.restTemplate.postForEntity(this.base.toString() + "/login", loginRequest, Response.class);
        System.out.println(String.format("测试结果为：%s", response.getBody()));
    }

    @Test
    public void testLogin_error_username() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test1");
        loginRequest.setPassword("test1");

        ResponseEntity<Response> response = this.restTemplate.postForEntity(this.base.toString() + "/login", loginRequest, Response.class);
        System.out.println(String.format("测试结果为：%s", response.getBody()));
    }

    @Test
    public void testLogin_success() throws Exception {
        new Expectations() {
            {
                userMapper.selectOneUserByName(anyString);
                User user = new User();
                user.setUsername("test1");
                user.setPassword("test1");
                result = user;
            }
        };

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test1");
        loginRequest.setPassword("test1");

        ResponseEntity<Response> response = this.restTemplate.postForEntity(this.base.toString() + "/login", loginRequest, Response.class);
        System.out.println(String.format("测试结果为：%s", response.getBody()));
    }

}
