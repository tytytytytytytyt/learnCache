package com.geotmt.cacheprime.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 主要用于存放redis时的序列化,
 * 由于使用fastjson序列化后的字节长度要远比java原生序列化小。所以统一改用fastjson进行序列化
 * @author 吴淑佳
 *
 */
@Slf4j
public class SerializeUtil {
	/**
	 * java默认序列化
	 * @param object
	 * @return
	 */
	public static byte[] javaSerialize(Object object) {
		if(object==null){
			return null ;
		}
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			return baos.toByteArray();
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
    /**
     * fastJson 序列化
     * @param object
     * @return
     */
	public static byte[] serialize(Object object) {
		if(object==null){
			return null ;
		}else{
			String jsonString = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
			return jsonString.getBytes(StandardCharsets.UTF_8);
		}
	}
	/**
	 * java 反序列化
	 * @param bytes
	 * @return
	 */
	public static <T> T javaUnSerialize(byte[] bytes, Class<T> clazz) {
		if(bytes==null){
			return null ;
		}
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (T)ois.readObject();
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	public static Object javaUnserialize(byte[] bytes) {
		return javaUnSerialize(bytes,Object.class) ;
	}
	/**
	 * 取出某些对象需要继续反序列化,使用改方法替代java的强制转换
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static <T> T unserialize(Object obj,Class<T> clazz) {
		return unserialize(serialize(obj),clazz) ;
	}
	/**
	 * fastJson 反序列化
	 * @param bytes
	 * @param clazz
	 * @return
	 */
	public static <T> T unserialize(byte[] bytes,Class<T> clazz) {
		if(bytes==null){
			return null ;
		}else{
			return JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), clazz) ;
		}
	}

	public static void main(String[] args){
		HashMap<String, String> map = new HashMap<>();
		map.put("k1","v1");
		map.put("k2","v2");
		System.out.println(Arrays.toString(javaSerialize(map)));
		System.out.println(Arrays.toString(serialize(map)));
	}
}
