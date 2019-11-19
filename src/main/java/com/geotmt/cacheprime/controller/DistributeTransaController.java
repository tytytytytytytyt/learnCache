package com.geotmt.cacheprime.controller;

import com.geotmt.cacheprime.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("jta")
@RestController
@RequestMapping("/jta")
public class DistributeTransaController {

    @Autowired
    private IUserService userService;


    @RequestMapping("/transaction")
    public void transaction() {

        userService.transaction();

    }
}
