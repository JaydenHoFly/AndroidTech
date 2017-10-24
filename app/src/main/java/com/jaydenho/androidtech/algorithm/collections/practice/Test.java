package com.jaydenho.androidtech.algorithm.collections.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */

public class Test {
    public static void main(String[] args) {
        List<Integer> l1 = new ArrayList<>();
        l1.add(1);
        l1.add(3);
        l1.add(5);
        l1.add(7);
        List<Integer> l2 = new ArrayList<>();
        l2.add(2);
        l2.add(3);
        l2.add(5);
        l2.add(6);
        l2.add(7);
        l2.add(8);
        List<Integer> intersection = Q3_4.intersection(l1, l2);
        System.out.println("intersection: " + Arrays.toString(intersection.toArray()));
        List<Integer> union = Q3_5.union(l1, l2);
        System.out.println("union: " + Arrays.toString(union.toArray()));

        int n = 5, m = 1;
        Q3_6.simulation(n, m);
        Q3_6.simulationLinkedList(n, m);
        System.out.println("Q3_6.winnerRecursion(n,m): " + Q3_6.winnerRecursion(n, m));
        System.out.println("Q3_6.winner(n,m): " + Q3_6.winner(n, m));

    }
}
