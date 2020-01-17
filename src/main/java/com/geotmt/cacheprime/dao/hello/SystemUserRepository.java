package com.geotmt.cacheprime.dao.hello;

import com.geotmt.cacheprime.entity.SysUserDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends CrudRepository<SysUserDO,Long> {

    SysUserDO findByUsername(String userName);
}
