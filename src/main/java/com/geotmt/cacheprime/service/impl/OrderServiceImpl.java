package com.geotmt.cacheprime.service.impl;

import com.geotmt.cacheprime.service.IOrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class OrderServiceImpl implements IOrderService {

    @Override
    public String order() {
        return "抢单成功";
    }
}
