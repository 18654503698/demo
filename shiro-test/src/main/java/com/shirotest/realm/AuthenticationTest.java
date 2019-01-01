package com.shirotest.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {
    DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
    @Before
    public void addUser() {
        //认证之前在realm 添加一个用户
        simpleAccountRealm.addAccount("zhangsan","123");
    }
    @Test
    public void testAuthentication() {
        //1 建立securityManager环境
        defaultSecurityManager.setRealm(simpleAccountRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        // 2 主题提交认证
        Subject subject = SecurityUtils.getSubject();//
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123");//默认构造器
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        subject.logout();
        System.out.println(subject.isAuthenticated());
    }
}
