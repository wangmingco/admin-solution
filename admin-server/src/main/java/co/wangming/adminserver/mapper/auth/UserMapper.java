package co.wangming.adminserver.mapper.auth;

import co.wangming.adminserver.model.auth.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
