package SwordToOffer;

import java.math.BigDecimal;
import java.util.*;

/**
 * 尝试自己做一遍
 *
 * @author ggz on 2022/10/9
 */
public class LeetCode {

    TreeNode treeNode;

    public static void main(String[] args) {
        LeetCode leetCode = new LeetCode();
//        int[] preorder = new int[]{3, 9, 20, 15, 7};
//        int[] inorder = new int[]{9, 3, 15, 20, 7};
//        leetCode.buildTree(preorder, inorder);

//        TreeNode a1 = new TreeNode(1);
//        TreeNode b2 = new TreeNode(2);
//        TreeNode c3 = new TreeNode(3);
//        TreeNode d4 = new TreeNode(4);
//        TreeNode e5 = new TreeNode(5);
//        TreeNode f6 = new TreeNode(6);
//        TreeNode g7 = new TreeNode(7);
//
//        e5.left = c3;
//        e5.right = f6;
//        c3.left = b2;
//        c3.right = d4;
//        b2.left = a1;

//        a.left = b;
//        a.right = c;
//        b.left = d;
//        b.right = e;
//        c.left = f;
//        c.right = g;

//        List<Integer> bfs = leetCode.rightSideView_BFS(a);
//        List<Integer> dfs = leetCode.rightSideView_DFS(a);
//        int score = leetCode.scoreOfParentheses("(())");
//        List list = leetCode.levelOrder(a);
//        List list = leetCode.dfs(a);
//        TreeNode treeNode = leetCode.inorderSuccessor(e5, a1);

//        二叉树
//        Node aa = new Node(1);
//        Node bb = new Node(2);
//        Node cc = new Node(3);
//        Node dd = new Node(4);
//        Node ee = new Node(5);
//        Node ff = new Node(6);
//        Node gg = new Node(7);
//        dd.left = bb;
//        dd.right = ee;
//        bb.left = aa;
//        bb.right = cc;
//        Node temp = leetCode.treeToDoublyList(dd);

//        链表
//        ListNode l1 = new ListNode(3);
//        ListNode l2 = new ListNode(2);
//        ListNode l3 = new ListNode(0);
//        ListNode l4 = new ListNode(-4);
//        l1.next = l2;
//        l2.next = l3;
//        l3.next = l4;
//        l4.next = l2;
//        l2.next = l3;
//        l3.next = l4;
//        ListNode temp = leetCode.sortList(l1);

//        PriorityQueue<Integer> queue = new PriorityQueue<Integer>((o11, o22) -> o11.compareTo(o22));


//        String[] a = new String[]{"abbb", "cd"};
//        System.out.println(leetCode.maxProduct(a));
//        leetCode.threeSum(new int[]{2, -3, 0, -2, -5, -5, -4, 1, 2, -2, 2, 0, 2, -4});
//        System.out.println(leetCode.minSubArrayLen(15, new int[]{5, 1, 3, 5, 10, 7, 4, 9, 2, 8}));
//        System.out.println(leetCode.subarraySum(new int[]{1}, 0));
//        leetCode.NumMatrix(new int[][]{
//                {1,2,3,4},
//                {2,2,3,4},
//                {3,3,3,4},
//                {4,4,4,4}
//        });
//        System.out.println(leetCode.sumRegion(0,0,2,2));
//        System.out.println(leetCode.sumRegion(2,0,2,2));
//        System.out.println(leetCode.sumRegion(1,1,3,3));
//        System.out.println(leetCode.checkInclusion("adc", "dcda"));
//        System.out.println(leetCode.findAnagrams("baa", "aa"));
//        System.out.println(leetCode.lengthOfLongestSubstring("abcabcbb"));
//        System.out.println(leetCode.countSubstrings("aaa"));
//        leetCode.detectCycle(l1);
//        leetCode.reorderList(l1);
//        String[] a = new String[]{"nozzle","punjabi"};
//        List<List<String>> list = leetCode.groupAnagrams(a);
//        String[] s = new String[]{"a", ""};
//        leetCode.groupAnagrams(s);
//        leetCode.changeOfDoubleColor();

        //2000w


//        TreeNode a = new TreeNode(1);
//        TreeNode b = new TreeNode(22222);
//        TreeNode c = new TreeNode(22222);
//        TreeNode d = new TreeNode(333333333);
//        TreeNode e = new TreeNode(333333333);
//        TreeNode f = new TreeNode(4);
//        TreeNode g = new TreeNode(4);
//
//        a.left = b;
//        a.right = c;
//        c.left = d;
//        c.right = e;
//        e.left = f;
//        e.right = g;
//
//        leetCode.bfs(a);


        leetCode.findMinDifference(Arrays.asList("23:59","00:00"));

        List<String> a = Arrays.asList("BOND_A","BOND_T");
        int[] aa = new int[]{1,2};

//        leetCode.changeOfFlushFlow();

        System.out.println(" a ".trim());

        String str = "abc";
        System.out.println(str.replace("a","b"));
        System.out.println(str);
    }


