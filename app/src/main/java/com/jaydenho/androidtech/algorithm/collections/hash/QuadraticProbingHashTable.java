package com.jaydenho.androidtech.algorithm.collections.hash;

/**
 * Created by hedazhao on 2018/2/12.
 * 平方探测法实现散列表，不需要使用链表。
 */

public class QuadraticProbingHashTable<AnyType> {

    private HashEntry<AnyType>[] entrys;
    private int currentSize = 0;

    public QuadraticProbingHashTable() {
        entrys = new HashEntry[11];
        allocArray();
    }

    public void insert(AnyType x) {
        int pos = findPos(x);
        if (isActive(pos)) {
            return;
        }
        entrys[pos] = new HashEntry<AnyType>(x, true);
        //定理：使用平方探测法时，只要表至少有一半是空的，而且表的大小是素数，那么一定可以插入成功。by 数据结构与算法分析p134.
        if (++currentSize > entrys.length / 2){
            rehash();
        }
    }

    /**
     * 需要惰性删除，因为不能改变findPos的结果，如果直接删除了，那么findPos返回的结果就不同，contains就失效了。
     */
    public void remove(AnyType x) {
        int pos = findPos(x);
        if(isActive(pos))
            entrys[pos].isActive = false;
    }

    public boolean contains(AnyType x) {
        int pos = findPos(x);
        return isActive(pos);
    }

    private void rehash() {
        HashEntry<AnyType>[] oldEntrys = entrys;
        //重新创建一个2倍原大小的数组
        entrys = new HashEntry[oldEntrys.length * 2];
        allocArray();
        currentSize = 0;

        //将原有元素重新插入到新表中
        for(HashEntry<AnyType> entry:oldEntrys) {
            if(entry != null && entry.isActive) {
                insert(entry.element);
            }
        }
    }

    /**
     * 该位置是否被使用
     */
    private boolean isActive(int pos) {
        return entrys[pos] != null && entrys[pos].isActive;
    }

    /**
     * f(i) = i^2;
     * pos = hash(x) + f(i);
     * i为尝试寻找pos的次数，从1开始，直到找到新的空位置。
     */
    private int findPos(AnyType x) {
        int offset = 1;
        int currentPos = myHash(x);
        while (entrys[currentPos] != null &&
                !entrys[currentPos].element.equals(x)) {
            //位置被占用
            currentPos += offset;
            //连续探测时，位置递增2.
            offset += 2;
            //避免溢出
            if (currentPos >= entrys.length) {
                currentPos -= entrys.length;
            }
        }
        return currentPos;
    }

    private int myHash(AnyType x) {
        return 0;
    }

    private void allocArray() {
        for (HashEntry entry : entrys) {
            entry = null;
        }
    }

    private static class HashEntry<AnyType> {
        public AnyType element;
        public boolean isActive;

        public HashEntry(AnyType element) {
            this.element = element;
        }

        public HashEntry(AnyType element, boolean isActive) {
            this.element = element;
            this.isActive = isActive;
        }
    }
}
