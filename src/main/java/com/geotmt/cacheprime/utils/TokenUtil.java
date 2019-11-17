package com.geotmt.cacheprime.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Log4j2
@Component
public class TokenUtil {

    @Autowired
    private  RedisUtils redisUtils;
    private Lock lock = new ReentrantLock();

    public String getToken(){
        lock.lock();
        long increment = redisUtils.increment(ConstUtil.TOKENID);
        String token = CommonUtil.getRandomString(10) + increment;
        redisUtils.set(token,token,ConstUtil.TOKENTIMEOUT);
        lock.unlock();
        return token;

    }

    public void setTokenToReq(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        request.setAttribute("token",this.getToken());
    }

    public Boolean isExistToken(String token){
        String tokenVal = redisUtils.get(token, String.class);
        if(tokenVal == null){
            return false;
        }
        redisUtils.remove(token);
        return true;
    }



}
