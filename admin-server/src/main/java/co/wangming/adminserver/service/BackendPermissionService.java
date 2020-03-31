package co.wangming.adminserver.service;

import co.wangming.adminserver.mapper.auth.BackendPermissionMapper;
import co.wangming.adminserver.mapper.auth.RoleBackendPermissionRelationMapper;
import co.wangming.adminserver.model.auth.BackendPermission;
import co.wangming.adminserver.model.auth.RolePermissionRelation;
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
 * Created By WangMing On 2020-03-30
 **/
@Service
public class BackendPermissionService implements BackendPermissionMapper, RoleBackendPermissionRelationMapper {

    private static final List list = new ArrayList();

    @Resource
    private BackendPermissionMapper backendPermissionMapper;

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
