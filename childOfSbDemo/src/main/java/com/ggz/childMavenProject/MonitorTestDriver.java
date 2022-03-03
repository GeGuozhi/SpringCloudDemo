package com.ggz.childMavenProject;

import com.fasterxml.jackson.databind.util.LinkedNode;
import com.ggz.algorithm.Algorithm;
import com.ggz.entity.Employee;
import com.ggz.entity.Flag;
import com.ggz.proxy.CglibProxy;
import com.ggz.proxy.LogHandler;
import com.ggz.service.UserService;
import com.ggz.service.UserService2;
import com.ggz.service.impl.UserServiceImpl;
import com.ggz.service.impl.UserServiceImpl3;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import lombok.Value;
import org.junit.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.awt.image.ImageWatched;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MonitorTestDriver {

    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException, ExecutionException, CloneNotSupportedException, IOException, ClassNotFoundException, ParseException {
        MonitorTestDriver monitor = new MonitorTestDriver();

//        Class c1 = Class.forName("com.ggz.entity.Employee");
//        Class c2 = Employee.class;
//        Employee employee = new Employee("xiaoming","12","rap",12,"hh",2000);
//        Class c3 = employee.getClass();
//        if(c1 == c2 && c2 == c3){
//            System.out.println("success");
//            System.out.println(c1);
//        }
//
//
//        Class person = Person.class;
//
//        try {
//            Constructor constructor = person.getConstructor(String.class,String.class,String.class);
//            try {
//                Person person1 = (Person) constructor.newInstance("aa","bb","cc");
//                System.out.println(person1.name);
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        String a = "38394789123748927389479237498";
//        String b = "28394789123748927389479237498";
//        System.out.println(new Algorithm().subtract(a,b));
//        BigInteger a1 = new BigInteger("38394789123748927389479237498");
//        BigInteger b1 = new BigInteger("28394789123748927389479237498");
//        System.out.println(a1.subtract(b1));
//
//
//        System.out.println(test("abcd"));
//        System.out.println("abc".substring(2,3));
//        System.out.println(7/2);

        TxThread_TestLock tx = new TxThread_TestLock();
        TxThread txThread = new TxThread();

        for (int i = 0; i < 5; i++) {
//            executorService.execute(tx);
        }

//        Thread thread = new Thread(()-> {
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

//        executorService.execute(thread);

//        thread.join();
//        System.out.println("主线程退出");
//        executorService.shutdown();
//
//        List list = Arrays.asList("a","b","c");
//        list.stream().filter(element -> element.equals("a")).forEach(element -> System.out.println(element));

//        new Algorithm().testStream();
        System.out.println(Flag.F.name());
        System.out.println(Flag.F.getA());
        System.out.println(Flag.F.getB());
        System.out.println(Flag.F.getC());
        Flag.F.setA("shibai");
        System.out.println(Flag.F.name());

    }

    /**
     * 计时器
     */
    public void timer() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println(stopwatch.elapsed(TimeUnit.MINUTES));
        }
    }


    /**
     * 寻找从根节点到叶子节点的路径之和等于target的路径并输出。
     * @param root
     * @param target
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> ret = new ArrayList<>();
        FindPathMain(root,result,ret,target);
        return result;
    }

    public ArrayList<ArrayList<Integer>> FindPathMain(TreeNode root,ArrayList<ArrayList<Integer>> result,ArrayList<Integer> ret,int target) {
        return null;
    }

    /**
     *找出众数，超过一般的数字
     * @param array
     * @return
     * 1，1，2，2，2
     */
    public int MoreThanHalfNum_Solution(int [] array) {
        int result = array[0];
        int count = 1;
        for(int i = 1 ; i<array.length ; i++){
            if(count < 1){
                result = array[i];
                count++;
            }else{
                if(array[i] == result){
                    count++;
                }else{
                    count--;
                }
            }
        }
        count = 0;
        for(int i = 0 ; i<array.length ; i++){
            if(array[i] == result){
                count++;
            }
            if(count > array.length/2){
                return result;
            }
        }
        return 0;
    }

    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
     * 如果是则返回true,否则返回false。假设输入的数组的任意两个数字都互不相同。
     * （ps：我们约定空树不是二叉搜素树）
     * @param sequence
     * @return
     */
    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence == null || sequence.length == 0){
            return false;
        }
        return isBST(sequence,0,sequence.length-1);
    }

    public  boolean isBST(int[] sequence,int start,int end){
        if((end-start)<=1){
            return true;
        }
        int val = sequence[end];
        int j = 0;
        for (int i = start; i < sequence.length; i++) {
            if(sequence[i]>=val){
                j = i;
                break;
            }
        }
        for (int i = j; i < end; i++) {
            if(sequence[i]<val){
                return false;
            }
        }
        return isBST(sequence,start,j-1)&&isBST(sequence,j,end-1);
    }

    /**
     * 深度优先遍历,使用队列先进先出，循环输出队列中的val
     * @param root
     *
     * Queue<TreeNode> queue = new LinkedList<>();
     *         queue.add(root);
     *         // 先进先出
     *         while (!queue.isEmpty()) {
     *             TreeNode tempTreeNode = queue.remove();
     *             System.out.println(tempTreeNode.val);
     *             if (tempTreeNode.left != null)
     *                 queue.add(tempTreeNode.left);
     *             if (tempTreeNode.right != null)
     *                 queue.add(tempTreeNode.right);
     *         }
     *
     */
    public static void printBFS(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        // 先进先出
        while (!queue.isEmpty()) {
            TreeNode tempTreeNode = queue.remove();
            System.out.println(tempTreeNode.val);
            if (tempTreeNode.left != null)
                queue.add(tempTreeNode.left);
            if (tempTreeNode.right != null)
                queue.add(tempTreeNode.right);
        }
    }

    /**
     * pop数组是否是push数组的弹出顺序。
     * @param pushA
     * @param popA
     * @return
     * int[] a = {1,2,3,4,5};
     * int[] b = {4,5,3,2,1};
     */
    public static boolean IsPopOrder(int [] pushA,int [] popA) {
        Stack<Integer> stack = new Stack<Integer>();
        int j = 0;
        for (int i = 0; i < pushA.length; i++) {
            stack.push(pushA[i]);
            while(j <= i && stack.peek() == popA[j]){
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }

    /**
     * n长度的数字在1-(n-1)之间，找出重复的其中一个数字
     * 根据数组长度重构一个空数组，每次循环给对应数字的下标的数组+1
     * 如果>1,则返回，因为这个下标被增加，代表再次遍历到了这个数字
     * @param numbers
     * @return
     */
    public static int duplicate(int[] numbers) {
        int[] res = new int[numbers.length];
        for(int i = 0; i < numbers.length; i++){
            res[numbers[i]]++;
            if(res[numbers[i]]==2){
                return numbers[i];
            }
        }
        return -1;
    }



    /**
     * 不用乘除，算1+2+...+n的结果
     * @param n
     * @return
     */
    public int Sum_Solution(int n) {
        int sum = n;
        boolean ans = (n>0) && ((sum = sum + Sum_Solution(n-1)) == 5);
        return sum;
    }

    /**
     * 定义一个新栈，设计一个可以获得min元素的时间复杂度为O(1)的方法
     * 采用空间换取时间的方式。
     *
     *         newStack a = new newStack();
     *         a.push(3);
     *         System.out.println(a.min());
     *         a.push(4);
     *         System.out.println(a.min());
     *         a.push(2);
     *         System.out.println(a.min());
     *         a.pop();
     *         System.out.println(a.min());
     *         a.pop();
     *         System.out.println(a.min());
     *         a.pop();
     *         System.out.println(a.min());
     *         a.push(0);
     *         System.out.println(a.min());
     *
     */
    public static class newStack{
        Stack<Integer> stack = new Stack<Integer>();
        Stack<Integer> minStack = new Stack<Integer>();

        //压入栈,如果最小值栈的顶元素大于等于node，则都压入node，则minStack是个单调不递增的栈
        public void push(int node) {
            if(minStack.size()==0 || minStack.peek()>=node){
                minStack.push(node);
            }
            stack.push(node);
        }
        //取栈顶元素,并弹出
        //如果minStack栈顶存储的等于stack的顶元素，则一起 弹出去。
        public void pop() {
            if(stack.peek()==minStack.peek()){
                minStack.pop();
            }
            stack.pop();
        }
        //取栈顶元素
        public int top() {
            return stack.peek();
        }

        public int min() {
            return minStack.peek();
        }
    }

    /**
     * 顺时针打印，m*n的矩阵
     * @param matrix
     * @return
     */
    public static ArrayList<Integer> printMatrix(int [][] matrix) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        int y = matrix.length-1;
        int x = matrix[0].length-1;
        int min = Math.min(matrix.length,matrix[0].length);
        for(int i = 0;i<=(min/2 + min%2 -1);i++){
            printCircle(i,i,x-i,y-i,matrix,ret);
        }
        System.out.println(ret.toString());
        return ret;
    }

    public static void printCircle(int lx,int ly,int rx,int ry,int[][] array,ArrayList<Integer> ret) {
        for (int i = ly; i <= rx; i++) {
            ret.add(array[lx][i]);
        }
        //当y轴，距离小于等于0，没有第二步
        if ((ry - ly) > 0) {
            for (int i = lx + 1; i <= ry; i++) {
                ret.add(array[i][rx]);
            }
        }

        //当x轴，距离小于等于0，则没有第三步
        if((rx - lx) > 0 && (ry - ly)> 0){
            for (int i = rx - 1; i >= lx; i--) {
                ret.add(array[ry][i]);
            }
        }

        //当x轴距离大于1，才有第四步
        if ((ry - ly) > 1 && (rx - lx) > 0) {
            //(2,0),(1,0)
            for (int i = ry - 1; i >= lx + 1; i--) {
                ret.add(array[i][lx]);
            }
        }
    }
    /**
     * 树节点
     */
    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val){
            this.val = val;
        }
    }

    /**
     * 镜像输入的二叉树
     * @param pRoot
     * @return
     */
    public TreeNode Mirror (TreeNode pRoot) {
        // write code here;
        return null;
    }

    /**
     * 中序便利一个树,看不懂，太抽象了，直接递归多简单。
     * @param tree
     */
    public static void inOrderTraversal(TreeNode tree) {
        Stack<TreeNode> stack = new Stack<>();
        while (tree != null || !stack.isEmpty()) {
            while (tree != null) {
                stack.push(tree);
                tree = tree.left;
            }
            if (!stack.isEmpty()) {
                tree = stack.pop();
                System.out.println(tree.val);
                tree = tree.right;
            }
        }

    }

    /**
     * 判断root2是不是root1的子树，null不属于任何树的子树
     * （ps：我们约定空树不是任意一个树的子结构）
     * @param root1
     * @param root2
     * @return
     */
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        //root2 为空，题设（必须返回false)
        //root1 为空，说明root2还有没判断的节点但是root1节点为空，则肯定结构不同。
         if ( root2 == null || root1 == null){
            return false;
        }
        return doesTree1HasTree2(root1,root2)||HasSubtree(root1.left,root2)||HasSubtree(root1.right,root2);
    }



    /**
     * 传入两棵根节点值相同的树，判断tree1是否和tree2结构一样
     * @param tree1
     * @param tree2
     * @return
     */
    public boolean doesTree1HasTree2(TreeNode tree1, TreeNode tree2){
        //tree2 == null 说明 tree2的叶子节点，已经被判断过了相等，这个时候已经满足了tree1.val = tree2.val 的条件。
        if (tree2 == null) {
            return true;
        }
        //tree1等于空，说明，tree2的叶子节点还不为空，但是tree1为空，这个时候tree2和tree1的结构肯定不同
        if (tree1 == null) {
            return false;
        }

        if ( tree1.val != tree2.val ){
            return false;
        }
        //对tree1和tree2的左树，右树进行递归校验。
        return doesTree1HasTree2(tree1.left, tree2.left) && doesTree1HasTree2(tree1.right, tree2.right);
    }

    /**
     * 返回树的前序数组
     * @return
     */
    public String preTree(TreeNode root){
        StringBuffer str = new StringBuffer();
        if (root != null) {
            return str.append(root.val).append(preTree(root.left)).append(preTree(root.right)).toString();
        }else{
            return "";
        }

    }

    /**
     *pre = [1,2,3,4,5,6,7]
     * in = [3,2,4,1,6,5,7]
     * @param pre 前序数组
     * @param in  中序数组
     * @return 重建后的二叉树根节点
     */
    public TreeNode reConstructBinaryTree(int[] pre,int[] in){
        if(pre.length == 0 || in.length == 0){
            return null;
        }
        TreeNode root = new TreeNode(pre[0]);
        for(int i = 0 ; i < pre.length ; i++){
            if(pre[0] == in[i]){
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre,1,i+1),Arrays.copyOfRange(in,0,i));
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre,i+1,pre.length),Arrays.copyOfRange(in,i+1,pre.length));
                break;
            }

        }
        return root;

    }

    /**
     * {1,2,3,4},0,2
     *
     * @param a
     * @param beg
     * @param end
     * @return
     */
    public int[] copyOfRange(int[] a,int beg,int end){
        int[] b = new int[end-beg];
        for (int i = beg; i < end; i++) {
            b[i-beg] = a[i];
        }
        return b;
    }

    /**
     * 求，插入一个空，奇数在前，偶数在后的数组。
     * @param array
     * @return
     * 1,2,3,4,5,6
     * 1,3,2,4,5,6
     * 1,3,2,4,
     * 遇到奇数，
     */
    public static int[] reOrderArray (int[] array) {
        LinkedList a = new LinkedList();
        LinkedList b = new LinkedList();
        for (int i = 0; i < array.length; i++) {
            if(array[i]%2 == 0){
                a.add(array[i]);
            }else{
                b.add(array[i]);
            }
        }
        int[] arrays = new int[array.length];
        for (int i = 0; i < (b.size()); i++) {
            arrays[i] = (int) b.get(i);
        }
        // 4 7 i=4,
        for (int i = 0; i < (a.size()); i++) {
            arrays[i+b.size()] = (int) a.get(i);
        }
        return arrays;
    }

    /**
     * 求二进制正数1的个数，使用n&n-1 消除最后面的1的原理，求。
     * @param n
     * @return
     */
    public static int NumberOf1(int n) {
        int count_1 = 0;
        while(n != 0){
            n = n & (n-1);
            count_1++;
        }
        return count_1;
    }


    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 合并两个排序的链表
     *
     * @param list1
     * @param list2
     * @return
     */
    public static ListNode Merge(ListNode list1, ListNode list2) {
        ListNode newList = new ListNode(-1);
        ListNode temp = newList;
        while(list1 != null && list2 != null){
            if(list1.val>list2.val){
                temp.next = list2;
                list2 = list2.next;
            }else{
                temp.next = list1;
                list1 = list1.next;
            }
            temp = temp.next;
        }
        if(list1 == null){
            temp.next = list2;
        }else{
            temp.next = list1;
        }
        return newList.next;
    }

    /**
     * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ListNode temp = listNode;
        ArrayList<Integer> a = new ArrayList<>();
        while(temp != null){
            a.add(0,temp.val);
            temp = temp.next;
        }
        return a;
    }

    /**
     * 输入一个链表和k，输出倒数第k个节点。
     * @param pHead
     * @param k
     * @return
     * a-b-c-d-e-f-g, k = 2，倒数k个 == 正数n-k+1个
     * 当快指针到末尾，slow刚好到倒数k个
     * 双指针，当
     *
     * 我的方法，求出长度，然后换算成正确的index
     */
    public ListNode FindKthToTail (ListNode pHead, int k) {
        if(k == 0){
            return null;
        }
        ListNode kNode = new ListNode(0);
        ListNode temp = pHead;
        int size = 0;
        while(temp != null){
            temp = temp.next;
            size++;
        }
        if(size<k){
            return null;
        }
        // abcde 5 real=1;
        int index = 0;
        int realIndex = size-k+1;
        while(realIndex != index && pHead != null){
            index++;
            kNode = pHead;
            pHead = pHead.next;
        }
        return kNode;

    }

    /**
     * 反转链表 并输出头节点 a->b->c
     * return c->b->a
     * @param head
     * @return
     */
    public ListNode ReverseList(ListNode head) {
        ListNode newHead = null;
        while(head != null){
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }
        return newHead;
    }

    /**
     * 实现replaceAll方法
     * abc def
     * @param s
     * @return
     */
    public static String replaceSpace (String s) {
        String str = s;
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' '){
                s = s.substring(0,i) + "%20" + s.substring(i+i);
//                s.replace()
            }
        }
        return s;
    }

    /**
     * 二位有序数组查看是否含有某数
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }


    static class TxThread extends Thread{
        @Override
        public void run() {

        }
    }

    static class TxThread_TestLock implements Runnable {
        int num = 1000;
        String str = new String();
        private Lock lock = new ReentrantLock();
        @Override
        public void run() {
            while (num > 0) {
                try {
                    lock.lock();
                    int a = num--;
                    System.out.println("当前线程名：" + Thread.currentThread().getName() + "," + a);
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    static class testEntity{
        private int a = 0;
        public  synchronized void test1(){
            for (int i = 0; i < 10000; i++) {
                System.out.println(Thread.currentThread().getName()+","+a++);
            }
        }

        public  void test2(){
            synchronized (this){
                for (int i = 0; i < 10000; i++) {
                    System.out.println(Thread.currentThread().getName()+","+a++);
                }
            }
        }
    }


    /**
     * abcba
     * 4/2 = 2
     * 5/2 =2.5
     * @param s
     * @return
     */
    public static boolean test(String s) {
        for (int i = 0; i < s.length()/2; i++) {
            if (s.charAt(i) != s.charAt(s.length()-1-i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 中心扩散法求最大回文数
     * @param s
     * @return
     */
    public static String palindrome(String s){
        int maxH = 1;
        String maxString = s.substring(0,1);//最大回文串保存
        for(int i = 1 ; i<s.length()-1  ; i++){
            String judgeString;
            if(i<=s.length()/2){
                judgeString = s.substring(0,2*i+1);
            }else{
                //abced i=a[3]=e (index+1)-(length-index)
                judgeString = s.substring(i+1-(s.length()-i));
            }
            String maxString1 = judgeString.substring(0,1);//该循环中的最大回文
            int maxL = 1;//该循环中的最大回文长度
            for(int j = 0 ; j <= judgeString.length()/2 ; j++){
                if(judgeString.charAt(judgeString.length()/2-j)!=judgeString.charAt(judgeString.length()/2+j)){
                    break;
                }else{
                    maxString1 = judgeString.substring(judgeString.length()/2-j,judgeString.length()/2+j+1);//abcde,j=1,substring(1,4)
                    maxL = maxString1.length();
                    if(maxL > maxH){
                        maxString = maxString1;
                    }
                }
            }
        }

        return maxString;
    }
}
