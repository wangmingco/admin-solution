package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM Role")
    List<Role> selectAllRoles();

    @Select("SELECT * FROM Role r WHERE r.id in (select roleId FROM UserRoleRelation where userId=#{userId})")
    List<Role> selectRolesByUserId(@Param("userId") long userId);

    @Insert("INSERT INTO Role (roleName, status) VALUES(#{roleName}, 1)")
    int insertOneRole(Role role);

    @Delete("DELETE FROM Role WHERE id = #{roleId}")
    int deleteRoleById(@Param("roleId") long roleId);
}
