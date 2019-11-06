package com.geotmt.cacheprime.config;

import com.google.common.hash.Hashing;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;


@Log4j2
@Component(value = "cacheKeyGenerator")
public class CacheKeyGenerator implements KeyGenerator {

    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;
    public static List<Class> SUPPORTPACKAGES =
            Arrays.asList(Boolean[].class, Byte[].class, Character[].class, Double[].class,
                    Float[].class, Integer[].class, Long[].class, Short[].class,String[].class);


    @Override
    public Object generate(Object target, Method method, Object... params) {

        StringBuilder key = new StringBuilder();
        key.append(target.getClass().getSimpleName()).append(".").append(method.getName()).append(":");
        if (params.length == 0) {
            return key.append(NO_PARAM_KEY).toString();
        }
        for (Object param : params) {
            if (param == null) {
                log.warn("input null param for Spring cache, use default key={}", NULL_PARAM_KEY);
                key.append(NULL_PARAM_KEY);
            } else if (ClassUtils.isPrimitiveArray(param.getClass())|| SUPPORTPACKAGES.contains(param.getClass())) { //isPrimitiveArray 只支持基本数组
                int length = Array.getLength(param);
                for (int i = 0; i < length; i++) {
                    key.append(Array.get(param, i));
                    key.append(',');
                }
            } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                key.append(param);
            } else {
                log.warn("Using an object as a cache key may lead to unexpected results. " +
                        "Either use @Cacheable(key=..) or implement CacheKey. Method is " + target.getClass() + "#" + method.getName());
                key.append(param.hashCode());
            }
            key.append('-');
        }

        String finalKey = key.toString();
        long cacheKeyHash = Hashing.murmur3_128().hashString(finalKey, Charset.defaultCharset()).asLong();
        log.debug("using cache key={} hashCode={}", finalKey, cacheKeyHash);
        return key.toString();
    }

    @Test
    public void testIsPrimitiveWrapper(){

//        // isPrimitiveWrapper 是原始类的包装类吗
//        System.out.println(ClassUtils.isPrimitiveWrapper(Integer.class)); // = ture
//        System.out.println(ClassUtils.isPrimitiveWrapper(Object.class)); // = false
//        System.out.println(ClassUtils.isPrimitiveWrapper(String.class)); // = false
//        System.out.println(ClassUtils.isPrimitiveWrapper(int.class));    // = false
//
//        // isPrimitiveOrWrapper  是原始类或原始类对应的包装类
//        System.out.println(ClassUtils.isPrimitiveOrWrapper(Integer.class)); // = ture
//        System.out.println(ClassUtils.isPrimitiveOrWrapper(Object.class));  // = false
//        System.out.println(ClassUtils.isPrimitiveOrWrapper(String.class));  // = false
//        System.out.println(ClassUtils.isPrimitiveOrWrapper(int.class));     // = true


        String [] arr  = new String[12];
        Integer []  integer  = new Integer[12];
        int []  ints  = new int[12];
        System.out.println(ClassUtils.isPrimitiveArray(arr.getClass()));        //=false
        System.out.println(ClassUtils.isPrimitiveArray(integer.getClass()));        //=false
        System.out.println(ClassUtils.isPrimitiveArray(ints.getClass()));        //=true

        //System.out.println(ClassUtils.isPrimitiveOrWrapper(arr.getClass()));         // = false
        // System.out.println(integer.getClass().getName());
    }

}
