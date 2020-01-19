package com.geotmt.cacheprime.utils;

import com.geotmt.cacheprime.base.common.HttpCode;
import com.geotmt.cacheprime.base.excepiton.GlobalException;
import com.google.common.collect.Maps;
import org.assertj.core.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VertifyUtil {



    public static void VertifyNotEmpty(Map<String,String> paramMap){
        Set<Map.Entry<String, String>> entries = paramMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(Strings.isNullOrEmpty(value)){
                throw new GlobalException(HttpCode.PARAM_ISNULL,key + " is null");
            }
        }
    }

    public static void VertifyNotEmpty(String paramName,String paramValue){
        if(Strings.isNullOrEmpty(paramValue)){
            throw new GlobalException(HttpCode.PARAM_ISNULL,paramName + " is null");
        }
    }
}
