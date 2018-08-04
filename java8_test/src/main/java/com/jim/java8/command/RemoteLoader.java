package com.jim.java8.command;

/**
 * @author Jim
 * @date 2018/2/12
 */
public class RemoteLoader {
    public static void main(String[] args) {
        Light light = new Light();
        LightOnCommand lightOnCommand = new LightOnCommand(light);
        LightOffCommand lightOffCommand = new LightOffCommand(light);

        CDPlayer cdPlayer = new CDPlayer();
        CDPlayOnCommand cdPlayOnCommand = new CDPlayOnCommand(cdPlayer);
        CDPlayerOffCommand cdPlayerOffCommand = new CDPlayerOffCommand(cdPlayer);


        RemoteControl remoteControl = new RemoteControl();
        remoteControl.setCommand(0, lightOnCommand, lightOffCommand);
        remoteControl.setCommand(1, cdPlayOnCommand, cdPlayerOffCommand);

        remoteControl.onButtonPause(0);
        remoteControl.offButtonPuase(0);

        remoteControl.onButtonPause(1);
        remoteControl.offButtonPuase(1);

        remoteControl.onButtonPause(2);
        remoteControl.offButtonPuase(2);

    }
}
