package com.shop.controller;


import com.shop.pojo.User;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/doLogin")
    public String doLogin(String userPassword,
                          String username,
                          String valideCode,
                          HttpSession session,
                          Model model) {
        String vrifyCode = (String) session.getAttribute("vrifyCode");
      if (!vrifyCode.equals(valideCode)) {
        //if (vrifyCode==null) {
            model.addAttribute("errorMsg", "验证码错误");
            return "login";
        }

        List<User> userList = userService.isLogin(username, userPassword);

        if (userList.size() > 0 && userList != null) {
            session.setAttribute("user",userList.get(0));
            return "redirect:/queryAuctions";
        } else {
            model.addAttribute("errorMsg", "账号或密码错误");
            return "login";
        }

    }
    @RequestMapping("/toregesiter")
    public String toregesiter(){
        return "regesiter";
    }

    @RequestMapping(value = "/regesiterUser")
    public String regesiterUser(User user){
        user.setUserisadmin(0);

        this.userService.regesiterUser(user);

        return "redirect:/login";
    }

}
