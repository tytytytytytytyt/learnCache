package com.geotmt.cacheprime.config;

import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.impl.internal.store.heap.OnHeapStore;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class EhcacheConfig {



    @Bean(name="ehcacheManager")
    public CacheManager ehcacheManager() {

        ResourcePoolsBuilder  poolsBuilder = ResourcePoolsBuilder
                .newResourcePoolsBuilder()
                .heap(5000L, EntryUnit.ENTRIES)              //设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到offheap中
                .offheap(30L, MemoryUnit.MB)              //设置堆外储存大小(内存存储) 超出offheap的大小会淘汰规则被淘汰
                .disk(100L, MemoryUnit.MB, false); //// 配置磁盘持久化储存(硬盘存储)用来持久化到磁盘,这里设置为false不启用

        // 配置默认缓存属性
        CacheConfiguration<String, String> defaultCacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, poolsBuilder)                   // 缓存数据K和V的数值类型
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(30L, TimeUnit.SECONDS)))  //设置缓存过期时间
                .withExpiry(Expirations.timeToIdleExpiration(Duration.of(60L, TimeUnit.SECONDS)))  //设置被访问后过期时间(同时设置和TTL和TTI之后会被覆盖,这里TTI生效,之前版本xml配置后是两个配置了都会生效)
                .build();

        CacheConfiguration<String, String> userCacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(300, EntryUnit.ENTRIES).offheap(30, MemoryUnit.MB).disk(60L, MemoryUnit.MB, false))
                .withExpiry(Expirations.timeToIdleExpiration(Duration.of(1L, TimeUnit.SECONDS)))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1L, TimeUnit.SECONDS)))
                .build();

        // CacheManager管理缓存
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence("e:/ehcacheData"))// 硬盘持久化地址
                .withCache("defaultCache", defaultCacheConfig) //// 设置一个默认缓存配置
                .withCache("userCache",userCacheConfig)
                .build(true);//创建之后立即初始化

        return cacheManager;

//        这两个参数很容易误解，看文档根本没用，我仔细分析了ehcache的代码。结论如下：
//        1、timeToLiveSeconds的定义是：以创建时间为基准开始计算的超时时长；
//        2、timeToIdleSeconds的定义是：在创建时间和最近访问时间中取出离现在最近的时间作为基准计算的超时时长；
//        3、如果仅设置了timeToLiveSeconds，则该对象的超时时间=创建时间+timeToLiveSeconds，假设为A；
//        4、如果没设置timeToLiveSeconds，则该对象的超时时间=min(创建时间，最近访问时间)+timeToIdleSeconds，假设为B；
//        5、如果两者都设置了，则取出A、B 谁后设置的谁生效，也就是ttl与tti是存在覆盖的问题的，后面的设置会覆盖前面的设置。
    }


}
