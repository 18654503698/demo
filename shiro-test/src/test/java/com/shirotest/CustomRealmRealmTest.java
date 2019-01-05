package com.shirotest;

import com.shirotest.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmRealmTest {
    @Test
    public void testAuthentication() {
        //创建 CustomRealm 对象
        CustomRealm customRealm = new CustomRealm();
        //第一步： 构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //第二步: 主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("张三","123");
        subject.login(token);
        System.out.println(subject.isAuthenticated());//是否认证
        subject.checkRole("员工");
    }
}
