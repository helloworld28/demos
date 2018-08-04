package com.jim.java8.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Jim on 2017/7/18.
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {
         try(ServerSocket serverSocket = new ServerSocket(9099)){
             Socket clientSocket = serverSocket.accept();
             PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             String userInputs;
             while ((userInputs = bufferedReader.readLine()) != null){
                 System.out.println("user input :"+ userInputs);
                 printWriter.println("echo:"+userInputs);
             }
             System.out.println("server was started!");
         }catch (Exception e){
             e.printStackTrace();
         }


    }
}
