package com.geotmt.cacheprime.zookeeper.LoadBalance;


import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.assertj.core.util.Lists;

import java.io.*;
import java.net.Socket;
import java.util.List;



public class ZkSocketClient {

    public static List<String> servers = Lists.newArrayList();
    public static String rootPath = "/test";
    public static int requestCount ;
    public static int serverCount ;

    public static void main(String[] args) {
        initServer();
        String url = getServerUrl();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                while(true){
                    String line = reader.readLine();
                    if(line == null){
                        break;
                    }
                    System.out.println("客户端输入 :" + line);
                    sendData(url, line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void sendData(String url, String msg) {
        ++requestCount ;
        String[] ipPort = url.split(":");
        OutputStream outputStream = null;
        Socket socket = null;
        try {
            socket = new Socket(ipPort[0], Integer.parseInt(ipPort[1]));
            outputStream = socket.getOutputStream();
            outputStream.write(msg.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream !=null){
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static String getSendData() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuffer data = new StringBuffer();
        try {
            while(true){
                String line = reader.readLine();
                if(line == null){
                    break;
                }
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    private static String getServerUrl() {
        int index = requestCount % serverCount;
        String url = servers.get(index);
        return url;
    }

    private static void initServer() {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");

        zkClient.subscribeChildChanges(rootPath,new IZkChildListener(){
            @Override
            public void handleChildChange(String parentPath, List<String> currentChild) throws Exception {
                getChildrenValues(currentChild,zkClient);
            }
        });

        List<String> children = zkClient.getChildren(rootPath);
        getChildrenValues(children,zkClient);
    }

    private static void getChildrenValues(List<String> children,ZkClient zkClient) {
        for (String child : children) {
            String path = rootPath + "/" + child;
            System.out.println("path :" + path);
            String readData = (String)zkClient.readData(path);
            servers.add(readData);
        }
        serverCount = servers.size();
        System.out.println(" server上游那些节点："+ servers);
    }



}
