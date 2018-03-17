package com.jaydenho.androidtech.algorithm.collections.heap;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/3/17.
 * 二叉堆
 * from 《数据结构与算法》p151
 */

public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {
    private int currentSize = 0;
    private AnyType[] array = null;

    public BinaryHeap() {
        allocArray(20);
    }

    public BinaryHeap(AnyType[] items) {
        currentSize = items.length;
        array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];

        int i = 1;
        for (AnyType item : items) {
            array[i++] = item;
        }
        buildHeap();
    }

    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * 平均时间：O(1)
     * 最坏时间：O(logN)
     */
    public void insert(AnyType x) {
        int hole = findNew();
        while (hole > 1 && array[findParent(hole)].compareTo(x) > 0) {
            array[hole] = array[findParent(hole)];
            hole = findParent(hole);
        }
        array[hole] = x;
        if (++currentSize >= array.length - 1) {
            array = Arrays.copyOf(array, currentSize * 2);
        }
    }

    /**
     * 最坏时间：O(logN)
     * 平均时间：O(logN), 几乎每次deleteMin都要下滤到底层，都要花费最坏时间。
     */
    public AnyType deleteMin() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        AnyType min = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);
        return min;
    }

    /**
     * 下滤
     */
    private void percolateDown(int hole) {
        int child;
        AnyType tmp = array[hole];
        while (findLeft(hole) <= currentSize) {
            //have left child
            child = findLeft(hole);
            if (child != currentSize && array[child].compareTo(array[child + 1]) < 0) {
                //have right child && right < left
                child++;
            }
            if (array[child].compareTo(array[hole]) < 0) {
                array[hole] = array[child];
            } else {
                break;
            }
            hole = child;
        }
        array[hole] = tmp;
    }

    public boolean isEmpty() {
        return currentSize < 1;
    }

    public AnyType findMin() {
        return array[1];
    }

    private int findParent(int pos) {
        //root?
        return pos / 2;
    }

    private int findLeft(int pos) {
        return 2 * pos;
    }

    private int findRight(int pos) {
        return findLeft(pos) + 1;
    }

    private int findNew() {
        return currentSize + 1;
    }

    private void allocArray(int size) {
        array = (AnyType[]) new Comparable[size];
    }
}
