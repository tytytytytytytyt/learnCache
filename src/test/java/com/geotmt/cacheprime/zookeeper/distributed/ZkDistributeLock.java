package com.geotmt.cacheprime.zookeeper.distributed;

import lombok.extern.log4j.Log4j2;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Log4j2
public class ZkDistributeLock {

   private ZkClient zkClient = new ZkClient("127.0.0.1",10000,8000);
   private String lockPath = "/lock";
   private CountDownLatch countDownLatch = new CountDownLatch(1);


   public boolean tryLock(){
       try {
           zkClient.createEphemeral(lockPath);
           return true;
       }catch (Exception e){
           return false;
       }
   }



   public void getLock(){
       if(tryLock()){
           log.info("got zookeeper lock");
       }else {
           doWait();
           getLock();
       }
   }

    private void doWait() {

        IZkDataListener listener = new IZkDataListener(){
            @Override
            public void handleDataChange(String s, Object o) throws Exception {}

            @Override
            public void handleDataDeleted(String s) throws Exception {
                countDownLatch.countDown();
            }
        };
        zkClient.subscribeDataChanges(lockPath,listener);

        if (zkClient.exists(lockPath)){
            try {
                //System.out.println("wait zookeeper lock to realse");
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("zookeeper lock already realse");
        zkClient.unsubscribeDataChanges(lockPath,listener);

    }

    public void unLock(){
       if(zkClient!=null){
           System.out.println("close session for realse the zookeeper lock");
           zkClient.close();
       }

    }


}
