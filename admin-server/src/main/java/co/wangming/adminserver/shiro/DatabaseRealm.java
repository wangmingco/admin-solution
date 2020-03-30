package co.wangming.adminserver.shiro;

import co.wangming.adminserver.logger.LoggerFactory;
import co.wangming.adminserver.model.auth.Permission;
import co.wangming.adminserver.model.auth.Role;
import co.wangming.adminserver.model.auth.User;
import co.wangming.adminserver.service.AuthorityService;
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
    private AuthorityService authorityService;

    /**
     * 认证, 根据用户名找到密码进行验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("认证开始");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = authorityService.selectOneUserByName(username);
        if (user == null) {
            LOGGER.warn("认证结束 找不到用户");
            return null;
        }
        LOGGER.info("认证结束 发现用户");
        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }

    /**
     * 授权
     * <p>
     * 根据用户进行授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOGGER.info("授权开始");

        String username = (String) super.getAvailablePrincipal(principalCollection);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        User user = authorityService.selectOneUserByName(username);
        if (user == null) {
            LOGGER.info("授权结束, 找不到用户");
            return authorizationInfo;
        }

        List<Role> roles = authorityService.selectRolesByUserId(user.getId());
        if (CollectionUtils.isNotEmpty(roles)) {
            Set<String> roleNames = roles.stream().map(it -> it.getRoleName()).collect(Collectors.toSet());
            authorizationInfo.setRoles(roleNames);

            Set<Long> roleIds = roles.stream().map(it -> it.getId()).collect(Collectors.toSet());

            List<Permission> permisions = authorityService.selectPermissionsByRoleIds(roleIds);

            if (CollectionUtils.isNotEmpty(permisions)) {
                Set<String> paths = permisions.stream().map(it -> it.getPath()).collect(Collectors.toSet());
                authorizationInfo.setStringPermissions(paths);
            }
        }

        LOGGER.info("授权完成");
        return authorizationInfo;
    }

}
