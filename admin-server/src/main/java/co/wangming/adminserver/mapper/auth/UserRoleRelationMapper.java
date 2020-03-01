package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.UserRoleRelation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface UserRoleRelationMapper {

    @Select("SELECT * FROM UserRoleRelation")
    List<UserRoleRelation> selectAllUserRoleRelations();

    @Insert("INSERT INTO UserRoleRelation (roleId, userId, status) VALUES(#{roleId}, #{userId}, 1)")
    int insertOneUserRoleRelation(UserRoleRelation user);
}
