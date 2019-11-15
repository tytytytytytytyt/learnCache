package com.geotmt.cacheprime.controller;


import com.geotmt.cacheprime.annotation.Limiter;
import com.geotmt.cacheprime.entity.User;
import com.geotmt.cacheprime.service.IOrderService;
import com.geotmt.cacheprime.service.IUserService;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * 使用RateLimiter 实现令牌通方式限流
 */
@Log4j2
@RestController
public class RateLimiterController {

    @Autowired
    private IOrderService orderService;
    // create 方法中传入一个参数 以每秒为单位固定的速率值 5r/s 每秒中往桶中存入5个令牌
    RateLimiter limiter = RateLimiter.create(5);


    @RequestMapping(value = "/ratelimiter", method = RequestMethod.GET)
    public String rateLimiter(HttpServletRequest  request) {
        // 1.限流处理 限流正常要放在网关 客户端从桶中获取对应的令牌，为什么返回double结果，这个结果表示 从桶中拿到令牌等待时间.
        // 2. 如果获取不到令牌，就会一直等待.设置服务降级处理（相当于配置在规定时间内如果没有获取到令牌的话，直接走服务降级。）
        // double acquire = rateLimiter.acquire();
        // System.out.println("从桶中获取令牌等待的时间:" + acquire);
        // 如果在500毫秒内如果没有获取到令牌的话，则直接走服务降级处理
        HttpSession session = request.getSession();
        if(limiter.tryAcquire()){
                log.info("user :"+ session.getId() +" 抢单成功");
                //log.info("user :"+ session.getId() +" 抢单成功耗时：" + limiter.acquire());
            return orderService.order();
        }

        log.info("服务繁忙，请重试");
        return failBack();
    }


    @Limiter(permitsPerSecond = 100,timeOut = 0)
    @RequestMapping(value = "/annolimiter", method = RequestMethod.GET)
    public String annoLimiter(){
        return orderService.order();
    }




    private String failBack() {
        return "服务繁忙，请重试";
    }
}
