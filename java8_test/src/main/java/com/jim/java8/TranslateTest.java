package com.jim.java8;

/**
 * Created by Jim on 2017/7/18.
 */
public class TranslateTest {
    public static void main(String[] args) {

        System.out.println("读取集中器时间结果解析");
        String str = "68EE0001712113050900170505145246050001DD16";

        byte[] bytes = DataConverter.hexStringToByte(str);

        System.out.println("转换回来:"+ DataConverter.bytesToHexString(bytes));

        System.out.println("集中器地址:"+DataConverter.bcd2Str(new byte[]{bytes[2],bytes[3],bytes[4],bytes[5],bytes[6]}));
        System.out.println("年:"+DataConverter.bcd2Str(new byte[]{bytes[10]}));
        System.out.println("月:"+DataConverter.bcd2Str(new byte[]{bytes[11]}));
        System.out.println("日:"+DataConverter.bcd2Str(new byte[]{bytes[12]}));
        System.out.println("时:"+DataConverter.bcd2Str(new byte[]{bytes[13]}));
        System.out.println("分:"+DataConverter.bcd2Str(new byte[]{bytes[14]}));
        System.out.println("秒:"+DataConverter.bcd2Str(new byte[]{bytes[15]}));
        System.out.println("星期"+DataConverter.bcd2Str(new byte[]{bytes[16]}));
        System.out.println("应答标志:"+DataConverter.bcd2Str(new byte[]{bytes[17]}));

    }
}
