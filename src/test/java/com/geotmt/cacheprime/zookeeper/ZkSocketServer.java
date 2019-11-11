package com.geotmt.cacheprime.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.assertj.core.util.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ZkSocketServer implements Runnable{


    public static String rootPath = "/test";
    public static int port = 18080;


    public static void main(String[] args) {
        ZkSocketServer zkSocketServer = new ZkSocketServer();
        new Thread(zkSocketServer).start();

    }

    private static void registServer(int port) {
        String path = "127.0.0.1:" + port ;
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        if(zkClient.exists(rootPath)){
            zkClient.delete(rootPath);
        }else{
            zkClient.createPersistent(rootPath);
        }
        String realPath = rootPath + "/" + path;
        zkClient.createEphemeral(realPath,path);
        System.out.println(" 注册服务key :" + realPath);

    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            registServer(port);

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("请求进来了");
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerHandler implements Runnable {

        private Socket socket;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter printWriter = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter = new PrintWriter(socket.getOutputStream());
                while (true){
                    String line = reader.readLine();
                    if(line == null){
                        break;
                    }
                    printWriter.write(line);
                    System.out.println("Server data:" + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(printWriter != null){
                    printWriter.close();
                }
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

}
