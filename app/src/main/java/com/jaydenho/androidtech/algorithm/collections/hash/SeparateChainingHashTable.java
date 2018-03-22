package com.jaydenho.androidtech.algorithm.collections.hash;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hedazhao on 2018/2/12.
 * 分离链接散列表，标准库通常使用该方法
 */

public class SeparateChainingHashTable<AnyType> {
    private List<AnyType>[] lists = null;
    private int currentSize = 0;

    public SeparateChainingHashTable() {
        this.lists = new ArrayList[10];
        for (List l : lists) {
            l = new LinkedList();
        }
    }

    public void insert(AnyType x) {
        List list = getList(x);
        if (list.contains(x)) return;
        list.add(x);
        if (++currentSize > lists.length) {
            reHash();
        }
    }

    private List getList(AnyType x) {
        int myHash = myHash(x);
        return lists[myHash];
    }

    public boolean contains(AnyType x) {
        return getList(x).contains(x);
    }

    public void remove(AnyType x) {
        if (getList(x).remove(x)) {
            currentSize--;
        }
    }

    public void makeEmpty() {
        for (List l : lists) {
            l.clear();
        }
        currentSize = 0;
    }

    private void reHash() {
        List<AnyType>[] oldLists = lists;

        lists = new List[oldLists.length * 2];
        for (List<AnyType> list : lists) {
            list = new LinkedList<>();
        }
        currentSize = 0;

        for (List<AnyType> list : oldLists) {
            for (AnyType x : list) {
                insert(x);
            }
        }
    }

    private int myHash(AnyType x) {
        int hashVal = x.hashCode();
        hashVal %= lists.length;
        if (hashVal < 0) {
            hashVal += lists.length;
        }
        return hashVal;
    }

}
