package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.RolePermissionRelation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface RolePermissionRelationMapper {

    @Select("SELECT * FROM RolePermissionRelation")
    List<RolePermissionRelation> selectAllRolePermissionRelations();


    @Select("SELECT * FROM RolePermissionRelation where roleId = #{roleId}")
    List<RolePermissionRelation> selectRolePermissionRelationByRoleId(long roleId);

    @Insert("INSERT INTO RolePermissionRelation (roleId, permissionId, status) VALUES(#{roleId}, #{permissionId}, 1)")
    int insertOneRolePermissionRelation(RolePermissionRelation user);
}
