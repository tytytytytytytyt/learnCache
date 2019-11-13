package com.geotmt.cacheprime.controller;

import com.alibaba.fastjson.JSONObject;
import com.geotmt.cacheprime.utils.OkHttpUtil;
import com.geotmt.cacheprime.utils.bean.HttpResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Map;

@Log4j2
@Controller
public class RegionCrossController {

    @RequestMapping("/aindex")
    public String aindex(Map<String, Object> paramMap) {
        paramMap.put("name", "张三");
        paramMap.put("age", 35);
        log.info("a项目的index接口");
        return "home";
    }

    @RequestMapping("/")
    public String index(Map<String, Object> paramMap) {
        paramMap.put("name", "张三");
        paramMap.put("age", 35);
        log.info("a项目的index接口");
        return "redirect:/aindex";
    }

    /**
     *     1、告诉客户端（浏览器 ）允许跨域访问 *表示所有域名都是可以 在公司中正常的代码应该放入在过滤器中
     *          HttpServletResponse response  response.setHeader("Access-Control-Allow-Origin", "*"); 现在很多浏览器不能用
     *
     *     2、httpclient 弊端：多一次请求
     *
     *
     *     3、jsonp 只支持get请求
     *
     *

     */

    @RequestMapping("/forwardB")
    @ResponseBody
    public JSONObject forwardB(Map<String, Object> paramMap) {
        paramMap.put("name", "张三");
        paramMap.put("age", 35);

        HttpResult httpResult = OkHttpUtil.doGet("http://b.geotmt.com:8019/getBInfo", null, null);
        String result = httpResult.getResult();
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }
}
