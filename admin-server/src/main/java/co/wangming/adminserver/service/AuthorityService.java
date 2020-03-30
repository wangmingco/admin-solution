package co.wangming.adminserver.service;

import co.wangming.adminserver.mapper.auth.*;
import co.wangming.adminserver.model.auth.*;
import co.wangming.adminserver.util.SpringUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created By WangMing On 2020-03-14
 **/
@Service
public class AuthorityService implements UserMapper, RoleMapper, BackendPermissionMapper, UserRoleRelationMapper, RoleBackendPermissionRelationMapper {

    // TODO 替换成不可修改list
    private static final List list = new ArrayList();

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private BackendPermissionMapper backendPermissionMapper;

    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    @Resource
    private RoleBackendPermissionRelationMapper roleBackendPermissionRelationMapper;

    @Override
    public List<BackendPermission> selectAllPermissions() {
        List<BackendPermission> backendPermissions = backendPermissionMapper.selectAllPermissions();
        return backendPermissions == null ? list : backendPermissions;
    }

    @Override
    public List<BackendPermission> selectPermissionsByRoleIds(Set<Long> roleIds) {
        return backendPermissionMapper.selectPermissionsByRoleIds(roleIds);
    }

    @Override
    public int insertOnePermission(BackendPermission backendPermission) {
        return backendPermissionMapper.insertOnePermission(backendPermission);
    }

    @Override
    public int deletePermissionById(long permissionId) {
        return backendPermissionMapper.deletePermissionById(permissionId);
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
        return roleBackendPermissionRelationMapper.selectAllRolePermissionRelations();
    }

    @Override
    public List<RolePermissionRelation> selectRolePermissionRelationByRoleId(long roleId) {
        return roleBackendPermissionRelationMapper.selectRolePermissionRelationByRoleId(roleId);
    }

    @Override
    public int insertOneRolePermissionRelation(RolePermissionRelation rolePermissionRelation) {
        return roleBackendPermissionRelationMapper.insertOneRolePermissionRelation(rolePermissionRelation);
    }

    @Override
    public int deleteRolePermissionRelationBy(long roleId, long permissionId) {
        return roleBackendPermissionRelationMapper.deleteRolePermissionRelationBy(roleId, permissionId);
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

    public void initPermissions() {

        List<BackendPermission> allBackendPermissions = backendPermissionMapper.selectAllPermissions();
        List<String> allPermissionPath = allBackendPermissions.stream().map(it -> it.getPath()).collect(Collectors.toList());

        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean(RequestMappingHandlerMapping.class);
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : requestMappingHandlerMapping.getHandlerMethods().entrySet()) {
            RequestMappingInfo key = entry.getKey();
            for (String path : key.getPatternsCondition().getPatterns()) {
                if (allPermissionPath.contains(path)) {
                    continue;
                }
                try {

                    BackendPermission backendPermission = new BackendPermission();
                    backendPermission.setPath(path);
                    backendPermission.setPermissionName(path);
                    backendPermissionMapper.insertOnePermission(backendPermission);
                } catch (DuplicateKeyException e) {
                }
            }
        }

    }
}
