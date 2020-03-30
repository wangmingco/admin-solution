package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created By WangMing On 2020-03-01
 **/
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM User")
    List<User> selectAllUsers();

    @Select("SELECT * FROM User where username=#{username}")
    User selectOneUserByName(@Param("username") String userName);

    @Insert("INSERT INTO User (username, password, status) VALUES(#{username}, #{password}, 1)")
    int insertOneUser(User user);

    @Delete("DELETE FROM User WHERE id = #{userId}")
    int deleteUserById(@Param("userId") long userId);
}
