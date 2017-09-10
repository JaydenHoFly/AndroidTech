package com.jaydenho.androidtech.algorithm.collections.tree;

/**
 * Created by Administrator on 2017/9/10.
 */

public class AVLTreeSet<AnyType extends Comparable<AnyType>> {

    private static class Node<AnyType> {
        private AnyType element;
        private Node<AnyType> left;
        private Node<AnyType> right;
        private int height = 0;

        public Node(AnyType element, Node<AnyType> left, Node<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

    }

    private int modCount = 0;
    private Node<AnyType> root = null;

    private int height(Node<AnyType> node) {
        return node == null ? -1 : node.height;
    }

    private int compare(AnyType element1, AnyType element2) {
        return element1.compareTo(element2);
    }

    private Node<AnyType> insert(AnyType element, Node<AnyType> t) {
        if (t == null) {
            modCount++;
            return new Node<>(element, null, null);
        }
        int compare = element.compareTo(t.element);
        if (compare < 0) {
            t.left = insert(element, t.left);
            if (height(t.left) - height(t.right) == 2) {
                //在左儿子为根的子树上插入新节点后，左儿子高度比右儿子多2
                int compare2 = compare(element, t.left.element);
                if (compare2 < 0) {
                    //case1：往左儿子的左子树添加了节点，单旋；
                    t = rotateWithLeftChild(t);
                } else if (compare2 > 0) {
                    //case2：往左儿子的右子树添加了节点，双旋；
                    t = doubleRotateWithLeftChild(t);
                }
            }
        } else if (compare > 0) {
            t.right = insert(element, t.right);
            if (height(t.right) - height(t.left) == 2) {
                //在右儿子为根的子树上插入新节点后，右儿子高度比左儿子多2
                int compare2 = compare(element, t.right.element);
                if (compare2 < 0) {
                    //case3：往右儿子的左子树添加了节点，双旋；
                    t = doubleRotateWithRightChild(t);
                } else if (compare2 > 0) {
                    //case4：往右儿子的右子树添加了节点，单旋；
                    t = rotateWithRightChild(t);
                }
            }
        } else {
            //do nothing
        }
        calcHeight(t);
        return t;
    }

    /**
     * from：数据结构与算法分析 page:100
     */
    private Node<AnyType> rotateWithLeftChild(Node<AnyType> k2) {
        Node<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        //k2是k1的右儿子，先计算k2的高度
        calcHeight(k2);
        calcHeight(k1);
        return k1;
    }

    private Node<AnyType> rotateWithRightChild(Node<AnyType> k2) {
        Node<AnyType> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        calcHeight(k2);
        calcHeight(k1);
        return k1;
    }

    private Node<AnyType> doubleRotateWithLeftChild(Node<AnyType> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private Node<AnyType> doubleRotateWithRightChild(Node<AnyType> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    private void calcHeight(Node<AnyType> t) {
        t.height = Math.max(height(t.left), height(t.right)) + 1;
    }
}
