package com.xuexi.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义realm
 */
public class UserRealm extends AuthorizingRealm {
    Map<String,String> userMap = new HashMap<String,String>(16);
    {
        userMap.put("张三","f51703256a38e6bab3d9410a070c32ea");
        super.setName("customRealm");
//        super.setName("123123");  //这里设置是错误的，下面的认证过程还是正确的，没找到原因，最后解决下
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

    /**
     * 临时设置下信息
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("员工");
        return sets;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
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
        //2 通过用户名到数据库获取凭证（该凭证就是用户名和salt 加密后的密文）
        String password = getPasswordByUserName(username);
        if(password == null) {
             return null;
        }
        //
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("张三",password,"customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));//加盐
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

    /**
     * 常量加盐生成加密的密文
     * @param args
     */
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","salt");
        System.out.println(md5Hash);
    }
}
