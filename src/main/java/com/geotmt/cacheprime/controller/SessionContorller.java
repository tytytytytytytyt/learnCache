package com.geotmt.cacheprime.controller;

import com.geotmt.cacheprime.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ITokenService tokenService;

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


    /**
     * 解决分布式session问题，改用token
     * @param name
     * @return
     */
    @RequestMapping(value = "/putToken", method = RequestMethod.GET)
    public String putToken(@RequestParam(value = "name") String name) {
       return tokenService.putToken(name) + port;
    }

    @RequestMapping(value = "/getUserByToken", method = RequestMethod.GET)
    public String getUserByToken(@RequestParam(value = "token") String token) {
        return tokenService.getUserByToken(token) + port;
    }

}
