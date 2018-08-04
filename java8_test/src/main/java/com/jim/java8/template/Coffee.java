package com.jim.java8.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * @author Jim
 * @date 2018/3/17
 */
public class Coffee extends CaffeinBeverage {

    @Override
    protected void addCondiments() {
        System.out.println("add Milk and Tea");
    }

    @Override
    protected void brew() {
        System.out.println("add coffee");
    }

    @Override
    public boolean isNeedAddCondiments() {
        String input = getInput();
        if (input.toLowerCase().startsWith("y")) {
            return true;
        } else {
            return false;
        }
    }

    public String getInput() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("do you want add Milk? (y/n)");
        try {
            String line = null;
            while (line == null || line.equals("")) {
                line = bufferedReader.readLine();
            }
            return line;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
