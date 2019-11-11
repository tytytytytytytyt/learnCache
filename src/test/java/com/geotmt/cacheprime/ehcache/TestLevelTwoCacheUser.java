package com.geotmt.cacheprime.ehcache;

import com.geotmt.cacheprime.utils.OkHttpUtil;
import com.geotmt.cacheprime.utils.bean.HttpResult;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;

public class TestLevelTwoCacheUser {


    @Test
    public void testLevelTwoCache()  {
        String url = "http://127.0.0.1:8010/twocache/getUserByAccount";
        HashMap<String, String> params = Maps.newHashMap();
        params.put("id", "47");
        HttpResult httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);

        httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);

    }


    @Test
    public void cacheAndDestoryCache()  {
        // update方法
        String url = "http://127.0.0.1:8010/twocache/updatePwdById";
        HashMap<String, String> params = Maps.newHashMap();
        params.put("id", "47");
        params.put("cuserPassword", "modifypassword");
        HttpResult httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);

        // get方法
        url = "http://127.0.0.1:8010/twocache/getUserByAccount";
        HashMap<String, String> params1 = Maps.newHashMap();
        params1.put("id", "47");
        httpResult = OkHttpUtil.doGet(url, null, params1);
        System.out.println(httpResult);

        httpResult = OkHttpUtil.doGet(url, null, params1);
        System.out.println(httpResult);

    }
}
