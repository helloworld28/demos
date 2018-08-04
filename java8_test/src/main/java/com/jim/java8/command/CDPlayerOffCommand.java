package com.jim.java8.command;

/**
 * @author Jim
 * @date 2018/2/12
 */
public class CDPlayerOffCommand implements Command {
    private CDPlayer cdPlayer;

    public CDPlayerOffCommand(CDPlayer cdPlayer) {
        this.cdPlayer = cdPlayer;
    }

    @Override
    public void execute() {
        cdPlayer.off();
    }
}
