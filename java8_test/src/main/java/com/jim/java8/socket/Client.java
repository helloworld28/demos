package com.jim.java8.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Jim on 2017/7/18.
 */
public class Client {

    public static void main(String[] args) {
        try (
            Socket client = new Socket("127.0.0.1", 9099)){
            PrintWriter printWriter = new PrintWriter(client.getOutputStream(),true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String msg ;
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(System.in));

            printWriter.print("hell you!");
            String userIn;
            while (true){
                if((userIn = bufferedReader1.readLine())!= null){
                    printWriter.println(userIn);
                }
                if((msg = bufferedReader.readLine() )!= null){
                    System.out.println("Server:"+ msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
