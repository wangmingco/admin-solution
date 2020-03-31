package co.wangming.adminserver.controller.user;

import co.wangming.adminserver.enums.ResponseCode;
import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.model.auth.*;
import co.wangming.adminserver.service.BackendPermissionService;
import co.wangming.adminserver.service.FrontendPermissionService;
import co.wangming.adminserver.service.RoleService;
import co.wangming.adminserver.service.UserService;
import co.wangming.adminserver.vo.Response;
import co.wangming.adminserver.vo.auth.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created By WangMing On 2020-03-14
 **/
@RestController
@RequestMapping("/api/user/authority")
public class AuthorityController {

    private static final Logger LOGGER = LoggerFactory.getUserLogger(AuthorityController.class);

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private BackendPermissionService backendPermissionService;

    @Resource
    private FrontendPermissionService frontendPermissionService;

    /******************************************************************************************
     *                                  用户相关api接口
     * ****************************************************************************************
     */
    @PostMapping("addUser")
    public Response addUser() {
        // TODO

        return ResponseCode.SUCCESS.build();
    }

    @GetMapping("getUsers")
    public Response getUsers() {

        List<User> allUsers = userService.selectAllUsers();

        GetUsersResponse response = GetUsersResponse.build(allUsers);

        return ResponseCode.SUCCESS.build(response);
    }

    /******************************************************************************************
     *                                  角色相关api接口
     * ****************************************************************************************
     */
    @PostMapping("addRole")
    public Response addRoles() {
        // TODO

        return ResponseCode.SUCCESS.build();
    }

    @GetMapping("getRoles")
    public Response getRoles() {

        List<Role> allRoles = roleService.selectAllRoles();

        GetRolesResponse response = GetRolesResponse.build(allRoles);

        return ResponseCode.SUCCESS.build(response);
    }

    @GetMapping("getRolesByUserId")
    public Response getRolesByUserId() {
        long userId = userService.getUserId();

        List<Role> roles = roleService.selectRolesByUserId(userId);

        GetRolesResponse response = GetRolesResponse.build(roles);

        return ResponseCode.SUCCESS.build(response);
    }

    @PostMapping("updateUserRole")
    public Response updateUserRole(@RequestBody UpdateUserRoleRequest updateUserRoleRequest) {

        if (updateUserRoleRequest.getType() == 0) {
            roleService.deleteUserRoleRelationBy(updateUserRoleRequest.getRoleId(), updateUserRoleRequest.getUserId());
        } else {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setRoleId(updateUserRoleRequest.getRoleId());
            userRoleRelation.setUserId(updateUserRoleRequest.getUserId());
            userRoleRelation.setStatus(1);
            roleService.insertOneUserRoleRelation(userRoleRelation);
        }

        return ResponseCode.SUCCESS.build();
    }

    /******************************************************************************************
     *                                  后端权限相关api接口
     * ****************************************************************************************
     */
    @GetMapping("getBackendPermissions")
    public Response getPermissions() {

        List<BackendPermission> backendPermissions = backendPermissionService.selectAllPermissions();

        GetPermissionsResponse response = GetPermissionsResponse.build(backendPermissions);

        return ResponseCode.SUCCESS.build(response);
    }

    @GetMapping("getBackendPermissionsByRoleId")
    public Response getPermissionsByRole(@Param("roleId") String roleId) {
        List<BackendPermission> backendPermissions = backendPermissionService.selectPermissionsByRoleIds(new HashSet() {{
            add(roleId);
        }});

        GetPermissionsResponse response = GetPermissionsResponse.build(backendPermissions);

        return ResponseCode.SUCCESS.build(response);
    }

    @PostMapping("updateRoleBackendPermission")
    public Response updateRolePermission(@RequestBody UpdateRolePermissionRequest updateRolePermissionRequest) {

        if (updateRolePermissionRequest.getType() == 0) {
            backendPermissionService.deleteRolePermissionRelationBy(updateRolePermissionRequest.getRoleId(), updateRolePermissionRequest.getPermissionId());
        } else {
            RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
            rolePermissionRelation.setRoleId(updateRolePermissionRequest.getRoleId());
            rolePermissionRelation.setPermissionId(updateRolePermissionRequest.getPermissionId());
            rolePermissionRelation.setStatus(1);
            backendPermissionService.insertOneRolePermissionRelation(rolePermissionRelation);
        }

        return ResponseCode.SUCCESS.build();
    }

