package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM Role")
    List<Role> selectAllRoles();

    @Insert("INSERT INTO Role (roleName, status) VALUES(#{roleName}, 1)")
    int insertOneRole(Role user);
}
