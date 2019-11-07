package com.geotmt.cacheprime.controller;

import com.geotmt.cacheprime.entity.Cuser;
import com.geotmt.cacheprime.entity.bo.CuserStatus;
import com.geotmt.cacheprime.service.ICuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/cache")
public class EhcacheCuserController {

   @Autowired
   private ICuserService cuserService;

    @RequestMapping(value = "/cuserbyid", method = RequestMethod.GET)
    public Object getCuserByCustId(@RequestParam(value = "id") long id) {
        return cuserService.getCuserByCustomerId(id);
    }

    @RequestMapping(value = "/cuserbyaccount", method = RequestMethod.GET)
    public List<Cuser> getCusersByType(@RequestParam(value = "type") String type,
                                       @RequestParam(value = "cuserAccount") String cuserAccount,
                                       @RequestParam(value = "customerId") String customerId,
                                       @RequestParam(value = "cuserId")  String cuserId) {
        return cuserService.getCuserByAccount(type,cuserAccount,customerId,cuserId);
    }

    @RequestMapping(value = "/cuserbycuserid", method = RequestMethod.GET)
    public Object getCuserByCuserId(@RequestParam(value = "cuserId") String cuserId) {
        return cuserService.getCuserByCuserId(cuserId);
    }

    @RequestMapping(value = "/updatecuserstatus", method = RequestMethod.POST)
    public int updateCuserStatus(CuserStatus cuserStatus){
        return cuserService.updateCuserStatus(cuserStatus.getCuserId(),cuserStatus.getStatus());
    }

    @RequestMapping(value = "/updatecuserstatusbyentity", method = RequestMethod.POST)
    public int updateCuserStatusByStatusEntity(CuserStatus cuserStatus){
        return cuserService.updateCuserStatusByStatusEntity(cuserStatus);
    }

    @RequestMapping(value = "/getcuserbycuserentity", method = RequestMethod.POST)
    public Cuser getCuserByStatusEntity(CuserStatus cuserStatus){
        return cuserService.getCuserByStatusEntity(cuserStatus);
    }


    @RequestMapping(value = "/updatecuserpwd", method = RequestMethod.GET)
    public int updateCuserPwd(@RequestParam(value = "cuserId") String cuserId,@RequestParam(value = "pwd") String pwd) {
        return cuserService.updateCuserPwd(cuserId,pwd);
    }


    @RequestMapping(value = "/existcuser", method = RequestMethod.GET)
    public Boolean existCuserWithCutomerId(@RequestParam(value = "id") long id) {
        return cuserService.existCuserWithCutomerId(id);
    }





}
