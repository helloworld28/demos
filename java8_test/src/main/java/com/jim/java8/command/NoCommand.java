package com.jim.java8.command;

/**
 * @author Jim
 * @date 2018/2/12
 */
public class NoCommand implements Command {

    @Override
    public void execute() {
        System.out.println("No Command!!!");
    }
}
