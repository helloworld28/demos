package com.jim.java8.completefuture;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

/**
 * @author Jim
 * @date 2018/6/14
 */
public class Shop {
    private String shopName;

    public Shop(String shopName) {
        this.shopName = shopName;
    }

    static List<Shop> shops = Arrays.asList(new Shop("SHop1"),
            new Shop("Shop2"),
            new Shop("Shop3"),
            new Shop("Shop4"),
            new Shop("Shop5"));

    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:sss");

    }

    private static List<String> findPrices() {


        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        return thread;
                    }
                });
        long startTime = System.currentTimeMillis();
        List<CompletableFuture> collect = shops.stream()
                .map(shop -> {
                    return CompletableFuture.supplyAsync(() -> {
                        return String.format("shop[%s] price[%s]", shop.getShopName(), shop.getPrice());
                    }, executorService);
                }).collect(toList());

        List<Object> collect1 = collect.stream().map(CompletableFuture::join).collect(toList());

        System.out.println(collect1);

        System.out.println("Cost TIme " + (System.currentTimeMillis() - startTime));
        return null;
    }

    private void doSomeThingElse() {
        System.out.println("doSomeThingElse!!");
    }

    public Future<Double> getPriceAsync() {
        CompletableFuture<Double> doubleCompletedFuture = new CompletableFuture<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    double price = caculatePrice();
                    doubleCompletedFuture.complete(price);
                } catch (Exception e) {
                    doubleCompletedFuture.completeExceptionally(e);
                }
            }
        }).start();
        return doubleCompletedFuture;
    }


    public Future<Double> getPriceAsync2() {
        return CompletableFuture.supplyAsync(() -> caculatePrice());
    }

    public double getPrice() {
        delay();
        return 22.00;
    }


    private double caculatePrice() {
        System.out.println("正在计算价格");
        delay();

        return 12.00;
    }

    private void delay() {
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
