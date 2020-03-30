package co.wangming.adminserver.controller.user;

import co.wangming.adminserver.enums.ResponseCode;
import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.model.auth.*;
import co.wangming.adminserver.service.AuthorityService;
import co.wangming.adminserver.vo.Response;
import co.wangming.adminserver.vo.auth.*;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/**
 * Created By WangMing On 2020-03-14
 **/
@RestController
@RequestMapping("/api/user/authority")
public class AuthorityController {

    private static final Logger LOGGER = LoggerFactory.getUserLogger(AuthorityController.class);

    @Resource
    private AuthorityService authorityService;

    @PostMapping("addUser")
    public Response addUser() {
        // TODO

        return ResponseCode.SUCCESS.build();
    }

    @GetMapping("getUsers")
    public Response getUsers() {

        List<User> allUsers = authorityService.selectAllUsers();

        GetUsersResponse response = GetUsersResponse.build(allUsers);

        return ResponseCode.SUCCESS.build(response);
    }

    @PostMapping("addRole")
    public Response addRoles() {
        // TODO

        return ResponseCode.SUCCESS.build();
    }

    @GetMapping("getRoles")
    public Response getRoles() {

        List<Role> allRoles = authorityService.selectAllRoles();

        GetRolesResponse response = GetRolesResponse.build(allRoles);

        return ResponseCode.SUCCESS.build(response);
    }

    @GetMapping("getPermissions")
    public Response getPermissions() {

        List<Permission> permissions = authorityService.selectAllPermissions();

        GetPermissionsResponse response = GetPermissionsResponse.build(permissions);

        return ResponseCode.SUCCESS.build(response);
    }

    @GetMapping("getRolesByUserId")
    public Response getRolesByUserId(@Param("userId") long userId) {

        List<Role> roles = authorityService.selectRolesByUserId(userId);

        GetRolesResponse response = GetRolesResponse.build(roles);

        return ResponseCode.SUCCESS.build(response);
    }

    @GetMapping("getPermissionsByRoleId")
    public Response getPermissionsByRole(@Param("roleId") String roleId) {

        List<Permission> permissions = authorityService.selectPermissionsByRoleIds(new HashSet() {{
            add(roleId);
        }});

        GetPermissionsResponse response = GetPermissionsResponse.build(permissions);

        return ResponseCode.SUCCESS.build(response);
    }

    @PostMapping("updateRolePermission")
    public Response updateRolePermission(@RequestBody UpdateRolePermissionRequest updateRolePermissionRequest) {

        if (updateRolePermissionRequest.getType() == 0) {
            authorityService.deleteRolePermissionRelationBy(updateRolePermissionRequest.getRoleId(), updateRolePermissionRequest.getPermissionId());
        } else {
            RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
            rolePermissionRelation.setRoleId(updateRolePermissionRequest.getRoleId());
            rolePermissionRelation.setPermissionId(updateRolePermissionRequest.getPermissionId());
            rolePermissionRelation.setStatus(1);
            authorityService.insertOneRolePermissionRelation(rolePermissionRelation);
        }

        return ResponseCode.SUCCESS.build();
    }

    @PostMapping("updateUserRole")
    public Response updateUserRole(@RequestBody UpdateUserRoleRequest updateUserRoleRequest) {

        if (updateUserRoleRequest.getType() == 0) {
            authorityService.deleteUserRoleRelationBy(updateUserRoleRequest.getRoleId(), updateUserRoleRequest.getUserId());
        } else {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setRoleId(updateUserRoleRequest.getRoleId());
            userRoleRelation.setUserId(updateUserRoleRequest.getUserId());
            userRoleRelation.setStatus(1);
            authorityService.insertOneUserRoleRelation(userRoleRelation);
        }

        return ResponseCode.SUCCESS.build();
    }

}
