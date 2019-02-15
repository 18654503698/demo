package com.xuexi.controller;

import com.xuexi.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {

    @RequestMapping("login")
    public String login(User user) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return "登录成功";
    }
}
