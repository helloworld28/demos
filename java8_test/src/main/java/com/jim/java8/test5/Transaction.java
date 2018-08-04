package com.jim.java8.test5;

/**
 * Created by Jim on 2017/11/1.
 */
public class Transaction {
    private final Trader trader;
    private  int year;
    private  int value;

    public Transaction(Trader trader, int year, int value) {
        this.year = year;
        this.value = value;
        this.trader = trader;
    }

    public Trader getTrader() {
        return trader;
    }

    public  int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "trader=" + trader +
                ", year=" + year +
                ", value=" + value +
                '}';
    }

    public String   getCurrency() {
        return "YMB";
    }
}
