package co.wangming.adminserver.service;

import co.wangming.adminserver.mapper.auth.RoleMapper;
import co.wangming.adminserver.mapper.auth.UserRoleRelationMapper;
import co.wangming.adminserver.model.auth.Role;
import co.wangming.adminserver.model.auth.UserRoleRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By WangMing On 2020-03-30
 **/
@Service
public class RoleService implements RoleMapper, UserRoleRelationMapper {


    private static final List list = new ArrayList();

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public List<Role> selectAllRoles() {
        List<Role> roles = roleMapper.selectAllRoles();
        return roles == null ? list : roles;
    }

    @Override
    public List<Role> selectRolesByUserId(long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    public int insertOneRole(Role role) {
        return roleMapper.insertOneRole(role);
    }

    @Override
    public int deleteRoleById(long roleId) {
        return roleMapper.deleteRoleById(roleId);
    }

    @Override
    public List<UserRoleRelation> selectAllUserRoleRelations() {
        return userRoleRelationMapper.selectAllUserRoleRelations();
    }

    @Override
    public List<UserRoleRelation> selectUserRoleRelationsByUserId(long userId) {
        return userRoleRelationMapper.selectUserRoleRelationsByUserId(userId);
    }

    @Override
    public int insertOneUserRoleRelation(UserRoleRelation user) {
        return userRoleRelationMapper.insertOneUserRoleRelation(user);
    }

    @Override
    public int deleteUserRoleRelationBy(long roleId, long userId) {
        return userRoleRelationMapper.deleteUserRoleRelationBy(roleId, userId);
    }


}
