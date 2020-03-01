package co.wangming.adminserver.model.auth;

import co.wangming.adminserver.model.BaseModel;

/**
 * Created By WangMing On 2020-03-01
 **/
public class RolePermissionRelation extends BaseModel {

    private long id;
    private long roleId;
    private long permissionId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }
}
