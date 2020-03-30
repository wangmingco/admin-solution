package co.wangming.adminserver.vo.auth;

/**
 * Created By WangMing On 2020-03-27
 **/
public class UpdateUserRoleRequest {

    private int type;
    private int roleId;
    private int userId;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
