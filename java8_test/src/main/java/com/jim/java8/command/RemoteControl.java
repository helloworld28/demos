package com.jim.java8.command;

/**
 * @author Jim
 * @date 2018/2/12
 */
public class RemoteControl {
    Command[] onCommands;
    Command[] offCommands;

    Command noCommand = new NoCommand();

    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    public void setCommand(int i, Command onCommand, Command offCommand) {
        onCommands[i] = onCommand;
        offCommands[i] = offCommand;
    }

    public void onButtonPause(int i) {
        onCommands[i].execute();
    }

    public void offButtonPuase(int i) {
        offCommands[i].execute();
    }
}
