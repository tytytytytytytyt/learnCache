package com.geotmt.cacheprime;

import com.geotmt.cacheprime.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CacheApplication.class, args);
        SpringContextUtil.setApplicationContext(applicationContext);
    }

}
