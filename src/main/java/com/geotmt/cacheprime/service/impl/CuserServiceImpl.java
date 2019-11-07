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




    //------------------------------key = "#cuserId"--------------------------------------start
    @Override
    @Cacheable(value = "cuserCache", key = "#cuserId")
    public Cuser getCuserByCuserId(String cuserId) {
        log.info(" select getCuserByCuserId from DB~~~~~~~~");
        return this.cuserMapper.getCuserByCuserId(cuserId);
    }


    /**
     * 这里需要注意的地方就是spring cache的生效，失效的前提
     * 是key的类型必须完全一致，就算参数一样，但是参数类型不同也是命不中缓存的
     * getCuserByCuserId(String cuserId) key为"#cuserId" 存入缓存，
     * updateCuserStatus(String cuserId,int status) key为"#cuserId"  让缓存失效
     * 将getCuserByCuserId(String cuserId)的参数改成Long  缓存失效失败，
     * 同理："#cuserStatus.cuserId" 写成这样子也是缓存失效失败， 必须是#key参数类型相同，参数名一致才能生效
     */
    @Override
    @CacheEvict(value = "cuserCache", key = "#cuserId") //key = "#cuserStatus.cuserId"
    public int updateCuserStatus(String cuserId,int status) {
        log.info(" updateCuserStatus from DB~~~~~~~~");
        return cuserMapper.setCuserStatus(cuserId, status);
    }
    //------------------------------key = "#cuserId"-----------------------------------------end








    //------------------------------key = "#cuserStatus.cuserId"-----------------------------start
    @CacheEvict(value = "cuserCache", key = "#cuserStatus.cuserId")
    public int updateCuserStatusByStatusEntity(CuserStatus cuserStatus) {
        log.info(" updateCuserStatusByStatusEntity from DB~~~~~~~~");
        return cuserMapper.setCuserStatus(cuserStatus.getCuserId(), cuserStatus.getStatus());
    }

    @Cacheable(value = "cuserCache", key = "#cuserStatus.cuserId")
    public Cuser getCuserByStatusEntity(CuserStatus cuserStatus) {
        log.info(" select getCuserByStatusEntity from DB~~~~~~~~");
        return this.cuserMapper.getCuserByCuserId(cuserStatus.getCuserId());
    }
    //------------------------------key = "#cuserStatus.cuserId"-----------------------------end







    @Override
    @CacheEvict(value = "cuserCache", keyGenerator = "cacheKeyGenerator")
    public int updateCuserPwd(String cuserId, String pwd) {
        log.info(" updateCuserPwd from DB~~~~~~~~");
        return cuserMapper.changeCuserPwd(cuserId,pwd);
    }


    /***
     * 解决自调用 @Cacheable， @CacheEvict失效的问题
     * 自调用缓存失效的解决办法，从容器中获取bean
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
