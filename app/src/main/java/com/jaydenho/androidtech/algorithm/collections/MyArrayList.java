package com.jaydenho.androidtech.algorithm.collections;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/7/23.
 * 数组地址是连续的，MyArrayList在存储时也是连续的，null值都留在数组的末尾，
 * 所以要有一个值持有真实的容器大小。
 */

public class MyArrayList<T> {

    private int mSize = 0;
    private T[] mItems = null;

    public MyArrayList() {
        mItems = (T[]) new Object[8];
        mSize = 0;
    }

    public T get(int position) {
        checkGetPosition(position);
        return mItems[position];
    }

    public void add(T t) {
        add(mSize, t);
    }

    public void add(int position, T t) {
        checkAddPosition(position);
        ensureCapacity(position, 1);
        //插入新元素，移动目标position后的所有元素
        System.arraycopy(mItems, position, mItems, position + 1, mSize - position);
        mItems[position] = t;
        mSize++;
    }

    public void addAll(MyArrayList<T> list) {
        addAll(mSize, list);
    }

    public void addAll(int position, MyArrayList<T> list) {
        checkAddPosition(position);
        ensureCapacity(position, list.size());
        System.arraycopy(mItems, position, mItems, position + list.size(), mSize - position);
        System.arraycopy(list.mItems, 0, mItems, position, list.size());
        mSize += list.size();
    }

    public T remove(int position) {
        checkRemovePosition(position);
        T removed = mItems[position];
        System.arraycopy(mItems, position + 1, mItems, position, mSize - (position + 1));
        mItems[--mSize] = null;
        return removed;
    }

    public T remove(T t) {
        int position = getPosition(t);
        if (position == -1) {
            return null;
        }
        return remove(position);
    }

    public int size() {
        return this.mSize;
    }

    private int getPosition(T t) {
        if (t == null) return -1;
        for (int i = 0; i < mSize; i++) {
            if (t.equals(mItems[i])) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(T t) {
        return getPosition(t) != -1;
    }

    @Override
    public String toString() {
        return Arrays.toString(mItems);
    }

    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private void ensureCapacity(int addPosition, int addSize) {
        ensureCapacity(addPosition + addSize);
    }

    private void ensureCapacity(int newCapacity) {
        int oldCapacity = mItems.length;
        if (newCapacity <= oldCapacity) {
            return;
        }
        T[] old = mItems;
        int addCapacity = newCapacity - oldCapacity;
        addCapacity = addCapacity > 4 ? addCapacity : addCapacity * 2;
        mItems = Arrays.copyOf(mItems, oldCapacity + addCapacity);
    }

    private void checkGetPosition(int position) {
        if (position < 0 || position >= mSize) {
            throw new IndexOutOfBoundsException("checkGetPosition. position error. position: " + position + " size: " + mSize);
        }
    }

    private void checkAddPosition(int position) {
        if (position < 0 || position > mSize) {
            throw new IndexOutOfBoundsException("checkAddPosition. position error. position: " + position + " size: " + mSize);
        }
    }

    private void checkRemovePosition(int position) {
        if (mSize == 0) {
            throw new IndexOutOfBoundsException("checkAddPosition. size is 0, can not remove any element");
        }
        //0< position<= mSize - 1
        if (position < 0 || position > mSize - 1) {
            throw new IndexOutOfBoundsException("checkRemovePosition. position error. position: " + position + " size: " + mSize);
        }
    }

    private class MyIterator implements Iterator<T> {
        protected final int limit = mSize;
        private int mIndex = -1;

        @Override
        public boolean hasNext() {
            return mIndex + 1 <= limit - 1;
        }

        @Override
        public T next() {
            return mItems[++mIndex];
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(mIndex--);
        }
    }
}
