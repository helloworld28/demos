package com.jim.java8.command;

/**
 * @author Jim
 * @date 2018/2/12
 */
public class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.down();
    }
}
