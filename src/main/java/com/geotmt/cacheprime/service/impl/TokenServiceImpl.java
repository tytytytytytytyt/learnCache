package com.geotmt.cacheprime.service.impl;

import com.geotmt.cacheprime.base.common.ConstUtil;
import com.geotmt.cacheprime.base.common.PayToken;
import com.geotmt.cacheprime.service.ITokenService;
import com.geotmt.cacheprime.utils.RedisService;
import com.geotmt.cacheprime.utils.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Log4j2
@Service
public class TokenServiceImpl implements ITokenService {


    @Autowired
    private RedisUtils redisUtils;
    private static final long tokenTimeOut = 3600;

    @Override
    public String putToken(String name) {
        String token = UUID.randomUUID().toString();
        redisUtils.set(token, name, tokenTimeOut);
        log.info("put key = " + token + " value =" + name + " into cache");
        return token;
    }

    @Override
    public String getUserByToken(String token) {
        String userName = redisUtils.get(token, String.class);
        log.info("get key = " + token + " value =" + userName + " from cache");
        return userName;
    }

    @Override
    public String getPayToken(PayToken payToken) {
        long payTokenTimeOut = redisUtils.increment(ConstUtil.PAY_TOKEN_KEY);
        UUID uuid = UUID.randomUUID();
        String payTokenKey = uuid.toString() + payTokenTimeOut;
        redisUtils.set(payTokenKey,payToken,ConstUtil.PAY_TOKEN_KEY_TIMEOUT);
        return payTokenKey;
    }
}