    /**
     * 剑指 Offer II 035. 最小时间差
     * 给定一个 24 小时制（小时:分钟 "HH:MM"）的时间列表，找出列表中任意两个时间的最小时间差并以分钟数表示。
     */
    public int findMinDifference(List<String> timePoints) {
        int min = 720;
        int temp = 0;
        timePoints.sort((o1, o2) -> {
            return o1.compareTo(o2);
        });
        for (int i = 0; i < timePoints.size() - 1; i++) {
            temp = Math.abs((Integer.valueOf(timePoints.get(i).substring(0, 2)) - Integer.valueOf(timePoints.get(i + 1).substring(0, 2))) * 60 + (Integer.valueOf(timePoints.get(i).substring(3)) - Integer.valueOf(timePoints.get(i + 1).substring(3))));
            temp = Math.min(1440 - temp, temp);
            min = Math.min(temp, min);
        }

        temp = Math.abs((Integer.valueOf(timePoints.get(timePoints.size() - 1).substring(0, 2)) - Integer.valueOf(timePoints.get(0).substring(0, 2))) * 60 + (Integer.valueOf(timePoints.get(timePoints.size() - 1).substring(3)) - Integer.valueOf(timePoints.get(0).substring(3))));
        temp = Math.min(1440 - temp, temp);
        min = Math.min(temp, min);

        return min;
    }


    //             1
    //         2       2
    //               3   3
    //                  4 4
    /**
     * 结构化二叉树
     */
    static List<List<Integer>> lists_bfs = new LinkedList<>();
    int maxLength = 0;

    public void bfs(TreeNode node) {
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(node);
        int size = queue.size();
        while (size != 0) {
            List<Integer> list = new LinkedList<>();

            while (size != 0) {

                TreeNode temp = queue.poll();
                if (String.valueOf(temp.val).length() > maxLength) maxLength = String.valueOf(temp.val).length();
                list.add(temp.val);
                if (temp.left != null) {
                    queue.offer(temp.left);
                } else {
                    queue.offer(new TreeNode(0));
                }

                if (temp.right != null) {
                    queue.offer(temp.right);
                } else {
                    queue.offer(new TreeNode(0));
                }

                size--;
            }

            for (Integer i : list) {
                if (i != 0) {
                    size = queue.size();
                    lists_bfs.add(list);
                    break;
                }
            }

        }

        size = lists_bfs.size();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < maxLength * 2; i++) {
            sb.append(" ");
        }

