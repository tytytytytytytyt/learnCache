package com.geotmt.cache.service.impl;


import com.geotmt.cache.dao.master.UserMapper;
import com.geotmt.cache.entity.User;
import com.geotmt.cache.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByAccount(String account) {
        return this.userMapper.getByAccount(account);
    }

    @Override
    public List<User> getUserByRoleId(String roleId) {
        return this.userMapper.getUserByRoleId(roleId);
    }

    @Override
    public List<User> getUserByDeptId(String deptId) {
        return this.userMapper.getUserByDeptId(deptId);
    }
}
