package com.geotmt.cacheprime;

import com.geotmt.cacheprime.utils.OkHttpUtil;
import com.geotmt.cacheprime.utils.bean.HttpResult;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;


public class Testlog4j2 {


    @Test
    public void log4j2ForSql() {

        String url = "http://127.0.0.1:8010/cache/getUserByRoleId";
        HashMap<String, String> params = Maps.newHashMap();
        params.put("roleId", "5");
        HttpResult httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);
    }
}
