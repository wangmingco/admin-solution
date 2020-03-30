package co.wangming.adminserver.vo.auth;

/**
 * Created By WangMing On 2020-03-27
 **/
public class UpdateRolePermissionRequest {

    private int type;
    private int roleId;
    private int permissionId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }
}
