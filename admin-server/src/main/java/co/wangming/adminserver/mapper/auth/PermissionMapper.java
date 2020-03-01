package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface PermissionMapper {

    @Select("SELECT * FROM Permission")
    List<Permission> selectAllPermissions();

    @Insert("INSERT INTO Permission (permissionName, path, status) VALUES(#{permissionName}, #{path}, 1)")
    int insertOnePermission(Permission user);
}
