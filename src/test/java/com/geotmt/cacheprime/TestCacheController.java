package com.geotmt.cacheprime;


import com.geotmt.cacheprime.controller.CacheController;
import com.geotmt.cacheprime.utils.OkHttpUtil;
import com.geotmt.cacheprime.utils.bean.HttpResult;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class TestCacheController {



    @Test
    public void ehcacheTestTTI()  {
        String url = "http://127.0.0.1:8010/cache/cuserbyaccount";
        HashMap<String, String> params = Maps.newHashMap();

        params.put("type", "");
        params.put("cuserAccount", "");
        params.put("customerId", "10");
        params.put("cuserId", "5");
        HttpResult httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);


    }
}
