package com.geotmt.cache.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Date: 2018/12/27
 * Time: 15:37
 *
 * @author 夏海华
 */
@Slf4j
@Component
public class RedisService {

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    /**
     * 删除key
     * @param keys 要删除的key
     * @return 删除的数量
     */

    public Long del(final byte[]... keys) {
        long st = System.currentTimeMillis();
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.del(keys));
        long et = System.currentTimeMillis();
        log.info("redis del :{},duration:{}", keys, (et - st));
        return obj;
    }


    public Long del(String... keys) {
        int length = keys.length;
        if (length == 0) {
            return -1L;
        }
        byte[][] bytes = new byte[length][];
        for (int i = 0; i < length; i++) {
            if (keys[i] == null) {
                return -1L;
            }
            bytes[i] = keys[i].getBytes(StandardCharsets.UTF_8);
        }
        return del(bytes);
    }

    /**
     * hash删除
     */

    public Long hDel(final byte[] key, final byte[]... fields) {
        long st = System.currentTimeMillis();
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.hDel(key, fields));
        long et = System.currentTimeMillis();
        log.info("redis hDel:{},duration:{}", new String(key, StandardCharsets.UTF_8), (et - st));
        return obj;
    }


    public Long hDel(String key, String... fields) {
        int length = fields.length;
        byte[][] bytes = new byte[length][];
        for (int i = 0; i < length; i++) {
            bytes[i] = fields[i].getBytes(StandardCharsets.UTF_8);
        }
        return hDel(key.getBytes(StandardCharsets.UTF_8), bytes);
    }

    /**
     * key是否存在
     * @param key not null
     * @return true false
     */

    public Boolean exists(final byte[] key) {
        long st = System.currentTimeMillis();
        Boolean obj = redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.exists(key));
        long et = System.currentTimeMillis();
        log.info("redis exists {}, duration:{}", new String(key, StandardCharsets.UTF_8), (et - st));
        return obj;
    }


    public Boolean exists(String key) {
        return exists(key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Determine if given hash {@code field} exists.
     */

    public Boolean hExists(final byte[] key, final byte[] field) {
        long st = System.currentTimeMillis();
        Boolean obj = redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.hExists(key, field));
        long et = System.currentTimeMillis();
        log.info("redis exists {}, duration:{}", new String(key, StandardCharsets.UTF_8), (et - st));
        return obj;
    }


    public Boolean hExists(String key, String field) {
        return hExists(key.getBytes(StandardCharsets.UTF_8), field.getBytes(StandardCharsets.UTF_8));
    }


    public String flushDB() {
        long st = System.currentTimeMillis() ;
        String obj = redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "yes";
        });
        long et = System.currentTimeMillis() ;
        log.info("redis flushDB duration:"+(et-st));
        return obj ;
    }


    public long dbSize() {
        long st = System.currentTimeMillis() ;
        Long obj =  redisTemplate.execute(RedisServerCommands::dbSize);
        long et = System.currentTimeMillis() ;
        log.info("redis dbSize duration:"+(et-st));
        return obj ;
    }

    /**
     * get by key,使用fastjson序列化
     * @param <T> 返回的类型
     * @return
     */

    public <T> T get(final byte[] key, final Class<T> clazz) {
        long st = System.currentTimeMillis();
        T obj = redisTemplate.execute((RedisCallback<T>) connection -> {
            byte[] result = connection.get(key);
            return SerializeUtil.unserialize(result, clazz);
        });
        long et = System.currentTimeMillis();
        log.info("redis get :{}, duration:{}", new String(key, StandardCharsets.UTF_8), (et - st));
        return obj;
    }

    /**
     * json序列化
     */

    public <T> T get(String key, final Class<T> clazz) {
        return get(key.getBytes(StandardCharsets.UTF_8), clazz);
    }


    public void setKeys(String pattern) {
        redisTemplate.keys(pattern);
    }

    /**
     * java原生序列化,复杂对象使用 少用
     */

    public Object get2(final byte[] key) {
        long st = System.currentTimeMillis();
        Object obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] result = connection.get(key);
            return SerializeUtil.javaUnserialize(result);
        });
        long et = System.currentTimeMillis();
        log.info("redis get obj {}, duration:{}", new String(key, StandardCharsets.UTF_8), (et - st));
        return obj;
    }

    /**
     * java原生序列化,复杂对象使用 少用
     */

    public Object get2(String key) {
        return get2(key.getBytes(StandardCharsets.UTF_8));
    }


    public String ping() {
        long st = System.currentTimeMillis();
        String obj = redisTemplate.execute(RedisConnectionCommands::ping);
        long et = System.currentTimeMillis();
        log.info("redis ping duration:{}", (et - st));
        return obj;
    }


    public void set(final byte[] key, final byte[] value, final long liveTime, final String timeUnit) {
        long st = System.currentTimeMillis();
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.set(key, value);
            if (liveTime > 0) {
                if ("millisecond".equalsIgnoreCase(timeUnit)) {
                    connection.pExpire(key, liveTime);
                } else {
                    connection.expire(key, liveTime);
                }
            }
            return 1L;
        });
        long et = System.currentTimeMillis();
        log.info("redis set {}:{};liveTime:{};timeUnit:{},duration:{} ", new String(key, StandardCharsets.UTF_8),
                new String(value, StandardCharsets.UTF_8), liveTime, timeUnit, (et - st));
    }


    public void set(final byte[] key, final byte[] value, final long liveTime) {
        set(key, value, liveTime,"second") ;
    }

    /**
     * json序列化
     */

    public void set(String key, Object value, long liveTime, String timeUnit) {
        set(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.serialize(value),liveTime,timeUnit);
    }

    /**
     * json序列化
     */

    public void set(String key, Object value, long liveTime) {
        set(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.serialize(value),liveTime);
    }

    /**
     * json序列化 默认永久 -1
     */

    public void set(String key, Object value) {
        set(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.serialize(value),-1L);
    }

    /**
     * 默认永久 -1
     */

    public void set(byte[] key, byte[] value) {
        set(key,value,-1L);
    }

    /**
     * 有效时间 默认永久 -1
     */

    public void set(String key, byte[] value) {
        set(key.getBytes(StandardCharsets.UTF_8),value,-1L);
    }


    /**
     * java序列化
     */

    public void set2(String key, Object value, long liveTime, String timeUnit) {
        set(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.javaSerialize(value),liveTime,timeUnit);
    }

    /**
     * java序列化
     */

    public void set2(String key, Object value, long liveTime) {
        set(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.javaSerialize(value),liveTime);
    }

    /**
     * 当 key 不存在时，返回 -2
     * 当 key 存在但没有设置剩余生存时间时，返回 -1
     * 否则，以秒为单位，返回 key 的剩余生存时间
     */

    public Long ttl(final byte[] key) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.ttl(key));
        long et = System.currentTimeMillis() ;
        log.info("redis ttl "+new String(key, StandardCharsets.UTF_8)+" duration:"+(et-st));
        return obj ;
    }

    /**
     * ttl 的ms表示
     */

    public Long pTtl(final byte[] key) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.pTtl(key));
        long et = System.currentTimeMillis() ;
        log.info("redis pTtl "+new String(key, StandardCharsets.UTF_8)+" duration:"+(et-st));
        return obj ;
    }


    public Long ttl(String key) {
        return ttl(key.getBytes(StandardCharsets.UTF_8));
    }


    public Long pTtl(String key) {
        return pTtl(key.getBytes(StandardCharsets.UTF_8));
    }



    public BoundHashOperations getHashOperations(String key) {
        return redisTemplate.boundHashOps(key);
    }



    public BoundListOperations getListOperations(String key) {
        return redisTemplate.boundListOps(key);
    }



    public BoundSetOperations getSetOperations(String key) {
        return redisTemplate.boundSetOps(key);
    }



    public BoundValueOperations getValueOperations(String key) {
        return redisTemplate.boundValueOps(key);
    }



    public BoundZSetOperations getZSetOperations(String key) {
        return redisTemplate.boundZSetOps(key);
    }



    public List<byte[]> lrange(final String key, final long s, final long e){
        long st = System.currentTimeMillis() ;
        List<byte[]> obj = redisTemplate.execute((RedisCallback<List<byte[]>>) connection -> connection.lRange(key.getBytes(StandardCharsets.UTF_8), s, e));
        long et = System.currentTimeMillis() ;
        log.info("redis lrange "+key+";s:"+s+";e:"+e+" duration:"+(et-st));
        return obj ;
    }



    public Long llen(final String key) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.lLen(key.getBytes(StandardCharsets.UTF_8)));
        long et = System.currentTimeMillis() ;
        log.info("redis llen "+key+" duration:"+(et-st));
        return obj ;
    }



    public Long lpush(final byte[] key, final byte[]... vals) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.lPush(key, vals));
        long et = System.currentTimeMillis() ;
        log.info("redis lpush "+new String(key, StandardCharsets.UTF_8)+" duration:"+(et-st));
        return obj ;
    }



    public Long lpush(String key, Object... vals) {
        int length = vals.length ;
        byte[][] bytes = new byte[length][] ;
        for(int i=0;i<length;i++){
            log.info("List lpush operation [key]:"+key+"[vals]:"+vals[i]);
            bytes[i] = SerializeUtil.serialize(vals[i]);
        }
        return lpush(key.getBytes(StandardCharsets.UTF_8), bytes) ;
    }



    public <T> T lpop(final String key, final Class<T> clazz) {
        long st = System.currentTimeMillis() ;
        T obj = redisTemplate.execute((RedisCallback<T>) connection -> {
            byte[] buff = null ;
            buff = connection.lPop(key.getBytes(StandardCharsets.UTF_8));
            T result = null ;
            if(buff != null && buff.length > 0){
                result = SerializeUtil.unserialize(buff,clazz);
            }
            return result;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis lpop "+key+" duration:"+(et-st));
        return obj ;
    }



    public Long rpush(final byte[] key, final byte[]... vals) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.rPush(key, vals));
        long et = System.currentTimeMillis() ;
        log.info("redis rpush "+new String(key, StandardCharsets.UTF_8)+" duration:"+(et-st));
        return obj ;
    }



    public Long rpush(String key, Object... vals) {
        int length = vals.length ;
        final byte[][] bvals = new byte[length][];
        for (int i = 0; i < length; i++) {
            log.info("List rpush operation [key]:"+key+"[vals]:"+vals[i]);
            bvals[i] = SerializeUtil.serialize(vals[i]) ;
        }
        return rpush(key.getBytes(StandardCharsets.UTF_8), bvals);
    }



    public <T> T rpop(final String key, final Class<T> clazz) {
        long st = System.currentTimeMillis() ;
        T obj = redisTemplate.execute((RedisCallback<T>) connection -> {
            byte[] buff = null ;
            try {
                buff = connection.rPop(key.getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                log.error("", e);
            }
            T result = null ;
            if(buff != null && buff.length > 0){
                result = SerializeUtil.unserialize(buff,clazz);
            }
            return result;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis rpop "+key+" duration:"+(et-st));
        return obj ;
    }



    public <T> T lindex(final String key, final long index, final Class<T> clazz) {
        long st = System.currentTimeMillis() ;
        T obj = redisTemplate.execute((RedisCallback<T>) connection -> {
            return SerializeUtil.unserialize(connection.lIndex(key.getBytes(StandardCharsets.UTF_8), index),clazz);
        });
        long et = System.currentTimeMillis() ;
        log.info("redis lindex "+key+";index:"+index+" duration:"+(et-st));
        return obj ;
    }



    public void lSet(final String key, final long index, final Object value){
        long st = System.currentTimeMillis() ;
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.lSet(key.getBytes(StandardCharsets.UTF_8), index, SerializeUtil.serialize(value)) ;
            return null ;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis lSet "+key+";index:"+index+" duration:"+(et-st));
    }



    public void lTrim(final String key, final long s, final long e){
        long st = System.currentTimeMillis() ;
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.lTrim(key.getBytes(StandardCharsets.UTF_8), s, e) ;
            return null ;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis lTrim "+key+";s:"+s+";e:"+e+" duration:"+(et-st));
    }



    public Long lRem (final String key, final long count, final Object value){
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.lRem(key.getBytes(StandardCharsets.UTF_8), count, SerializeUtil.serialize(value)));
        long et = System.currentTimeMillis() ;
        log.info("redis lRem "+key+";count:"+count+";value:"+value+" duration:"+(et-st));
        return obj ;
    }



    public Long lRem (final String key, final long index){
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.lSet(key.getBytes(StandardCharsets.UTF_8), index, SerializeUtil.serialize("__base__sys__deleted__sign__")) ;
            return connection.lRem(key.getBytes(StandardCharsets.UTF_8), 0, SerializeUtil.serialize("__base__sys__deleted__sign__")) ;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis lRem "+key+";index:"+index+" duration:"+(et-st));
        return obj ;
    }



    public byte[] hGet(final byte[] key, final byte[] field) {
        long st = System.currentTimeMillis() ;
        byte[] obj = redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.hGet(key, field));
        long et = System.currentTimeMillis() ;
        log.info("redis hGet "+new String(key, StandardCharsets.UTF_8)+";field:"+new String(field, StandardCharsets.UTF_8)+" duration:"+(et-st));
        return obj ;
    }



    public <T> T hGet(String key, String field, final Class<T> clazz) {
        return SerializeUtil.unserialize(hGet(key.getBytes(StandardCharsets.UTF_8),field.getBytes(StandardCharsets.UTF_8)),clazz);
    }



    public List<byte[]> hMGet(final byte[] key, final byte[]... fields) {
        long st = System.currentTimeMillis() ;
        List<byte[]> obj = redisTemplate.execute((RedisCallback<List<byte[]>>) connection -> connection.hMGet(key, fields));
        long et = System.currentTimeMillis() ;
        log.info("redis hMGet "+new String(key, StandardCharsets.UTF_8)+" duration:"+(et-st));
        return obj ;
    }



    public <T> List<T> hMGet(String key, final Class<T> clazz, String... fields) {
        int length = fields.length ;
        byte[][] bytes = new byte[length][] ;
        for(int i=0;i<length;i++){
            bytes[i] = fields[i].getBytes(StandardCharsets.UTF_8) ;
        }
        List<byte[]> list = hMGet(key.getBytes(StandardCharsets.UTF_8), bytes);
        List<T> result = null ;
        if(list!=null){
            result = new ArrayList<T>() ;
            for(byte[] item : list){
                result.add(SerializeUtil.unserialize(item,clazz)) ;
            }
        }
        return result ;
    }





    public Object hGet2(String key, String field) {
        return SerializeUtil.javaUnserialize(hGet(key.getBytes(StandardCharsets.UTF_8),field.getBytes(StandardCharsets.UTF_8)));
    }



    public List<Object> hMGet2(String key, String... fields) {
        int length = fields.length ;
        byte[][] bytes = new byte[length][] ;
        for(int i=0;i<length;i++){
            try {
                bytes[i] = fields[i].getBytes("UTF8") ;
            } catch (UnsupportedEncodingException e) {
                log.error("", e);
            }
        }
        List<byte[]> list = null ;
        try {
            list = hMGet(key.getBytes("UTF8"), bytes);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        List<Object> result = null ;
        if(list!=null){
            result = new ArrayList<Object>() ;
            for(byte[] item : list){
                result.add(SerializeUtil.javaUnserialize(item)) ;
            }
        }
        return result ;
    }



    public void hMSet(final byte[] key, final Map<byte[], byte[]> hashes) {
        long st = System.currentTimeMillis() ;
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.hMSet(key, hashes);
            return null ;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis hMSet "+new String(key, StandardCharsets.UTF_8)+" duration:"+(et-st));
    }



    public void hMSet(String key, Map<String, Object> hashes) {
        Map<byte[], byte[]> hashes2 = null ;
        if(hashes!=null){
            hashes2 = new HashMap<>() ;
            Set<String> set = hashes.keySet();
            for(String hashesKey : set){
                Object obj = hashes.get(hashesKey);
                hashes2.put(SerializeUtil.serialize(hashesKey), SerializeUtil.serialize(obj));
            }
        }
        hMSet(key.getBytes(StandardCharsets.UTF_8), hashes2) ;
    }



    public Boolean hSet(final byte[] key, final byte[] field, final byte[] value, final long liveTime, final String timeUnit) {
        long st = System.currentTimeMillis() ;
        Boolean obj = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            boolean b = connection.hSet(key, field, value);
            if (liveTime > 0) {
                if("millisecond".equalsIgnoreCase(timeUnit)){
                    connection.pExpire(key, liveTime);
                }else{
                    connection.expire(key, liveTime);
                }
            }
            return b ;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis hSet "+new String(key, StandardCharsets.UTF_8)+";field:"+new String(field, StandardCharsets.UTF_8)+";"+
                new String(value, StandardCharsets.UTF_8)+";liveTime:"+liveTime+";timeUnit:"+timeUnit+" duration:"+(et-st));
        return obj ;
    }



    public Boolean hSet(final byte[] key, final byte[] field, final byte[] value, final long liveTime) {
        return hSet(key, field, value, liveTime, "second") ;
    }


    public Boolean hSet(String key, String field, Object value, long liveTime, String timeUnit) {
        return hSet(key.getBytes(StandardCharsets.UTF_8), field.getBytes(StandardCharsets.UTF_8), SerializeUtil.serialize(value),liveTime,timeUnit);
    }


    public Boolean hSet(String key, String field, Object value, long liveTime) {
        return hSet(key.getBytes(StandardCharsets.UTF_8), field.getBytes(StandardCharsets.UTF_8), SerializeUtil.serialize(value),liveTime);
    }



    public Boolean hSet2(String key, String field, Object value, long liveTime, String timeUnit) {
        return hSet(key.getBytes(StandardCharsets.UTF_8), field.getBytes(StandardCharsets.UTF_8), SerializeUtil.javaSerialize(value),liveTime,timeUnit);
    }



    public Boolean hSet2(String key, String field, Object value, long liveTime) {
        try {
            return hSet(key.getBytes("UTF8"), field.getBytes("UTF8"), SerializeUtil.javaSerialize(value),liveTime);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null ;
    }



    public Boolean hSetNX(final byte[] key, final byte[] field, final byte[] value, final long liveTime, final String timeUnit) {
        long st = System.currentTimeMillis() ;
        Boolean obj = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            boolean b =  connection.hSetNX(key, field, value);
            if (liveTime > 0) {
                if("millisecond".equalsIgnoreCase(timeUnit)){
                    connection.pExpire(key, liveTime);
                }else{
                    connection.expire(key, liveTime);
                }
            }
            return b ;
        });
        long et = System.currentTimeMillis() ;
        try {
            log.info("redis hSetNX "+new String(key,"UTF8")+";field:"+new String(field,"UTF8")+";"+new String(value,"UTF8")+";liveTime:"+liveTime+";timeUnit:"+timeUnit+" duration:"+(et-st));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return obj ;
    }


    public Boolean hSetNX(final byte[] key, final byte[] field, final byte[] value, final long liveTime) {
        return hSetNX(key,field,value,liveTime,"second") ;
    }


    public Boolean hSetNX(String key, String field, Object value, long liveTime, String timeUnit) {
        try {
            return hSetNX(key.getBytes("UTF8"), field.getBytes("UTF8"), SerializeUtil.serialize(value),liveTime,timeUnit);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null ;
    }


    public Boolean hSetNX(String key, String field, Object value, long liveTime) {
        try {
            return hSetNX(key.getBytes("UTF8"), field.getBytes("UTF8"), SerializeUtil.serialize(value),liveTime);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null ;
    }


    public Boolean hSetNX2(String key, String field, Object value, long liveTime, String timeUnit) {
        try {
            return hSetNX(key.getBytes("UTF8"), field.getBytes("UTF8"), SerializeUtil.javaSerialize(value),liveTime,timeUnit);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null ;
    }


    public Boolean hSetNX2(String key, String field, Object value, long liveTime) {
        try {
            return hSetNX(key.getBytes("UTF8"), field.getBytes("UTF8"), SerializeUtil.javaSerialize(value),liveTime);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null ;
    }



    public Long sAdd(final byte[] key, final byte[]... values) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.sAdd(key, values));
        long et = System.currentTimeMillis() ;
        log.info("redis sAdd "+new String(key, StandardCharsets.UTF_8)+";"+" duration:"+(et-st));
        return obj ;
    }



    public Long sAdd(String key, Object... values) {
        int length = values.length ;
        byte[][] bytes = new byte[length][] ;
        for(int i=0;i<length;i++){
            bytes[i] = SerializeUtil.serialize(values[i]);
        }
        try {
            return sAdd(key.getBytes("UTF8"), bytes);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null ;
    }


    public Set<byte[]> sMembers(final byte[] key) {
        long st = System.currentTimeMillis() ;
        Set<byte[]> obj = redisTemplate.execute((RedisCallback<Set<byte[]>>) connection -> connection.sMembers(key));
        long et = System.currentTimeMillis() ;
        log.info("redis sMembers "+new String(key, StandardCharsets.UTF_8)+";"+" duration:"+(et-st));
        return obj ;
    }


    public <T> Set<T> sMembers(String key, Class<T> clazz) {
        Set<byte[]> set = null;
        try {
            set = sMembers(key.getBytes("UTF8"));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        if(clazz.isInstance(byte[].class)){
            return (Set<T>)set ;
        }else{
            Set<T> set2 = new HashSet<T>();
            Iterator<byte[]> it = set.iterator();
            while (it.hasNext()) {
                byte[] bytes = it.next();
                T t = SerializeUtil.unserialize(bytes, clazz) ;
                set2.add(t) ;
            }
            return set2 ;
        }
    }


    public Boolean sMove(final byte[] srcKey, final byte[] destKey, final byte[] value) {
        long st = System.currentTimeMillis() ;
        Boolean obj = redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.sMove(srcKey, destKey, value));
        long et = System.currentTimeMillis() ;
        try {
            log.info("redis sMove "+new String(srcKey,"UTF8")+";"+new String(destKey,"UTF8")+" duration:"+(et-st));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return obj ;
    }



    public Boolean sMove(String srcKey, String destKey, Object value) {
        try {
            return sMove(srcKey.getBytes("UTF8"), destKey.getBytes("UTF8"), SerializeUtil.serialize(value)) ;
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null ;
    }



    public Long sRem(final byte[] key, final byte[]... values) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> connection.sRem(key, values));
        long et = System.currentTimeMillis() ;
        log.info("redis sRem "+new String(key, StandardCharsets.UTF_8)+"; duration:"+(et-st));
        return obj ;
    }



    public Long sRem(String key, Object... values) {
        int length = values.length ;
        byte[][] bytes = new byte[length][] ;
        for(int i=0;i<length;i++){
            bytes[i] = SerializeUtil.serialize(values[i]);
        }
        try {
            return sRem(key.getBytes("UTF8"), bytes);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null ;
    }



    public Long incr(final byte[] key) {
        return incr(key,-1,"",true) ;
    }


    public Long incr(final byte[] key, final long liveTime, final String timeUnit, final boolean refreshExpire) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> {
            // 设置初始值，这里不能用序列化类
            connection.setNX(key, "100000".getBytes());
            Long rs = connection.incr(key);
            if (liveTime > 0) {
                if("millisecond".equalsIgnoreCase(timeUnit)){
                    if(refreshExpire){
                        connection.pExpire(key, liveTime);
                    }else{
                        Long pttl = connection.pTtl(key) ;
                        if(pttl>0){
                            connection.pExpire(key, pttl);
                        }else{
                            connection.pExpire(key, liveTime);
                        }
                    }
                }else{
                    if(refreshExpire){
                        connection.expire(key, liveTime);
                    }else{
                        Long ttl = connection.ttl(key) ;
                        if(ttl>0){
                            connection.expire(key, ttl);
                        }else{
                            connection.expire(key, liveTime);
                        }
                    }
                }
            }
            return rs ;
        });
        long et = System.currentTimeMillis() ;
        try {
            log.info("redis incr "+new String(key,"UTF8")+"; duration:"+(et-st));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return obj ;
    }



    public Long incr(String key) {
        return incr(key,-1,"") ;
    }



    public Long incr(String key, final long liveTime, final String timeUnit) {
        return incr(key.getBytes(StandardCharsets.UTF_8),liveTime,timeUnit,true);
    }


    private Long incrBy(final byte[] key,final long incrAmount,final long liveTime,final String timeUnit,final boolean refreshExpire) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> {
            Long rs = connection.incrBy(key,incrAmount);
            if (liveTime > 0) {
                if("millisecond".equalsIgnoreCase(timeUnit)){
                    if(refreshExpire){
                        connection.pExpire(key, liveTime);
                    }else{
                        Long pttl = connection.pTtl(key) ;
                        if(pttl>0){
                            connection.pExpire(key, pttl);
                        }else{
                            connection.pExpire(key, liveTime);
                        }
                    }
                }else{
                    if(refreshExpire){
                        connection.expire(key, liveTime);
                    }else{
                        Long ttl = connection.ttl(key) ;
                        if(ttl>0){
                            connection.expire(key, ttl);
                        }else{
                            connection.expire(key, liveTime);
                        }
                    }
                }
            }
            return rs ;
        });
        long et = System.currentTimeMillis() ;
        try {
            log.info("redis incrBy "+new String(key,"UTF8")+";"+incrAmount+" duration:"+(et-st));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return obj ;
    }



    public Long incrBy(String key, long incrAmount) {
        try {
            return incrBy(key.getBytes("UTF8"),incrAmount,-1,"second",true);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null;
    }



    public Long incrBy(String key, long incrAmount, final long liveTime, final String timeUnit, final boolean refreshExpire) {
        try {
            return incrBy(key.getBytes("UTF8"),incrAmount,liveTime,timeUnit,refreshExpire);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null;
    }


    private Long hIncrBy(final byte[] key,final byte[] field,final long incrByNumber,final long liveTime,final String timeUnit,final boolean refreshExpire) {
        long st = System.currentTimeMillis() ;
        Long obj = redisTemplate.execute((RedisCallback<Long>) connection -> {
            Long rs = connection.hIncrBy(key,field,incrByNumber);
            if (liveTime > 0) {
                if("millisecond".equalsIgnoreCase(timeUnit)){
                    if(refreshExpire){
                        connection.pExpire(key, liveTime);
                    }else{
                        Long pttl = connection.pTtl(key) ;
                        if(pttl>0){
                            connection.pExpire(key, pttl);
                        }else{
                            connection.pExpire(key, liveTime);
                        }
                    }
                }else{
                    if(refreshExpire){
                        connection.expire(key, liveTime);
                    }else{
                        Long ttl = connection.ttl(key) ;
                        if(ttl>0){
                            connection.expire(key, ttl);
                        }else{
                            connection.expire(key, liveTime);
                        }
                    }
                }
            }
            return rs ;
        });
        long et = System.currentTimeMillis() ;
        try {
            log.info("redis hIncrBy "+new String(key,"UTF8")+";"+new String(field,"UTF8")+";"+incrByNumber+";"+liveTime+" duration:"+(et-st));
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return obj ;
    }



    public Long hIncrBy(String key, String field, long incrByNumber, long liveTime, final String timeUnit, final boolean refreshExpire) {
        try {
            return hIncrBy(key.getBytes("UTF8"),field.getBytes("UTF8"),incrByNumber, liveTime,timeUnit,refreshExpire);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null;
    }



    public Long hIncrBy(String key, String field, long incrByNumber){
        return hIncrBy(key,field,incrByNumber,-1,"second",true) ;
    }



    public Boolean setNX(final byte[] key, final byte[] value, final long liveTime, final String timeUnit) {
        long st = System.currentTimeMillis() ;
        Boolean obj = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Boolean result = connection.setNX(key, value);
            if (liveTime > 0 && result) {
                if("millisecond".equalsIgnoreCase(timeUnit)){
                    connection.pExpire(key, liveTime);
                }else{
                    connection.expire(key, liveTime);
                }
            }
            return result;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis setNX "+new String(key, StandardCharsets.UTF_8)+";"+new String(value, StandardCharsets.UTF_8)+";"+liveTime+";"+timeUnit+" duration:"+(et-st));
        return obj ;
    }



    public Boolean setNX(final byte[] key, final byte[] value, final long liveTime) {
        return setNX(key,value,liveTime,"second") ;
    }



    public Boolean setNX(String key, Object value, long liveTime, String timeUnit) {
        return setNX(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.serialize(value),liveTime,timeUnit);
    }



    public Boolean setNX(String key, Object value, long liveTime) {
        return setNX(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.serialize(value),liveTime);
    }



    public Boolean setNX(String key, Object value) {
        return setNX(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.serialize(value),0L);
    }



    public Boolean setNX(byte[] key, byte[] value) {
        return setNX(key,value, 0L);
    }



    public Boolean setNX2(String key, Object value, long liveTime, String timeUnit) {
        return setNX(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.javaSerialize(value),liveTime,timeUnit);
    }



    public Boolean setNX2(String key, Object value, long liveTime) {
        return setNX(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.javaSerialize(value),liveTime);
    }



    public Boolean setNX2(String key, Object value) {
        return setNX(key.getBytes(StandardCharsets.UTF_8),SerializeUtil.javaSerialize(value),0l);
    }



    public void bgSave() {
        long st = System.currentTimeMillis() ;
        redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.bgSave();
            return true ;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis bgSave duration:"+(et-st));
    }



    public void save() {
        long st = System.currentTimeMillis() ;
        redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.save();
            return true ;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis save duration:"+(et-st));
    }



    public Boolean expire(final byte[] key, final long seconds) {
        long st = System.currentTimeMillis() ;
        Boolean obj = redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.expire(key, seconds));
        long et = System.currentTimeMillis() ;
        log.info("redis expire "+new String(key, StandardCharsets.UTF_8)+";"+seconds+" duration:"+(et-st));
        return obj ;
    }



    public Boolean expire(String key, long seconds){
        return expire(key.getBytes(StandardCharsets.UTF_8),seconds) ;
    }



    public Boolean pExpire(final byte[] key, final long millis) {
        long st = System.currentTimeMillis() ;
        Boolean obj = redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.pExpire(key, millis));
        long et = System.currentTimeMillis() ;
        log.info("redis pExpire "+new String(key, StandardCharsets.UTF_8)+";"+millis+" duration:"+(et-st));
        return obj ;
    }



    public Boolean pExpire(String key, long millis){
        return pExpire(key.getBytes(StandardCharsets.UTF_8),millis) ;
    }

    /**
     * keys方法，少用
     */

    public Set<byte[]> keys(final byte[] pattern) {
        long st = System.currentTimeMillis() ;
        Set<byte[]> obj = redisTemplate.execute((RedisCallback<Set<byte[]>>) connection -> connection.keys(pattern));
        long et = System.currentTimeMillis() ;
        log.info("redis keys "+new String(pattern, StandardCharsets.UTF_8)+" duration:"+(et-st));
        return obj ;
    }

    /**
     * keys方法，少用
     */

    public Set<byte[]> keys(String pattern) {
        return keys(pattern.getBytes(StandardCharsets.UTF_8));
    }


    public Cursor<byte[]> scan(final long cursorId, final String pattern, final long count){
        long st = System.currentTimeMillis() ;
        Cursor<byte[]> obj = redisTemplate.execute((RedisCallback<Cursor<byte[]>>) connection -> {
            ScanOptions arg0 = ScanOptions.scanOptions().match(pattern).count(count).build() ;
            return connection.scan(arg0) ;
        });
        long et = System.currentTimeMillis() ;
        log.info("redis scan "+cursorId+";"+pattern+";"+count+" duration:"+(et-st));
        return obj ;
    }



}
