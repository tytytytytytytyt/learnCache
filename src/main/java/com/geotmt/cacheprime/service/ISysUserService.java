package com.geotmt.cacheprime.service;


import com.geotmt.cacheprime.entity.SysUserDO;

public interface ISysUserService {

    /**
     * 添加新用户
     *
     * username 唯一， 默认 USER 权限
     */
    void insert(SysUserDO userDO);

    /**
     * 查询用户信息
     * @param username 账号
     * @return UserEntity
     */
    SysUserDO getByUsername(String username);

}
