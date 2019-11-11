package com.geotmt.cacheprime.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.EventType.*;
import static org.apache.zookeeper.Watcher.Event.KeeperState.*;

public class ZookeeperTest1 {


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        //String connectString, int sessionTimeout, Watcher watcher
        CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 状态类型 ①KeeperState.Disconnected：未连接 ②KeeperState.SyncConnected：已连接  ③KeeperState.AuthFailed：认证失败  ④KeeperState.Expired：会话过期
                Event.KeeperState state = watchedEvent.getState();
                if (state == SyncConnected){
                    latch.countDown();
                    System.out.println(" 已经连接上zookeeper了");
                }
                if(state == Disconnected){
                    System.out.println("zookeeper客户端断开连接");
                }

                // 事件类型 ①NodeCreated,②NodeDeleted, ③NodeDataChanged,④NodeChildrenChanged;
                Event.EventType type = watchedEvent.getType();
                if (type == NodeCreated){
                    System.out.println("节点创建通知");
                }else if(type == NodeDeleted){
                    System.out.println("节点删除通知");
                }else if(type == NodeDataChanged ){
                    System.out.println("节点改变通知");
                }else if(type == NodeChildrenChanged ){
                    System.out.println("子节点改变通知");
                }


            }
        });

        //latch.await();
        zooKeeper.exists("/test",true);
        //String path, byte[] data, List<ACL> acl, CreateMode createMode
        String test = zooKeeper.create("/test", "testvalu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("创建节点成功  节点："+ test);

        //zooKeeper.delete("/test",0);
        zooKeeper.exists("/test",true);
//        String temp = zooKeeper.create("/temp", "testvalu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//        System.out.println("创建节点成功  节点："+ temp);
        zooKeeper.close();

    }
}
