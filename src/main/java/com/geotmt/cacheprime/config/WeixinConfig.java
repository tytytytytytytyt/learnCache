package com.geotmt.cacheprime.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

@Data
@Component
@ConfigurationProperties(prefix = "weixin")
public class WeixinConfig {

    private String appid;
    private String secret;
    private String redirectUri;
    private String authorizedUrl;
    private String access_token;
    private String userinfo;


    public String getAuthorizedUrl() {
        //https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
        return authorizedUrl.replace("APPID", appid).replace("REDIRECT_URI", URLEncoder.encode(redirectUri));
    }

    public String getAccessTokenUrl(String code) {
        //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        return access_token.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
    }

    public String getUserInfo(String accessToken, String openId) {
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        return userinfo.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public void setAuthorizedUrl(String authorizedUrl) {
        this.authorizedUrl = authorizedUrl;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(String userinfo) {
        this.userinfo = userinfo;
    }
}
