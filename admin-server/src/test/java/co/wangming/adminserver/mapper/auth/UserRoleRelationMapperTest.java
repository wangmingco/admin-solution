package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.AdminServerApplicationTests;
import co.wangming.adminserver.model.auth.UserRoleRelation;
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
public class UserRoleRelationMapperTest {

    @Resource
    private UserRoleRelationMapper mapper;

    @Test
    @Rollback
    @Transactional
    public void testInsert() throws Exception {

        insert();
    }

    private void insert() throws Exception {
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setRoleId(1);
        userRoleRelation.setUserId(1);
        int num = mapper.insertOneUserRoleRelation(userRoleRelation);
        Assert.assertEquals(1, num);
    }

    @Test
    @Rollback
    @Transactional
    public void testSelectAll() throws Exception {

        insert();
        List<UserRoleRelation> all = mapper.selectAllUserRoleRelations();
        Assert.assertEquals(1, all.size());
        Assert.assertEquals(1, all.get(0).getRoleId());

    }

}
