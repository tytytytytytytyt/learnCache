package com.geotmt.cacheprime.base.excepiton;

import com.geotmt.cacheprime.base.common.HttpCode;
import lombok.Data;


@Data
public class GlobalException extends RuntimeException {

    private Integer code;
    private String message;

    public GlobalException(HttpCode httpCode,String msg) {
        super(httpCode.getMsg());
        this.message = msg;
        this.code = httpCode.getCode();
    }

    public GlobalException(HttpCode httpCode) {
        super(httpCode.getMsg());
        this.message = httpCode.getMsg();
        this.code = httpCode.getCode();
    }

    public GlobalException(String message) {
        super(message);
        this.message = message;
    }

    public GlobalException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public GlobalException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public GlobalException(Integer code, String message, Throwable e) {
        super(message, e);
        this.code = code;
        this.message = message;
    }
}
