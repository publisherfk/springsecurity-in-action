package com.supcon.reactive.demo1;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class FunctionDemo1 {
    public static void main(String args[]) {
        //断言函数接口
        Predicate<Integer> integerPredicate = i -> i == 10;
        System.out.println(integerPredicate.test(10));
        //消费函数接口
        Consumer<String> stringConsumer = i -> {
            int temp = 10;
            System.out.println(temp + i);
        };
        stringConsumer.accept("20");
    }
}
