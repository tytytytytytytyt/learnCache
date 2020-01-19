package com.geotmt.cacheprime.base.common;

public enum HttpCode {
    SUCCESS("success",200),
    FAIL("fail",500),
    UN_LOGIN("not login",10000),
    PARAM_ISNULL("param is null",10001),
    VERTIFY_CODE_ERROR("验证码错误",10002),
    LOGIN_ERROR("用户名或者密码错误",10003);

    HttpCode(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    private String msg;
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
