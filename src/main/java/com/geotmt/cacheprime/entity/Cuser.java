package com.geotmt.cache.entity;

import lombok.Data;
import lombok.ToString;


import java.util.Date;


@Data
@ToString
public class Cuser  {

    private static final long serialVersionUID = 1L;
    /**
     * 客户用户id
     */
    private Long id;
    /**
     * 客户用户名称
     */
    private String cuserName;
    /**
     * 客户用户权限
     */
    private Integer type;
    /**
     * 客户用户是否冻结
     */
    private Integer cuserIsFrozen;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private String modifyUser;
    /**
     * 逻辑删除标识（1：删除，0：存在）
     */
    private Integer isDelete;
    /**
     * 客户用户密码
     */
    private String cuserPassword;
    /**
     * 客户用户账号
     */
    private String cuserAccount;
    /**
     * 客户用户邮箱
     */
    private String cuserEmail;
    /**
     * 客户用户联系方式
     */
    private String cuserPhone;
    /**
     * 客户用户地址
     */
    private String cuserAddress;
    /**
     * 客户用户联系人
     */
    private String cuserLp;
    /**
     * 客户ID
     */
    private Integer customerId;
    /**
     * 加盐
     */
    private String salt;

    /**
     * 加密类型 1: AES2
     */
    private Integer encryptType;

    /**
     * 16位的加密key
     */
    private String encryptKey;


    /**
     * hash值
     */
    private String hashVal;



}
