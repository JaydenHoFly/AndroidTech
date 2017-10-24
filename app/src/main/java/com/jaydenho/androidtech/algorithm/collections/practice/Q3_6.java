package com.jaydenho.androidtech.algorithm.collections.practice;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Administrator on 2017/9/24.
 * 约瑟夫环
 * http://www.cnblogs.com/kkrisen/p/3569281.html#undefined
 * 假设问题是从n个人编号分别为0...n-1，取第k个，
 * <p>
 * 则第k个人编号为k-1的淘汰，剩下的编号为  0,1,2,3...k-2,k,k+1,k+2...
 * <p>
 * 此时因为从刚刚淘汰那个人的下一个开始数起，因此重新编号
 * <p>
 * 把k号设置为0,则
 * <p>
 * k    0
 * <p>
 * k+1 1
 * <p>
 * ...
 * <p>
 * 0 n-k
 * <p>
 * 1 n-k+1
 * <p>
 * 假设已经求得了n-1个人情况下的最终胜利者保存在f[n-1]中，则毫无疑问，该胜利者还原到原来的真正编号即为 (f[n-1]+k)%n （因为第二轮重新编号的时候，相当于把每个人的编号都减了k，因此重新+k即可恢复到原来编号）。由此，我们可以想象，当最终只剩下一个人的时候，该人即为胜利者，此时重新编号，因为只有一个人，所以此时f[1]=0
 * <p>
 * 这样f[2]=(f[1]+k)%2,这样就可以求出最终胜利者在2个人的时候的情况下的编号，由递推公式f[n]=(f[n-1]+k)%n,可递推到最初编号序列中该胜利者的编号。
 * <p>
 * 因此用这个方法，只需一遍On的扫描，即可求出最终答案
 */

public class Q3_6 {
    /**
     * 编号从1开始
     */
    public static void simulation(int n, int m) {
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            queue.offer(i);
        }
        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m + 1; i++) {
                int element = queue.poll();
                if (i == m + 1) {
                    System.out.println("remove: " + element);
                } else {
                    queue.offer(element);
                }
            }
        }
    }

    public static class Node<AnyType> {
        public AnyType element;
        public Node<AnyType> next;
    }

    public static void simulationLinkedList(int n, int m) {
        Node<Integer> first = null, current = null;
        for (int i = 1; i <= n; i++) {
            if (first == null) {
                current = first = new Node<>();
                first.element = i;
            } else {
                current.next = new Node<>();
                current.next.element = i;
                current = current.next;
            }
        }
        if (current != null) {
            current.next = first;//first的作用只是让单链表形成循环链表
            while (current.next != current) {
                for (int i = 0; i < m; i++) {
                    current = current.next;//执行m-1次，current是要移除的元素的前一个元素，因为在单链表中，移除下一个元素更方便。
                }
                System.out.println("remove: " + current.next.element);
                current.next = current.next.next;//移除current的下一个元素
            }
            System.out.println("survivors: " + current.element);
        }

    }

    /**
     * F(1) = 0;
     * k = m + 1;m位上的元素被移除，下一次移除操作从m+1位开始
     * F(n) = (F(n-1) + k) % n
     * 编号从0开始
     */
    public static int winnerRecursion(int n, int m) {
        if (n == 1) return 0;
        return (winner(n - 1, m) + (m + 1)) % n;
    }

    /**
     * F(1) = 0;
     * k = m + 1;m位上的元素被移除，下一次移除操作从m+1位开始
     * F(n) = (F(n-1) + k) % n
     * 编号从0开始
     */
    public static int winner(int n, int m) {
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = (result + (m + 1)) % i;
        }
        return result;
    }
}
