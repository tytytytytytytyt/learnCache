package com.geotmt.cacheprime;


import lombok.extern.log4j.Log4j2;
//import org.ehcache.Cache;
//import org.ehcache.CacheManager;
//import org.ehcache.config.CacheConfiguration;
//import org.ehcache.config.builders.CacheConfigurationBuilder;
//import org.ehcache.config.builders.ResourcePoolsBuilder;
//import org.ehcache.config.units.EntryUnit;
//import org.ehcache.config.units.MemoryUnit;
//import org.ehcache.expiry.Duration;
//import org.ehcache.expiry.Expirations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


/**
 * 此测试类只支持ehcache3.3版本  但是springboot 现在还不支持因此注释掉
 */
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class EhcacheTest {

//    @Autowired
//    private CacheManager ehcacheManager;
//
//    // userCache中的ttl是后设置的因此不管之前是不是一直访问key,到一分钟后key都会失效
//    @Test
//    public void ehcacheTestTTL() throws InterruptedException {
//
//        Cache<String, String> mineCache = ehcacheManager.getCache("userCache", String.class, String.class);
//        StringBuilder strTemp = new StringBuilder("测试");
//        mineCache.put("key", strTemp.toString());
//
//        int count = 0;
//        while(true){
//            Thread.sleep(50);
//            String keyValue = mineCache.get("key");
//            if(keyValue != null){
//                System.out.println("keyValue:  [" + keyValue + "]  , count:  [" + ++count + "]");
//            }else {
//                break;
//            }
//        }
//
//        // 关闭ehcache
//        ehcacheManager.close();
//
//    }
//
//    // 缓存过期策略 是tti设置在最后的，在循环中每次访问后，这个缓存都永不过期，不会执行到break
//    @Test
//    public void ehcacheTestTTI() throws InterruptedException {
//
//
//        CacheConfiguration<String, String> TTICacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
//                String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(300, EntryUnit.ENTRIES).offheap(30, MemoryUnit.MB).disk(60L, MemoryUnit.MB, false))
//                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1L, TimeUnit.SECONDS)))
//                .withExpiry(Expirations.timeToIdleExpiration(Duration.of(1L, TimeUnit.SECONDS)))
//                .build();
//
//        ehcacheManager.createCache("tti", TTICacheConfig);
//        Cache<String, String> mineCache = ehcacheManager.getCache("tti", String.class, String.class);
//        StringBuilder strTemp = new StringBuilder("ttiValue");
//        mineCache.put("ttiKey", strTemp.toString());
//
//
//        int count = 0;
//        while(true){
//            Thread.sleep(50);
//            String ttiValue = mineCache.get("ttiKey");
//            if(ttiValue != null){
//                System.out.println("ttiKey: [" + ttiValue + "] , count:  [" + ++count + "]");
//            }else {
//                break;
//            }
//        }
//        // 关闭ehcache
//        ehcacheManager.close();
//
//    }
//
//    // heap  1条数据,offHeap 无  ,disk 无
//    // heap只能存放一条数据，第二条就会覆盖第一条数据，可以取出来第二条数据，取不出来第一条数据
//    // heapKey1: [heapValue测试] ,heapKey2: [测试2] ,heapKey1: [null]
//    @Test
//    public void ehcacheTestHeap(){
//
//        CacheConfiguration<String, String> heapCacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
//                String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(1, EntryUnit.ENTRIES))
//                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1L, TimeUnit.SECONDS)))
//                .withExpiry(Expirations.timeToIdleExpiration(Duration.of(1L, TimeUnit.SECONDS)))
//                .build();
//
//        ehcacheManager.createCache("heap", heapCacheConfig);
//        Cache<String, String> mineCache = ehcacheManager.getCache("heap", String.class, String.class);
//        StringBuilder strTemp = new StringBuilder("heapValue");
//        strTemp.append("测试");
//
//        mineCache.put("heapKey1", strTemp.toString());
//        System.out.println("heapKey1: [" + mineCache.get("heapKey1") + "]");
//
//        strTemp = new StringBuilder("测试2");
//        mineCache.put("heapKey2", strTemp.toString());
//        System.out.println("heapKey2: [" +   mineCache.get("heapKey2") + "]");
//
//        System.out.println("heapKey1: [" + mineCache.get("heapKey1") + "]");
//        // 关闭ehcache
//        ehcacheManager.close();
//    }
//
//
//
//    // heap  1条数据,offHeap 1MB数据 ,disk 无 ，由于超过了offHeap储存
//    // 因此会StoreAccessException: The element with key 'heapKey1' is too large to be stored in this offheap store.
//    // 超出数据没有dick可以放置数据，因此Heapkey1 取出来的数据为null,报错后会释放掉heapKey1的offHeap储存，当数据超过1条的时候可以进去到offHeap来存储
//    // 执行结果 heapKey1: [null] ，heapKey2: [测试2]，heapKey1: [null]
//    @Test
//    public void ehcacheTestHeapAndOffheap(){
//
//        CacheConfiguration<String, String> heapCacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
//                String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(1, EntryUnit.ENTRIES).offheap(1, MemoryUnit.MB))
//                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1L, TimeUnit.SECONDS)))
//                .withExpiry(Expirations.timeToIdleExpiration(Duration.of(1L, TimeUnit.SECONDS)))
//                .build();
//
//        ehcacheManager.createCache("heap", heapCacheConfig);
//        Cache<String, String> mineCache = ehcacheManager.getCache("heap", String.class, String.class);
//        StringBuilder strTemp = new StringBuilder("heapValue");
//        while (strTemp.toString().getBytes().length <= 1024 * 1024) {
//            strTemp.append("测试一二三四五六七八九十");
//        }
//
//        System.out.println("heapKey1 的value 大小为：" + strTemp.toString().getBytes().length + " Byte,1MB大小为:" + 1024*1024 + " Byte");
//        mineCache.put("heapKey1", strTemp.toString());
//        System.out.println("heapKey1: [" + mineCache.get("heapKey1") + "]");
//
//
//        strTemp = new StringBuilder("测试2");
//        mineCache.put("heapKey2", strTemp.toString());
//        System.out.println("heapKey2: [" +   mineCache.get("heapKey2") + "]");
//
//        // 关闭ehcache
//        ehcacheManager.close();
//    }
//
//
//    // heap  1条数据,offHeap 1MB数据 ,disk 6MB ，由于超过了offHeap最大容量,
//    // 因此会StoreAccessException: The element with key 'heapKey1' is too large to be stored in this offheap store.
//    // 但是超出的数据会有Disk来兜底，数据不会丢失，heapKey1释放掉的offHeap，当数据超过1条的时候可以进去到offHeap来存储
//    @Test
//    public void ehcacheTestDisk(){
//
//        CacheConfiguration<String, String> heapCacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
//                String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(1, EntryUnit.ENTRIES).offheap(1, MemoryUnit.MB).disk(6L, MemoryUnit.MB, false))
//                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1L, TimeUnit.SECONDS)))
//                .withExpiry(Expirations.timeToIdleExpiration(Duration.of(1L, TimeUnit.SECONDS)))
//                .build();
//
//        ehcacheManager.createCache("heap", heapCacheConfig);
//        Cache<String, String> mineCache = ehcacheManager.getCache("heap", String.class, String.class);
//        StringBuilder strTemp = new StringBuilder("heapValue");
//        while (strTemp.toString().getBytes().length <= 1024 * 1024) {
//            strTemp.append("测试一二三四五六七八九十");
//        }
//
//        System.out.println("heapKey1 的value 大小为：" + strTemp.toString().getBytes().length + " Byte,1MB大小为:" + 1024*1024 + " Byte");
//        mineCache.put("heapKey1", strTemp.toString());
//        System.out.println("heapKey1: [" + mineCache.get("heapKey1") + "]");
//
//
//        strTemp = new StringBuilder("测试2");
//        mineCache.put("heapKey2", strTemp.toString());
//        System.out.println("heapKey2: [" +   mineCache.get("heapKey2") + "]");
//
//        // 关闭ehcache
//        ehcacheManager.close();
//    }


}
