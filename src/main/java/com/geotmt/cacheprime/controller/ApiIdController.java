package com.geotmt.cacheprime.controller;


import com.geotmt.cacheprime.dao.hello.FormOrderMapper;
import com.geotmt.cacheprime.entity.FormOrder;
import com.geotmt.cacheprime.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodType;


@Controller
@RequestMapping("/order")
public class ApiIdController {


    @Autowired
    private FormOrderMapper orderMapper ;
    @Autowired
    private TokenUtil tokenUtil;


    @RequestMapping("/index")
    public String indexPage(HttpServletRequest req) {
        tokenUtil.setTokenToReq();
        return "order";
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String addOrder(FormOrder order) {
        Boolean exist = tokenUtil.isExistToken(order.getToken());
        if(!exist){
            return "请勿重复提交";
        }
        int addOrder = orderMapper.insertFormOrder(order);
        return addOrder > 0 ? "success" : "fail";
    }



}
