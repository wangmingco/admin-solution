package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface PermissionMapper {

    @Select("SELECT * FROM Permission")
    List<Permission> selectAllPermissions();

    @Select("<script>" +
            "SELECT * FROM Permission p WHERE p.id in (select permissionId from RolePermissionRelation r where r.roleId in " +
            "<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>" +
            "#{roleId}" +
            "</foreach>)" +
            "</script>")
    List<Permission> selectPermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);

    @Insert("INSERT INTO Permission (permissionName, path, status) VALUES(#{permissionName}, #{path}, 1)")
    int insertOnePermission(Permission permission);

    @Delete("DELETE FROM Permission WHERE id = #{permissionId}")
    int deletePermissionById(@Param("permissionId") long permissionId);
}
