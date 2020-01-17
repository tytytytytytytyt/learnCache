package com.geotmt.cacheprime.service.impl;


import com.geotmt.cacheprime.dao.hello.SystemUserRepository;
import com.geotmt.cacheprime.entity.SysUserDO;
import com.geotmt.cacheprime.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Slf4j
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SystemUserRepository sysUserRepository;


    @Override
    public void insert(SysUserDO userDO) {
        String username = userDO.getUsername();
        if (exist(username)){
            throw new RuntimeException("用户名已存在！");
        }
        userDO.setPassword(MD5Encoder.encode(userDO.getPassword().getBytes()));
        sysUserRepository.save(userDO);
    }

    @Override
    public SysUserDO getByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }


    /**
     * 判断用户是否存在
     */
    private boolean exist(String username){
        SysUserDO userDO = sysUserRepository.findByUsername(username);
        return (userDO != null);
    }

}
