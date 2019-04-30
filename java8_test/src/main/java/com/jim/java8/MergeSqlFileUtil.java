package com.jim.java8;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jim
 * @date 2018/1/19
 */
public class MergeSqlFileUtil {

    public static final String DIR = "D:/temp/test";
    public static final String SUFFIX_FILE_NAME = ".sql";
    public static final String OUT_PUT_FILE = "D:/out/out.sql";


    public static void main(String[] args) {
        List<File> sqlFiles = new ArrayList<>();
        getAllFiles(sqlFiles, new File(DIR));
        System.out.println(sqlFiles);


        try (
                FileOutputStream fileOutputStream = new FileOutputStream(new File(OUT_PUT_FILE));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)
        ) {
            for (File file : sqlFiles) {
                bufferedOutputStream.write("\n".getBytes());
                bufferedOutputStream.write("\n".getBytes());
                bufferedOutputStream.write(("/*      " + file.getName() + " */").getBytes());
                bufferedOutputStream.write("\n".getBytes());
                bufferedOutputStream.write("\n".getBytes());

                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedOutputStream.write(line.getBytes());
                    bufferedOutputStream.write("\n".getBytes());
                }
                bufferedReader.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getAllFiles(List<File> files, File file) {
        if (file.isFile()) {
            if (file.getName().endsWith(SUFFIX_FILE_NAME)) {
                files.add(file);
            }
        } else if (file.isDirectory()) {
            File[] files1 = file.listFiles();
            for (File tempFile : files1) {
                getAllFiles(files, tempFile);
            }
        } else {
            throw new RuntimeException("unkwown file type");
        }
    }
}
