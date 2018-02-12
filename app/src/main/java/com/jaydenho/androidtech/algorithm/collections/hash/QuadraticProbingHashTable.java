package com.jaydenho.androidtech.algorithm.collections.hash;

/**
 * Created by hedazhao on 2018/2/12.
 */

public class QuadraticProbingHashTable<AnyType> {

    private HashEntry<AnyType>[] entrys;
    private int currentSize = 0;

    public QuadraticProbingHashTable() {
        entrys = new HashEntry[11];
        allocArray();
    }

    public void insert(AnyType x) {

    }

    /**
     * f(i) = i^2;
     */
    private int findPos(AnyType x) {
        int offset = 1;
        int currentPos = myHash(x);
        while (entrys[currentPos] != null &&
                !entrys[currentPos].element.equals(x)) {
            //位置被占用
            currentPos += offset;
//            offset +=
        }
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
