package com.xuexi.controller;

import com.sun.deploy.net.HttpResponse;
import com.xuexi.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/")
public class UserController {
    @RequestMapping(value = "login",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public String login(User user) {
          try {
            //获取当前主题
            Subject subject = SecurityUtils.getSubject();
            //通过用户名和密码拿到唯一的token
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
        return "登录成功";
    }
}
