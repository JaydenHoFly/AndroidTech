package com.jaydenho.androidtech.algorithm.collections.tree;

/**
 * Created by Administrator on 2017/9/3.
 */

public class TreeSet<AnyType extends Comparable<? super AnyType>> {
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
    }

    private Node<AnyType> root;

    public void insert(AnyType element) {
        insert(element, root, null);
    }

    public void remove(AnyType element) {

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
}
