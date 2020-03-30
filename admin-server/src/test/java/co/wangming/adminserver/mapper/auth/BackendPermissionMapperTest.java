package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.AdminServerApplicationTests;
import co.wangming.adminserver.model.auth.BackendPermission;
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
public class BackendPermissionMapperTest {

    @Resource
    private BackendPermissionMapper backendPermissionMapper;

    @Test
    @Rollback
    @Transactional
    public void testInsert() throws Exception {

        insert();
    }

    private void insert() throws Exception {
        BackendPermission backendPermission = new BackendPermission();
        backendPermission.setPermissionName("test");
        backendPermission.setPath("/path/selectAll");
        int num = backendPermissionMapper.insertOnePermission(backendPermission);
        Assert.assertEquals(1, num);
    }

    @Test
    @Rollback
    @Transactional
    public void testSelectAll() throws Exception {

        insert();
        List<BackendPermission> all = backendPermissionMapper.selectAllPermissions();
        Assert.assertEquals(1, all.size());
        Assert.assertEquals("test", all.get(0).getPermissionName());

    }
}
