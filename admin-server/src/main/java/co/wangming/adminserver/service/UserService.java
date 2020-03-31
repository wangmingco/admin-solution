package co.wangming.adminserver.service;

import co.wangming.adminserver.mapper.auth.UserMapper;
import co.wangming.adminserver.model.auth.User;
import co.wangming.adminserver.util.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By WangMing On 2020-03-14
 **/
@Service
public class UserService implements UserMapper {

    // TODO 替换成不可修改list
    private static final List list = new ArrayList();

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectAllUsers() {
        List<User> allUsers = userMapper.selectAllUsers();
        return allUsers == null ? list : allUsers;
    }

    @Override
    public User selectOneUserByName(String userName) {
        return userMapper.selectOneUserByName(userName);
    }

    @Override
    public int insertOneUser(User user) {
        return userMapper.insertOneUser(user);
    }

    @Override
    public int deleteUserById(long userId) {
        return userMapper.deleteUserById(userId);
    }

    public long getUserId() {
        long userId;
        String username = UserUtil.getCurrentUserName();
        User user = selectOneUserByName(username);
        if (user == null) {
            return -1;
        }
        userId = user.getId();
        return userId;
    }

}
