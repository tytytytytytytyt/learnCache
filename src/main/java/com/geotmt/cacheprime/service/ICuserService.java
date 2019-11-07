package com.geotmt.cacheprime.service;
import com.geotmt.cacheprime.entity.Cuser;
import com.geotmt.cacheprime.entity.bo.CuserStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ICuserService {

    List<Cuser> getCuserByAccount(String type, String cuserAccount, String customerId, String cuserId);

    List<Cuser> getCuserByCustomerId(Long customerId);

    int updateCuserStatus(CuserStatus cuserStatus);

    int updateCuserPwd(String cuserId, String pwd);

    boolean existCuserWithCutomerId(Long customerId);

}
