package com.geotmt.cacheprime.controller;

import com.geotmt.cacheprime.base.BaseController;
import com.geotmt.cacheprime.base.common.PayToken;
import com.geotmt.cacheprime.service.ITokenService;
import com.geotmt.cacheprime.utils.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/pay")
public class PayController extends BaseController {

    @Autowired
    private ITokenService tokenService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 这个接口是在两个服务器间通过http调用的，而非是前端页面
     */
    @RequestMapping("/getToken")
    public Object getPayToken(Long userId,Double money){
        if(Strings.isNullOrEmpty(String.valueOf(userId)) || Strings.isNullOrEmpty(String.valueOf(money))){
            return setResultError("参数为空");
        }
        PayToken payToken = new PayToken(userId, money);
        return setResultSuccessData(tokenService.getPayToken(payToken));
    }

    @RequestMapping("/pay")
    public Object pay(String token){
        if(Strings.isNullOrEmpty(token)){
            return setResultError("token参数为空");
        }
        PayToken payToken = redisUtils.get(token, PayToken.class);
        log.info("服务器端发起支付，支付数据:" + payToken);
        return setResultSuccess("支付成功");
    }


}
