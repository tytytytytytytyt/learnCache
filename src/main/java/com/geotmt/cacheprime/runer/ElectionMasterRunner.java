package com.geotmt.cacheprime.runer;

import lombok.extern.log4j.Log4j2;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
@Log4j2
@Component
public class ElectionMasterRunner implements ApplicationRunner {


    @Value("${server.port}")
    private String port;
    private ZkClient zkClient = new ZkClient("127.0.0.1",6379);
    private static final String path = "/election";
    private CountDownLatch countDownLatch ;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        lock();
    }

    boolean tryLock(){
        try {
            countDownLatch = new CountDownLatch(1);
            zkClient.createEphemeral(path);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    void lock(){
        if(tryLock()){
            log.info("创建节点成功 ，此节点为master" + "_" + port);
            ElectionStaus.ElectionResult = "创建节点成功 ，此节点为master";
        }else {
            waitLock();
            log.info("再次获取锁对象");
            lock();
        }
    }

    private void waitLock() {
        try {

            IZkDataListener zkDataListener = new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {
                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    log.info(path + "路径" + "_" + port + "_" + "删除成功");
                    countDownLatch.countDown();
                }
            };

            zkClient.subscribeDataChanges(path,zkDataListener);
            if(zkClient.exists(path)){
                countDownLatch.await();
            }
            zkClient.unsubscribeDataChanges(path,zkDataListener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static class ElectionStaus{
       public static String ElectionResult ;
    }
}
