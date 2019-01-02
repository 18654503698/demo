package com.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class IniRealmTest {
    @Test
    public void testAuthentication() {
        //创建iniRealm 对象
        IniRealm iniRealm = new IniRealm("classpath:user.ini");
        //第一步： 构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //第二步: 主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("张三","1");
        subject.login(token);
        System.out.println(subject.isAuthenticated());//是否认证
        subject.checkRoles("admin");//角色
        subject.checkPermission("user:delete");//权限
    }
}
