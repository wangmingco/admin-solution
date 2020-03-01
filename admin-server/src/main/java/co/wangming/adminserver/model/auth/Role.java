package co.wangming.adminserver.model.auth;

import co.wangming.adminserver.model.BaseModel;

/**
 * Created By WangMing On 2020-03-01
 **/
public class Role extends BaseModel {

    private long id;
    private String roleName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
