package com.geotmt.cacheprime.zookeeper.distributed;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import java.util.concurrent.CountDownLatch;

public class ZkDistributeLock {


    private  ZkClient zkClient = new ZkClient("127.0.0.1:2181", 10000, 5000);
    private  String lockPath = "/lock";
    private  CountDownLatch countDownLatch = new CountDownLatch(1);


    public  boolean tryLock(){
        try {
            zkClient.createEphemeral(lockPath);
            return true;
        }catch (Exception e){
            //e.printStackTrace();
            return false;
        }
    }


    public void getLock(){
        if(tryLock()){
            System.out.println("获取到锁");
        }else {
            waitLock();
            getLock();
        }
    }

    private void waitLock() {
        try {
            // 使用zk临时事件监听
            IZkDataListener iZkDataListener = new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {
                }
                public void handleDataDeleted(String path) throws Exception {
                    if (countDownLatch != null) {
                        countDownLatch.countDown();
                    }
                }
            };
            zkClient.subscribeDataChanges(lockPath,iZkDataListener);
            if(zkClient.exists(lockPath)){
                countDownLatch = new CountDownLatch(1);
                countDownLatch.await();
            }
           zkClient.unsubscribeDataChanges(lockPath,iZkDataListener);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void unLock(){
        if(zkClient != null){
            System.out.println("释放锁");
            zkClient.close();
        }
    }




}
