package com.geotmt.cacheprime.service;


;
import com.geotmt.cacheprime.entity.Cuser;

import java.util.List;


public interface ICuserService {

    Cuser getCuserByAccount(String type, String cuserAccount, String customerId, String cuserId);

    List<Cuser> getCusersByType(Long customerId);

}
