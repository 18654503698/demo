package com.xuexi.controller;

import com.xuexi.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {

    @RequestMapping("login")
    public String login(User user) {
        Subject subject = SecurityUtils.getSubject();
        return "登录成功拍";
    }
}
