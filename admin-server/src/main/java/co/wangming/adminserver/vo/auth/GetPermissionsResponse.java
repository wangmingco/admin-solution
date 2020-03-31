package co.wangming.adminserver.vo.auth;

import co.wangming.adminserver.model.auth.BackendPermission;
import co.wangming.adminserver.model.auth.FrontendPermission;
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

    public static GetPermissionsResponse build(List<BackendPermission> backendPermissions) {
        TableData tableData = TableData.build(backendPermissions, permission -> new HashMap() {{
            put("id", permission.getId());
            put("path", permission.getPath());
            put("permissionName", permission.getPermissionName());
        }});

        GetPermissionsResponse response = new GetPermissionsResponse();
        response.setPermissions(tableData);
        return response;
    }

    public static GetPermissionsResponse buildFrontend(List<FrontendPermission> frontendPermissions) {
        TableData tableData = TableData.build(frontendPermissions, permission -> new HashMap() {{
            put("id", permission.getId());
            put("name", permission.getName());
            put("path", permission.getPath());
            put("parrent", permission.getParrent());
            put("component", permission.getComponent());
            put("icon", permission.getIcon());
            put("redirect", permission.getRedirect());
            put("title", permission.getTitle());
        }});

        GetPermissionsResponse response = new GetPermissionsResponse();
        response.setPermissions(tableData);
        return response;
    }
}
