package com.jim.java8;

import java.io.*;
import java.nio.file.*;

public class ProcessExecutor {

    public static final int SUCCESS = 0;            // 表示程序执行成功

    public static final String COMMAND_PATTERN = "ping -t %s";    // 要执行的语句

    public static final String SUCCESS_MESSAGE = "程序执行成功！";

    public static final String ERROR_MESSAGE = "程序执行出错：";

    static String timeoutFlagStr1 = "无法访问目标主机";
    //无法访问目标主机出现出现的次数
    private static int count1 = 0;

    private static String timeoutFlagStr2 = "请求超时";
    //请求出现的次数
    private static int count2 = 0;

    private static String ip;
    private static int MAX_TIMEOUT_TIMES = 5;

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("参数个数不对");
            return;
        }


        ip = args[0];
        String times = args[1];
        System.out.println(String.format("传入的参数是%s, %s", ip, times));
        MAX_TIMEOUT_TIMES = Integer.valueOf(args[1]);
//        String[] commandArray = new String[]{ "ping -t 192.168.1.81"};
        // 执行程序
        Process process = Runtime.getRuntime().exec(String.format(COMMAND_PATTERN, ip));

        // 打印程序输出
        readProcessOutput(process);

        // 等待程序执行结束并输出状态
        int exitCode = process.waitFor();

        if (exitCode == SUCCESS) {
            System.out.println(SUCCESS_MESSAGE);
        } else {
            System.err.println(ERROR_MESSAGE + exitCode);
        }
    }

    /**
     * 打印进程输出
     *
     * @param process 进程
     */
    private static void readProcessOutput(final Process process) {
        // 将进程的正常输出在 System.out 中打印，进程的错误输出在 System.err 中打印
        read(process.getInputStream(), System.out);
        read(process.getErrorStream(), System.err);
    }

    // 读取输入流
    private static void read(InputStream inputStream, PrintStream out) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "GB2312"));

            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
                if (line != null && line.contains(timeoutFlagStr1)) {
                    count1++;
                    if (count1 >= MAX_TIMEOUT_TIMES) {
                        System.out.println("发现了断开,写到文件");
                        writeToFile("TIMEOUT");
                        count1 = 0;
                        count2 = 0;
                    }
                } else if (line != null && line.contains(timeoutFlagStr2)) {
                    count2++;
                    if (count2 >= MAX_TIMEOUT_TIMES) {
                        System.out.println("发现了断开,写到文件");
                        writeToFile("TIMEOUT");
                        count1 = 0;
                        count2 = 0;
                    }
                } else {
                    if (count1 != 0 || count2 != 0) {
                        count1 = 0;
                        count2 = 0;
                        writeToFile("CONNECTED");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private static void writeToFile(String result) throws IOException {
        System.out.println("写结果到文件:"+FileSystems.getDefault().getPath(getFileName(ip)).toAbsolutePath());
        Files.write(FileSystems.getDefault().getPath(getFileName(ip)), result.getBytes());
    }
    private static String getFileName(String ip){
        return String.format("%s.txt", ip);
    }
}