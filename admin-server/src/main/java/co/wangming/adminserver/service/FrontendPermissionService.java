package co.wangming.adminserver.service;

import co.wangming.adminserver.mapper.auth.FrontendPermissionMapper;
import co.wangming.adminserver.mapper.auth.RoleFrontendPermissionRelationMapper;
import co.wangming.adminserver.model.auth.FrontendPermission;
import co.wangming.adminserver.model.auth.RolePermissionRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created By WangMing On 2020-03-30
 **/
@Service
public class FrontendPermissionService implements FrontendPermissionMapper, RoleFrontendPermissionRelationMapper {

    @Resource
    private FrontendPermissionMapper frontendPermissionMapper;
    @Resource
    private RoleFrontendPermissionRelationMapper roleFrontendPermissionRelationMapper;

    @Override
    public List<FrontendPermission> selectAllPermissions() {
        return frontendPermissionMapper.selectAllPermissions();
    }

    @Override
    public List<FrontendPermission> selectPermissionsByRoleIds(Set<Long> roleIds) {
        return frontendPermissionMapper.selectPermissionsByRoleIds(roleIds);
    }

    @Override
    public int insertOnePermission(FrontendPermission frontendPermission) {
        return frontendPermissionMapper.insertOnePermission(frontendPermission);
    }

    @Override
    public int deletePermissionById(long permissionId) {
        return frontendPermissionMapper.deletePermissionById(permissionId);
    }

    @Override
    public List<RolePermissionRelation> selectAllRolePermissionRelations() {
        return roleFrontendPermissionRelationMapper.selectAllRolePermissionRelations();
    }

    @Override
    public List<RolePermissionRelation> selectRolePermissionRelationByRoleId(long roleId) {
        return roleFrontendPermissionRelationMapper.selectRolePermissionRelationByRoleId(roleId);
    }

    @Override
    public int insertOneRolePermissionRelation(RolePermissionRelation rolePermissionRelation) {
        return roleFrontendPermissionRelationMapper.insertOneRolePermissionRelation(rolePermissionRelation);
    }

    @Override
    public int deleteRolePermissionRelationBy(long roleId, long permissionId) {
        return roleFrontendPermissionRelationMapper.deleteRolePermissionRelationBy(roleId, permissionId);
    }
}
