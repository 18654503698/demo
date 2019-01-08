package com.shirotest;

import com.shirotest.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmRealmTest {
    @Test
    public void testAuthentication() {
    /*    //创建 CustomRealm 对象
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
        subject.checkRole("员工");*/
        //md5 加密 加盐    加盐的好处是数据库存的是密码和盐值的密文，就算数据库中的密文丢失，解析出来的密文不是密码
        //创建 CustomRealm 对象
        CustomRealm customRealm = new CustomRealm();
        //第一步： 构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //第二步: 主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("张三","123456");
        subject.login(token);
        System.out.println(subject.isAuthenticated());//是否认证
        subject.checkRole("员工");
    }
}
