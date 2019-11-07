package com.geotmt.cacheprime.controller;


import com.geotmt.cacheprime.entity.User;
import com.geotmt.cacheprime.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/twocache")
public class LevelTwoCacheUserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/getUserByAccount", method = RequestMethod.GET)
    public User getUserByAccount(@RequestParam(value = "id") String id) {
        return userService.getByAccount(id);
    }

    @RequestMapping(value = "/updatePwdById", method = RequestMethod.GET)
    public Integer updateUserPwdById(@RequestParam(value = "id") String id,@RequestParam(value = "cuserPassword")  String cuserPassword){
        return userService.updatePwdById(id,cuserPassword);
    }

}
