package com.jim.java8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by Jim on 2017/12/20.
 */
public class LogToBin {

    public static void main(String[] args) throws IOException {
        byte[] origianlBytes = Files.readAllBytes(FileSystems.getDefault().getPath("D:\\temp\\bb-R100-20171033.bin"));

        List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath("D:\\temp\\bin.txt"));
        lines = lines.stream().filter(str -> str.indexOf("3C E6") > -1).map(str -> str.substring(str.indexOf("3C E6")).replace(" ", "")).collect(toList());
        lines.forEach(System.out::println);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        lines.stream().forEach(line -> {
            byte[] bytes = DataConverter.hexStringToByte(line);
            try {
                byteArrayOutputStream.write(bytes, 3, bytes.length -7);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        byte[] bytes = byteArrayOutputStream.toByteArray();

        byte[] newBytes = new byte[origianlBytes.length];
        System.arraycopy(bytes, 0, newBytes, 0, origianlBytes.length);

        Files.write(FileSystems.getDefault().getPath("D:\\temp\\out.bin"), newBytes);
    }
}