        int length = (int) Math.pow(2, size);
        for (int i = 0; i < size; i++) {
            int[] temp = new int[length];
            for (int j = 0; j < lists_bfs.get(i).size(); j++) {
                temp[(int) (Math.pow(2, size - 1 - i) + Math.pow(2, size - i) * j)] = lists_bfs.get(i).get(j);
            }
            for (int j = 0; j < temp.length; j++) {
                if (temp[j] == 0) System.out.print(sb);
                else System.out.print(temp[j]);
            }

            System.out.println();

            //        System.out.println("┌");
            //        ┌┌ - ┴ - ┐");

            for (int j = 0; j < temp.length; j++) {

            }
        }
    }

    /**
     * 剑指 Offer II 033. 变位词组
     * 给定一个字符串数组 strs ，将 变位词 组合在一起。 可以按任意顺序返回结果列表。
     * <p>
     * 注意：若两个字符串中每个字符出现的次数都相同，则称它们互为变位词。
     * 存在 一个字符含有相同字符
     * [aa],[bc,cb],[abc,bac,cab]
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            int[] chars = new int[26];
            for (int j = 0; j < strs[i].length(); j++) {
                chars[strs[i].charAt(j) - 'a']++;
            }
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 26; j++) {
                if (chars[j] > 0) {
                    sb.append((char) ('a' + j)).append(chars[j]);
                }
            }
            String str = sb.toString();
            List<String> list = map.getOrDefault(str, new ArrayList<>());
            list.add(strs[i]);
            map.put(str, list);
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 剑指 Offer II 031. 最近最少使用缓存
     * 运用所掌握的数据结构，设计和实现一个 LRU (Least Recently Used，最近最少使用) 缓存机制 。
     * 每次使用，判断是否拥有key，拥有将key提到最前面，不拥有返回-1
     * put的时候先判断长度，超过推出最后的元素
     * 会有重复数据，重复直接替换
     */
    HashMap<Integer, Integer> map_JZ031 = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> map_JZ031_index = new HashMap<Integer, Integer>();
    ArrayList<Integer> list_JZ031 = new ArrayList<Integer>();
    int maxSize_JZ031 = 0;

    public void LRUCache(int capacity) {
        maxSize_JZ031 = capacity;
    }

    public int get(int key) {
        if (!map_JZ031.containsKey(key)) {
            return -1;
        } else {
            list_JZ031.remove(Integer.valueOf(key));
            list_JZ031.add(0, key);
            return map_JZ031.get(key);
        }
    }

    //get必须是hash
    //put必须是array

    public void put(int key, int value) {
        if (map_JZ031.containsKey(key)) {
            list_JZ031.remove(Integer.valueOf(key));
        }
        //超过最大值，删除队尾
        if (list_JZ031.size() == maxSize_JZ031) {
            int last = list_JZ031.remove(maxSize_JZ031 - 1);
            map_JZ031.remove(last);
        }
        //添加新数据到队首
        list_JZ031.add(0, key);
        map_JZ031.put(key, value);
    }

    /**
     * 剑指 Offer II 030. 插入、删除和随机访问都是 O(1) 的容器
     * 设计一个支持在平均 时间复杂度 O(1) 下，执行以下操作的数据结构：
     * <p>
     * insert(val)：当元素 val 不存在时返回 true ，并向集合中插入该项，否则返回 false 。
     * remove(val)：当元素 val 存在时返回 true ，并从集合中移除该项，否则返回 false 。
     * getRandom：随机返回现有集合中的一项。每个元素应该有 相同的概率 被返回。
     * 1.不可重复
     * 2.记录个数，返回随机数，get(randomIndex)
     * 3.链表记录
     */

    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    ArrayList<Integer> list = new ArrayList<Integer>();
    Random random = new Random();

    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        } else {
            list.add(val);
            map.put(val, list.size() - 1);
            return true;
        }
    }

    // 删除之后，下标就不对了，前面的多1
    // 找到最后一个节点，将map中下标互换，这样每次只有最后一个受影响，不会产生下标被改变的情况
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        } else {
            int deleteIndex = map.get(val).intValue();
            int lastIndex = list.size() - 1;
            int lastVal = list.get(lastIndex);
            list.set(deleteIndex, lastVal);
            map.put(lastVal, deleteIndex);
            list.remove(lastIndex);
            map.remove(val);
            return true;
        }
    }

    public int getRandom() {
        return list.get(random.nextInt(list.size()));
    }

    /**
     * 剑指 Offer II 029. 排序的循环链表
     * 给定一个循环链表，随即一个节点，插入使之继续升序
     * 1.找到第一个前面比自己小，后面比自己大的节点插入
     * 2.不在范围内，在max->min判断是否范围内，不在直接插入
     * 3.继续后半段循环，找到第一个前面比自己小，后面比自己大的节点插入
     * <p>
     * 333->0
     */
    public ListNode insert(ListNode head, int insertVal) {
        //head==null
        ListNode newNode = new ListNode(insertVal);
        newNode.next = newNode;
        if (head == null) return newNode;

        //只有一个节点的情况
        if (head.next == head) {
            head.next = newNode;
            newNode.next = head;
            return head;
        }

        ListNode headTemp = head.next;
        ListNode pre = head;
        while (headTemp != head) {
            if (headTemp.val <= insertVal && headTemp.next.val >= insertVal && headTemp.val <= headTemp.next.val) {
                break;
            } else if (headTemp.val > headTemp.next.val && (insertVal >= headTemp.val || insertVal <= headTemp.next.val)) {
                break;
            }
            pre = headTemp;
            headTemp = headTemp.next;
        }
        ListNode next = headTemp.next;
        headTemp.next = newNode;
        newNode.next = next;
        return head;
    }

    /**
     * 剑指 Offer II 026. 重排链表
     * 给定一个单链表 L 的头节点 head ，单链表 L 表示为：
     * L0 → L1 → … → Ln-1 → Ln
     * 请将其重新排列后变为：
     * L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …
     * 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     */
    public void reorderList(ListNode head) {
        //循环一遍，计数
        //将n/2之后的尾插法,之后重新拼接
        ListNode temp = head;
        int count = 0;
        while (temp != null) {
            temp = temp.next;
            count++;
        }
        if (count == 1) return;
        int flag = count;
        count = 0;
        temp = head;
        ListNode next = null;
        ListNode dummy = null;
        while (temp != null) {
            if (count >= (flag / 2 + flag % 2)) {
                next = temp.next;
                temp.next = null;
                temp.next = dummy;
                dummy = temp;
                temp = next;
                count++;
            } else if (count == (flag / 2 + flag % 2 - 1)) {
                next = temp.next;
                temp.next = null;
                temp = next;
                count++;
            } else {
                temp = temp.next;
                count++;
            }
        }

        //head
        //dummy
        ListNode headNext = null;
        ListNode dummyNext = null;
        ListNode head1 = head;
        while (head1 != null) {
            if (dummy != null) {
                headNext = head1.next;
                dummyNext = dummy.next;

                head1.next = null;
                dummy.next = null;

                if (temp != null) {
                    temp.next = head1;
                }

                head1.next = dummy;
                temp = dummy;
                head1 = headNext;
                dummy = dummyNext;
            } else {
                temp.next = head1;
                head1 = null;
            }
        }
    }

    /**
     * 剑指 Offer II 119. 最长连续序列
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     */

    public int longestConsecutive1(int[] nums) {
        if (nums.length < 2) return nums.length;
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i : nums) {
            set.add(i);
        }
        int max = 0;
        // 3,2,1,5,6
        for (int i : set) {
            if (!set.contains(i - 1)) {
                int count = 1;
                int now = i;
                while (set.contains(now + 1)) {
                    count++;
                    now++;
                }
                max = Math.max(max, count);
            }
        }
        return max;
    }

    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i : nums) {
            set.add(i);
        }
        nums = new int[set.size()];
        Iterator<Integer> ii = set.iterator();
        for (int i = 0; i < set.size(); i++) {
            nums[i] = ii.next();
        }
        Arrays.sort(nums);
        int max = 1;
        int count = 1;
        // 100,1,2,3,4,6,7,8
        // 0,0,1->8
        // 0 1 1 2
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == 1) {
                count++;
            } else {
                max = Math.max(count, max);
                count = 1;
            }
        }
        max = Math.max(count, max);
        return max;
    }

    /**
     * 剑指 Offer II 025. 链表中的两数相加
     * 给定两个 非空链表 l1和 l2 来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
     * 这两个数字都不会以零开头。
     * <p>
     * [7,2,4,3]
     * [5,6,4]
     * 7 8 0 7
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        ListNode temp = l1;
        while (temp != null) {
            stack1.push(temp.val);
            temp = temp.next;
        }
        temp = l2;
        while (temp != null) {
            stack2.push(temp.val);
            temp = temp.next;
        }
        ListNode head = null;
        int flag = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            int a = 0;
            int b = 0;
            if (stack1.isEmpty()) {
                a = 0;
                b = stack2.pop();
            } else if (stack2.isEmpty()) {
                a = stack1.pop();
                b = 0;
            } else {
                a = stack1.pop();
                b = stack2.pop();
            }
            int sum = a + b + flag;
            ListNode temp1 = new ListNode(sum > 9 ? sum - 10 : sum);
            if (head != null) {
                temp1.next = head;
            }
            head = temp1;
            flag = sum > 9 ? 1 : 0;
        }
        if (flag == 1) {
            ListNode temp1 = new ListNode(1);
            temp1.next = head;
            return temp1;
        } else {
            int[] a = new int[6];
            return head;
        }

    }


    /**
     * 剑指 Offer II 022. 链表中环的入口节点
     * 给定一个链表，返回链表开始入环的第一个节点。 从链表的头节点开始沿着 next 指针进入环的第一个节点为环的入口节点。
     * 如果链表无环，则返回 null。
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
     * 如果 pos 是 -1，则在该链表中没有环。注意，pos 仅仅是用于标识环的情况，并不会作为参数传递到函数中。
     */
    // a->b->c->d
    //       |  |
    //       f<-e
    // fast:c->e->c->e
    // slow:b->c->d->e->f->c
    //slow1:a->b->c
    public ListNode detectCycle(ListNode head) {
        //0,1为空
        if (head == null || head.next == null || head.next.next == null) return null;
        ListNode fast = head.next.next;
        ListNode slow = head.next;
        ListNode slow1 = head;
        //fast与slow相遇，slow2从头开始相遇则为环入口
        while (fast != slow && fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast == null || fast.next == null || fast.next.next == null) return null;

        while (slow1 != slow) {
            slow1 = slow1.next;
            slow = slow.next;
        }

        return slow1;
    }

    // 0->a->b->c
    // 0->a 1
    // 倒2 == fast前进2,slow开始，fast到队尾，slow处的位置
    public ListNode removeNthFromEnd_1(ListNode head, int n) {
        ListNode result = new ListNode(0, head);
        ListNode fast = result;
        ListNode slow = result;
        while (fast.next != null) {
            if (n > 0) {
                fast = fast.next;
                n--;
            } else {
                fast = fast.next;
                slow = slow.next;
            }
        }

        slow.next = slow.next.next;
        return result.next;
    }

    /**
     * 剑指 Offer II 021. 删除链表的倒数第 n 个结点
     * 给定一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {

        int count = 0;
        ListNode temp = head;
        while (temp != null) {
            temp = temp.next;
            count++;
        }
        //diff:正数第几个--2
        count = count - n + 1;
        ListNode result = new ListNode(0, head);
        temp = result;
        while (count > 1) {
            temp = temp.next;
            count--;
        }
        temp.next = temp.next.next;
        return result.next;
    }

    /**
     * 剑指 Offer II 020. 回文子字符串的个数
     * 给定一个字符串 s ，请计算这个字符串中有多少个回文子字符串。
     * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
     */
    public int countSubstrings(String s) {
        //abc
        int count = 0;
        int len = s.length();
        for (int i = 0; i < len * 2 - 1; i++) {
            int left = i / 2;
            int right = i / 2 + i % 2;
            while (left >= 0 && right < len && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                count++;
            }
        }
        return count;
    }

    public boolean isPalindrome(String s) {
        //abc
        //ab
        int left = 0;
        int right = s.length() - 1;
        char[] chars = s.toCharArray();
        while (left < right) {
            if (chars[left] == chars[right]) {
                left++;
                right--;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 剑指 Offer II 016. 不含重复字符的最长子字符串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长连续子字符串 的长度。
     * 滑动窗口
     * pwwkew
     * p:0
     * w:1
     * left = 2
     */
    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        int sLen = s.length();
        if (sLen < 2) return sLen;
        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0;
        for (int i = 0; i < sLen; i++) {
            char right_016 = s.charAt(i);
            map.put(right_016, map.getOrDefault(right_016, 0) + 1);
            while (map.get(right_016) > 1) {
                char c_left = s.charAt(left);
                map.put(c_left, map.get(c_left) - 1);
                left++;
            }
            maxLen = Math.max(maxLen, i - left + 1);
        }
        return maxLen;
    }

    /**
     * 剑指 Offer II 015. 字符串中的所有变位词
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 变位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * <p>
     * 变位词 指字母相同，但排列不同的字符串。
     * 滑动窗口
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> list = new LinkedList<>();
        int s_len = s.length();
        int p_len = p.length();
        if (p_len > s_len || p_len == 0) return list;
        int[] ss = new int[26];
        int[] pp = new int[26];
        for (int i = 0; i < p_len; i++) {
            ss[s.charAt(i) - 'a']++;
            pp[p.charAt(i) - 'a']++;
        }
        if (Arrays.equals(ss, pp)) {
            list.add(0);
        }
        for (int i = p_len; i < s_len; i++) {
            ss[s.charAt(i - p_len) - 'a']--;
            ss[s.charAt(i) - 'a']++;
            if (Arrays.equals(ss, pp)) {
                list.add(i - p_len + 1);
            }
        }
        return list;
    }


    /**
     * 剑指 Offer II 014. 字符串中的变位词
     * s1:abc
     * s2:AAA b A bcaAAA
     * return true;  bca-->abc;
     * 暴力法：超出时间限制
     * 滑动窗口：
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s2.length() < 1 || s1.length() > s2.length()) return false;
        int len1 = s1.length();
        int len2 = s2.length();
        int[] index1 = new int[26];
        int[] index2 = new int[26];
        for (int i = 0; i < len1; i++) {
            index1[s1.charAt(i) - 'a']++;
            index2[s2.charAt(i) - 'a']++;
        }
//        if(Arrays.equals(index1,index2)) return true;
        if (equal(index1, index2)) return true;
        for (int i = 0; i < len2 - len1; i++) {
            index2[s2.charAt(i + len1) - 'a']++;
            index2[s2.charAt(i) - 'a']--;
//            if(Arrays.equals(index1,index2)) return true;
            if (equal(index1, index2)) return true;
        }
        return false;
    }

    public boolean equal(int[] index1, int[] index2) {
        for (int i = 0; i < index1.length; i++) {
            if (index1[i] != index2[i]) return false;
        }
        return true;
    }

    /**
     * 剑指 Offer II 013. 二维子矩阵的和
     *
     * @param row1 行
     * @param col1 列
     * 前缀法，新建数组保存到每个下标的时候前缀和，寻找的时候只需要用(尾-首)即可计算出，其间和
     */
    int[][] sum;

    public void NumMatrix(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] sum = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (j == 0) sum[i][j] = matrix[i][j];
                else sum[i][j] = sum[i][j - 1] + matrix[i][j];
            }
        }
        this.sum = sum;
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum = 0;
        for (int i = row1; i <= row2; i++) {
            if (col1 == 0) sum += this.sum[i][col2];
            else sum += this.sum[i][col2] - this.sum[i][col1 - 1];
        }
        return sum;
    }

    /**
     * 剑指 Offer II 011.
     * 0 和 1 个数相同的子数组
     * 给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
     * 1,1,0,1,1,1,0,0,0,1,1
     */
    public int findMaxLength(int[] nums) {
        //0视为-1，和为0的连续子数组，value记录下标
        //1,1,0,0
        //0,-1
        //1,0
        //2,1
        //1,2
        //0,3
        int length = 0;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int sum = 0;
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            int a = nums[i] == 1 ? sum++ : sum--;
            if (map.containsKey(sum)) {
                length = Math.max(i - map.get(sum), length);
            } else {
                map.put(Integer.valueOf(sum), Integer.valueOf(i));
            }
        }
        return length;
    }

    /**
     * 给定一个整数数组和一个整数 k ，请找到该数组中和为 k 的连续子数组的个数。
     * 正确：
     * 1.暴力破解
     * 2.前缀法
     * 7,3,4,2,-2
     * (0,1),(7,1)
     */
    public int subarraySum(int[] nums, int k) {
        int len = nums.length;
        int count = 0;
        int sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < len; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    public int subarraySum_violence(int[] nums, int k) {
        int len = nums.length;
        int count = 0;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                sum += nums[j];
                if (sum == k) count++;
            }
            sum = 0;
        }
        return count;
    }

    /**
     * 剑指 Offer II 009
     * 乘积小于 K 的子数组
     * 给定一个正整数数组 nums和整数 k ，请找出该数组内乘积小于 k 的连续的子数组的个数。
     * <p>
     * 右边滑动，
     */

    //[10,5,2,6]
    // 100
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        //乘积小于k
        int count = 0;
        int product = 1;
        int left = 0;
        int right = 0;
        for (int i = 0; i < nums.length; i++) {
            product *= nums[i];
            right = i;
            while (left <= right && product >= k) {
                product /= nums[left];
                left++;
            }
            count += right - left + 1;
        }
        return count;
    }

    /**
     * 剑指 Offer II 008
     * 和大于等于 target 的最短子数组
     * 给定一个含有n个正整数的数组和一个正整数 target 。
     * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组[a,b,c]
     * 并返回其长度。如果不存在符合条件的子数组，返回 0 。
     */
    public int minSubArrayLen(int target, int[] nums) {
        Deque<Integer> deque = new LinkedList<>();
        int sum = 0;
        int dequeLength = 0;
        int minLength = 0;
        for (int i = 0; i < nums.length; i++) {
            deque.offerLast(nums[i]);
            sum += nums[i];
            dequeLength++;
            int temp = deque.peekFirst();
            while (sum - temp >= target) {
                deque.pollFirst();
                sum -= temp;
                dequeLength--;
                temp = deque.peekFirst();
            }
            if (sum >= target && minLength != 0) minLength = Math.min(minLength, dequeLength);
            else if (sum >= target && minLength == 0) minLength = dequeLength;
        }

        return minLength;
    }

    /**
     * 剑指 Offer II 007
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]]
     * 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
     * <p>
     * 你返回所有和为 0 且不重复的三元组。
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> lists = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if ((i > (0)) && nums[i] == nums[i - 1]) continue;
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {

                while (j < k && j > i + 1 && nums[j] == nums[j - 1]) {
                    j++;
                }

                while (j < k && k < nums.length - 1 && nums[k] == nums[k + 1]) {
                    k--;
                }

                if (j >= k) break;

                if (nums[i] + nums[j] + nums[k] == 0) {
                    List<Integer> list = new LinkedList<>();
                    list.add(nums[i]);
                    list.add(nums[j]);
                    list.add(nums[k]);
                    lists.add(list);
                    j++;
                    k--;
                } else if ((nums[i] + nums[j] + nums[k]) > 0) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return lists;
    }

    /**
     * 给你一个整数数组 nums ，判断是否存在三元组
     * [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
     * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
     * 你返回所有和为 0 且不重复的三元组。
     */
    public List<List<Integer>> threeSum_1(int[] nums) {
        List<List<Integer>> lists = new LinkedList<>();

        return lists;
    }

    /**
     * 剑指 Offer II 005
     * 给定一个字符串数组words，请计算当两个字符串 words[i] 和 words[j] 不包含相同字符时，
     * 它们长度的乘积的最大值。假设字符串中只包含英语的小写字母。如果没有不包含相同字符的一对字符串，返回 0。
     * <p>
     * 每个字符-'a' 等于第i位位1，两个字符串 与运算 之后为0，则代表没有相同的字符，否则都为1，结果为1，最终结果不为0
     */
    public int maxProduct(String[] words) {
        int max = 0;
        int[] compare = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                compare[i] |= 1 << (words[i].charAt(j) - 'a');
            }
        }
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if ((compare[i] & compare[j]) == 0) {
                    max = Math.max(max, words[i].length() * words[j].length());
                }
            }
        }
        return max;
    }

    /**
     * 重建二叉树
     * 例如输入前序遍历序列{1, 2,4,7 , 3,5,6,8}和中序遍历序列{4,7,2, 1 , 5,3,8,6}
     * preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;
        int index = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (preorder[0] == inorder[i]) break;
            index++;
        }
        TreeNode root = new TreeNode(preorder[0]);
        int[] leftPre = new int[index];
        int[] rightPre = new int[inorder.length - index - 1];
        int[] leftIn = new int[index];
        int[] rightIn = new int[inorder.length - index - 1];

        for (int i = 0; i < inorder.length - 1; i++) {
            if (i < index) {
                leftPre[i] = preorder[i + 1];
                leftIn[i] = inorder[i];
            } else {
                rightPre[i - index] = preorder[i + 1];
                rightIn[i - index] = inorder[i + 1];
            }
        }
        root.left = buildTree(leftPre, leftIn);
        root.right = buildTree(rightPre, rightIn);

        return root;
    }

    /**
     * 给定一个二叉树 根节点root，树的每个节点的值要么是 0，要么是 1。请剪除该二叉树中所有节点的值为 0 的子树。
     * <p>
     * 节点 node 的子树为node 本身，以及所有 node的后代。
     */
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) return null;
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        if (root.right == null && root.left == null && root.val == 0) return null;
        return root;
    }

    /**
     * 剑指 Offer II 046. 二叉树的右侧视图
     */
    public List<Integer> rightSideView_BFS(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        if (root == null) return list;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {

            int size = queue.size();
            while (size > 0) {
                TreeNode newNode = queue.poll();
                //list<Integer>.add(newNode.val)
                size--;
                if (size == 0) list.add(newNode.val);
                if (newNode.left != null) queue.offer(newNode.left);
                if (newNode.right != null) queue.offer(newNode.right);
            }
            //list<list<Integer>>.add(list);
        }

        return list;
    }

    public List<Integer> rightSideView_DFS(TreeNode root) {
        List<Integer> view = new LinkedList<>();

        recursion_dfs(root, 0, view);

        return view;
    }

    public void recursion_dfs(TreeNode root, int depth, List<Integer> view) {
        if (root == null) return;
        if (depth == view.size()) view.add(0);
        view.set(depth, root.val);
        depth++;
        recursion_dfs(root.left, depth, view);
        recursion_dfs(root.right, depth, view);
    }

    /**
     * 括号的分数
     * () 得 1 分。
     * AB 得 A + B 分，其中 A 和 B 是平衡括号字符串。
     * (A) 得 2 * A 分，其中 A 是平衡括号字符串。
     * 分治法：长度=2终止，返回1，如果提前加起来==0，为()(),否则是(())
     */
    public int scoreOfParentheses(String s) {

        if (s.length() == 2) return 1;
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int flag = s.charAt(i) == '(' ? 1 : -1;
            sum += flag;
            if (sum == 0) {
                sum = i;
                break;
            }
        }

        //(())
        if (sum == s.length() - 1) {
            return 2 * scoreOfParentheses(s.substring(1, sum));
        } else {
            return scoreOfParentheses(s.substring(0, sum + 1)) + scoreOfParentheses(s.substring(sum + 1));
        }
    }

    /**
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。
     */
    TreeNode head, temp;

    public TreeNode treeToDoublyList(TreeNode root) {
        treeToDoublyList_dfs(root);
        temp.right = head;
        head.left = temp;
        return head;
    }

    public void treeToDoublyList_dfs(TreeNode current) {
        if (current == null) return;
        treeToDoublyList_dfs(current.left);

        if (head == null) head = current;
        else temp.right = current;
        current.left = temp;
        temp = current;
        treeToDoublyList_dfs(current.right);
    }

    /**
     * Z字BFS
     */
    public List<List<Integer>> lists = new LinkedList<>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return lists;
        Queue<TreeNode> queue = new LinkedList();
        queue.offer(root);
        boolean flag = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> list = new LinkedList<>();
            while (size > 0) {
                TreeNode temp = queue.poll();
                if (temp.left != null) queue.offer(temp.left);
                if (temp.right != null) queue.offer(temp.right);
                if (flag) list.add(temp.val);
                else list.addFirst(temp.val);
                size--;
            }
            lists.add(list);
            flag = !flag;
        }
        return lists;
    }

    public List<List<Integer>> dfs(TreeNode root) {
        if (root == null) return lists;
        LinkedList<Integer> list = new LinkedList<>();
        dfs(root, list);
        return lists;
    }

    public void dfs(TreeNode root, LinkedList<Integer> list) {
        list.add(root.val);
        if (root.left == null && root.right == null) {
            lists.add(list);
            return;
        }
        if (root.left != null) {
            LinkedList<Integer> left = new LinkedList<>(list);
            dfs(root.left, left);
        }
        if (root.right != null) {
            LinkedList<Integer> right = new LinkedList<>(list);
            dfs(root.right, right);
        }
    }

    int resultHeight;
    int val;

    /**
     * 最底层最左边的值
     */
    public int findBottomLeftValue(TreeNode root) {
        int height = 0;
        findBottomLeftValue_dfs(root, height);
        return val;
    }

    public void findBottomLeftValue_dfs(TreeNode root, int height) {
        if (root == null) return;
        height++;
        if (height > resultHeight) {
            resultHeight = height;
            val = root.val;
        }
        findBottomLeftValue_dfs(root.left, height);
        findBottomLeftValue_dfs(root.right, height);
    }

    /**
     * 给定一棵二叉搜索树和其中的一个节点 p ，找到该节点在树中的中序后继。如果节点没有中序后继，请返回 null 。
     * 节点p的后继是值比p.val大的节点中键值最小的节点，即按中序遍历的顺序节点 p 的下一个节点。
     */
    TreeNode root;

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) return root;
        inorderSuccessor_dfs(root, p);
        return this.root;
    }

    public void inorderSuccessor_dfs(TreeNode root, TreeNode p) {
        if (root == null) return;
        inorderSuccessor_dfs(root.left, p);
        if (this.root != null) return;
        if (root.val > p.val) {
            this.root = root;
            return;
        }
        inorderSuccessor_dfs(root.right, p);
    }

    /**
     * 链表排序
     * 给定链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     */
    public ListNode sortList(ListNode head) {
        ListNode cur = new ListNode(0);
        ArrayList<Integer> list = new ArrayList<>();
        if (head == null) return cur;
        int size = 0;
        while (head != null) {
            list.add(head.val);
            head = head.next;
            size++;
        }
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = list.get(i);
        }

        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            ListNode temp = new ListNode(array[i]);
            if (i == 0) {
                cur = temp;
            } else {
                head.next = temp;
            }
            head = temp;
        }
        return cur;
    }

    /**
     * 剑指 Offer 35. 复杂链表的复制
     * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
     */
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Node temp = head;
        HashMap<Node, Node> map = new HashMap<Node, Node>();
        while (temp != null) {
            map.put(temp, new Node(temp.val));
            temp = temp.next;
        }
        temp = head;
        while (temp != null) {
            map.get(temp).next = map.get(temp.next);
            map.get(temp).random = map.get(temp.random);
            temp = temp.next;
        }
        return map.get(head);
    }

    /**
     * 双色球，每张彩票权重，红：1-33，蓝：1-16,抽中不放回模型
     */

    public static void changeOfDoubleColor() {
        //总排列组合  C(33,6)*C(16,1)=17721088
        // 6+1
        // C(6,6) * C(1,1)
        System.out.println("1等奖概率：" + new BigDecimal(C(6, 6) * C(1, 1))
                .divide(new BigDecimal(C(33, 6) * C(16, 1)), 10, 0));
        // 6+0
        // C(6,6) * C(15,1)
        System.out.println("2等奖概率：" + new BigDecimal(C(6, 6) * C(15, 1))
                .divide(new BigDecimal(C(33, 6) * C(16, 1)), 10, 0));
        // 5+1
        // C(6,5) * C(28,1) * C(1,1)
        System.out.println("3等奖概率：" + new BigDecimal(C(6, 5) * C(28, 1) * C(1, 1))
                .divide(new BigDecimal(C(33, 6) * C(16, 1)), 10, 0));
        // 5+0,4+1
        // C(6,5) * C(28,1) * C(15,1)
        // C(6,4) * C(28,2) * C(1,1)
        System.out.println("4等奖概率：" + new BigDecimal(C(6, 5) * C(28, 1) * C(15, 1))
                .add(new BigDecimal(C(6, 4) * C(28, 2) * C(1, 1)))
                .divide(new BigDecimal(C(33, 6) * C(16, 1)), 20, 0));
        // 4+0,3+1
        // C(6,4) * C(28,2) * C(15,1) 15*15
        // C(6,3) * C(28,3) * C(1,1) 15
        System.out.println("5等奖概率：" + new BigDecimal(C(6, 4) * C(28, 2) * C(15, 1))
                .add(new BigDecimal(C(6, 3) * C(28, 3) * C(1, 1)))
                .divide(new BigDecimal(C(33, 6) * C(16, 1)), 20, 0));
        // 2+1,1+1,0+1
        // C(6,2) * C(28,4) * C(1,1)
        // C(6,1) * C(28,5) * C(1,1)
        // C(6,0) * C(28,6) * C(1,1)
        System.out.println("6等奖概率：" + new BigDecimal(C(6, 2) * C(28, 4) * C(1, 1))
                .add(new BigDecimal(C(6, 1) * C(28, 5) * C(1, 1)))
                .add(new BigDecimal(C(6, 0) * C(28, 6) * C(1, 1)))
                .divide(new BigDecimal(C(33, 6) * C(16, 1)), 20, 0));
    }

    public static int C(int m, int n) {
        if (m == 0 || n == 0 || n > m) return 1;
        int mm = 1;
        int nn = 1;
        //C(5,4) = 5*4*3*2 / 4*3*2*1
        for (int i = m; i > (m - n); i--) {
            mm = mm * i;
            nn = nn * (i - (m - n));
        }
        return mm / nn;
    }

    public static BigDecimal C(int m, int n, boolean isBig) {
        if (m == 0 || n == 0 || n > m) return new BigDecimal(1);
        BigDecimal mm = new BigDecimal(1);
        BigDecimal nn = new BigDecimal(1);
        //C(5,4) = 5*4*3*2 / 4*3*2*1
        for (int i = m; i > (m - n); i--) {
            mm = mm.multiply(new BigDecimal(i));
            nn = nn.multiply(new BigDecimal(i - (m - n)));
        }
        return mm.divide(nn);
    }

    /**
     * 4种花色，A->10
     */
    public static void changeOfFlushFlow(){
        System.out.println("同花顺概率：");
        System.out.println(C(5, 5, true).multiply(C(47, 2, true)).multiply(new BigDecimal(40)));
        System.out.println("---");
        System.out.println(C(52, 7, true));

        System.out.println(C(5, 5, true).multiply(C(47, 45, true)).multiply(new BigDecimal(40)));
        System.out.println("---");
        System.out.println(C(52, 50, true));
    }

    /**
     * 德州扑克-前门花概率
     */
    public void changeOfFlush() {
        //包含自己人数
        for (int j = 2; j < 10; j++) {
            //对手几张花
            for (double i = 0; i < (j - 1) * 2 + 1; i++) {
                if (i < 10) {
                    System.out.println(j + "人场,翻牌停同花,对手" + i + "张花,两张牌发出同花的概率为" + ((9 - i) / 49 + (49 - (9 - i)) / 49 * (9 - i) / 48));
                }
            }
            System.out.println("---------------------------------------------------------------");
        }
    }
}

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

