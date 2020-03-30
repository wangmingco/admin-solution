package co.wangming.adminserver.vo.auth;

import co.wangming.adminserver.model.auth.Permission;
import co.wangming.adminserver.vo.TableData;

import java.util.HashMap;
import java.util.List;

/**
 * Created By WangMing On 2020-03-14
 **/
public class GetPermissionsResponse {

    private TableData permissions;

    public TableData getPermissions() {
        return permissions;
    }

    public void setPermissions(TableData permissions) {
        this.permissions = permissions;
    }

    public static GetPermissionsResponse build(List<Permission> permissions) {
        TableData tableData = TableData.build(permissions, permission -> new HashMap() {{
            put("id", permission.getId());
            put("path", permission.getPath());
            put("permissionName", permission.getPermissionName());
        }});

        GetPermissionsResponse response = new GetPermissionsResponse();
        response.setPermissions(tableData);
        return response;
    }
}
