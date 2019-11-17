package com.geotmt.cacheprime.dao.hello;


import com.geotmt.cacheprime.entity.FormOrder;
import com.geotmt.cacheprime.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FormOrderMapper {

    int insertFormOrder(@Param("order") FormOrder order);
}
