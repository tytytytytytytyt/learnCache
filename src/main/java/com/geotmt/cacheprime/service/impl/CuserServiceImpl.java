package com.geotmt.cache.service.impl;

import com.geotmt.cache.dao.cluster.CuserMapper;
import com.geotmt.cache.entity.Cuser;
import com.geotmt.cache.service.ICuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CuserServiceImpl  implements ICuserService {

    @Autowired
    private CuserMapper cuserMapper;

    @Override
    public Cuser getCuserByAccount(String type, String cuserAccount, String customerId, String cuserId) {

        return this.cuserMapper.getCuserByAccount(type, cuserAccount, customerId, cuserId);
    }

    @Override
    public List<Cuser> getCusersByType(Long customerId) {
        return this.cuserMapper.getCusersByType(customerId);
    }



}
