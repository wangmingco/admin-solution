package co.wangming.adminserver.service;

import co.wangming.adminserver.mapper.auth.*;
import co.wangming.adminserver.model.auth.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created By WangMing On 2020-03-14
 **/
@Service
public class AuthorityService implements UserMapper, RoleMapper, PermissionMapper, UserRoleRelationMapper, RolePermissionRelationMapper {

    // TODO 替换成不可修改list
    private static final List list = new ArrayList();

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    @Resource
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List<Permission> selectAllPermissions() {
        List<Permission> permissions = permissionMapper.selectAllPermissions();
        return permissions == null ? list : permissions;
    }

    @Override
    public List<Permission> selectPermissionsByRoleIds(Set<Long> roleIds) {
        return permissionMapper.selectPermissionsByRoleIds(roleIds);
    }

    @Override
    public int insertOnePermission(Permission permission) {
        return permissionMapper.insertOnePermission(permission);
    }

    @Override
    public int deletePermissionById(long permissionId) {
        return permissionMapper.deletePermissionById(permissionId);
    }

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

    @Override
    public List<RolePermissionRelation> selectAllRolePermissionRelations() {
        return rolePermissionRelationMapper.selectAllRolePermissionRelations();
    }

    @Override
    public List<RolePermissionRelation> selectRolePermissionRelationByRoleId(long roleId) {
        return rolePermissionRelationMapper.selectRolePermissionRelationByRoleId(roleId);
    }

    @Override
    public int insertOneRolePermissionRelation(RolePermissionRelation rolePermissionRelation) {
        return rolePermissionRelationMapper.insertOneRolePermissionRelation(rolePermissionRelation);
    }

    @Override
    public int deleteRolePermissionRelationBy(long roleId, long permissionId) {
        return rolePermissionRelationMapper.deleteRolePermissionRelationBy(roleId, permissionId);
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
