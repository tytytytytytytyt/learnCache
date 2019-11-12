package com.geotmt.cacheprime.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionContorller {

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object index() {
        return port;
    }

    @RequestMapping(value = "/createSession", method = RequestMethod.GET)
    public Object createSession(@RequestParam(value = "name") String name, HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println("存入Session  sessionid:信息" + session.getId() + ",nameValue:" + name + ",serverPort:" + port);
        session.setAttribute("name",name);
        return "success_" + session.getId() +"_"+ port;
    }

    @RequestMapping(value = "/getSession", method = RequestMethod.GET)
    public Object getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null){
            return "无session" +"_" + port;
        }
        System.out.println("获取Session sessionid:信息" + session.getId() + "serverPort:" + port);
        Object value = session.getAttribute("name");
        return "success_" + session.getId() + "_" + port + "_" + value;
    }

}
