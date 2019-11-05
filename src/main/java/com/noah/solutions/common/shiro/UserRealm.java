package com.noah.solutions.common.shiro;

import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.common.utils.StringUtil;
import com.noah.solutions.system.entity.Authorities;
import com.noah.solutions.system.entity.Role;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.service.AuthoritiesService;
import com.noah.solutions.system.service.RoleService;
import com.noah.solutions.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Shiro认证和授权
 * Created by wangfan on 2018-02-22 上午 11:29
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private RoleService roleService;
    @Autowired
    @Lazy
    private AuthoritiesService authoritiesService;



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
        User user = new User();
        BeanUtils.copyProperties(SecurityUtils.getSubject().getPrincipal(),user);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 角色
        List<Role> userRoles = roleService.getByUserId(user.getUserId());
        Set<String> roles = new HashSet<>();
        for (int i = 0; i < userRoles.size(); i++) {
            roles.add(String.valueOf(userRoles.get(i).getRoleId()));
        }
        authorizationInfo.setRoles(roles);
        // 权限
        List<Authorities> authorities = authoritiesService.listByUserId(user.getUserId());
        Set<String> permissions = new HashSet<>();
        for (int i = 0; i < authorities.size(); i++) {
            String authority = authorities.get(i).getAuthority();
            if (StringUtil.isNotBlank(authority)) {
                permissions.add(authority);
            }
        }
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();

        User user = userService.getByUsername(username);
        if (user == null) {
            throw new UnknownAccountException(); // 账号不存在
        }
        if (user.getState() != 0) {
            throw new LockedAccountException();  // 账号被锁定
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), getName());

//        Subject subject = SecurityUtils.getSubject();
//        Session session = subject.getSession();
//        Serializable sessionId = session.getId();
//        session.setAttribute("userId", user.getUserId());
//        session.setAttribute("loginName", user.getUsername());
//        System.out.println(sessionId);
//        ShiroSession shiroSession = (ShiroSession) sessionDao.readSession(sessionId);
//        if(shiroSession!=null){
//            shiroSession.setAttribute("userId", user.getUserId());
//            shiroSession.setAttribute("loginName", user.getUsername());
//            sessionDao.update(shiroSession);
//        }

        return authenticationInfo;
    }
}
