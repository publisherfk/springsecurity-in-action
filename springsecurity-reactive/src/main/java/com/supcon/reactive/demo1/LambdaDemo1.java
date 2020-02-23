package com.supcon.reactive.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

interface InterfaceDemo {
    int intNum(int i);
}

public class LambdaDemo1 {
    public static void main(String args[]) {
        //lamba表达式
        InterfaceDemo demo1 = (i) -> 2020;
        System.out.println(demo1.intNum(10));
        //函数式编程
        Function<Integer, Integer> function = i -> ++i;
        System.out.println(function.apply(10));
        //函数式编程链式操作
        System.out.println(function.andThen(i -> "结果集:" + i).apply(10));
        List<String> list = new ArrayList<String>();
        list.add("11");
        list.add("22");
        list.add("22");

        int[] ints = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            ints[i] = i;
        }
        long startTime = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(sum);
        startTime = System.currentTimeMillis();
        sum = IntStream.of(ints).sum();
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(sum);
    }
}
