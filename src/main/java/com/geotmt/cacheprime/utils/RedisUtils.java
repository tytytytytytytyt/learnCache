package com.geotmt.cacheprime.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * redis cache 工具类
 * <p>
 * Created by cheng on 16/6/26.
 */
@Service
@Slf4j
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;


    /**
     * 批量删除对应的value
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的value
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisService.del(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     */
    public boolean exists(final String key) {
        return redisService.exists(key);
    }

    /**
     * 读取缓存
     */
    public <T> T get(final String key, Class<T> clazz) {
        return redisService.get(key, clazz);
    }

    public <T> T get(String key, String field, Class<T> clazz) {
        T result;
        if (StringUtils.isEmpty(field)) {
            result = redisService.get(key, clazz);
        } else {
            result = redisService.hGet(key, field, clazz);
        }
        return result;
    }

    /**
     * 写入缓存
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            redisService.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 写入缓存
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            redisService.set(key, value, expireTime);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 自增
     */
    public long increment(final String key, Long value) {
        return redisService.incr(key);
    }

    public long increment(final String key) {
        return redisService.incr(key);
    }


    public void expire(final String key, final Long seconds) {
        redisService.expire(key, seconds);
    }

    /**
     * 获取List
     *
     * @param key   键值
     * @param start 起始值
     * @param end   结束位置-- -1表示全部
     */
    public List<String> lrange(String key, long start, long end) {
        List<String> list = new ArrayList<>();
        try {
            ListOperations<String, String> valueOper = redisTemplate.opsForList();
            list = valueOper.range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 出队
     *
     * @param key   键值
     * @param count 个数
     * @param value 值
     * @return 个数
     */
    public Long lrem(String key, long count, String value) {
        Long num = 0L;
        try {
            ListOperations<String, String> valueOper = redisTemplate.opsForList();
            num = valueOper.remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 右入队
     *
     * @param key     键值
     * @param strings strings
     * @return 个数
     */
    public Long listRpush(String key, String... strings) {
        Long num = 0L;
        try {
            ListOperations<String, String> valueOper = redisTemplate.opsForList();
            num = valueOper.rightPushAll(key, strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 获取list的长度
     *
     * @param key 键值
     * @return 长度
     */
    public Long listSize(String key) {
        Long num = 0L;
        try {
            ListOperations<String, String> valueOper = redisTemplate.opsForList();
            num = valueOper.size(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 消减队列
     *
     * @param key   键值
     * @param start 起始值
     * @param end   终点值
     */
    public void listTrim(String key, long start, long end) {
        try {
            ListOperations<String, String> valueOper = redisTemplate.opsForList();
            valueOper.trim(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * redis发布消息
     *
     * @param channel 渠道
     * @param message 消息
     */
    public void sendMessage(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }

}

