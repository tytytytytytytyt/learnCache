package com.geotmt.cacheprime.service.impl;



import com.geotmt.cacheprime.dao.hello.UserMapper;
import com.geotmt.cacheprime.entity.User;
import com.geotmt.cacheprime.service.IUserService;
import com.geotmt.cacheprime.utils.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;


@Log4j2
@Service
public class UserServiceImpl implements IUserService {

    public static final String USER_KEY = "USER_KEY_";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 二级缓存，先ehcache,后redis,最后db
     */
    @Override
    @Cacheable(value = "defaultCache", key = "#id")
    public User getByAccount(String id) {

        User user = redisUtils.get(USER_KEY + id, User.class);
        log.info("getByAccount  from  Redis~~~~~~~~~~~~~");
        if(user != null){
            return user;
        } else{
            user = this.userMapper.getById(id);
            log.info("getByAccount  from  DB~~~~~~~~~~~~~");
            if(user !=null){
                redisUtils.set(USER_KEY + id,user,5000L);
            }
            return user;
        }

    }

    @Override
    @CacheEvict(value = "defaultCache", key = "#id")
    public Integer updatePwdById(String id, String cuserPassword) {
        redisUtils.remove(USER_KEY + id);
        return this.userMapper.updatePwdById(id,cuserPassword);
    }


    @Override
    public List<User> getUserByRoleId(String roleId) {
        return this.userMapper.getUserByRoleId(roleId);
    }

    @Override
    public List<User> getUserByDeptId(String deptId) {
        return this.userMapper.getUserByDeptId(deptId);
    }
}
