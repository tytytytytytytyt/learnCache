package com.geotmt.cacheprime.utils.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Date: 2019/5/28
 * Time: 18:35
 *
 * @author 夏海华
 */
@Data
@Accessors(chain = true)
public class HttpResult {

    /**
     * http状态码 200 404 500等
     */
    private int httpCode;

    /**
     * 返回字符串
     */
    private String result;

}
