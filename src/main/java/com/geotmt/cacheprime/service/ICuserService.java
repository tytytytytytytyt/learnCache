package com.geotmt.cacheprime.service;
import com.geotmt.cacheprime.entity.Cuser;
import com.geotmt.cacheprime.entity.bo.CuserStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ICuserService {

    List<Cuser> getCuserByAccount(String type, String cuserAccount, String customerId, String cuserId);

    List<Cuser> getCuserByCustomerId(Long customerId);

    Cuser getCuserByCuserId(String cuserId);

    int updateCuserStatus(String cuserId,int status);

    int updateCuserPwd(String cuserId, String pwd);

    boolean existCuserWithCutomerId(Long customerId);

    int updateCuserStatusByStatusEntity(CuserStatus cuserStatus);

    Cuser getCuserByStatusEntity(CuserStatus cuserStatus);

}
