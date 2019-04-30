package com.jim.java8;

/**
 * @author Jim
 * @date 2018/5/21
 */
public class Test
{
    public static void main(String[] args) {
      /*  System.out.println("类在第一次使用的时候才会被加载到内存里， ");
        try {
            Class.forName("com.jim.java8.MergeSqlFileUtil");
//            system.out.println(filereadtest.foo2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/



        System.out.println(Integer.parseInt("0001111",2)&15);
        System.out.println(Integer.parseInt("0011111",2)&15);
        System.out.println(Integer.parseInt("0111111",2)&15);
        System.out.println(Integer.parseInt("1111111",2)&15);
        System.out.println(Integer.toBinaryString(15));
    }

    public  void foo(){
        System.out.println("fooooooooooooo");
    }
}
