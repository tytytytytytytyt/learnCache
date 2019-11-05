package com.geotmt.cache;


import com.geotmt.cache.entity.Cuser;
import com.geotmt.cache.entity.User;
import com.geotmt.cache.service.ICuserService;
import com.geotmt.cache.service.IUserService;
import com.google.common.base.Joiner;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class CacheApplicationTests {

    @Autowired
    private ICuserService cuserService;
    @Autowired
    private IUserService userService;



    @Test
    public void doubleDB() {
        log.info("查看master 库数据~~~~~~~~~~~~~");
        List<User> userList = userService.getUserByRoleId("1");
        String userNames = userList.stream().map(user -> user.getName()).collect(Collectors.joining(","));
        log.info(userNames);

        log.info("查看cluster 库数据~~~~~~~~~~~~~");
        List<Cuser> cuserList = cuserService.getCusersByType(10L);
        String accounts = cuserList.stream().map(cuser -> cuser.getCuserAccount()).collect(Collectors.joining(","));
        log.info(accounts);
    }

}