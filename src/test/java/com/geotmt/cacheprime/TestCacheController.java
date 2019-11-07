package com.geotmt.cacheprime;


import com.geotmt.cacheprime.controller.CacheController;
import com.geotmt.cacheprime.utils.OkHttpUtil;
import com.geotmt.cacheprime.utils.bean.HttpResult;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class TestCacheController {



    @Test
    public void cuserbyaccount()  {
        String url = "http://127.0.0.1:8010/cache/cuserbyaccount";
        HashMap<String, String> params = Maps.newHashMap();

        params.put("type", "");
        params.put("cuserAccount", "");
        params.put("customerId", "10");
        params.put("cuserId", "5");
        HttpResult httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);

    }


    /**
     * 测试同是key = "#cuserId" 的
     * @Cacheable(value = "cuserCache", key = "#cuserId")
     * @CacheEvict(value = "cuserCache", key = "#cuserId")
     */
    @Test
    public void cuserbyCuserid()  {
        // 查询用户id为5的用户 缓存起来
        String url = "http://127.0.0.1:8010/cache/cuserbycuserid";
        HashMap<String, String> params = Maps.newHashMap();
        params.put("cuserId", "5");
        HttpResult httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);

        // 查询用户id为5的用户
        httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);

        // 更新id为5的用户缓存失效
        url = "http://127.0.0.1:8010/cache/updatecuserstatus";
        HashMap<String, String> params1 = Maps.newHashMap();
        params1.put("cuserId", "5");
        params1.put("status","2");
        httpResult = OkHttpUtil.doPostForm(url, null, params1);
        System.out.println(httpResult);

        // 查询用户id为5的用户 缓存起来
        url = "http://127.0.0.1:8010/cache/cuserbycuserid";
        HashMap<String, String> params2 = Maps.newHashMap();
        params2.put("cuserId", "5");
        httpResult = OkHttpUtil.doGet(url, null, params2);
        System.out.println(httpResult);

        // 查询用户id为5的用户
        httpResult = OkHttpUtil.doGet(url, null, params2);
        System.out.println(httpResult);
    }


    /**
     * 测试同是key =#cuserStatus.cuserId 的
     * @CacheEvict(value = "cuserCache", key = "#cuserStatus.cuserId")
     * @Cacheable(value = "cuserCache", key = "#cuserStatus.cuserId")
     */
    @Test
    public void cuserByEntity()  {
        // 查询用户id为5的用户 缓存起来
        String url = "http://127.0.0.1:8010/cache/getcuserbycuserentity";
        HashMap<String, String> params = Maps.newHashMap();
        params.put("cuserId", "5");
        HttpResult httpResult = OkHttpUtil.doPostForm(url, null, params);
        System.out.println(httpResult);

        // 查询用户id为5的用户
        httpResult = OkHttpUtil.doPostForm(url, null, params);
        System.out.println(httpResult);


        // 更新id为5的用户缓存失效
        url = "http://127.0.0.1:8010/cache/updatecuserstatusbyentity";
        HashMap<String, String> params1 = Maps.newHashMap();
        params1.put("cuserId", "5");
        params1.put("status","-1");
        httpResult = OkHttpUtil.doPostForm(url, null, params1);
        System.out.println(httpResult);

        // 查询用户id为5的用户 缓存起来
        url = "http://127.0.0.1:8010/cache/getcuserbycuserentity";
        HashMap<String, String> params2 = Maps.newHashMap();
        params2.put("cuserId", "5");
        httpResult = OkHttpUtil.doPostForm(url, null, params2);
        System.out.println(httpResult);

        // 查询用户id为5的用户
        httpResult = OkHttpUtil.doPostForm(url, null, params2);
        System.out.println(httpResult);
    }


    /**
     * @Cacheable(value = "cuserCache", key = "#cuserId")
     * @CacheEvict(value = "cuserCache", key = "#cuserStatus.cuserId")
     */
    @Test
    public void cuserByCuseridAndEntity()  {
        // 查询用户id为5的用户 缓存起来
        String url = "http://127.0.0.1:8010/cache/cuserbycuserid";
        HashMap<String, String> params = Maps.newHashMap();
        params.put("cuserId", "5");
        HttpResult httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);

        // 更新id为5的用户缓存失效失败
        url = "http://127.0.0.1:8010/cache/updatecuserstatusbyentity";
        HashMap<String, String> params1 = Maps.newHashMap();
        params1.put("cuserId", "5");
        params1.put("status","-1");
        httpResult = OkHttpUtil.doPostForm(url, null, params1);
        System.out.println(httpResult);

        // 查询用户id为5的用户
        url = "http://127.0.0.1:8010/cache/cuserbycuserid";
        HashMap<String, String> params2 = Maps.newHashMap();
        params2.put("cuserId", "5");
        httpResult = OkHttpUtil.doGet(url, null, params2);
        System.out.println(httpResult);

    }






    /**
     * 自调用缓存失效的解决办法，从容器中获取bean
     */
    @Test
    public void selfInvoke(){
        // 查询id为10的客户 缓存起来
        String  url = "http://127.0.0.1:8010/cache/cuserbyid";
        HashMap<String, String> params = Maps.newHashMap();
        params.put("cuserId", "5");
        HttpResult httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);

        // 自调用缓存失效的解决办法，从容器中获取bean
        url = "http://127.0.0.1:8010/cache/existcuser";
        httpResult = OkHttpUtil.doGet(url, null, params);
        System.out.println(httpResult);
    }
}
