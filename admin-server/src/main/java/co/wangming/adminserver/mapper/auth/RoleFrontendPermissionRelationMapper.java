package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.RolePermissionRelation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface RoleFrontendPermissionRelationMapper {

    @Select("SELECT * FROM RoleFrontendPermissionRelation")
    List<RolePermissionRelation> selectAllRolePermissionRelations();

    @Select("SELECT * FROM RoleFrontendPermissionRelation where roleId = #{roleId}")
    List<RolePermissionRelation> selectRolePermissionRelationByRoleId(long roleId);

    @Insert("INSERT INTO RoleFrontendPermissionRelation (roleId, permissionId, status) VALUES(#{roleId}, #{permissionId}, 1)")
    int insertOneRolePermissionRelation(RolePermissionRelation rolePermissionRelation);

    @Delete("DELETE FROM RoleFrontendPermissionRelation WHERE roleId = #{roleId} AND permissionId = #{permissionId}")
    int deleteRolePermissionRelationBy(@Param("roleId") long roleId, @Param("permissionId") long permissionId);
}
