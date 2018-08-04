package com.jim.java8.test5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by Jim on 2017/11/1.
 */
public class Test {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //找出2011年的所有交易并按交易额排序（从低到高
        transactions.stream().filter(transaction -> transaction.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(toList());
        //交易员都在哪些不同的城市工作过
        transactions.stream().map(Transaction::getTrader).map(Trader::getCity).distinct().collect(toList());
        //) 查找所有来自于剑桥的交易员，并按姓名排序
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(toList());
        System.out.println("查找所有来自于剑桥的交易员，并按姓名排序:"+traders);

        List<String> traderNames = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .sorted(Comparator.comparing(String::toString))
                .collect(toList());
        System.out.println("traderNames:" + traderNames);

        boolean result = transactions.stream().map(Transaction::getTrader).anyMatch(trader -> "Milan".equals(trader.getCity()));
        System.out.println("有没有交易员是在米兰工作的:"+result);

        //打印生活在剑桥的交易员的所有交易额
        Integer reduce = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                .distinct()
                .flatMap(trader -> transactions.stream().filter(transaction -> trader == transaction.getTrader()))
                .map(Transaction::getValue)
                .reduce(0, (a, b) -> a + b);
        System.out.println("生活在剑桥的交易员的所有交易额:"+reduce);


    }
}
