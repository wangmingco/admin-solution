package co.wangming.adminserver.vo.auth;

import co.wangming.adminserver.model.auth.Role;
import co.wangming.adminserver.vo.TableData;

import java.util.HashMap;
import java.util.List;

/**
 * Created By WangMing On 2020-03-14
 **/
public class GetRolesResponse {

    private TableData roles;

    public TableData getRoles() {
        return roles;
    }

    public void setRoles(TableData roles) {
        this.roles = roles;
    }

    public static GetRolesResponse build(List<Role> roles) {
        TableData tableData = TableData.build(roles, permission -> new HashMap() {{
            put("id", permission.getId());
            put("roleName", permission.getRoleName());
        }});

        GetRolesResponse getRolesResponse = new GetRolesResponse();
        getRolesResponse.roles = tableData;
        return getRolesResponse;
    }
}
