package com.geotmt.cacheprime.controller;

import com.geotmt.cacheprime.runer.ElectionMasterRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/election")
public class ZkElectionMasterController {

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object index() {
         if(ElectionMasterRunner.ElectionStaus.ElectionResult !=null){
             return ElectionMasterRunner.ElectionStaus.ElectionResult + "_" + port;
         }else{
             return "创建节点失败 ，此节点为slave_"+ port;
         }
    }

}
