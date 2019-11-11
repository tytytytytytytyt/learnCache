//package com.geotmt.cacheprime.zookeeper;
//
//import org.I0Itec.zkclient.ZkClient;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class ZkServerSocket implements Runnable{
//
//    private int port;
//    static ZkServerSocket zkServerSocket = null;
//
//    public ZkServerSocket(int port) {
//        this.port = port;
//    }
//
//    public static void main(String[] args) {
//        int port = 18080;
//        zkServerSocket = new ZkServerSocket(port);
//        Thread thread = new Thread(zkServerSocket);
//        thread.start();
//    }
//
//    @Override
//    public void run() {
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(port);
//            registServer();
//            System.out.println("Server start port:" + port);
//            while (true) {
//                Socket socket = serverSocket.accept();
//                System.out.println("进来了");
//                new Thread(new ZkServerHandler(socket)).start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (serverSocket != null) {
//                    serverSocket.close();
//                }
//            } catch (Exception e2) {
//
//            }
//        }
//    }
//
//    private void registServer() {
//        ZkClient zkClient = new ZkClient("127.0.0.1:2181" ,6000,1000);
//        String path = "/test/server" + this.port;
//        if (zkClient.exists(path)){
//            System.out.println("已经存在path:" + path);
//            zkClient.delete(path);
//        }
//        // 创建临时节点
//        zkClient.createEphemeral(path, "127.0.0.1:" + port);
//       // String result = zkClient.create(path, port, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//        System.out.println("zk 注册零食节点 path:" + path + "value :" + port);
//
//    }
//}
