package com.geotmt.cacheprime;


import lombok.extern.log4j.Log4j2;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class EhcacheTest {

    @Autowired
    private CacheManager ehcacheManager;


    /**
     * key:测试
     * key2:测试2
     * key:测试
     * key:null
     * @throws InterruptedException
     */
    @Test
    public void ehcache() throws InterruptedException {


        Cache<String, String> mineCache = ehcacheManager.getCache("userCache", String.class, String.class);
        StringBuilder strTemp = new StringBuilder("测试");
        mineCache.put("key", strTemp.toString());
        System.out.println("key:" + mineCache.get("key"));

        strTemp = new StringBuilder("测试2");
        mineCache.put("key2", strTemp.toString());
        System.out.println("key2:" + mineCache.get("key2"));

        Thread.sleep(1000);  //1000毫秒就是1秒
        System.out.println("key:" + mineCache.get("key"));
        // 关闭ehcache
        ehcacheManager.close();


        //设置缓存堆容纳元素个数(JVM内存空间)
 //.heap(1L, EntryUnit.ENTRIES)
//设置堆外储存大小(内存存储)
//.offheap(100L, MemoryUnit.MB)
// 配置磁盘持久化储存(硬盘存储)
//.disk(500L, MemoryUnit.MB, false)


    }


}
