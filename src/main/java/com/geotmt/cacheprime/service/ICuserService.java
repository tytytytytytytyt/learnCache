package com.geotmt.cache.service;


import com.geotmt.cache.entity.Cuser;
import java.util.List;


public interface ICuserService {

    Cuser getCuserByAccount(String type, String cuserAccount, String customerId, String cuserId);

    List<Cuser> getCusersByType(Long customerId);

}
