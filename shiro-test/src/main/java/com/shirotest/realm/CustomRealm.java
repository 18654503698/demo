package com.shirotest.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {
    Map<String,String> userMap = new HashMap<>(16);
    {
        userMap.put("张三","123");
        super.setName("customRealm");
    }
    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        //从数据库或缓存中获取角色数据
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    private Set<String> getRolesByUserName(String userName) {
        Set<String> sets = new HashSet<>();
        sets.add("admin");
        sets.add("员工");
        return sets;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets = new HashSet<>();
        sets.add("user:select");
        sets.add("user:add");
        return sets;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1 从主体传过来的认证信息中，获得用户名
        String username = (String)authenticationToken.getPrincipal();
        //2 通过用户名到数据库获取凭证
        String password = getPasswordByUserName(username);
        if(password == null) {
             return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("张三",password,"customRealm");
        return authenticationInfo;
    }

    /**
     * 通过用户名查找密码
     * @param username
     * @return
     */
    private String getPasswordByUserName(String username) {
        return userMap.get(username);
    }
}
