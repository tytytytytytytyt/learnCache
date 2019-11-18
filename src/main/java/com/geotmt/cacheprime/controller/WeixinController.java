package com.geotmt.cacheprime.controller;


import com.alibaba.fastjson.JSONObject;
import com.geotmt.cacheprime.config.WeixinConfig;
import com.geotmt.cacheprime.utils.HttpClientUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
public class WeixinController {

    @Autowired
    private WeixinConfig weixinConfig;

    @RequestMapping("/callback")
    public void callback(String code, HttpServletRequest request) {
        // 1.使用Code 获取 access_token
        String accessTokenUrl = weixinConfig.getAccessTokenUrl(code);
        JSONObject resultAccessToken = HttpClientUtils.httpGet(accessTokenUrl);
        boolean containsKey = resultAccessToken.containsKey("errcode");

        if (containsKey) {
            request.setAttribute("errorMsg", "登录异常，请重试!");
            return ;
        }
        // 2.使用access_token获取用户信息
        String accessToken = resultAccessToken.getString("access_token");
        String openid = resultAccessToken.getString("openid");
        // 3.拉取用户信息(需scope为 snsapi_userinfo)
        String userInfoUrl = weixinConfig.getUserInfo(accessToken, openid);
        JSONObject userInfoResult = HttpClientUtils.httpGet(userInfoUrl);
        log.info("-------------userInfoResult:" + userInfoResult);
        request.setAttribute("nickname", userInfoResult.getString("nickname"));
        request.setAttribute("city", userInfoResult.getString("city"));
        request.setAttribute("headimgurl", userInfoResult.getString("headimgurl"));
    }


    @RequestMapping("/authorizedUrl")
    public String index() {
        String url = weixinConfig.getAuthorizedUrl();
        log.info("----------url:"+url);
        return "redirect:" + url;
        //return "redirect:" + weiXinUtils.getAuthorizedUrl();
    }
}
