package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.BackendPermission;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface BackendPermissionMapper {

    @Select("SELECT * FROM BackendPermission")
    List<BackendPermission> selectAllPermissions();

    @Select("<script>" +
            "SELECT * FROM BackendPermission p WHERE p.id in (select permissionId from RoleBackendPermissionRelation r where r.roleId in " +
            "<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>" +
            "#{roleId}" +
            "</foreach>)" +
            "</script>")
    List<BackendPermission> selectPermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);

    @Insert("INSERT INTO BackendPermission (permissionName, path, status) VALUES(#{permissionName}, #{path}, 1)")
    int insertOnePermission(BackendPermission backendPermission);

    @Delete("DELETE FROM BackendPermission WHERE id = #{permissionId}")
    int deletePermissionById(@Param("permissionId") long permissionId);
}
