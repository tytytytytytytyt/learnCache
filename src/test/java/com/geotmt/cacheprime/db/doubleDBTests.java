package com.geotmt.cacheprime.db;



import com.geotmt.cacheprime.entity.Cuser;
import com.geotmt.cacheprime.entity.User;
import com.geotmt.cacheprime.service.ICuserService;
import com.geotmt.cacheprime.service.IUserService;
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
public class doubleDBTests {

    @Autowired
    private ICuserService cuserService;
    @Autowired
    private IUserService userService;



    @Test
    public void doubleDB() {
        log.info("查看hello 库数据~~~~~~~~~~~~~");
        List<User> userList = userService.getUserByRoleId("1");
        String userNames = userList.stream().map(user -> user.getName()).collect(Collectors.joining(","));
        log.info(userNames);

        log.info("查看world 库数据~~~~~~~~~~~~~");
        List<Cuser> cuserList = cuserService.getCuserByCustomerId(10L);
        String accounts = cuserList.stream().map(cuser -> cuser.getCuserAccount()).collect(Collectors.joining(","));
        log.info(accounts);
    }

}
