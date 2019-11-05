package com.geotmt.cache.service;

import com.geotmt.cache.entity.User;
import java.util.List;

public interface IUserService  {


    User getByAccount(String account);

    List<User> getUserByRoleId(String roleId);

    List<User> getUserByDeptId(String deptId);

}
