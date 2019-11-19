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

    /**
     * 分布式实务
     * @Transactional(value = "transactionManager")
     */

    @RequestMapping("/transaction")
    public void transaction() {
        userService.transaction();
    }

    @RequestMapping("/transaction2")
    public void transaction2(Integer num) {
        userService.transaction2(num);
    }

    @RequestMapping("/transaction3")
    public void transaction3(Integer num) {
        userService.transaction3(num);
    }
}
