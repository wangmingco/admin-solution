package co.wangming.adminserver.shiro;

import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.mapper.auth.PermissionMapper;
import co.wangming.adminserver.mapper.auth.RoleMapper;
import co.wangming.adminserver.mapper.auth.UserMapper;
import co.wangming.adminserver.model.auth.Permission;
import co.wangming.adminserver.model.auth.Role;
import co.wangming.adminserver.model.auth.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created By WangMing On 2020-03-02
 **/
@Component
public class DatabaseRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getSystemLogger(DatabaseRealm.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 认证, 根据用户名找到密码进行验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userMapper.selectOneUserByName(username);
        if (user == null) {
            LOGGER.warn("找不到用户");
            return null;
        }
        LOGGER.info("发现用户");
        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }

    /**
     * 授权
     * <p>
     * 根据用户进行授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) super.getAvailablePrincipal(principalCollection);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        User user = userMapper.selectOneUserByName(username);
        if (user == null) {
            return authorizationInfo;
        }

        List<Role> roles = roleMapper.selectRolesByUserId(user.getId());
        if (CollectionUtils.isNotEmpty(roles)) {
            Set<String> roleNames = roles.stream().map(it -> it.getRoleName()).collect(Collectors.toSet());
            authorizationInfo.setRoles(roleNames);

            Set<Long> roleIds = roles.stream().map(it -> it.getId()).collect(Collectors.toSet());

            List<Permission> permisions = permissionMapper.selectPermissionsByRoleIds(roleIds);

            if (CollectionUtils.isNotEmpty(permisions)) {
                Set<String> paths = permisions.stream().map(it -> it.getPath()).collect(Collectors.toSet());
                authorizationInfo.setStringPermissions(paths);
            }
        }

        return authorizationInfo;
    }

}
