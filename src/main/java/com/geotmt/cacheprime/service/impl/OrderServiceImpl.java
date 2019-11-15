package com.geotmt.cacheprime.service.impl;

import com.geotmt.cacheprime.service.IOrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Random;

@Log4j2
@Service
public class OrderServiceImpl implements IOrderService {

    private Random random = new Random();

    @Override
    public String order() {
        try {
            Thread.sleep(random.nextInt(60));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "抢单成功";
    }
}
