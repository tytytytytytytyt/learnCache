package com.geotmt.cacheprime.dao.hello;


import com.geotmt.cacheprime.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {


    User getById(@Param("id") String id);

    Integer updatePwdById(@Param("id") String id, @Param("cuserPassword") String cuserPassword);

    List<User> getUserByRoleId(@Param("roleId") String roleId);

    List<User> getUserByDeptId(@Param("deptId") String deptId);

    //
}
