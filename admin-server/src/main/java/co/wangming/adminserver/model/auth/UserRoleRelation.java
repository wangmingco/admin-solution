package co.wangming.adminserver.model.auth;

import co.wangming.adminserver.model.BaseModel;

/**
 * Created By WangMing On 2020-03-01
 **/
public class UserRoleRelation extends BaseModel {

    private long id;
    private long userId;
    private long roleId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
