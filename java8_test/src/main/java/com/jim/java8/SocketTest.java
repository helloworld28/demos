package com.jim.java8;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Jim
 * @date 2018/7/23
 */
public class SocketTest {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        while (true) {
            Socket accept = serverSocket.accept();
            accept.getInputStream();
            System.out.println("发现连接：" + accept.getRemoteSocketAddress());
            new Thread(new ProcessSocket(accept)).start();
            new Thread(new PrintSocket(accept)).start();

        }
    }


    public static class ProcessSocket implements Runnable {
        Socket accept;
        BufferedReader bufferedReader;

        public ProcessSocket(Socket accept) {
            this.accept = accept;
        }


        @Override
        public void run() {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                byte[] buffer = new byte[1024];
                InputStream inputStream = accept.getInputStream();
                while ((inputStream.read(buffer, 0, 1024)) != -1) {
                    System.out.println(new String(buffer));
                }


            } catch (Exception e) {
                e.printStackTrace();
                try {
                    bufferedReader.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        }
    }


    public static class PrintSocket implements Runnable {
        Socket accept;
        BufferedWriter bufferedWriter;

        public PrintSocket(Socket accept) {
            this.accept = accept;
        }


        @Override
        public void run() {
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
                while (true) {
                    bufferedWriter.write("hello");


                }


            } catch (Exception e) {
                e.printStackTrace();
                try {
                    accept.close();
                    bufferedWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
