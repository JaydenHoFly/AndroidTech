package com.jaydenho.androidtech.algorithm.collections.practice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 * List1, List2 are sortable, result List1 ∪ List2.
 */

public class Q3_5 {
    public static <T extends Comparable<T>> List<T> union(List<T> l1, List<T> l2) {
        Iterator<T> it1 = l1.iterator();
        Iterator<T> it2 = l2.iterator();
        List<T> result = new ArrayList<>();
        T t1 = null;
        T t2 = null;
        if (it1.hasNext()) {
            t1 = it1.next();
        }
        if (it2.hasNext()) {
            t2 = it2.next();
        }
        while (t1 != null || t2 != null) {
            int compare;
            if (t1 != null && t2 != null)
                compare = t1.compareTo(t2);
            else if (t1 != null)
                compare = -1;
            else
                compare = 1;
            if (compare == 0) {
                result.add(t1);
                t1 = it1.hasNext() ? it1.next() : null;
                t2 = it2.hasNext() ? it2.next() : null;
            } else if (compare < 0) {
                //t1 < t2
                result.add(t1);
                t1 = it1.hasNext() ? it1.next() : null;
            } else {
                result.add(t2);
                t2 = it2.hasNext() ? it2.next() : null;
            }
        }
        return result;
    }
}
