package com.jaydenho.androidtech.algorithm.others;

import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

/**
 * Created by hedazhao on 2021/5/29.
 */
public class Practice {
    public static void main(String[] args) {
//        lengthOfLongestSubstring("abcabc");
        threeSum(new int[]{-1, 0, 1, 2, -1, -4});
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // 两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode first = null, pre = null;

        int temp = 0;

        for (; ; ) {
            ListNode node = new ListNode();

            int sum = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + temp;

            node.val = sum % 10;

            temp = sum / 10;

            l1 = (l1 != null ? l1.next : null);
            l2 = (l2 != null ? l2.next : null);

            if (first == null) {
                first = node;
            } else {
                pre.next = node;
            }
            pre = node;

            if (l1 == null && l2 == null && temp == 0) {
                break;
            }

        }

        return first;
    }

    // 接雨水
    public int trap(int[] height) {
        if (height.length == 0) {
            return 0;
        }

        // 计算左右侧最高点。
        int[] maxHeightLeft = new int[height.length];
        int[] maxHeightRight = new int[height.length];

        maxHeightLeft[0] = height[0];
        for (int i = 1; i < height.length; i++) {
            maxHeightLeft[i] = Math.max(maxHeightLeft[i - 1], height[i - 1]);
        }

        System.out.println("maxHeightLeft=" + Arrays.toString(maxHeightLeft));

        maxHeightRight[height.length - 1] = height[height.length - 1];
        for (int j = height.length - 2; j >= 0; j--) {
            maxHeightRight[j] = Math.max(maxHeightRight[j + 1], height[j + 1]);
        }

        int sum = 0;
        for (int z = 0; z < height.length; z++) {
            int cur = Math.max(Math.min(maxHeightLeft[z], maxHeightRight[z]) - height[z], 0);
            sum += cur;
        }
        return sum;
    }

    // 最长回文子串
    public String longestPalindrome(String s) {
        char[] ss = s.toCharArray();
        int n = ss.length;

        int start = 0, end = 0;

        boolean[][] records = new boolean[n][n];

        for (int L = 1; L <= n; L++) {
            for (int i = 0; i < n; i++) {

                if (i + L - 1 >= n) {
                    break;
                }

                if (L == 1) {
                    records[i][i] = true;
                } else if (L == 2) {
                    records[i][i + 1] = ss[i] == ss[i + 1];
                } else {
                    records[i][i + L - 1] = records[i + 1][i + L - 2] && (ss[i] == ss[i + L - 1]);
                }

                if (records[i][i + L - 1] && L - 1 > end - start) {
                    start = i;
                    end = i + L - 1;
                }
            }
        }
        return s.substring(start, end + 1);
    }

    // 无重复字符的最长子串
//    输入: s = "abcabcbb"
//    输出: 3
//    解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
    public int lengthOfLongestSubstring(String s) {
        char[] ss = s.toCharArray();

        HashSet<Character> set = new HashSet<>();

        int rt = 0, max = 0;

        for (int i = 0; i < ss.length; i++) {
            if (i != 0) {
                set.remove(ss[i - 1]);
            }
            while (rt < ss.length && !set.contains(ss[rt])) {
                set.add(ss[rt++]);
            }
            max = Math.max(max, rt - i);
        }

        return max;
    }

    public LRUCache(int capacity) {

    }

    // 合并两个有序链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    }

    // 最大子序和
    public int maxSubArray(int[] nums) {

    }

    // 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
    }

    // 盛水最多的容器
    public int maxArea(int[] height) {
    }

    // 下一个排列
    public void nextPermutation(int[] nums) {
    }

    // 合并区间
    public int[][] merge(int[][] intervals) {
    }

    // 爬楼梯
    public int climbStairs(int n) {
    }

    // 岛屿数量
    public int numIslands(char[][] grid) {
    }

    // 输入：nums = [1,2,3]
    // 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
    // 全排列
    public List<List<Integer>> permute(int[] nums) {

    }

    public void backtrace() {

    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //    前序遍历 preorder = [3,9,20,15,7]
//    中序遍历 inorder = [9,3,15,20,7]
    // 前序后序还原二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
    }

    // 回文数
    public boolean isPalindrome(int x) {
    }

    // 螺旋矩阵
    public List<Integer> spiralOrder(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int total = rows * columns;

        List<Integer> res = new ArrayList<>(total);

        int row = 0, column = 0;
        boolean[][] records = new boolean[rows][columns];

        int direction = 0;

        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int i = 0; i < total; i++) {
            res.add(matrix[row][column]);
            records[row][column] = true;

            int nextRow = row + directions[direction][0];
            int nextColumn = column + directions[direction][1];
            if (nextRow < 0 || nextColumn < 0 || nextRow >= rows || nextColumn >= columns || records[nextRow][nextColumn]) {
                direction = (direction + 1) % 4;
            }

            row += directions[direction][0];
            column += directions[direction][1];
        }

        return res;
    }


    public int search(int[] nums, int target) {
        // 有序的数组中查找某个数，可以用二分法。
        return binarySearch(nums, target, 0, nums.length - 1);
    }

    public int binarySearch(int[] nums, int target, int left, int right) {
        if(left > right) {
            return -1;
        }

        int mid = (right - left) / 2 + left;

        System.out.println("left=" + left + ", mid" + mid + ", right=" + right);

        if(nums[mid] == target) {
            return mid;
        }

        if (isOrder(nums, left, mid)) {
            if (target >= nums[left] && target < nums[mid]) {
                return binarySearch(nums, target, left, mid);
            } else {
                return binarySearch(nums, target, mid + 1, right);
            }

        } else {
            if(target >= nums[mid + 1] && target <= nums[right]) {
                return binarySearch(nums, target, mid + 1, right);
            } else {
                return binarySearch(nums, target, left, mid);
            }

        }
    }

    public boolean isOrder(int[] nums, int left, int right) {
        return nums[right] >= nums[left];
    }

    // 重排链表
    public void reorderList(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();

        while (head != null) {
            nodes.add(head);
            head = head.next;
        }

        int lt = 0, rt = nodes.size() - 1;
        while (lt < rt) {
            nodes.get(lt).next = nodes.get(rt);
            lt++;
            if(lt == rt) {
                break;
            }
            nodes.get(rt).next = nodes.get(lt);
            rt--;
        }
        nodes.get(lt).next = null;

    }


    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 因为头节点有可能发生变化，使用虚拟头节点可以避免复杂的分类讨论
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        ListNode pre = dummyNode;
        // 第 1 步：从虚拟头节点走 left - 1 步，来到 left 节点的前一个节点
        // 建议写在 for 循环里，语义清晰
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        // 第 2 步：从 pre 再走 right - left + 1 步，来到 right 节点
        ListNode rightNode = pre;
        for (int i = 0; i < right - left + 1; i++) {
            rightNode = rightNode.next;
        }

        // 第 3 步：切断出一个子链表（截取链表）
        ListNode leftNode = pre.next;
        ListNode curr = rightNode.next;

        // 注意：切断链接
        pre.next = null;
        rightNode.next = null;

        // 第 4 步：同第 206 题，反转链表的子区间
        reverseLinkedList(leftNode);

        // 第 5 步：接回到原来的链表中
        pre.next = rightNode;
        leftNode.next = curr;
        return dummyNode.next;
    }

    作者：LeetCode-Solution
    链接：https://leetcode-cn.com/problems/reverse-linked-list-ii/solution/fan-zhuan-lian-biao-ii-by-leetcode-solut-teyq/
    来源：力扣（LeetCode）
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
