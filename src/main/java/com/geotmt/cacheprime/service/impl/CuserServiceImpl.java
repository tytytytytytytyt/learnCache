package com.geotmt.cacheprime.service.impl;


import com.geotmt.cacheprime.dao.world.CuserMapper;
import com.geotmt.cacheprime.entity.Cuser;
import com.geotmt.cacheprime.entity.bo.CuserStatus;
import com.geotmt.cacheprime.service.ICuserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CuserServiceImpl  implements ICuserService {

    @Autowired
    private CuserMapper cuserMapper;

    @Override
    @Cacheable(value = "serviceCache", key="#type.concat(#cuserAccount).concat(#customerId).concat(#cuserId)")
    public Cuser getCuserByAccount(String type, String cuserAccount, String customerId, String cuserId) {
        log.info(" select getCuserByAccount from DB~~~~~~~~");
        return this.cuserMapper.getCuserByAccount(type, cuserAccount, customerId, cuserId);
    }


    @Override
    @Cacheable(value = "serviceCache", key = "#customerId")
    public List<Cuser> getCusersByType(Long customerId) {
        log.info(" select getCusersByType from DB~~~~~~~~");
        return this.cuserMapper.getCusersByType(customerId);
    }

    @Override
    @CacheEvict(value = "serviceCache", key = "#cuserStatus.cuserId")
    public int updateCuserStatus(CuserStatus cuserStatus) {
        log.info(" updateCuserStatus from DB~~~~~~~~");
        return cuserMapper.setCuserStatus(cuserStatus.getCuserId(), cuserStatus.getStatus());
    }

    @Override
    @CacheEvict(value = "serviceCache", keyGenerator = "cacheKeyGenerator")
    public int updateCuserPwd(String cuserId, String pwd) {
        log.info(" updateCuserPwd from DB~~~~~~~~");
        return cuserMapper.changeCuserPwd(cuserId,pwd);
    }


}
