package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.FrontendPermission;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface FrontendPermissionMapper {

    @Select("SELECT * FROM FrontendPermission")
    List<FrontendPermission> selectAllPermissions();

    @Select("<script>" +
            "SELECT * FROM FrontendPermission p WHERE p.id in (select permissionId from RoleFrontendPermissionRelation r where r.roleId in " +
            "<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>" +
            "#{roleId}" +
            "</foreach>)" +
            "</script>")
    List<FrontendPermission> selectPermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);

    @Insert("INSERT INTO FrontendPermission (permissionName, path, status) VALUES(#{permissionName}, #{path}, 1)")
    int insertOnePermission(FrontendPermission frontendPermission);

    @Delete("DELETE FROM FrontendPermission WHERE id = #{permissionId}")
    int deletePermissionById(@Param("permissionId") long permissionId);
}
