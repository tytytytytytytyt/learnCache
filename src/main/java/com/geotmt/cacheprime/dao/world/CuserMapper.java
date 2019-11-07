package com.geotmt.cacheprime.dao.world;


import com.geotmt.cacheprime.entity.Cuser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CuserMapper {

    List<Cuser> getCuserByAccount(@Param("type") String type, @Param("cuserAccount") String cuserAccount, @Param("customerId") String customerId, @Param("cuserId") String cuserId);

    List<Cuser> getCusersByType(@Param("customerId") Long customerId);

    int setCuserStatus(@Param("cuserId") String cuserId, @Param("status") int status);

    int changeCuserPwd(@Param("cuserId") String cuserId, @Param("pwd") String pwd);
}
