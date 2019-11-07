package com.geotmt.cacheprime.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.geotmt.cacheprime.dao.world.CuserMapper;
import com.geotmt.cacheprime.entity.Cuser;
import com.geotmt.cacheprime.entity.bo.CuserStatus;
import com.geotmt.cacheprime.service.ICuserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CuserServiceImpl  implements ICuserService {

    @Autowired
    private CuserMapper cuserMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    @Cacheable(value = "cuserCache", key="#type.concat(#cuserAccount).concat(#customerId).concat(#cuserId)")
    public List<Cuser> getCuserByAccount(String type, String cuserAccount, String customerId, String cuserId) {
        log.info(" select getCuserByAccount from DB~~~~~~~~");
        return this.cuserMapper.getCuserByAccount(type, cuserAccount, customerId, cuserId);
    }

    @Override
    @Cacheable(value = "cuserCache", key = "#customerId")
    public List<Cuser> getCuserByCustomerId(Long customerId) {
        log.info(" select getCuserByCustomerId from DB~~~~~~~~");
        return this.cuserMapper.getCusersByType(customerId);
    }

    @Override
    @CacheEvict(value = "cuserCache", key = "#cuserStatus.cuserId")
    public int updateCuserStatus(CuserStatus cuserStatus) {
        log.info(" updateCuserStatus from DB~~~~~~~~");
        return cuserMapper.setCuserStatus(cuserStatus.getCuserId(), cuserStatus.getStatus());
    }

    @Override
    @CacheEvict(value = "cuserCache", keyGenerator = "cacheKeyGenerator")
    public int updateCuserPwd(String cuserId, String pwd) {
        log.info(" updateCuserPwd from DB~~~~~~~~");
        return cuserMapper.changeCuserPwd(cuserId,pwd);
    }

    /***
     * 解决自调用 @Cacheable， @CacheEvict失效的问题
     * @param customerId
     * @return
     */
    @Override
    public boolean existCuserWithCutomerId(Long customerId){
        ICuserService cuserService = applicationContext.getBean(ICuserService.class);
        List<Cuser> cuser = cuserService.getCuserByCustomerId(customerId);
        if(CollectionUtil.isNotEmpty(cuser))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }


}
