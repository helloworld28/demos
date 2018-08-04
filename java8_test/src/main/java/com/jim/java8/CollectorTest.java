package com.jim.java8;

import com.jim.java8.example.Dish;
import com.jim.java8.test5.Trader;
import com.jim.java8.test5.Transaction;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * Created by Jim on 2017/10/12.
 */
public class CollectorTest {
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
                new Transaction(alan, 2013, 950)
        );
        //分组归集
        Map<String, List<Transaction>> mpa = transactions.stream().collect(Collectors.groupingBy(Transaction::getCurrency));

        //收集器
        //最大值
        Optional<Transaction> collect = transactions.stream().collect(maxBy(comparingInt(Transaction::getValue)));

        //汇总
        transactions.stream().collect(summingInt(Transaction::getValue));

        //平均数
        Double collect1 = transactions.stream().collect(averagingInt(Transaction::getValue));

        //多个汇总的值
        IntSummaryStatistics collect2 = transactions.stream().collect(summarizingInt(Transaction::getValue));
        System.out.println("averg:" + collect2.getAverage());
        System.out.println("sum:" + collect2.getSum());

        //连接字符串
        String collect3 = transactions.stream().map(Transaction::getTrader).map(Trader::getName).distinct().collect(joining(", "));
        System.out.println("trader names:" + collect3);

        //分组
        transactions.stream().collect(groupingBy(Transaction::getTrader));
        //#根据交易年份分组
        transactions.stream().collect(groupingBy(transaction -> {
            if (transaction.getYear() < 2013) {
                return 2012;
            } else {
                return 2013;
            }
        }));

        //分级分组,根据交易员并且根据年份分组
        Map<Trader, Map<Integer, List<Transaction>>> collect4 = transactions.stream().collect(groupingBy(Transaction::getTrader, groupingBy(transaction -> {
            if (transaction.getYear() < 2013) {
                return 2012;
            } else {
                return 2013;
            }
        })));

        //分组还还分收集分组数据
        Map<Trader, Long> collect5 = transactions.stream().collect(groupingBy(Transaction::getTrader, counting()));
        System.out.println("各个交易员的交易：" + collect5);

        //求分组中最高的记录
        transactions.stream().collect(groupingBy(Transaction::getTrader,
                collectingAndThen(maxBy(comparingInt(Transaction::getValue)), Optional::get)));

        //各个交易员分组，针对每个交易的金额进行评分
        Map<Trader, Set<String>> collect8 = transactions.stream().collect(groupingBy(Transaction::getTrader,
                mapping(transaction -> {
                    if (transaction.getValue() > 1000) {
                        return "good";
                    } else {
                        return "bad";
                    }
                }, toSet())));

        //求分组中最高热量的鱼
        //#collectingAndThen 收集后执行另一种挪作， 用于转换
        Map<Dish.Type, Optional<Dish>> collect7 = Dish.menu.stream().collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
        Map<Dish.Type, Dish> collect6 = Dish.menu.stream()
                .collect(
                        groupingBy(Dish::getType,
                                collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)), Optional::get)));


        //#分区就是简单的你分为两组
        Dish.menu.stream().collect(partitioningBy(Dish::isVegetarian));


    }


}
