package com.geotmt.cacheprime.annotation;


import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.log4j.Log4j2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Log4j2
@Aspect
@Component
public class RateLimiterAspect {

    private ConcurrentHashMap<String,RateLimiter> limiters = new ConcurrentHashMap<>();
    private static HashMap systemMsg = new HashMap();
    static {
        systemMsg.put("code",50010);
        systemMsg.put("msg","系统异常");

    }

    @Pointcut("@annotation(com.geotmt.cacheprime.annotation.Limiter)")
    public void limitPointCut(){
    }

    @Around("limitPointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Limiter annotation = signature.getMethod().getAnnotation(com.geotmt.cacheprime.annotation.Limiter.class);

        try {
            if (annotation == null) {
                log.info("this method have anything RateLimiter annotion ");
            } else {
                // 获取注解上的参数 获取配置的速率
                int permitsPerSecond = annotation.permitsPerSecond();
                // 获取等待令牌等待时间
                int timeOut = annotation.timeOut();
                RateLimiter limiter = getRateLimiter(permitsPerSecond);
                // 判断令牌桶获取token 是否超时
                boolean acquire = limiter.tryAcquire(timeOut, TimeUnit.MILLISECONDS);
                if (!acquire) {
                    // 在timeOut毫秒内必须获取令牌，获取不到将会令牌被该服务被降级
                    log.info("系统繁忙，请稍后");
                    return serverDown();
                }
            }
            log.info("下单成功");
            return joinPoint.proceed();
        }catch (Throwable e){
            e.printStackTrace();
            log.error("Limiter of aspect  exception : "+ e);
        }
        log.info("系统异常");
        return getSystemException();
    }

    private Object getSystemException() {
        return systemMsg;
    }

    private Object serverDown() {
        return "系统繁忙请稍后。。。";
    }

    private RateLimiter getRateLimiter(int permitsPerSecond) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        RateLimiter limiter = limiters.get(requestURI);
        if(limiter == null){
            // 开启令牌通限流
            limiter = RateLimiter.create(permitsPerSecond);
            limiters.put(requestURI,limiter);
        }
        return limiter;
    }

}
