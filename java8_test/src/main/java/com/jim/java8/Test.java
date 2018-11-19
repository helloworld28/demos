package com.jim.java8;

/**
 * @author Jim
 * @date 2018/5/21
 */
public class Test
{
    public static void main(String[] args) {
        System.out.println("类在第一次使用的时候才会被加载到内存里， ");
        try {
            Class.forName("com.jim.java8.MergeSqlFileUtil");
//            system.out.println(filereadtest.foo2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  void foo(){
        System.out.println("fooooooooooooo");
    }
}
