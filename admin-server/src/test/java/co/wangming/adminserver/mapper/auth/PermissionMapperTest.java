package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.AdminServerApplicationTests;
import co.wangming.adminserver.model.auth.Permission;
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
public class PermissionMapperTest {

    @Resource
    private PermissionMapper permissionMapper;

    @Test
    @Rollback
    @Transactional
    public void testInsert() throws Exception {

        insert();
    }

    private void insert() throws Exception {
        Permission permission = new Permission();
        permission.setPermissionName("test");
        permission.setPath("/path/selectAll");
        int num = permissionMapper.insertOnePermission(permission);
        Assert.assertEquals(1, num);
    }

    @Test
    @Rollback
    @Transactional
    public void testSelectAll() throws Exception {

        insert();
        List<Permission> all = permissionMapper.selectAllPermissions();
        Assert.assertEquals(1, all.size());
        Assert.assertEquals("test", all.get(0).getPermissionName());

    }
}
