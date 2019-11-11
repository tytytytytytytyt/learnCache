//package com.geotmt.cacheprime.zookeeper;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class ZkServerHandler implements Runnable {
//
//    private Socket socket;
//
//    public ZkServerHandler(Socket socket) {
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        BufferedReader reader = null;
//        PrintWriter printWriter = null;
//        try {
//            reader =  new BufferedReader(new InputStreamReader(socket.getInputStream()));;
//            printWriter =  new PrintWriter(socket.getOutputStream());
//            String line;
//            while (true){
//                line = reader.readLine();
//                if (line == null) {
//                    break;
//                }
//                System.out.println("revieve body :" + line);
//                printWriter.print("print writer body:" + line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(reader !=null){
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(printWriter != null){
//                printWriter.close();
//            }
//
//            if(socket != null){
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