    /******************************************************************************************
     *                                  前端权限相关api接口
     * ****************************************************************************************
     */
    @GetMapping("getUserFrontendPermissions")
    public Response getUserFrontendPermissions() {
        long userId = userService.getUserId();
        List<Role> roles = roleService.selectRolesByUserId(userId);
        GetUserBackendPermissionsResponse getUserBackendPermissionsResponse = new GetUserBackendPermissionsResponse();

        if (roles == null) {
            return ResponseCode.SUCCESS.build(getUserBackendPermissionsResponse);
        }

        Set<Long> roleIds = roles.stream().map(it -> it.getId()).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(roleIds)) {
            return ResponseCode.SUCCESS.build(getUserBackendPermissionsResponse);
        }

        Map<String, GetUserBackendPermissionsResponse.RouteNode> routeNodeMap = new HashMap<>();

        List<FrontendPermission> frontendPermissions = frontendPermissionService.selectPermissionsByRoleIds(roleIds);

        // 根据角色id找到所有前端权限, 然后遍历构建前端路由树
        for (FrontendPermission frontendPermission : frontendPermissions) {

            if (StringUtils.isEmpty(frontendPermission.getParrent())) {
                // 如果父为空, 则直接构建一个路由节点
                GetUserBackendPermissionsResponse.RouteNode routeNode = routeNodeMap.get(frontendPermission.getPath());
                if (routeNode == null) {
                    routeNode = new GetUserBackendPermissionsResponse.RouteNode();
                    routeNodeMap.put(frontendPermission.getPath(), routeNode);
                }

                routeNode.setComponent(frontendPermission.getComponent());
                routeNode.setName(frontendPermission.getName());
                routeNode.setPath(frontendPermission.getPath());
                routeNode.setRedirect(frontendPermission.getRedirect());
                routeNode.setMeta(frontendPermission.getIcon(), frontendPermission.getTitle());

            } else {
                GetUserBackendPermissionsResponse.RouteNode parentRouteNode = routeNodeMap.get(frontendPermission.getParrent());

                if (parentRouteNode == null) {
                    parentRouteNode = new GetUserBackendPermissionsResponse.RouteNode();
                    routeNodeMap.put(frontendPermission.getParrent(), parentRouteNode);
                }

                GetUserBackendPermissionsResponse.RouteNode routeNode = new GetUserBackendPermissionsResponse.RouteNode();
                routeNode.setComponent(frontendPermission.getComponent());
                routeNode.setName(frontendPermission.getName());
                routeNode.setPath(frontendPermission.getPath());
                routeNode.setRedirect(frontendPermission.getRedirect());
                routeNode.setMeta(frontendPermission.getIcon(), frontendPermission.getTitle());

                parentRouteNode.getChildren().add(routeNode);
            }
        }

        getUserBackendPermissionsResponse.setRouteNodes(routeNodeMap.values());

        return ResponseCode.SUCCESS.build(getUserBackendPermissionsResponse);
    }

    @GetMapping("getFrontendPermissions")
    public Response getFrontendPermissions() {

        List<FrontendPermission> frontendPermissions = frontendPermissionService.selectAllPermissions();

        GetPermissionsResponse response = GetPermissionsResponse.buildFrontend(frontendPermissions);

        return ResponseCode.SUCCESS.build(response);
    }

    @GetMapping("getFrontendPermissionsByRoleId")
    public Response getFrontendPermissionsByRoleId(@Param("roleId") String roleId) {
        List<FrontendPermission> frontendPermissions = frontendPermissionService.selectPermissionsByRoleIds(new HashSet() {{
            add(roleId);
        }});

        GetPermissionsResponse response = GetPermissionsResponse.buildFrontend(frontendPermissions);

        return ResponseCode.SUCCESS.build(response);
    }

    @PostMapping("updateRoleFrontendPermission")
    public Response updateRoleFrontendPermission(@RequestBody UpdateRolePermissionRequest updateRolePermissionRequest) {

        if (updateRolePermissionRequest.getType() == 0) {
            frontendPermissionService.deleteRolePermissionRelationBy(updateRolePermissionRequest.getRoleId(), updateRolePermissionRequest.getPermissionId());
        } else {
            RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
            rolePermissionRelation.setRoleId(updateRolePermissionRequest.getRoleId());
            rolePermissionRelation.setPermissionId(updateRolePermissionRequest.getPermissionId());
            rolePermissionRelation.setStatus(1);
            frontendPermissionService.insertOneRolePermissionRelation(rolePermissionRelation);
        }

        return ResponseCode.SUCCESS.build();
    }

}
