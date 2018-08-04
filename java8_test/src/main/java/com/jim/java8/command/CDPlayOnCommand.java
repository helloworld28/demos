package com.jim.java8.command;

/**
 * @author Jim
 * @date 2018/2/12
 */
public class CDPlayOnCommand implements Command {

    private CDPlayer cdPlayer;

    public CDPlayOnCommand(CDPlayer cdPlayer) {
        this.cdPlayer = cdPlayer;
    }

    @Override
    public void execute() {
        cdPlayer.on();
        cdPlayer.play();
        cdPlayer.setVolumn(11);
    }
}
