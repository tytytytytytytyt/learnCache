package com.geotmt.cacheprime.controller.business;

import com.geotmt.cacheprime.utils.SessionKey;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@Controller
public class UserController {

    @GetMapping("/user")
    public String user(HttpServletRequest request, Model model){
        model.addAttribute("account", request.getSession().getAttribute(SessionKey.SYS_USER_ACCOUNT));
        return "user/user";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, Model model){
        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String attributeName = attributeNames.nextElement();
            request.getSession().removeAttribute(attributeName);
        }
        return "login";
    }

}
