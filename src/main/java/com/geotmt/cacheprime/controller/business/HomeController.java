package com.geotmt.cacheprime.controller.business;

import com.geotmt.cacheprime.entity.SysUserDO;
import com.geotmt.cacheprime.service.ISysUserService;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private ISysUserService userService;

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "index";
    }

    @GetMapping("/loginPage")
    public String login(){
        return "login";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        @RequestParam(value = "username")String username,
                        @RequestParam(value = "password")String password,
                        @RequestParam(value = "vertifyCode")String vertifyCode){
        Object attribute = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        return null;
    }




    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(SysUserDO userDO){
        // 此处省略校验逻辑
        userService.insert(userDO);
        return "redirect:register?success";
    }


}
