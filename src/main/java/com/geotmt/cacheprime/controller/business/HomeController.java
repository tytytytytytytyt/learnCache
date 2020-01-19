package com.geotmt.cacheprime.controller.business;

import com.geotmt.cacheprime.base.common.HttpCode;
import com.geotmt.cacheprime.base.excepiton.GlobalException;
import com.geotmt.cacheprime.entity.SysUserDO;
import com.geotmt.cacheprime.service.ISysUserService;
import com.geotmt.cacheprime.utils.VertifyUtil;
import com.google.code.kaptcha.Constants;
import com.google.common.collect.Maps;
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

    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        @RequestParam(value = "username")String username,
                        @RequestParam(value = "password")String password,
                        @RequestParam(value = "vertifyCode")String vertifyCode){

        VertifyUtil.VertifyNotEmpty("username",username);
        VertifyUtil.VertifyNotEmpty("password",password);
        VertifyUtil.VertifyNotEmpty("vertifyCode",vertifyCode);

        String sessionCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(!sessionCode.toLowerCase().equals(vertifyCode)){
            throw new GlobalException(HttpCode.VERTIFY_CODE_ERROR);
        }
        SysUserDO userDO = userService.CheckUserNameAndPasswod(username,password);
        request.setAttribute("account",userDO.getAccount());
        return "index";
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
