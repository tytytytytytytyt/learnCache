package com.geotmt.cacheprime.base;

import com.geotmt.cacheprime.base.common.HttpCode;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

public abstract class BaseController {

    public ResponseBase setResultError(Integer code, String msg) {
        return setResult(code, msg, null);
    }

    // 返回错误，可以传msg
    public ResponseBase setResultError(String msg) {
        return setResult(HttpCode.HTTP_RES_CODE_500, msg, null);
    }

    // 返回成功，可以传data值
    public ResponseBase setResultSuccessData(Object data) {
        return setResult(HttpCode.HTTP_RES_CODE_200, HttpCode.HTTP_RES_CODE_200_VALUE, data);
    }

    public ResponseBase setResultSuccessData(Integer code, Object data) {
        return setResult(code, HttpCode.HTTP_RES_CODE_200_VALUE, data);
    }

    // 返回成功，沒有data值
    public ResponseBase setResultSuccess() {
        return setResult(HttpCode.HTTP_RES_CODE_200, HttpCode.HTTP_RES_CODE_200_VALUE, null);
    }

    // 返回成功，沒有data值
    public ResponseBase setResultSuccess(String msg) {
        return setResult(HttpCode.HTTP_RES_CODE_200, msg, null);
    }

    // 通用封装
    public ResponseBase setResult(Integer code, String msg, Object data) {
        return new ResponseBase(code, msg, data);
    }


    @Data
    @Log4j2
    static class ResponseBase{

        private Integer rtnCode;
        private String msg;
        private Object data;

        public ResponseBase() {

        }

        public ResponseBase(Integer rtnCode, String msg, Object data) {
            super();
            this.rtnCode = rtnCode;
            this.msg = msg;
            this.data = data;
        }

    }
}
