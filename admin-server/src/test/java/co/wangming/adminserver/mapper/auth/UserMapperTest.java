package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.AdminServerApplicationTests;
import co.wangming.adminserver.model.auth.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created By WangMing On 2020-03-01
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.properties", classes = AdminServerApplicationTests.class)
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    @Rollback
    @Transactional
    public void testInsert() throws Exception {

        insert();
    }

    private void insert() throws Exception {
        User user = new User();
        user.setUsername("John");
        user.setPassword(md5("John"));
        int num = userMapper.insertOneUser(user);
        Assert.assertEquals(1, num);
    }

    private String md5(String content) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(content.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误，" + e.toString());
        }
    }

    @Test
    @Rollback
    @Transactional
    public void testSelectAll() throws Exception {

        insert();
        List<User> allUsers = userMapper.selectAllUsers();
        Assert.assertEquals(1, allUsers.size());
        Assert.assertEquals("John", allUsers.get(0).getUsername());

    }
}
