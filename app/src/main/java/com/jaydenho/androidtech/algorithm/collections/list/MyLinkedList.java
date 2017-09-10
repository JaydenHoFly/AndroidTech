package com.jaydenho.androidtech.algorithm.collections.list;

import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/9/10.
 */

public class MyLinkedList<AnyType>
        implements Iterable<AnyType> {

    private static class Node<AnyType> {
        private Node<AnyType> pre;
        private Node<AnyType> next;
        private AnyType element;

        public Node() {
        }

        public Node(Node<AnyType> pre, Node<AnyType> next, AnyType element) {
            this.pre = pre;
            this.next = next;
            this.element = element;
        }
    }

    private Node<AnyType> head;
    private Node<AnyType> tail;
    private int size = 0;
    private int modCount = 0;
    private LinkedList a;

    public MyLinkedList() {
        head = new Node<>();
        tail = new Node<>();
        head.next = tail;
        tail.pre = head;
    }

    public void add(int i, AnyType element) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<AnyType> anchor = getNode(i);

    }

    private void addBefore(AnyType element, Node<AnyType> anchor) {

    }

    private Node<AnyType> getNode(int i) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<AnyType> p;
        if (i > size / 2) {
            //从后往前找
            p = tail;
            for (int j = size - 1; j >= i; j--) {
                p = p.pre;
            }
        } else {
            p = head.next;
            for (int j = 0; j < i; j++) {
                p = p.next;
            }
        }
        return p;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<AnyType> iterator() {
        return null;
    }
}
