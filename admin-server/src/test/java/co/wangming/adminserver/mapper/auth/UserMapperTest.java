package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.AdminServerApplicationTests;
import co.wangming.adminserver.model.auth.User;
import co.wangming.adminserver.util.MessageDigestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        user.setPassword(MessageDigestUtil.md5("John"));
        int num = userMapper.insertOneUser(user);
        Assert.assertEquals(1, num);
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
