package com.geotmt.cacheprime;


import com.geotmt.cacheprime.config.jta.DBConfig1;
import com.geotmt.cacheprime.config.jta.DBConfig2;
import com.geotmt.cacheprime.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
//@EnableConfigurationProperties(value = { DBConfig1.class, DBConfig2.class })
public class CacheApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CacheApplication.class, args);
        SpringContextUtil.setApplicationContext(applicationContext);
    }

}
