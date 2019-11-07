package com.geotmt.cacheprime.service;


import com.geotmt.cacheprime.entity.User;

import java.util.List;

public interface IUserService  {


    User getByAccount(String account);

    Integer updatePwdById(String id, String cuserPassword);

    List<User> getUserByRoleId(String roleId);

    List<User> getUserByDeptId(String deptId);

}
