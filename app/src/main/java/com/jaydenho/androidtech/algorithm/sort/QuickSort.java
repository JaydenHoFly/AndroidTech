package com.jaydenho.androidtech.algorithm.sort;

/**
 * Created by hedazhao on 2021/1/17.
 */
public class QuickSort {
    public static void main(String[] args) {
        C[] cs = new C[]{new C(1), new C(2),new C(100),new C(10),new C(6),new C(98),new C(0)};
        quickSort(cs);
    }

    public static void quickSort(Comparable[] cs) {
        quickSort(cs, 0, cs.length - 1);
    }

    public static void quickSort(Comparable[] cs, int left, int right) {
        Object pivot = median3(cs, left, right);
        int i = left, j = right - 1;
        for (; ; ) {
            while (cs[++i].compareTo(pivot) < 0) {
            }
            while (cs[--j].compareTo(pivot) > 0) {
            }
            if (i < j) {
                swap(cs, i, j);
            } else {
                break;
            }
        }

        swap(cs, i, right - 1);

        quickSort(cs, left, i - 1);
        quickSort(cs, i + 1, right);
    }

    /**
     * 将 left, center, right 从小到大排序，将 center 定为 pivot，并将 pivot 放在 right - 1。
     *
     * @param left  左侧最小 index。
     * @param right 右侧最大 index。
     * @return pivot 的值。
     */
    private static Object median3(Comparable[] cs, int left, int right) {
        int center = (left + right) / 2;
        if (cs[left].compareTo(cs[center]) > 0) {
            swap(cs, left, center);
        }
        if (cs[center].compareTo(cs[right]) > 0) {
            swap(cs, center, right);
        }
        if (cs[left].compareTo(cs[center]) > 0) {
            swap(cs, left, center);
        }
        swap(cs, center, right - 1);
        return cs[right - 1];
    }

    private static void swap(Comparable[] cs, int i, int j) {
        Comparable o = cs[i];
        cs[i] = cs[j];
        cs[j] = o;
    }

    private static class C implements Comparable<C> {
        private final int i;

        public C(int i) {
            this.i = i;
        }

        @Override
        public int compareTo(C o) {
            return i - o.i;
        }
    }
}
