package com.jaydenho.androidtech.algorithm.collections.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Created by Administrator on 2017/9/10.
 */

public class MyLinkedList<AnyType>
        implements Iterable<AnyType> {

    private static final class Node<AnyType> {
        private Node<AnyType> pre;
        private Node<AnyType> next;
        private AnyType element;

        public Node(AnyType element, Node<AnyType> pre, Node<AnyType> next) {
            this.element = element;
            this.pre = pre;
            this.next = next;
        }
    }

    private Node<AnyType> head;
    private Node<AnyType> tail;
    private int size = 0;
    private int modCount = 0;
    private LinkedList a;

    public MyLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, null, null);
        head.next = tail;
        tail.pre = head;
    }

    public void add(AnyType element) {
        add(size, element);
    }

    public void add(int i, AnyType element) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<AnyType> anchor = getNode(i);
        addBefore(element, anchor);
    }

    private void addBefore(AnyType element, Node<AnyType> anchor) {
        Node<AnyType> node = new Node<>(element, anchor.pre, anchor);
        node.next.pre = node;
        node.pre.next = node;
        size++;
        modCount++;
    }

    private void addFirstImpl(AnyType element) {
        Node<AnyType> next = head.next;
        addBefore(element, next);
    }

    private void addLastImpl(AnyType element) {
        addBefore(element, tail);
    }

    public void set(int i, AnyType element) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<AnyType> node = getNode(i);
        node.element = element;
    }

    public AnyType get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<AnyType> node = getNode(i);
        return node.element;
    }

    public boolean remove(AnyType element) {
        ListIterator<AnyType> iterator = new LinkIterator<>(this);
        while (iterator.hasNext()) {
            AnyType p = iterator.next();
            if (element == null ? p == null : element.equals(p)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private Node<AnyType> getNode(AnyType element) {
        Node<AnyType> p = head;
        for (int i = 0; i < size; i++) {
            p = p.next;
            if (element == null ? p.element == null : element.equals(p.element)) {
                return p;
            }
        }
        return null;
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

    public void offer(AnyType element) {
        addLastImpl(element);
    }

    public AnyType peek() {
        return isEmpty() ? null : head.next.element;
    }

    public AnyType poll() {
        if (isEmpty()) {
            return null;
        } else {
            Node<AnyType> p = head.next;
            head.next = p.next;
            head.next.pre = head;
            size--;
            modCount++;
            return p.element;
        }
    }

    @Override
    public Iterator<AnyType> iterator() {
        return null;
    }


    /**
     * 高层不按要求错误的调用方法时要抛出合适的异常
     */
    private static final class LinkIterator<AnyType> implements ListIterator<AnyType> {
        private int pos, modCount = 0;
        private MyLinkedList<AnyType> linkedList = null;
        /**
         * 只用于遍历链表
         */
        private Node<AnyType> node = null;
        /**
         * 用于执行修改操作；
         * 执行了一次增删操作后要置空，避免高层模块胡乱操作；
         */
        private Node<AnyType> modNode = null;

        public LinkIterator(MyLinkedList<AnyType> linkedList) {
            this.linkedList = linkedList;
            this.modCount = linkedList.modCount;
            this.pos = -1;
            this.modNode = null;
            this.node = linkedList.head;
        }

        @Override
        public void add(AnyType object) {
            //在当前元素后增加一个元素
            checkModCount();
            if (modNode == null) throw new IllegalStateException();
            Node<AnyType> next = modNode.next;
            Node<AnyType> p = new Node<>(object, modNode, next);
            next.pre = p;
            modNode.next = p;
            linkedList.size++;
            linkedList.modCount++;
            node = p;
            modNode = null;
            pos++;
            modCount++;
        }

        @Override
        public boolean hasNext() {
            return node != linkedList.tail && node.next != linkedList.tail;
        }

        @Override
        public boolean hasPrevious() {
            return node != linkedList.head && node.pre != linkedList.head;
        }

        @Override
        public AnyType next() {
            checkModCount();
            if (!hasNext()) throw new NoSuchElementException();
            node = modNode = node.next;
            pos++;
            return node.element;
        }

        @Override
        public int nextIndex() {
            return pos + 1;
        }

        @Override
        public AnyType previous() {
            checkModCount();
            if (!hasPrevious()) throw new NoSuchElementException();
            node = modNode = node.pre;
            return node.element;
        }

        private void checkModCount() {
            if (modCount != linkedList.modCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public int previousIndex() {
            return pos - 1;
        }

        @Override
        public void remove() {
            checkModCount();
            if (modNode == null) throw new IllegalStateException();
            Node<AnyType> pre = modNode.pre;
            Node<AnyType> next = modNode.next;
            pre.next = next;
            next.pre = pre;
            linkedList.size--;
            pos--;
            node = pre;
            //用于删除操作，删除该node后，该节点置空，避免被连续调用删除操作。
            modNode = null;
            linkedList.modCount++;
            modCount++;
        }

        @Override
        public void set(AnyType object) {
            checkModCount();
            if (modNode == null) throw new IllegalStateException();
            modNode.element = object;
        }
    }
}
