package com.geotmt.cacheprime.controller.business;

import com.geotmt.cacheprime.utils.SessionKey;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {

    @GetMapping("/user")
    public String user(HttpServletRequest request, Model model){
        model.addAttribute("username", request.getSession().getAttribute(SessionKey.SYS_USER_ACCOUNT));
        return "user/user";
    }

}
