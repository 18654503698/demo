package com.shirotest;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
public class JdbcRealmTest {
    DruidDataSource dataSource = new DruidDataSource();//德鲁伊数据源
    {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }
    @Test
    public void testAuthentication() {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);//打开权限开关
        String sql = "select password from test_user where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);
        //第一步： 构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //第二步: 主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken("张三","1");
        UsernamePasswordToken token = new UsernamePasswordToken("李四","123");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        /*subject.checkRole("admin");
        subject.checkPermission("user:select");//这里要设置打开权限开关*/
    }
}
