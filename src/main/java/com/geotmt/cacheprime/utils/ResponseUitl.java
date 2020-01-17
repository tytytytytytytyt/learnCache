package com.geotmt.cacheprime.utils;

import com.alibaba.fastjson.JSONObject;
import com.geotmt.cacheprime.base.BaseController;
import com.geotmt.cacheprime.base.common.HttpCode;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ResponseUitl {

    public static void fail(HttpServletResponse response, HttpCode httpCode){
        BaseController.ResponseBase responseBase = new BaseController.ResponseBase(httpCode);
        response.setContentType("applicaiton/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(JSONObject.toJSONString(responseBase));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
