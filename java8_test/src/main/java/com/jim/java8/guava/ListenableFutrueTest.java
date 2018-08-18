package com.jim.java8.guava;

import com.google.common.util.concurrent.*;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author Jim
 * @date 2018/8/18
 */
public class ListenableFutrueTest {

    public static void main(String[] args) {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        ListenableFuture<String> future = listeningExecutorService.submit(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "Hello world";
                    }


                }
        );

        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("success:" + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("onFailure:" + t.getStackTrace());
            }
        }, Executors.newSingleThreadExecutor());
    }
}
