package com.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("张三","1","admin");
    }
    @Test
    public void testAuthentication() {
        //第一步： 构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //第二步: 主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("张三","1");
        subject.login(token);
        System.out.println("登录结果"+subject.isAuthenticated());
/*        subject.logout();
        System.out.println("登录结果"+subject.isAuthenticated());*/
        subject.checkRoles("admin");
    }
}
