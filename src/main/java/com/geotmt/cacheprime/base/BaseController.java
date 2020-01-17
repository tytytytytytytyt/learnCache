package com.geotmt.cacheprime.base;

import com.geotmt.cacheprime.base.common.HttpCode;
import lombok.Data;
import lombok.extern.log4j.Log4j2;




public abstract class BaseController {

    public ResponseBase setResultError(Integer code, String msg) {
        return setResult(code, msg, null);
    }


    public ResponseBase setResultError(String msg) {
        return setResult(HttpCode.SUCCESS.getCode(), msg, null);
    }


    public ResponseBase setResultSuccessData(Object data) {
        return setResult(HttpCode.SUCCESS.getCode(),HttpCode.SUCCESS.getMsg(), data);
    }



    public ResponseBase setResultSuccess() {
        return setResult(HttpCode.SUCCESS.getCode(),HttpCode.SUCCESS.getMsg(), null);
    }


    public ResponseBase setResultSuccess(String msg) {
        return setResult(HttpCode.SUCCESS.getCode(), msg, null);
    }

    // 通用封装
    public ResponseBase setResult(Integer code, String msg, Object data) {
        return new ResponseBase(code, msg, data);
    }


    @Data
    @Log4j2
    public static class ResponseBase{

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

        public ResponseBase(HttpCode httpCode) {
            super();
            this.rtnCode = httpCode.getCode();
            this.msg = httpCode.getMsg();

        }

    }
}
