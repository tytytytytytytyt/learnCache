package com.geotmt.cacheprime.dao.hello;


import com.geotmt.cacheprime.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {


    User getByAccount(@Param("account") String account);

    List<User> getUserByRoleId(@Param("roleId") String roleId);

    List<User> getUserByDeptId(@Param("deptId") String deptId);
}
