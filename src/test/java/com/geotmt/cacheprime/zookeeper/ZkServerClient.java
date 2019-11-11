//package com.geotmt.cacheprime.zookeeper;
//
//import org.I0Itec.zkclient.IZkChildListener;
//import org.I0Itec.zkclient.ZkClient;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ZkServerClient {
//
//    public static List<String> listServer = new ArrayList<String>();
//    public static String parent = "/test";
//    public static ZkServerClient client = null;
//    private static int reqestCount ;// 请求次数
//    private static int serverCount ;// 服务数量
//
//    public static void main(String[] args) {
//        initServer();
//        client = new ZkServerClient();
//        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
//        while (true) {
//            String name;
//            try {
//                name = console.readLine();
//                if ("exit".equals(name)) {
//                    System.exit(0);
//                }
//                client.send(name);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public static void initServer() {
//
//        final ZkClient zkClient = new ZkClient("127.0.0.1:2181", 6000, 1000);
//        List<String> children = zkClient.getChildren(parent);
//        getChilds(zkClient, children);
//        // 监听事件
//        zkClient.subscribeChildChanges(parent, new IZkChildListener() {
//
//            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
//                getChilds(zkClient, currentChilds);
//            }
//        });
//    }
//
//    private static void getChilds(ZkClient zkClient, List<String> currentChilds) {
//        listServer.clear();
//        for (String p : currentChilds) {
//            String pathValue = (String) zkClient.readData(parent + "/" + p);
//            listServer.add(pathValue);
//        }
//        serverCount = listServer.size();
//        System.out.println("从zk读取到信息:" + listServer.toString());
//
//    }
//
//
//    public static String getServer() {
//        ++reqestCount;
//        // 实现负载均衡
//        String serverName = listServer.get(reqestCount % serverCount);
//
//        return serverName;
//    }
//
//
//
//    public void send(String name) {
//
//        String server = ZkServerClient.getServer();
//        String[] cfg = server.split(":");
//
//        Socket socket = null;
//        BufferedReader in = null;
//        PrintWriter out = null;
//        try {
//            InputStream is = new ByteArrayInputStream(name.getBytes());
//            socket = new Socket(cfg[0], Integer.parseInt(cfg[1]));
//            in = new BufferedReader(new InputStreamReader(is));
//            out = new PrintWriter(socket.getOutputStream(), true);
//
//            while (true) {
//                String resp = in.readLine();
//                if (resp == null)
//                    break;
//                else if (resp.length() > 0) {
//                    out.write(resp);
//                    System.out.println("Receive : " + resp);
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (socket != null) {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}
