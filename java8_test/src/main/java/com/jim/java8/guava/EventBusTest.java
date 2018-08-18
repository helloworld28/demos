package com.jim.java8.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author Jim
 * @date 2018/8/18
 */
public class EventBusTest {

    public static void main(String[] args) {
        EventBus myEventBus = new EventBus("myEventBus");
        myEventBus.register(new EventChangeRecoder());
        myEventBus.register(new EventChangeRecoder2());
        myEventBus.post(new ChangeEvent("Helloworld"));
        myEventBus.post(new ChangeEvent2("Helloworld2"));
    }

    public static class EventChangeRecoder {
        @Subscribe
        public void readChange(ChangeEvent changeEvent) {
            System.out.println(changeEvent.getMessage());
        }
    }

    public static class EventChangeRecoder2 {
        @Subscribe
        public void readChange(ChangeEvent2 changeEvent) {
            System.out.println(changeEvent.getMessage());
        }
    }

    public static class ChangeEvent {
        private String message;

        public ChangeEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ChangeEvent2 {
        private String message;

        public ChangeEvent2(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
