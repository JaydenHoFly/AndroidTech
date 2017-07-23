package com.jaydenho.androidtech.algorithm.collections;

import java.util.Iterator;

/**
 * Created by Administrator on 2017/7/23.
 * 数组地址是连续的，MyArrayList在存储时也是连续的，null值都留在数组的末尾，
 * 所以要有一个值持有真实的容器大小
 */

public class MyArrayList<T> implements Iterator {

    private int mSize = 0;
    private T[] mItems = null;

    public MyArrayList() {
        mItems = (T[]) new Object[8];
    }

    public T get(int position) {
        return mItems[position];
    }

    public void add(int position, T t) {
        ensureCapacity(position, 1);
        int addBegin = position;
        int addEnd = addBegin + 1;
        for(int i = mSize; i > addEnd - 1; i--){
            mItems[i] = mItems[i - 1];
        }
        mItems[addBegin] = t;
    }

    public boolean addAll(int position, MyArrayList<T> list) {
        return false;
    }

    public T remove(int position) {
        return null;
    }

    public T remove(T t) {
        return null;
    }

    public int size() {
        return this.mSize;
    }

    public boolean contains(T t) {
        return false;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public void remove() {

    }

    private void ensureCapacity(int addPosition, int addSize) {
        ensureCapacity(addPosition + addPosition + 1);
    }
    private void ensureCapacity(int newCapacity) {
        if (newCapacity < size()) {
            return;
        }
        T[] old = mItems;
        mItems = (T[]) new Object[newCapacity];
        System.arraycopy(old, 0, mItems, 0, old.length);
    }
}
