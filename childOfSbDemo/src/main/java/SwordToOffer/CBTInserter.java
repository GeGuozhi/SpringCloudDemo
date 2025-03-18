package SwordToOffer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 设计一个用完全二叉树初始化的数据结构CBTInserter，
 * 它支持以下几种操作：
 * CBTInserter(TreeNode root)使用根节点为root的给定树初始化该数据结构；
 * CBTInserter.insert(int v) 向树中插入一个新节点，节点类型为 TreeNode，值为 v 。
 * 使树保持完全二叉树的状态，并返回插入的新节点的父节点的值；
 * CBTInserter.get_root() 将返回树的根节点。
 *
 * @author ggz on 2022/10/9
 */
public class CBTInserter {
    public Queue<TreeNode> notFullAndleaf = new LinkedList<>();
    public TreeNode root;

    public CBTInserter(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        this.root = root;
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode newNode = queue.poll();
            if (newNode.left == null || newNode.right == null) {
                notFullAndleaf.add(newNode);
            }
            if (newNode.left != null) {
                queue.offer(newNode.left);
            }
            if (newNode.right != null) {
                queue.offer(newNode.right);
            }
        }
    }

    public int insert(int v) {
        TreeNode node = new TreeNode(v);
        TreeNode firstNotFull = notFullAndleaf.peek();
        if (firstNotFull.left == null) {
            firstNotFull.left = node;
        } else {
            firstNotFull.right = node;
            notFullAndleaf.poll();
        }
        notFullAndleaf.offer(node);
        return firstNotFull.val;
    }

    public TreeNode get_root() {
        return root;
    }
}