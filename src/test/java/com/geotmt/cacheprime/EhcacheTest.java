package com.geotmt.cacheprime;


import lombok.extern.log4j.Log4j2;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class EhcacheTest {

    @Autowired
    private CacheManager ehcacheManager;

    // userCache中的ttl是后设置的因此不管之前是不是一直访问key,到一分钟后key都会失效
    @Test
    public void ehcacheTestTTL() throws InterruptedException {

        Cache<String, String> mineCache = ehcacheManager.getCache("userCache", String.class, String.class);
        StringBuilder strTemp = new StringBuilder("测试");
        mineCache.put("key", strTemp.toString());

        int count = 0;
        while(true){
            Thread.sleep(50);
            String keyValue = mineCache.get("key");
            if(keyValue != null){
                System.out.println("keyValue:  [" + keyValue + "]  , count:  [" + ++count + "]");
            }else {
                break;
            }
        }

        // 关闭ehcache
        ehcacheManager.close();

    }

    // 缓存过期策略 是tti设置在最后的，在循环中每次访问后，这个缓存都永不过期，不会执行到break
    @Test
    public void ehcacheTestTTI() throws InterruptedException {


        CacheConfiguration<String, String> TTICacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(300, EntryUnit.ENTRIES).offheap(30, MemoryUnit.MB).disk(60L, MemoryUnit.MB, false))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1L, TimeUnit.SECONDS)))
                .withExpiry(Expirations.timeToIdleExpiration(Duration.of(1L, TimeUnit.SECONDS)))
                .build();

        ehcacheManager.createCache("tti", TTICacheConfig);
        Cache<String, String> mineCache = ehcacheManager.getCache("tti", String.class, String.class);
        StringBuilder strTemp = new StringBuilder("ttiValue");
        mineCache.put("ttiKey", strTemp.toString());


        int count = 0;
        while(true){
            Thread.sleep(50);
            String ttiValue = mineCache.get("ttiKey");
            if(ttiValue != null){
                System.out.println("ttiKey: [" + ttiValue + "] , count:  [" + ++count + "]");
            }else {
                break;
            }
        }
        // 关闭ehcache
        ehcacheManager.close();

    }


}
