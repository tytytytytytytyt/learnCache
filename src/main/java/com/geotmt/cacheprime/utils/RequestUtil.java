package com.geotmt.cacheprime.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {


    /**
     * 判断ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }
}
