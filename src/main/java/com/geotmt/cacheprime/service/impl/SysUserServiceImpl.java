package com.geotmt.cacheprime.service.impl;


import com.geotmt.cacheprime.base.common.HttpCode;
import com.geotmt.cacheprime.base.excepiton.GlobalException;
import com.geotmt.cacheprime.dao.jpa.SysUserRepository;
import com.geotmt.cacheprime.entity.SysUserDO;
import com.geotmt.cacheprime.service.ISysUserService;
import com.geotmt.cacheprime.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Primary
@Slf4j
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;


    @Override
    public void insert(SysUserDO userDO) {
        String account = userDO.getAccount();
        if (exist(account)){
            throw new RuntimeException("用户名已存在！");
        }
        String randomSalt = MD5Util.getRandomSalt(4);
        userDO.setSalt(randomSalt);
        String md5Value = MD5Util.md5(userDO.getPassword(), randomSalt);
        userDO.setPassword(md5Value);
        Date date = new Date();
        userDO.setCreateTime(date);
        userDO.setUpdateTime(date);
        sysUserRepository.save(userDO);
    }


    @Override
    public SysUserDO getByUsername(String username) {
        return sysUserRepository.findByAccount(username);
    }



    @Override
    public SysUserDO CheckUserNameAndPasswod(String username, String password) {
        SysUserDO sysUserDO = getByUsername(username);
        if (sysUserDO == null) {
            throw new GlobalException(HttpCode.LOGIN_ERROR);
        }
        String salt = sysUserDO.getSalt();
        String encodePassWord = MD5Util.md5(password, salt);
        if (!encodePassWord.equals(sysUserDO.getPassword())) {
            throw new GlobalException(HttpCode.LOGIN_ERROR);
        }
        return sysUserDO;
    }

    public static void main(String[] args) {
        String gbe0 = MD5Util.md5("111111", "gbe0");
        System.out.println(gbe0);
    }


    /**
     * 判断用户是否存在
     */
    private boolean exist(String username){
        SysUserDO userDO = sysUserRepository.findByAccount(username);
        return (userDO != null);
    }

}
