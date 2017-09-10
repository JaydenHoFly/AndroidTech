package com.jaydenho.androidtech.algorithm.collections.tree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Created by hedazhao on 2017/9/3.
 * 二叉查找树实现的TreeSet
 */

public class SimpleTreeSet<AnyType extends Comparable<? super AnyType>>
    implements Iterable<AnyType> {
    private static class Node<AnyType> {
        private AnyType element;
        private Node left;
        private Node right;
        private Node parent;

        public Node(AnyType element, Node left, Node right, Node parent) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    ", left=" + left +
                    ", right=" + right +
                    ", parent=" + parent +
                    '}';
        }

        public String toShortString() {
            return "Node.element=" + element;
        }
    }

    private Node<AnyType> root;

    private int modCount = 0;

    public void insert(AnyType element) {
        root = insert(element, root, null);
    }

    public void remove(AnyType element) {
        root = remove(element, root);
    }

    private Node<AnyType> remove(AnyType element, Node<AnyType> t) {
        if (t == null) {
            return null;
        }
        int compare = element.compareTo(t.element);
        if (compare < 0) {
            t.left = remove(element, t.left);
        } else if (compare > 0) {
            t.right = remove(element, t.right);
        } else {
            //remove t
            if (t.left != null && t.right != null) {
                //two children
                t.element = (AnyType) findMin(t.right).element;
                //remove t.newElement from the subTree which root is t.right.
                remove(t.element, t.right);
            } else {
                //one child or no child
                Node<AnyType> oneChild = (t.left != null) ? t.left : t.right;
                if (oneChild != null) {
                    oneChild.parent = t;
                }
                t = oneChild;
                modCount++;
            }
        }
        return t;
    }

    public boolean contains(AnyType element) {
        return contains(element, root);
    }

    private boolean isLeaf(Node<AnyType> node) {
        return node == null || (node.left == null && node.right == null);
    }

    private Node<AnyType> findMin(Node<AnyType> t) {
        if (t == null) {
            return null;
        }
        if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    private Node<AnyType> findMax(Node<AnyType> t) {
        if (t == null) {
            return null;
        }
        if (t.right == null) {
            return t;
        }
        return findMax(t.right);
    }

    private boolean contains(AnyType element, Node<AnyType> t) {
        if (t == null) {
            return false;
        }
        int compare = element.compareTo(t.element);
        if (compare < 0) {
            return contains(element, t.left);
        } else if (compare > 0) {
            return contains(element, t.right);
        } else {
            return true;
        }
    }

    /**
     * @param element 目标元素
     * @param t       要插入的子数的根节点
     * @param pt      子数根节点的父节点
     * @return 要插入的子数的根节点
     */
    private Node<AnyType> insert(AnyType element, Node<AnyType> t, Node<AnyType> pt) {
        if (t == null) {
            modCount++;
            t = new Node<AnyType>(element, null, null, pt);
        } else {
            int compare = element.compareTo(t.element);
            if (compare < 0) {
                //insert to left child
                t.left = insert(element, t.left, t);
            } else if (compare > 0) {
                t.right = insert(element, t.right, t);
            } else {
                //do nothing;
            }
        }
        return t;
    }

    public void printTree() {
        if (isEmpty()) {
            System.out.println("the tree is empty");
        }
        System.out.println("----print tree start----");
        printTree(root);
        System.out.println("----print tree end----");
    }

    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 先序遍历
     */
    private void printTree(Node<AnyType> t) {
        if (t == null) return;
        System.out.println(t.toShortString());
        printTree(t.left);
        printTree(t.right);
    }

    private Node<AnyType> findMin() {
        return findMin(root);
    }

    @Override
    public Iterator<AnyType> iterator() {
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<AnyType> {

        private Node<AnyType> nextItem = findMin();
        private Node<AnyType> currentItem;
        private boolean isEnd = isEmpty();
        private int expectModCount = modCount;

        @Override
        public boolean hasNext() {
            return !isEnd;
        }

        @Override
        public AnyType next() {
            if (expectModCount != modCount) throw new ConcurrentModificationException();
            if (!hasNext()) throw new UnsupportedOperationException();
            AnyType element = nextItem.element;
            currentItem = nextItem;

            //从最小的节点开始，采取中序遍历，左子树->父节点->右子树的最小节点；
            //如果当前的节点有右子树，那么下一个节点就用右子树的最小节点
            if (nextItem.right != null) {
                nextItem = findMin(nextItem.right);
            } else {
                //没有右子树，那么遍历父节点，如果父节点的右子树是当前节点，那么该父节点已经被遍历过，所以要找出左子树是当前节点的父节点；
                Node<AnyType> child = nextItem;
                nextItem = nextItem.parent;
                while (nextItem != null && nextItem.left != child) {
                    child = nextItem;
                    nextItem = nextItem.parent;
                }
                if (nextItem == null) {
                    isEnd = true;
                }
            }
            return element;
        }

        @Override
        public void remove() {
            if (expectModCount != modCount) throw new ConcurrentModificationException();
            SimpleTreeSet.this.remove(currentItem.element);
            expectModCount++;
        }
    }
}
