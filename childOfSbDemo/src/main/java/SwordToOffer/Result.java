package SwordToOffer;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.nio.channels.SelectionKey;
import java.util.*;

/**
 * 剑指offer 算法入口
 *
 * @author ggz on 2022/3/25
 */
public class Result {
    public static void main(String[] args) {
//        int[] a = {1, -2, 3, 10, -4, 7, 2, -5};
//        int[][] b = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};

//        System.out.println(Find(0, b));

//        Permutation("ABC");

//        System.out.println(findNthDigit(10));

//        System.out.println(jumpFloor(7));

//        System.out.println(match("ab",".*"));

//        System.out.println(rectCover(5));

//        System.out.println(Sum_Solution(5));

//        System.out.println(Add(2, -2));

//        System.out.println(NumberOf1(10));

//        System.out.println(Power(2, 3));

//        int[][] grid = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

//        System.out.println(maxValue(grid));

//        System.out.println(lengthOfLongestSubstring("N$po-O66.n=h!!#oJM#MNh:kIwxSEjFP7F)(@ROpH5x|t*XC+[`jkWor@F!Cmu8{|rft,fx;QM1p4W+U|9`gk_}(0*=cc93P"));

//        System.out.println(solve("105106202"));

        //普通树
//        TreeNode a = new TreeNode(1);
//        TreeNode b = new TreeNode(2);
//        TreeNode c = new TreeNode(3);
//        TreeNode d = new TreeNode(4);
//        TreeNode e = new TreeNode(5);
//        TreeNode f = new TreeNode(6);
//        TreeNode g = new TreeNode(7);
//        a.left = b;
//        a.right = c;
//        b.left = d;
//        b.right = e;
//        c.left = f;
//        c.right = g;

        //二叉搜索树

//        TreeNode aa = new TreeNode(4);
//        TreeNode bb = new TreeNode(2);
//        TreeNode cc = new TreeNode(6);
//        TreeNode dd = new TreeNode(1);
//        TreeNode ee = new TreeNode(3);
//        TreeNode ff = new TreeNode(5);
//        TreeNode gg = new TreeNode(7);
//        aa.left = bb;
//        aa.right = cc;
//        bb.left = dd;
//        bb.right = ee;
//        cc.left = ff;
//        cc.right = gg;

//        System.out.println(Print(a));

//        System.out.println(preTree(a));

//        System.out.println(inorderTree(a));

//        System.out.println(postTree(a));

//        System.out.println(BFS(a));

//        System.out.println(KthNode(a,1));

//        Mirror(a);

//        System.out.println(PrintFromTopToBottom(a));

//        int[] aa = {1, 2, 4, 7, 3, 5, 6, 8};
//        int[] bb = {4, 7, 2, 1, 5, 3, 8, 6};

//        reConstructBinaryTree(aa, bb);

//        int[] sequence = {4,6,7,5};

//        System.out.println(VerifySquenceOfBST(sequence));

//        System.out.println(hasPathSum(a, 12));

//        System.out.println(DFS(a));

//        System.out.println(BFS(a));

//        System.out.println(FindPath(aa,22));

//        System.out.println(Convert(aa));

//        System.out.println(DeepOfBalance(aa));

//        System.out.println(IsBalanced_Solution(aa));

//        TreeLinkNode aaa = new TreeLinkNode(1);
//        TreeLinkNode bbb = new TreeLinkNode(2);
//        TreeLinkNode ccc = new TreeLinkNode(3);
//        aaa.left = bbb;
//        aaa.right = ccc;
//        bbb.next = aaa;
//        ccc.next = aaa;

//        System.out.println(GetNext(aaa)==null?null:GetNext(aaa).val);

//        TreeNode aaaa = new TreeNode(1);
//        TreeNode bbbb = new TreeNode(2);
//        TreeNode cccc = new TreeNode(2);
//        TreeNode dddd = new TreeNode(3);
//        TreeNode eeee = new TreeNode(4);
//        TreeNode ffff = new TreeNode(4);
//        TreeNode gggg = new TreeNode(3);
//        aaaa.left = bbbb;
//        aaaa.right = cccc;
//        bbbb.left = dddd;
//        bbbb.right = eeee;
//        cccc.left = ffff;
//        cccc.right = gggg;

//        System.out.println(isSymmetrical(aaaa));

//        System.out.println(Print_left_right(a));

//        System.out.println(lowestCommonAncestor(a, 2, 5));

//        TreeNode aa = new TreeNode(11);
//        TreeNode bb = new TreeNode(22);
//        TreeNode cc = new TreeNode(33);
//        aa.left = bb;
//        aa.right = cc;

//        System.out.println(Serialize(aa));

//        TreeNode node = Deserialize(Serialize(aa));

//        System.out.println(IsPopOrder(new int[]{3, 1, 2, -900, 5, 6, 8, 4, 7, 0}, new int[]{2, 6, 4, 0, 7, 8, 5, -900, 1, 3}));

//        System.out.println(ReverseSentence("nowcoder. a am I"));

//        System.out.println(maxInWindows_violence(new int[]{10, 14, 12, 11}, 1));

//        System.out.println(maxInWindows(new int[]{2,3,4,2,6,2,5,1}, 3));

//        char[][] a = new char[][]{
//                {'A', 'B', 'C', 'E', 'H', 'J', 'I', 'G'},
//                {'S', 'F', 'C', 'S', 'L', 'O', 'P', 'Q'},
//                {'A', 'D', 'E', 'E', 'M', 'N', 'O', 'E'},
//                {'A', 'D', 'I', 'D', 'E', 'J', 'F', 'M'},
//                {'V', 'C', 'E', 'I', 'F', 'G', 'G', 'S'}};

//        System.out.println(hasPath(a, "SGGFI" +
//                                            "ECVAA" +
//                                            "SABCE" +
//                                            "HJIGQEM"));


//        char[][] a = new char[][]{
//                {'A', 'B', 'C', 'E'},
//                {'S', 'F', 'C', 'S'},
//                {'A', 'D', 'E', 'E'}};

//        System.out.println(hasPath(a, "ABCB"));

//        char[][] aa = new char[][]{
//                {'a', 'b', 'f'},
//                {'b', 'e', 'g'},
//                {'c', 'd', 'g'}};

//        System.out.println(hasPath(aa, "abcdebf"));

//        System.out.println(movingCount(0, 1, 1));

//        int[] a = new int[]{8, 5, 2, 7, 10, 9, 4, 7, 6};
//        int[] b = new int[]{3, 1, 2, -900, 5, 6, 8, 4, 7, 0};
//        int[] c = new int[]{1, 2, 3, 4, 5, 6, 7, 0};

//        sort_bubble(a);
//        sort_bubble(b);

//        quickSort(a, 0, a.length - 1);/** 快速排序 **/
//        quickSort(b, 0, b.length - 1);/** 快速排序 **/

//        mergeSort(a, 0, a.length - 1);
//        mergeSort(b, 0, b.length - 1);

//        mergeSort1(a, 0, a.length - 1);
//        mergeSort1(b, 0, b.length - 1);

//        heapSort(a);
//        heapSort(b);

//        System.out.println(Arrays.toString(a));
//        System.out.println(Arrays.toString(b));

//        System.out.println(InversePairs(c));

//        int[] a = new int[]{1, 2, 4};
//        int[] b = new int[]{3, 5, 6};
//        int[] c = new int[]{1, 2, 3};
//        int[] d = new int[]{4, 5, 6};

//        System.out.println(Arrays.toString(sortSortedArrays(a, b)));

//        System.out.println(Arrays.toString(sortSortedArrays(c, d)));

//        System.out.println(Math.floor(5 / 2) - 1);//取浮点向下最大整数

//        System.out.println(GetLeastNumbers_Solution(a, 4));

//        int[] d = new int[]{1, 4, 1, 6};
//        System.out.println(Arrays.toString(FindNumsAppearOnce(d)));

//        int[][] e = new int[][]{{1,2},{3,4},{5,6},{7,8},{9,10}};
//        System.out.println(printMatrix(e));

//        int[] JZ61 = new int[]{1, 0, 0, 1, 0};
//        System.out.println(IsContinuous(JZ61));

//        String JZ67 = "-00082";
//        System.out.println(StrToInt(JZ67));

//        System.out.println(isNumeric("+.8"));

//        System.out.println(Arrays.toString(multiply(new int[]{1, 2, 3, 4, 5})));
//        System.out.println(Arrays.toString(multiply(new int[]{100, 50})));

//        System.out.println(FirstNotRepeatingChar("aa"));

//        System.out.println(replaceSpace(" a b "));

//        System.out.println(Arrays.toString(reOrderArray(new int[]{2, 4, 6, 5, 7})));

//        System.out.println(MoreThanHalfNum_Solution(new int[]{1,1,1,1,2,2,2}));

//        System.out.println(NumberOf1Between1AndN_Solution(13));

//        System.out.println(PrintMinNumber(new int[]{3,321,4}));

//        System.out.println(GetUglyNumber_Solution(1500));

//        System.out.println(FindContinuousSequence(9));

//        System.out.println(FindNumbersWithSum(new int[]{1, 2, 3, 4, 5, 6, 7}, 5));

//        System.out.println(LeftRotateString("aab", 10));

//        System.out.println(LastRemaining_Solution(10,17));

//        Insert('g');
//        Insert('o');
//        Insert('o');
//        Insert('g');
//        System.out.println(FirstAppearingOnce());

//        System.out.println(cutRope(18));

//        System.out.println(Arrays.toString(reOrderArrayTwo(new int[]{1, 2, 3, 4, 5, 6, 7})));

//        System.out.println(cutRope(9696969696968L));

//        System.out.println(pow(2, 3));
//        Student student = new Student("1", "1");
//        Student student2 = new Student("2", "2");
//        Student[] stu = new Student[]{student, student2};
//        System.out.println(stu[0]);
//        System.out.println(stu[1]);
//
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(3);
//        list.add(2);
//        Iterator<Integer> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            iterator.next();
//            iterator.remove();
//        }
//
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

//        System.out.println(lengthOfLongestSubstring_1("abcbde"));

//        System.out.println((int) Math.pow(2.0,17.0)>>>16);
        HashMap<String,String> map = new HashMap<>();
        for(int i = 0;i<12;i++){
            map.put(String.valueOf((char)(i+97)), String.valueOf(i));
        }

        map.get("a");
        map.get("b");

        System.out.println();

    }

    /**
     * 最长不重复子串
     */

    public static int lengthOfLongestSubstring_1(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }


    /**
     * JZ83 剪绳子（进阶版）
     * 给你一根长度为 n 的绳子，请把绳子剪成整数长的 m 段（ m 、 n 都是整数， n > 1 并且 m > 1 ， m <= n ），
     * 每段绳子的长度记为 k[1],...,k[m] 。请问 k[1]*k[2]*...*k[m] 可能的最大乘积是多少？
     * 例如，当绳子的长度是 8 时，我们把它剪成长度分别为 2、3、3 的三段，此时得到的最大乘积是 18 。
     * <p>
     * 2≤n≤1E14
     * 由于答案过大，请对 998244353 取模。
     * Integer.MAX =   2147483647
     */

    final static int MOD = 998244353;

    public static long cutRope(long number) {
        if (number < 4) return number - 1;
        long result = 1;
        if (number % 3 == 0) {
            result = pow(3, number / 3);
        } else if (number % 3 == 1) {
            result = pow(3, (number / 3) - 1) * 4 % MOD;
        } else {
            result = pow(3, number / 3) * 2 % MOD;
        }
        return result;
    }

    /**
     * 快速计算a^b
     */
    public static long pow(long a, long b) {
        long result = 1;
        while (b != 0) {
            if ((b & 1L) != 0) {
                result = (result * a) % MOD;
            }
            a = (a * a) % MOD;
            b = (b >> 1);
        }
        return result;
    }

    /**
     * JZ81 调整数组顺序使奇数位于偶数前面(二)
     * 输入一个长度为 n 整数数组，数组里面可能含有相同的元素，
     * 实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前面部分，
     * 所有的偶数位于数组的后面部分，对奇数和奇数，偶数和偶数之间的相对位置不做要求，
     * 但是时间复杂度和空间复杂度必须如下要求。
     */

    public static int[] reOrderArrayTwo(int[] array) {
        int len = array.length;
        int[] result = new int[len];
        int index = 0;
        int left = 0;
        int right = len - 1;
        while (index < len) {
            if (array[index] % 2 == 1) {
                result[left] = array[index];
                left++;
            } else {
                result[right] = array[index];
                right--;
            }
            index++;
        }
        return result;
    }

    /**
     * JZ14 剪绳子
     * 给你一根长度为n的绳子
     * 请把绳子剪成整数长的 m 段(m,n都是整数,n>1并且m>1,m<=n)
     * 每段绳子的长度记为 k[1],...,k[m] 。请问 k[1]*k[2]*...*k[m] 可能的最大乘积是多少?
     * 例如,当绳子的长度是 8 时,我们把它剪成长度分别为 2,3,3 的三段,此时得到的最大乘积是18.
     * 数据范围:2<=n<=60
     * 进阶：空间复杂度 O(1),时间复杂度O(n)
     */


    public static int cutRope(int n) {
        if (n < 4) return n - 1;
        int count = n / 3;
        int result = n % 3 == 2 ? (n - 3 * count) : (n - 3 * (count - 1));
        return n % 3 == 2 ? (int) (Math.pow(3, count) * result) : (int) (Math.pow(3, count - 1) * result);
    }

    public static int cutRope_dp(int n) {
        if (n < 4) return n - 1;
        int[] dp = new int[n + 1];
        dp[2] = 2;//直接*2最大
        dp[3] = 3;//直接*3最大
        dp[4] = 4;//*2*2最大
        for (int i = 5; i <= n; i++) {
            for (int j = 2; j < i - 1; j++) {
                dp[i] = Math.max(dp[i], j * dp[i - j]);
            }
        }
        return dp[n];
    }

    /**
     * JZ75 字符流中第一个不重复的字符
     * 请实现一个函数用来找出字符流中第一个只出现一次的字符。
     * 例如，当从字符流中只读出前两个字符 "go" 时，第一个只出现一次的字符是 "g" 。当从该字符流中读出前六个字符 “google" 时，第一个只出现一次的字符是"l"。
     * 数据范围：字符串长度满足 1<=n<=1000
     * 后台会用以下方式调用 Insert 和 FirstAppearingOnce 函数
     */

    private static StringBuffer stringBuffer_JZ75 = new StringBuffer();
    private static HashMap<Character, Integer> hashMap_JZ75 = new HashMap<>();

    public static void Insert(char ch) {
        stringBuffer_JZ75.append(ch);
        hashMap_JZ75.put(ch, hashMap_JZ75.getOrDefault(ch, 1) - 1);
    }

    public static char FirstAppearingOnce() {
        char[] result = stringBuffer_JZ75.toString().toCharArray();
        for (int i = 0; i < result.length; i++) {
            if (hashMap_JZ75.get(result[i]) == 0) return result[i];
        }
        return '#';
    }

    /**
     * JZ62 孩子们的游戏(圆圈中最后剩下的数)
     * 有个游戏是这样的：首先，让 n 个小朋友们围成一个大圈，小朋友们的编号是0~n-1。
     * 然后，随机指定一个数 m ，让编号为0的小朋友开始报数。每次喊到 m-1 的那个小朋友要出列唱首歌，
     * 然后可以在礼品箱中任意的挑选礼物，并且不再回到圈中，从他的下一个小朋友开始，
     * 继续0... m-1报数....这样下去....直到剩下最后一个小朋友，可以不用表演，
     * 并且拿到牛客礼品，请你试着想下，哪个小朋友会得到这份礼品呢？
     * <p>
     * 约瑟夫环
     */

    public static int LastRemaining_Solution(int n, int m) {
        int index = 0;
        int result = 0;
        for (int i = 2; i <= n; i++) {
            index = (index + m) % i;
        }
        return index;
    }

    /**
     * JZ58 左旋转字符串
     * 对于一个给定的字符序列S ，请你把其循环左移 K 位后的序列输出。例如，字符序列 S = ”abcXYZdef” ,
     * 要求输出循环左移 3 位后的结果，即 “XYZdefabc”
     */

    public static String LeftRotateString(String str, int n) {
        if (str.length() < 2) return str;
        int a = n % str.length();
        return str.substring(a) + str.substring(0, a);
    }

    /**
     * JZ57 和为S的两个数字
     * 输入一个升序数组 array 和一个数字S，在数组中查找两个数，使得他们的和正好是S，
     * 如果有多对数字的和等于S，返回任意一组即可，如果无法找出这样的数字，返回一个空数组即可。
     * <p>
     * l,r
     */

    public static ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> list = new ArrayList<>();
        if (array.length == 0) return list;
        int l = 0;//min = 1;
        int r = array.length - 1;//min =
        while (l < r) {
            if ((array[l] + array[r]) == sum) {
                list.add(array[l]);
                list.add(array[r]);
                return list;
            } else if ((array[l] + array[r]) > sum) {
                r--;
            } else {
                l++;
            }
        }
        return list;
    }

    /**
     * JZ74 和为S的连续正数序列
     * 小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。
     * 但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。
     * 没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。
     * 现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列?
     * <p>
     * 滑动数组的方法，从1,2开始，如果小于就右移，直到
     */
    public static ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        if (sum < 3) return res;
        //从1到2的区间开始
        int flag = sum / 2 + sum % 2;
        int l = 1;
        int r = 2;
        while (r <= flag) {
            int sum_l_r = (l + r) * (r - l + 1) / 2;
            if (sum == sum_l_r) {
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = l; i <= r; i++) {
                    list.add(i);
                }
                res.add(list);
                l++;
                r++;
            } else if (sum_l_r < sum) {
                r++;
            } else {
                l++;
            }
        }
        return res;
    }


    /**
     * JZ49 丑数
     * 把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。
     * 习惯上我们把1当做是第一个丑数。
     * 求按从小到大的顺序的第 n个丑数。
     */

    /**
     * 使用小顶堆优先队列,每次从小顶堆取出最小的然后乘以2,3,5,根据hashMap.containKey判断是否重复，重复不加入
     * 直到取到第index个丑数
     */
    public static int GetUglyNumber_Solution(int index) {
        if (index == 0) return 0;
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        HashMap<Long, Integer> map = new HashMap<>();
        int[] factors = new int[]{2, 3, 5};
        map.put(1L, 1);
        priorityQueue.offer(1L);
        long res = 1L;
        for (int i = 0; i < index; i++) {
            res = priorityQueue.poll();
            for (int fac : factors) {
                if (!map.containsKey(res * fac)) {
                    map.put(res * fac, 1);
                    priorityQueue.offer(res * fac);
                }
            }
        }
        return (int) res;
    }

    /**
     * 暴力方法，判断是否是丑数，然后从1开始循环
     */
    public static int GetUglyNumber_Solution_1(int index) {
        if (index < 2) return index;
        int result = 1;
        index--;
        while (index != 0) {
            result++;
            if (judgeUglyNumber_1(result)) {
                index--;
            }
        }
        return result;
    }

    public static boolean judgeUglyNumber_1(int number) {
        int res = 0;
        while (number != 1) {
            if (number % 2 == 0) {
                number = number / 2;
            } else if (number % 3 == 0) {
                number = number / 3;
            } else if (number % 5 == 0) {
                number = number / 5;
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * JZ45 把数组排成最小的数
     * 输入一个非负整数数组numbers，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     * 例如输入数组[3，32，321]，则打印出这三个数字能排成的最小数字为321323。
     * 1.输出结果可能非常大，所以你需要返回一个字符串而不是整数
     * 2.拼接起来的数字可能会有前导 0，最后结果不需要去掉前导 0
     */

    public static String PrintMinNumber(int[] numbers) {
        if (numbers.length == 0) return "";
        String[] str = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            str[i] = numbers[i] + "";
        }
        Arrays.sort(str, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o1 + o2).compareTo(o2 + o1);
            }
        });
        String result = "";
        for (String s : str) result += s;
        return result;
    }

    /**
     * Arrays.sort()
     */
    public void testArraySort() {

        Integer[] a = new Integer[]{4, 1, 5, 2, 6, 3, 7};
        Comparator<Integer> comparator = new Comparator<Integer>() {
            /**
             * 小于0向左放，大于0向右放
             * compare 返回负数就是从小到大，正数就是从大到小
             */
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        //按照重载排序
        Arrays.sort(a, comparator);
        System.out.println(Arrays.toString(a));
    }

    /**
     * JZ43 整数中1出现的次数（从1到n整数中1出现的次数）
     * 输入一个整数 n ，求 1～n 这 n 个整数的十进制表示中 1 出现的次数
     * 例如， 1~13 中包含 1 的数字有 1 、 10 、 11 、 12 、 13 因此共出现 6 次
     * 注意：11 这种情况算两次
     */
    public static int count_JZ43 = 0;

    public static int NumberOf1Between1AndN_Solution(int n) {
        for (int i = 1; i <= n; i++) {
            count_JZ43(i);
        }
        return count_JZ43;
    }

    public static void count_JZ43(int number) {

        while (number != 0) {
            if (number % 10 == 1) count_JZ43++;
            number = number / 10;
        }
    }

    /**
     * JZ39 数组中出现次数超过一半的数字
     * 给一个长度为 n 的数组，数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
     * 例如输入一个长度为9的数组[1,2,3,2,2,2,5,4,2]。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。
     */

    public static int MoreThanHalfNum_Solution(int[] array) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            map.put(array[i], map.getOrDefault(array[i], 0) + 1);
            if (map.get(array[i]) > array.length / 2) {
                return array[i];
            }
        }
        return 1;
    }

    /**
     * JZ21 调整数组顺序使奇数位于偶数前面(一)
     * 输入一个长度为 n 整数数组，实现一个函数来调整该数组中数字的顺序，
     * 使得所有的奇数位于数组的前面部分，
     * 所有的偶数位于数组的后面部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
     * <p>
     * 24657 --> 57246
     */

    public static int[] reOrderArray(int[] array) {
        int len = array.length;
        int[] result = new int[len];
        int left = 0;
        int right = len - 1;
        int resultLeft = 0;
        int resultRight = len - 1;
        while (left < len) {
            if (array[left] % 2 == 1) {
                result[resultLeft] = array[left];
                resultLeft++;
            }
            if (array[right] % 2 == 0) {
                result[resultRight] = array[right];
                resultRight--;
            }
            left++;
            right--;
        }
        return result;
    }

    public static int[] reOrderArray_1(int[] array) {
        LinkedList<Integer> odd = new LinkedList();
        LinkedList<Integer> even = new LinkedList();
        for (int i : array) {
            if (i % 2 == 0) {
                even.add(i);
            } else {
                odd.add(i);
            }
        }
        int index = 0;
        while (!odd.isEmpty()) {
            array[index] = odd.pollFirst();
            index++;
        }
        while (!even.isEmpty()) {
            array[index] = even.pollFirst();
            index++;
        }
        return array;
    }

    /**
     * JZ05 替换空格
     * 请实现一个函数，将一个字符串s中的每个空格替换成“%20”。
     * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     */

    public static String replaceSpace(String s) {
        int index = 0;
        while (index < s.length()) {
            if (s.charAt(index) == ' ') {
                s = s.substring(0, index).concat("%20").concat(s.substring(index + 1));
                index = index + 2;
            }
            index++;
        }
        return s;
//        return s.replaceAll(" ", "%20");
    }

    /**
     * JZ50 第一个只出现一次的字符
     * 在一个长为 字符串中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.（从0开始计数）
     */

    public static int FirstNotRepeatingChar(String str) {
        HashMap<Character, Integer> map = new HashMap();
        LinkedList list = new LinkedList();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            list.offerLast(c);
            if (!map.containsKey(c)) {
                map.put(c, i);
            } else {
                map.put(c, -1);
            }
            while (!list.isEmpty() && map.get(list.peekFirst()) == -1) {
                list.pollFirst();
            }
        }
        return map.get(list.peekFirst()) == null ? -1 : map.get(list.peekFirst());
    }

    public static int FirstNotRepeatingChar_LinkedHashMap(String str) {
        LinkedHashMap<Character, Integer> map = new LinkedHashMap();
        for (char temp : str.toCharArray()) {
            if (!map.containsKey(temp)) {
                map.put(temp, 1);
            } else {
                map.put(temp, map.get(temp) + 1);
            }
        }
        for (char c : map.keySet()) {
            if (map.get(c) == 1) return str.indexOf(c);
        }
        return -1;
    }

    /**
     * JZ66 构建乘积数组
     * 给定一个数组 A[0,1,...,n-1] ,请构建一个数组 B[0,1,...,n-1] ,
     * 其中 B 的元素 B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1](除 A[i] 以外的全部元素的的乘积)。程序中不能使用除法。
     * 注意：规定
     * B[0] = A[1] * A[2] * ... * A[n-1]
     * B[n-1] = A[0] * A[1] * ... * A[n-2]
     * 对于 A 长度为 1 的情况，B 无意义，故而无法构建，用例中不包括这种情况。
     */

    /**
     * 分析数据，矩阵图分为上下两部分
     * 下面B[0]=1,B[i]=B[i-1]*A[i-1]
     * 上面B[i]=1,B[i-2]=A[i-2]+1到A[i]的阶乘,定义一个数字记录阶乘结果
     */
    public static int[] multiply(int[] A) {
        int lengthA = A.length;
        int[] B = new int[lengthA];
        B[0] = 1;
        for (int i = 1; i < lengthA; i++) {
            B[i] = B[i - 1] * A[i - 1];
        }
        int up = 1;
        for (int i = lengthA - 1; i >= 0; i--) {
            B[i] = B[i] * up;
            up = up * A[i];
        }
        return B;
    }

    /**
     * 两次循环
     */
    public static int[] multiply_violence(int[] A) {
        int lengthA = A.length;
        int[] B = new int[lengthA];
        for (int i = 0; i < lengthA; i++) {
            B[i] = 1;
        }
        for (int i = 0; i < lengthA; i++) {
            for (int j = 0; j < lengthA; j++) {
                if (i != j) {
                    B[i] = B[i] * A[j];
                }
            }
        }
        return B;
    }

    /**
     * JZ20 表示数值的字符串
     * 请实现一个函数用来判断字符串str是否表示数值（包括科学计数法的数字，小数和整数）。
     * <p>
     * 科学计数法的数字(按顺序）可以分成以下几个部分:
     * 1.若干空格
     * 2.一个整数或者小数
     * 3.（可选）一个 'e' 或 'E' ，后面跟着一个整数(可正可负)
     * 4.若干空格
     * <p>
     * 小数（按顺序）可以分成以下几个部分：
     * 1.若干空格
     * 2.（可选）一个符号字符（'+' 或 '-'）
     * 3. 可能是以下描述格式之一:
     * 3.1 至少一位数字，后面跟着一个点 '.'
     * 3.2 至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字
     * 3.3 一个点 '.' ，后面跟着至少一位数字
     * 4.若干空格
     * <p>
     * 整数（按顺序）可以分成以下几个部分：
     * 1.若干空格
     * 2.（可选）一个符号字符（'+' 或 '-')
     * 3. 至少一位数字
     * 4.若干空格
     * <p>
     * 例如，字符串["+100","5e2","-123","3.1416","-1E-16"]都表示数值。
     * 但是["12e","1a3.14","1.2.3","+-5","12e+4.3"]都不是数值。
     */

    public static boolean isNumeric(String str) {
        return isNumeric_1(str) || isNumeric_2(str) || isNumeric_3(str) || isNumeric_4(str);
    }

    /**
     * 是否是 不带符号的整数
     */
    public static boolean isNumeric_1(String str) {
        str = str.trim();
        if (str.length() == 0) return false;
        int index = 0;
        while (index < str.length() && str.charAt(index) <= 57 && str.charAt(index) >= 48) {
            index++;
        }
        if (index == str.length()) return true;
        return false;
    }

    /**
     * 是否是带符号的整数
     */
    public static boolean isNumeric_2(String str) {
        str = str.trim();
        if (str.length() == 0) return false;
        if (str.charAt(0) != '+' && str.charAt(0) != '-') return false;
        str = str.substring(1);
        return isNumeric_1(str);
    }

    /**
     * 判断是否是小数
     */
    public static boolean isNumeric_3(String str) {
        str = str.trim();
        if (str.length() == 0) return false;
        int index = str.indexOf('.');
        if (index == -1) return false;
        if (index == 0) return isNumeric_1(str.substring(index + 1));
        // pre . suf ,pre 为 ""/不带符号整数/带符号的整数/单独符号，suf为 不带符号整数/""
        if (index == 1 && (str.charAt(0) == '-' || str.charAt(0) == '+')) return isNumeric_1(str.substring(index + 1));
        return (isNumeric_1(str.substring(0, index)) || isNumeric_2(str.substring(0, index)))
                && (index == str.length() - 1 || isNumeric_1(str.substring(index + 1)));
    }

    /**
     * 判断是否是科学计数法
     */
    public static boolean isNumeric_4(String str) {
        str = str.trim();
        if (str.length() == 0) return false;
        int index = str.indexOf('e') > -1 ? str.indexOf('e') : str.indexOf('E');
        if (index == -1) return false;
        if (index == 0) return false;
        // pre e/E suf ,pre 为 不带符号整数/带符号的整数/小数 suf为 不带符号整数
        return (isNumeric_1(str.substring(0, index)) || isNumeric_2(str.substring(0, index)) || isNumeric_3(str.substring(0, index)))
                && (isNumeric_1(str.substring(index + 1)) || isNumeric_2(str.substring(index + 1)));
    }

    /**
     * JZ67 把字符串转换成整数(atoi)
     * 写一个函数 StrToInt，实现把字符串转换成整数这个功能。不能使用 atoi 或者其他类似的库函数。传入的字符串可能有以下部分组成:
     * 1.若干空格
     * 2.（可选）一个符号字符（'+' 或 '-'）
     * 3. 数字，字母，符号，空格组成的字符串表达式
     * 4. 若干空格
     * <p>
     * 1.去掉无用的前导空格
     * 2.第一个非空字符为+或者-号时，作为该整数的正负号，如果没有符号，默认为正数
     * 3.判断整数的有效部分：
     * 3.1 确定符号位之后，与之后面尽可能多的连续数字组合起来成为有效整数数字，如果没有有效的整数部分，那么直接返回0
     * 3.2 将字符串前面的整数部分取出，后面可能会存在存在多余的字符(字母，符号，空格等)，这些字符可以被忽略，它们对于函数不应该造成影响
     * 3.3 整数超过 32 位有符号整数范围 [−2^31,  2^31−1] ，需要截断这个整数，使其保持在这个范围内。
     * 3.4 具体来说，小于 −2^31的整数应该被调整为 −2^31 ，大于 2^31−1 的整数应该被调整为 2^31−1
     * 4.去掉无用的后导空格
     */

    public static int StrToInt(String s) {
        s = s.trim();
        if (s.length() == 0) return 0;
        char a = '+';
        int index = 0;
        String resultNumber = "";
        long result = 0;
        if (s.charAt(0) == '-' || s.charAt(0) == '+') {
            a = s.charAt(0);
            s = s.substring(1);
        }
        while (index < s.length() && s.charAt(index) == '0') {
            index++;
        }
        while (index < s.length() && s.charAt(index) <= 57 && s.charAt(index) >= 48) {
            resultNumber = resultNumber.concat(String.valueOf(s.charAt(index)));
            index++;
        }
        resultNumber = resultNumber.length() >= 13 ? resultNumber.substring(0, 13) : resultNumber;
        if (resultNumber.length() != 0) {
            if (a == '+') {
                result = Long.valueOf(resultNumber);
            } else {
                result = 0 - Long.valueOf(resultNumber);
            }
        }
        return result > Integer.MAX_VALUE ? Integer.MAX_VALUE : (result < Integer.MIN_VALUE ? Integer.MIN_VALUE : ((int) result));
    }

    /**
     * JZ61 扑克牌顺子
     * 现在有2副扑克牌，从扑克牌中随机五张扑克牌，我们需要来判断一下是不是顺子。
     * 有如下规则：
     * 1. A为1，J为11，Q为12，K为13，A不能视为14
     * 2. 大、小王为 0，0可以看作任意牌
     * 3. 如果给出的五张牌能组成顺子（即这五张牌是连续的）就输出true，否则就输出false。
     * 4.数据保证每组5个数字，每组最多含有4个零，数组的数取值为 [0, 13]
     */

    /**
     * 两副牌，最多4张0，其余都是两个1-13,
     * 0除外，1-13重复，false
     * 1-13不重复5种情况
     * a. 0个0,Max-Min=4,不重复的话，Max-Min<=4,不可能小于4，因此小于也就是下面的类型
     * b. 1个0,Max-Min<=4
     * c. 2个0,Max-Min<=4
     * d. 3个0,Max-Min<=4
     * e. 4个0,不用判断，Max-Min<=4也肯定满足
     */

    public static boolean IsContinuous(int[] numbers) {
        //判断是否重复
        int[] a = new int[13];
        int max = 0;
        int min = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != 0) {
                a[numbers[i] - 1] = ++a[numbers[i] - 1];
                if (a[numbers[i] - 1] == 2) return false;
                if (min == 0) {
                    min = numbers[i];
                }
                min = numbers[i] == 0 ? numbers[i] : (numbers[i] < min ? numbers[i] : min);
                max = numbers[i] > max ? numbers[i] : max;
            }
        }
        if (max - min <= 4) return true;
        return false;
    }

    /**
     * JZ29 顺时针打印矩阵
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字
     * <p>
     * 循环 上-右-下-左 每次循环前判断坐标是否符合继续循环的要求
     */
    private static ArrayList<Integer> arrayList_JZ29 = new ArrayList<>();

    public static ArrayList<Integer> printMatrix(int[][] matrix) {

        int x = matrix[0].length;
        int y = matrix.length;
        for (int i = 0; i < Math.min(x, y) / 2 + Math.min(x, y) % 2; i++) {
            printMatrix_JZ29(matrix, i, x - i - 1, i, y - i - 1);
        }
        return arrayList_JZ29;
    }

    public static void printMatrix_JZ29(int[][] matrix, int left_x, int right_x, int up_y, int down_y) {
        //上
        for (int i = left_x; i <= right_x; i++) {
            arrayList_JZ29.add(matrix[up_y][i]);
        }
        //右
        if (down_y - up_y > 0) {
            for (int i = up_y + 1; i <= down_y; i++) {
                arrayList_JZ29.add(matrix[i][right_x]);
            }
        }
        //下
        if (down_y - up_y > 0 && right_x - left_x > 0) {
            for (int i = right_x - 1; i >= left_x; i--) {
                arrayList_JZ29.add(matrix[down_y][i]);
            }
        }
        //左
        if (down_y - up_y > 1 && right_x - left_x > 0) {
            for (int i = down_y - 1; i > up_y; i--) {
                arrayList_JZ29.add(matrix[i][left_x]);
            }
        }
    }

    /**
     * JZ56 数组中只出现一次的两个数字
     * 一个整型数组里除了两个数字只出现一次，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字
     * <p>
     * 异或之后，根据第一个不为0的位数分类数组
     */
    public static int[] FindNumsAppearOnce(int[] array) {
        int diff = 0;
        int[] result = new int[2];
        for (int i : array) diff ^= i;
        int index = 0;
        while (diff != 0) {
            diff >>= 1;
            index++;
        }
        int left = 0;
        int right = 0;
        index = 1 << (index - 1);
        for (int i : array) {
            if ((i & index) == 0) left ^= i;
            else right ^= i;
        }
        if (left > right) {
            result[0] = right;
            result[1] = left;
        } else {
            result[0] = left;
            result[1] = right;
        }
        return result;
    }

    /**
     * JZ41 数据流中的中位数
     * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
     * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，
     * 使用GetMedian()方法获取当前读取数据的中位数。
     */

    private static ArrayList<Integer> JZ41 = new ArrayList<Integer>();

    public void Insert(Integer num) {
        JZ41.add(num);
    }

    public Double GetMedian() {
        Integer[] a = JZ41.toArray(new Integer[JZ41.size()]);
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i];
        }
        heapSort(b);
        int len = b.length;
        if (len % 2 == 0) return new Double(b[len / 2 - 1] + b[len / 2]) / 2;
        else return new Double(b[len / 2]);
    }

    /**
     * JZ40
     * 最小的K个数
     * 给定一个长度为 n 的可能有重复值的数组，找出其中不去重的最小的 k 个数。
     * 例如数组元素是4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4(任意顺序皆可)。
     * 数据范围：0<=k,n≤10000，数组中每个数的大小0≤val≤1000
     * 要求：空间复杂度 O(n),时间复杂度 O(nlogn)
     */

    /**
     * 利用优先队列的大顶堆，PriorityQueue默认小顶堆，(o1, o2) -> o2.compareTo(o1) 可以变成大顶堆
     */
    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (input.length < k || k == 0) return arrayList;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>((o1, o2) -> o2.compareTo(o1));
        for (int i = 0; i < k; i++) {
            priorityQueue.offer(input[i]);
        }
        for (int i = k; i < input.length; i++) {
            if (input[i] < priorityQueue.peek()) {
                priorityQueue.poll();
                priorityQueue.offer(input[i]);
            }
        }
        for (Integer i : priorityQueue) arrayList.add(i);
        return arrayList;
    }

    /**
     * 小顶堆
     */
    public static ArrayList<Integer> GetLeastNumbers_Solution_1(int[] input, int k) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (input.length < k || k == 0) return arrayList;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
        for (int i = 0; i < input.length; i++) {
            priorityQueue.offer(input[i]);
        }
        for (int i = 0; i < k; i++) {
            arrayList.add(priorityQueue.poll());
        }
        return arrayList;
    }

    /**
     * 使用堆排序，大顶堆
     */
    public static ArrayList<Integer> GetLeastNumbers_Solution_heapSort(int[] input, int k) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        heapSort(input);
        for (int i = 0; i < k; i++) {
            arrayList.add(input[i]);
        }
        return arrayList;
    }

    /**
     * JZ51 数组中的逆序对
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组,求出这个数组中的逆序对的总数P。
     * 并将P对1000000007取模的结果输出。 即输出P mod 1000000007
     * 题目保证输入的数组中没有的相同的数字
     * 要求：时间复杂度 O(nlogn)
     */

    public static int InversePairs(int[] array) {
        int n = array.length;
        int[] res = new int[n];
        return inversePairs_MergeSort(0, n - 1, array, res);
    }

    public static int mod = 1000000007;

    /**
     * 归并排序过程，统计交换次数
     */
    public static int inversePairs_MergeSort(int left, int right, int[] data, int[] temp) {
        //停止划分
        if (left >= right)
            return 0;
        //取中间
        int mid = (left + right) / 2;
        //左右划分合并
        int res = inversePairs_MergeSort(left, mid, data, temp) + inversePairs_MergeSort(mid + 1, right, data, temp);
        //防止溢出
        res %= mod;
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++)
            temp[k] = data[k];
        for (int k = left; k <= right; k++) {
            if (i == mid + 1)
                data[k] = temp[j++];
            else if (j == right + 1 || temp[i] <= temp[j])
                data[k] = temp[i++];
                //左边比右边大，答案增加
            else {
                data[k] = temp[j++];
                // 统计逆序对,如果左边的大于右边的，并且因为是升序的两个序列，所有左边的所有的左边的数字都大于这个数字，
                res += mid - i + 1;
            }
        }
        return res % mod;
    }

    /**
     * 暴力方法n²复杂度，没通过
     */
    public int InversePairs_violence(int[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) return count;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[i]) count++;
            }
        }
        return count;
    }

    /**
     * JZ3 数组中重复的数字
     * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。
     * 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
     * 例如，如果输入长度为7的数组[2,3,1,0,2,5,3]，那么对应的输出是2或者3。存在不合法的输入的话输出-1
     * <p>
     * 解法：将数字本身作为下标，如果里面的值等于2，则证明加了两次，则输出
     */

    public int duplicate(int[] numbers) {
        int[] a = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            a[numbers[i]]++;
            if (a[numbers[i]] == 2) return numbers[i];
        }
        return -1;
    }

    /**
     * 集合相同元素去重，如果add之后，size没有增加，则输出add的元素
     */
    public int duplicate_set(int[] numbers) {
        Set<Integer> set = new HashSet<>();
        int size = 0;
        for (Integer i : numbers) {
            set.add(i);
            if (set.size() == size) return i;
            size = set.size();
        }
        return -1;
    }

    /**
     * JZ13 机器人的运动范围
     * 地上有一个 rows 行和 cols 列的方格,坐标从 [0,0] 到 [rows-1,cols-1] 。
     * 一个机器人从坐标 [0,0] 的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
     * 但是不能进入行坐标和列坐标的数位之和大于 threshold 的格子。
     * <p>
     * DFS
     */

    static int count_JZ13 = 0;

    public static int movingCount(int threshold, int rows, int cols) {
        boolean[][] robots = new boolean[rows][cols];
        movingCount_DFS(0, 0, rows, cols, robots, threshold);
        return count_JZ13;
    }

    public static void movingCount_DFS(int h, int w, int height, int weight, boolean[][] robots, int threshold) {
        if (h < 0 || w < 0 || h >= height || w >= weight || robots[h][w] == true || (cal(h) + cal(w)) > threshold)
            return;
        count_JZ13++;
        robots[h][w] = true;
        movingCount_DFS(h, w + 1, height, weight, robots, threshold);
        movingCount_DFS(h - 1, w, height, weight, robots, threshold);
        movingCount_DFS(h, w - 1, height, weight, robots, threshold);
        movingCount_DFS(h + 1, w, height, weight, robots, threshold);
    }

    static int cal(int n) {
        int sum = 0;
        //连除法算出每一位
        while (n != 0) {
            sum += (n % 10);
            n /= 10;
        }
        return sum;
    }

    /**
     * JZ12 矩阵中的路径
     * dfs
     * 请设计一个函数，用来判断在一个n乘m的矩阵中是否存在一条包含某长度为len的字符串所有字符的路径。
     * 路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
     * 如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。
     * <p>
     * DFS当成四叉树，每次四个方向
     * 每次经过的点设置成 一个不存在的字符，这样就不会重复进入
     * 当前路径下返回出结果后，还原数组，这样就不会因为修改导致其他方向也走不通
     */

    public static boolean hasPath(char[][] matrix, String word) {
        boolean flag = false;
        int height = matrix.length;
        int weight = matrix[0].length;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < weight; w++) {
                if (matrix[h][w] == word.charAt(0)) {
                    flag = flag || hasPath_DFS(h, w, height, weight, matrix, word);
                    if (flag) return flag;
                }
            }
        }
        return flag;
    }

    private static boolean hasPath_DFS(int h, int w, int height, int weight, char[][] matrix, String word) {
        if (word.length() == 0) return true;
        if (h < 0 || w < 0 || h >= height || w >= weight || word.substring(0, 1).charAt(0) != matrix[h][w]) {
            return false;
        }
        char temp = matrix[h][w];
        matrix[h][w] = '.';
        //返回上右下左的返回结果
        boolean flag =
                hasPath_DFS(h - 1, w, height, weight, matrix, word.substring(1)) ||
                        hasPath_DFS(h, w + 1, height, weight, matrix, word.substring(1)) ||
                        hasPath_DFS(h + 1, w, height, weight, matrix, word.substring(1)) ||
                        hasPath_DFS(h, w - 1, height, weight, matrix, word.substring(1));
        matrix[h][w] = temp;
        return flag;
    }

    /**
     * JZ59 滑动窗口的最大值
     * 给定一个长度为 n 的数组 nums 和滑动窗口的大小 size ，找出所有滑动窗口里数值的最大值。
     * <p>
     * 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
     * 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个： {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
     * <p>
     * 解法1：遍历数组获取存着滑动窗口的list<List<Integer>>，继续遍历List<Integer>找到最大值
     * 解法2：
     * 1.创建一个是双向队列
     * 2.先将第一个最大值压入队列(如果加入的下标的值比前面的大，则依次弹出，因为比前面的下标代表的值大，所以此次周期内不可能竞争得过当前下标)
     * 3.最前面记录最大值的下标，如果过期(i-size+1>index)则弹出首位
     * 4.如果加入的下标的值比前面的大，则依次弹出，因为比前面的下标代表的值大，所以此次周期内不可能竞争得过当前下标
     */

    public static ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> list = new ArrayList<>();
        Deque<Integer> deque = new LinkedList<Integer>();
        for (int i = 0; i < size; i++) {
            while (!deque.isEmpty() && num[deque.peekLast()] < num[i]) deque.pollLast();
            deque.add(i);
        }
        for (int i = size; i < num.length; i++) {
            list.add(num[deque.peekFirst()]);
            while (!deque.isEmpty() && deque.peekFirst() < (i - size + 1)) deque.pollFirst();
            while (!deque.isEmpty() && num[deque.peekLast()] < num[i]) deque.pollLast();
            deque.add(i);
        }
        list.add(num[deque.peekFirst()]);
        return list;
    }

    public static ArrayList<Integer> maxInWindows_violence(int[] num, int size) {
        ArrayList<Integer> list = new ArrayList<>();
        int max = num[0];
        for (int i = 0; i < num.length - size + 1; i++) {
            for (int j = i; j < i + size; j++) {
                if (num[j] >= max) max = num[j];
                if (j == i + size - 1) {
                    list.add(max);
                    if (i + 1 < num.length) max = num[i + 1];
                }
            }
        }
        return list;
    }

    /**
     * JZ73 翻转单词序列
     * 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。
     * 同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，但却读不懂它的意思。
     * 例如，“nowcoder. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
     * 正确的句子应该是“I am a nowcoder.”。Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？
     */

    public static String ReverseSentence(String str) {
        if (str.length() == 0) return "";
        Stack stack = new Stack();
        str = str + " ";
        int index = 0;
        int end = 0;
        while (index != str.length()) {
            if (str.charAt(index) == ' ') {
                stack.push(str.substring(end, index));
                end = index + 1;
            }
            index++;
        }
        str = "";
        while (!stack.isEmpty()) {
            str = str + " " + stack.pop();
        }
        return str.substring(1);
    }

    /**
     * JZ31 栈的压入、弹出序列
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。
     * 假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。
     * 3. pushV 的所有数字均不相同
     * <p>
     * 解法：两个栈，第二个全部压入，第一个压入的过程中如果碰到相同的则都退栈，直到不相同为止，如果stack1到了最后没有退完栈，则false
     */
    public static boolean IsPopOrder(int[] pushA, int[] popA) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        for (int i = 0; i < popA.length; i++) {
            stack2.push(popA[popA.length - 1 - i]);
        }
        for (int i = 0; i < pushA.length; i++) {
            if (pushA[i] != stack2.peek()) {
                stack1.push(pushA[i]);
            } else {
                stack2.pop();
                while (!stack1.isEmpty() && !stack2.isEmpty() && stack2.peek().equals(stack1.peek())) {
                    stack2.pop();
                    stack1.pop();
                }
            }
        }
        if (stack1.isEmpty() && stack2.isEmpty()) return true;
        else return false;
    }

    /**
     * JZ30 包含min函数的栈
     * 定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的 min 函数，输入操作时保证 pop、top 和 min 函数操作时，栈中一定有元素。
     * <p>
     * 此栈包含的方法有：
     * push(value):将value压入栈中
     * pop():弹出栈顶元素
     * top():获取栈顶元素
     * min():获取栈中最小元素
     * <p>
     * 第一个正常pop，top，push
     * 第二个记录最小值，如果没有则重复压栈顶元素，有的话就压最小的进栈
     * pop的话，同时pop，因为每一次push的时候同层都是最小的，所以pop掉也是最小的，省去了比较
     * <p>
     * 第二种：s2只存最小的，每次pop都比较
     */
    //最上面放min
    static Stack<Integer> stack1_JZ30_1 = new Stack<Integer>();
    static Stack<Integer> stack1_JZ30_2 = new Stack<Integer>();

    public void push(int node) {
        stack1_JZ30_1.push(node);
        if (stack1_JZ30_2.isEmpty() || stack1_JZ30_2.peek() >= node) stack1_JZ30_2.push(node);
    }

    public void pop() {
        int pop = stack1_JZ30_1.pop();
        if (pop == stack1_JZ30_2.peek()) stack1_JZ30_2.pop();
    }

    public int top() {
        return stack1_JZ30_1.peek();
    }

    public int min() {
        return stack1_JZ30_2.peek();
    }

    /**
     * JZ9 用两个栈实现队列
     * 用两个栈来实现一个队列，使用n个元素来完成 n 次在队列尾部插入整数(push)和n次在队列头部删除整数(pop)的功能。
     * 队列中的元素为int类型。保证操作合法，即保证pop操作时队列内已有元素。
     * <p>
     * Queue先进先出:offer(add),poll(pop) LinkedList
     * Stack先进后出:push,peek,pop
     */
    static Stack<Integer> stack1_JZ9 = new Stack<Integer>();
    static Stack<Integer> stack2_JZ9 = new Stack<Integer>();

    public static void push_JZ9(int node) {
        stack1_JZ9.push(node);
    }

    //弹出首位
    public static int pop_JZ9() {
        if (stack2_JZ9.size() == 0) {
            while (stack1_JZ9.size() > 0) {
                stack2_JZ9.push(stack1_JZ9.pop());
            }
        }
        return stack2_JZ9.pop();
    }

    //压入
    public static void push_1(int node) {

        while (stack2_JZ9 != null && stack2_JZ9.size() > 0) {
            stack1_JZ9.push(stack2_JZ9.pop());
        }
        stack1_JZ9.push(node);
        while (stack1_JZ9 != null && stack1_JZ9.size() > 0) {
            stack2_JZ9.push(stack1_JZ9.pop());
        }
    }

    //弹出首位
    public static int pop_1() {
        return stack2_JZ9.pop();
    }

    /**
     * JZ37 序列化二叉树
     * 请实现两个函数，分别用来序列化和反序列化二叉树，不对序列化之后的字符串进行约束，但要求能够根据序列化之后的字符串重新构造出一棵与原二叉树相同的树。
     * 二叉树的序列化(Serialize)是指：把一棵二叉树按照某种遍历方式的结果以某种格式保存为字符串，从而使得内存中建立起来的二叉树可以持久保存。序列化可以基于先序、中序、后序、层序的二叉树等遍历方式来进行修改，序列化的结果是一个字符串，序列化时通过 某种符号表示空节点（#）
     * 二叉树的反序列化(Deserialize)是指：根据某种遍历顺序得到的序列化字符串结果str，重构二叉树。
     * <p>
     * 中序inOrder Left Root Right
     * ##
     */
    static int index = 0;

    static String Serialize(TreeNode root) {
        if (root == null) return "#";
        return root.val + "!" + Serialize(root.left) + Serialize(root.right);
    }

    static TreeNode Deserialize(String str) {
        if (str.charAt(index) == '#') {
            index++;
            return null;
        }
        int val = Integer.valueOf(String.valueOf(str.charAt(index)));
        while (str.charAt(index + 1) != '!') {
            index++;
            val = 10 * val + Integer.valueOf(String.valueOf(str.charAt(index)));
        }
        TreeNode node = new TreeNode(val);
        index++;
        index++;
        node.left = Deserialize(str);
        node.right = Deserialize(str);
        return node;
    }


    /**
     * JZ68 二叉搜索树的最近公共祖先
     * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     * <p>
     * 1.左右子树各一个，一个大于等于
     */

    public static int lowestCommonAncestor(TreeNode root, int p, int q) {
        if (root == null) return -1;
        if ((p >= root.val && q <= root.val) || (p <= root.val && q >= root.val)) return root.val;
        if (p < root.val && q < root.val) return lowestCommonAncestor(root.left, p, q);
        else return lowestCommonAncestor(root.right, p, q);
    }

    /**
     * JZ86 在二叉树中找到两个节点的最近公共祖先
     * 给定一棵二叉树(保证非空)以及这棵树上的两个节点对应的val值 o1 和 o2，请找到 o1 和 o2 的最近公共祖先节点。
     * <p>
     * 1.非空
     * 2.每个节点val不相同
     * 3.如果一个节点为另一个节点的子节点，则返回父节点
     * 时间复杂度太高！
     */

    public static int lowestCommonAncestor_JZ86(TreeNode root, int p, int q) {
        if (root == null) return -1;
        if (root.val == p || root.val == q) return root.val;
        int left = lowestCommonAncestor_JZ86(root.left, p, q);
        int right = lowestCommonAncestor_JZ86(root.right, p, q);
        if (left == -1) return right;
        if (right == -1) return left;
        return root.val;
    }


    public static int lowestCommonAncestor_2(TreeNode root, int o1, int o2) {
        return helper(root, o1, o2).val;
    }

    //1.如果左右都有返回值，则证明该节点是最近公共子节点
    //2.最终返回空，说明遍历到最后没有符合的节点，说明在另一边的树里
    public static TreeNode helper(TreeNode root, int o1, int o2) {
        if (root == null) return null;
        if (root.val == o1 || root.val == o2) return root;

        TreeNode left = helper(root.left, o1, o2);
        TreeNode right = helper(root.right, o1, o2);
        //如果left为空，说明这两个节点在root结点的右子树上，我们只需要返回右子树查找的结果即可
        if (left == null) return right;
        //同上
        if (right == null) return left;
        //如果left和right都不为空，说明这两个节点一个在root的左子树上一个在root的右子树上，
        //我们只需要返回cur结点即可。
        return root;
    }

    /**
     * JZ84 二叉树中和为某一值的路径(三)
     * 给定一个二叉树root和一个整数值 sum ，求该树有多少路径的的节点值之和等于 sum 。
     * 1.该题路径定义不需要从根节点开始，也不需要在叶子节点结束，但是一定是从父亲节点往下到孩子节点
     * <p>
     * 不以根节点开始可以视为，以任何节点作为跟节点，计算sum直到符合。
     * 前序遍历所有节点，寻找符合的路径
     */
    public static int count_JZ84 = 0;

    //计数，DFS
    public static void FindPath_notRootToLeaf_count_JZ84(TreeNode root, int sum) {
        sum = sum - root.val;
        if (sum == 0) count_JZ84++;
        if (root.left != null) FindPath_notRootToLeaf_count_JZ84(root.left, sum);
        if (root.right != null) FindPath_notRootToLeaf_count_JZ84(root.right, sum);
    }

    public static int FindPath_notRootToLeaf(TreeNode root, int sum) {
        if (root == null) return 0;
        FindPath_notRootToLeaf_count_JZ84(root, sum);
        if (root.left != null) FindPath_notRootToLeaf(root.left, sum);
        if (root.right != null) FindPath_notRootToLeaf(root.right, sum);
        return count_JZ84;
    }


    /**
     * JZ78 把二叉树打印成多行
     * 给定一个节点数为 n 二叉树，要求从上到下按层打印二叉树的 val 值，同一层结点从左至右输出，每一层输出一行，将输出的结果存放到一个二维数组中返回。
     * 例如：
     * 给定的二叉树是{1,2,3,#,#,4,5}
     * BFS
     */
    public static ArrayList<ArrayList<Integer>> Print_left_right(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (pRoot == null) return lists;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(pRoot);
        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> list = new ArrayList<>();
            while (size != 0) {
                TreeNode temp = queue.poll();
                list.add(temp.val);
                if (temp.left != null) queue.offer(temp.left);
                if (temp.right != null) queue.offer(temp.right);
                size--;
            }
            lists.add(list);
        }
        return lists;
    }

    /**
     * JZ28 对称的二叉树
     * <p>
     * 给定一棵二叉树，判断其是否是自身的镜像（即：是否对称）
     * 1
     * 2   2
     * 4 5 5 4
     */
    public static boolean isSymmetrical(TreeNode pRoot) {
        return recursion_isSymmetrical(pRoot, pRoot);
    }

    //  两边val必须相同，其中一个为null，则不符合。
    //  都空符合，因为两个都没有后续了，空也是对称的。
    public static boolean recursion_isSymmetrical(TreeNode pRoot, TreeNode pRoot1) {
        if (pRoot == null && pRoot1 == null) return true;
        if (pRoot == null || pRoot1 == null || pRoot1.val != pRoot.val) return false;
        return recursion_isSymmetrical(pRoot.left, pRoot1.right) && recursion_isSymmetrical(pRoot.right, pRoot1.left);
    }


    /**
     * JZ8 二叉树的下一个结点
     * 给定一个二叉树其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，
     * 同时包含指向父结点的next指针。下图为一棵有9个节点的二叉树。树中从父节点指向子节点的指针用实线表示，从子节点指向父节点的用虚线表示
     * <p>
     * 返回传入的子树根节点的下一个节点，后台会打印输出这个节点
     * 1.找到根节点
     * 2.中序后寻找下一个
     */
    public static TreeLinkNode GetNext(TreeLinkNode pNode) {
        TreeLinkNode temp = pNode;
        List<TreeLinkNode> list = new LinkedList<>();
        while (temp.next != null) {
            temp = temp.next;
        }
        GetNext_Inorder(temp, list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) == pNode) return list.get(i + 1);
        }
        return null;
    }

    public static void GetNext_Inorder(TreeLinkNode root, List<TreeLinkNode> list) {
        if (root.left != null) GetNext_Inorder(root.left, list);
        list.add(root);
        if (root.right != null) GetNext_Inorder(root.right, list);
    }

    /**
     * JZ79 判断是不是平衡二叉树
     * dfs
     * 输入一棵节点数为 n 二叉树，判断该二叉树是否是平衡二叉树。
     * 在这里，我们只需要考虑其平衡性，不需要考虑其是不是排序二叉树
     * 平衡二叉树（Balanced Binary Tree），
     * 具有以下性质：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
     */

    public static boolean IsBalanced_Solution(TreeNode root) {
        if (root == null) return true;
        return IsBalanced_Solution(root.right) && IsBalanced_Solution(root.left) && Math.abs(DeepOfBalance(root.left) - DeepOfBalance(root.right)) < 2;
    }

    public static int DeepOfBalance(TreeNode root) {
        if (root == null) return 0;
        return Math.max(DeepOfBalance(root.left), DeepOfBalance(root.right)) + 1;
    }

    /**
     * JZ36 二叉搜索树与双向链表
     * 题目
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。如下图所示
     * <p>
     * 数据范围：输入二叉树的节点数 0≤n≤1000，二叉树中每个节点的值 0≤val≤1000
     * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
     * 当转化完成以后，树中节点的左指针需要指向前驱，树中节点的右指针需要指向后继
     * <p>
     * 中序遍历,将树中序遍历，然后依次前驱接后驱
     */
    static List<TreeNode> arrayList = new ArrayList<>();

    public static TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) return pRootOfTree;
        recursion_Convert(pRootOfTree);
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == arrayList.size() - 1) {
                arrayList.get(i).right = null;
            } else {
                arrayList.get(i).right = arrayList.get(i + 1);
                arrayList.get(i + 1).left = arrayList.get(i);
            }
        }

        return arrayList.get(0);
    }

    public static void recursion_Convert(TreeNode pRootOfTree) {
        if (pRootOfTree.left != null) recursion_Convert(pRootOfTree.left);
        arrayList.add(pRootOfTree);
        if (pRootOfTree.right != null) recursion_Convert(pRootOfTree.right);
    }

    /**
     * JZ34 二叉树中和为某一值的路径(二)
     * 输入一颗二叉树的根节点root和一个整数expectNumber，找出二叉树中结点值的和为expectNumber的所有路径。
     * <p>
     * 要求是必须根节点到叶子节点
     * DFS
     */
    static ArrayList<ArrayList<Integer>> lists_FindPath = new ArrayList<>();

    public static ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int expectNumber) {
        if (root == null) return lists_FindPath;
        ArrayList<Integer> list = new ArrayList<>();
        recursion_FindPath(root, list, expectNumber);
        return lists_FindPath;
    }

    public static void recursion_FindPath(TreeNode root, ArrayList<Integer> list, int expectNumber) {
        int balance = expectNumber - root.val;
        list.add(root.val);
        if (balance == 0 && root.left == null && root.right == null) {
            ArrayList<Integer> temp = new ArrayList<>(list);
            lists_FindPath.add(list);
        }
        ArrayList<Integer> left = new ArrayList<>(list);
        ArrayList<Integer> right = new ArrayList<>(list);
        if (root.left != null) recursion_FindPath(root.left, left, balance);
        if (root.right != null) recursion_FindPath(root.right, list, balance);
    }


    /**
     * JZ82 二叉树中和为某一值的路径(一)
     * <p>
     * 给定一个二叉树root和一个值 sum ，判断是否有从根节点到叶子节点的节点值之和等于 sum 的路径。
     * 1.该题路径定义为从树的根结点开始往下一直到叶子结点所经过的结点
     * 2.叶子节点是指没有子节点的节点
     * 3.路径只能从父节点到子节点，不能从子节点到父节点
     * <p>
     * 树上的节点数满足  0 ≤ n ≤ 10000
     * 每 个节点的值都满足 val ≤ 1000
     */
    public static boolean hasPathSum(TreeNode root, int sum) {
        // 如果root左右为空，且差额等于0，则返回true
        // 否则，返回根节点的左右节点的hasPathSum的或
        if (root == null) return false;
        int count = sum - root.val;
        if (root.right == null && root.left == null && count == 0) return true;
        return hasPathSum(root.left, count) || hasPathSum(root.right, count);
    }

    /**
     * JZ33 二叉搜索树的后序遍历序列
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
     * 如果是则返回 true ,否则返回 false 。假设输入的数组的任意两个数字都互不相同。
     * <p>
     * 提示：
     * 1.二叉搜索树是指父亲节点大于左子树中的全部节点，但是小于右子树中的全部节点的树。
     * 2.该题我们约定空树不是二叉搜索树
     * 3.后序遍历是指按照 “左子树-右子树-根节点” 的顺序遍历
     * </p>
     */
    public static boolean VerifySquenceOfBST_Recursion(int[] sequence, int begin, int end) {
        if (end - begin <= 1) return true;
        int root = sequence[end];
        int index = 0;
        for (int i = begin; i <= end; i++) {
            if (sequence[i] >= root) {
                index = i;
                break;
            }
        }
        for (int i = index; i < end; i++) {
            if (sequence[i] < root) {
                return false;
            }
        }
        return VerifySquenceOfBST_Recursion(sequence, begin, index - 1) && VerifySquenceOfBST_Recursion(sequence, index, end - 1);

    }

    public static boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;

        //找出第一个比根节点大的数字，遍历后面的如果都小于则然后分成两个然后继续调用，否则返回false;
        // sequence.length == 1 || == 0,则符合要求

        return VerifySquenceOfBST_Recursion(sequence, 0, sequence.length - 1);
    }


    /**
     * JZ32 从上往下打印二叉树
     * <p>
     * 不分行从上往下打印出二叉树的每个节点，同层节点从左至右打印。
     * 例如输入{8,6,10,#,#,2,1}
     * 则依次打印8,6,10,2,1(空节点不打印，跳过)，请你将打印的结果存放到一个数组里面，返回。
     * <p>
     * BFS Breadth
     */
    public static ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) return list;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (queue != null && queue.size() > 0) {
            TreeNode temp = queue.poll();
            if (temp.left != null) queue.add(temp.left);
            if (temp.right != null) queue.add(temp.right);
            list.add(temp.val);
        }
        return list;
    }

    /**
     * JZ27 二叉树的镜像
     * 操作给定的二叉树，将其变换为源二叉树的镜像。
     *
     * @param pRoot
     * @return
     */
    public static TreeNode Mirror(TreeNode pRoot) {
        if (pRoot != null) {
            TreeNode left = pRoot.left;
            TreeNode right = pRoot.right;
            pRoot.right = left;
            pRoot.left = right;
            if (pRoot.left != null) Mirror(pRoot.left);
            if (pRoot.right != null) Mirror(pRoot.right);
        }
        return pRoot;
    }

    /**
     * JZ26 树的子结构
     * 输入两棵二叉树A，B，判断B是不是A的子结构。（我们约定空树不是任意一个树的子结构）
     * 假如给定A为{8,8,7,9,2,#,#,#,#,4,7}，B为{8,9,2}，2个树的结构如下，可以看出B是A的子结构
     *
     * @param root1
     * @param root2
     * @return
     */

    public boolean HasSubtree_Recursion(TreeNode root1, TreeNode root2) {
        // 子树为空，说明是符合要求的
        // 子树不空，左树为空，说明不符合要求
        // 递归循环左树和右树，直到返回是否相等的结果
        if (root2 == null) return true;
        if (root1 == null) return false;
        if (root1.val == root2.val) {
            return HasSubtree_Recursion(root1.left, root2.left) && HasSubtree_Recursion(root1.right, root2.right);
        } else {
            return false;
        }
    }

    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null) return false;

        if (root1 == null) return false;

        boolean flag1 = HasSubtree_Recursion(root1, root2);
        boolean flag2 = HasSubtree(root1.left, root2);
        boolean flag3 = HasSubtree(root1.right, root2);

        return flag1 || flag2 || flag3;
    }


    /**
     * JZ7 重建二叉树
     * <p>
     * 给定节点数为 n 的二叉树的前序遍历和中序遍历结果，请重建出该二叉树并返回它的头结点。
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}
     */
    public static TreeNode reConstructBinaryTree(int[] pre, int[] vin) {
        // 如果为0，则返回空，节点不可再分
        if (pre.length == 0) return null;
        // 前序的第一个节点为头结点，在函数末尾返回，在进入函数前，将上一层的头结点的左右子节点设置为该返回值。
        TreeNode root = new TreeNode(pre[0]);
        // 求出pre，在vin中的位置
        int index = 0;
        for (int i : vin) {
            if (i == pre[0]) break;
            index++;
        }
        // 根据index求出新的pre,vin的左右节点
        int[] preLeft = new int[index];
        int[] preRight = new int[pre.length - index - 1];
        int[] vinLeft = new int[index];
        int[] vinRight = new int[pre.length - index - 1];
        for (int i = 0; i < pre.length - 1; i++) {
            if (i < index) {
                preLeft[i] = pre[i + 1];
                vinLeft[i] = vin[i];
            } else {
                preRight[i - index] = pre[i + 1];
                vinRight[i - index] = vin[i + 1];
            }
        }

        // 因为该函数返回参数中前序的第一个节点，因此用本层的root的左右节点
        // 去接该函数的返回节点，递归到0，返回null最终返回第一个头结点root
        root.left = reConstructBinaryTree(preLeft, vinLeft);
        root.right = reConstructBinaryTree(preRight, vinRight);

        return root;
    }

    /**
     * JZ54 二叉搜索树的第k个节点
     */
    public static int KthNode(TreeNode proot, int k) {
        if (proot == null) return -1;
        TreeMap<Integer, Object> map = new TreeMap<>();
        preTree(map, proot);
        if (k > map.keySet().size()) return -1;
        for (Integer i : map.keySet()) {
            if (k == 1) return i;
            k--;
        }
        return -1;
    }

    public static void preTree(TreeMap<Integer, Object> map, TreeNode proot) {
        if (proot != null) {
            map.put(proot.val, proot.val);
            preTree(map, proot.left);
            preTree(map, proot.right);
        }
    }

    /**
     * JZ77 按之字形顺序打印二叉树
     * <p>
     * 1
     * 2    3
     * 4 5
     * 遍历后为
     * [1,
     * 3,2
     * 4,5]
     * <p>
     * BFS 搞一个标志，然后反向输出即可
     */
    public static ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        if (pRoot == null) {
            return lists;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        boolean flag = true;
        int size;
        while (!queue.isEmpty()) {
            size = queue.size();
            ArrayList arrayList = new ArrayList();
            while (size > 0) {
                TreeNode treeNode = queue.poll();
//                TreeNode treeNode = queue.get(0);
//                queue.remove(0);
                if (flag) {
                    arrayList.add(treeNode.val);
                } else {
                    arrayList.add(0, treeNode.val);
                }
                if (treeNode.left != null) queue.add(treeNode.left);
                if (treeNode.right != null) queue.add(treeNode.right);
                size--;
            }
            lists.add(arrayList);
            flag = !flag;
        }
        return lists;
    }


    /**
     * JZ55 二叉树的深度
     */
    public int TreeDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(TreeDepth(root.left), TreeDepth(root.right)) + 1;
    }

    /**
     * JZ46 把数字翻译成字符串
     * <p>
     * string s = "1233423214"
     * 状态转移方程
     * if(26>=s[i-1]*10+s[i]>10) f[i]=f[i-1]+f[i-2]
     * if(s[i]=0)                f[i]=f[i-2]
     * if(s[i-1]=0)              f[i]=f[i-1]
     * else                      f[i]=f[i-1]
     *
     * @param nums
     * @return
     */
    public static int solve(String nums) {
        if (nums.length() < 3 && 27 > Integer.parseInt(nums) && Integer.parseInt(nums) > 10) return nums.length();
        if (nums.length() < 3) return 1;
        int m = (nums.charAt(nums.length() - 1) - 48);//当前位数字
        int n = (nums.charAt(nums.length() - 2) - 48);//前一位数字
        int i = n * 10 + m;
        String j = nums.substring(0, nums.length() - 1);
        String k = nums.substring(0, nums.length() - 2);
        if (nums.charAt(nums.length() - 1) == '0' && n != 1 && n != 2) {
            return 0;
        } else if (nums.charAt(nums.length() - 1) == '0') {
            return solve(k);
        } else if (i > 10 && i < 27) {
            return solve(j) + solve(k);
        } else {
            return solve(j);
        }
    }

    /**
     * JZ48 最长不含重复字符的子字符串
     * <p>
     * "abcabcbb"
     * dp[i]代表下标为i时，最长子串长度。
     * dp[i] =
     * index = s.subString(0,i-1).indexOf(s.charAt(i-1));
     * if(index>0) dp[i] = dp[i-1]-index
     * else dp[i] = dp[i-1]+1;
     */
    public static int lengthOfLongestSubstring(String s) {
        int dp = 1;
        int flag = 0;
        int max = 1;
        int index = 0;
        for (int i = 1; i < s.length(); i++) {
            index = s.substring(flag, i).indexOf(s.charAt(i));
            if (index > -1) {
                dp = dp - index;
                flag = index + flag + 1;
            } else {
                dp = dp + 1;
                if (dp > max) max = dp;
            }
        }

        return max;
    }

    /**
     * JZ47 礼物的最大价值
     * <p>
     * [[1,3,1],
     * [1,5,1],
     * [4,2,1]]
     * 12
     *
     * @param grid
     * @return
     */
    public static int maxValue(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] max = new int[m + 1][n + 1];
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                max[i][j] = Math.max(max[i - 1][j], max[i][j - 1]) + grid[i - 1][j - 1];
            }
        }
        return max[m][n];
    }

    /**
     * JZ16 数值的整数次方
     */
    public static double Power_baoli(double base, int exponent) {
        double power = 1;
        if (exponent < 0) {
            exponent = -exponent;
            base = 1 / base;
        }
        for (int i = 0; i < exponent; i++) {
            power *= base;
        }
        return power;
    }

    public static double Power(double base, int exponent) {
        if (exponent == 0) return 1;
        double power = 1;
        if (exponent < 0) {
            exponent = -exponent;
            base = 1 / base;
        }
        return Power_1(base, exponent);
    }

    public static double Power_1(double base, int exponent) {
        double result = 1;
        while (exponent != 0) {
            if ((exponent & 1) == 1) result *= base;
            base *= base;
            exponent = exponent >> 1;
        }
        return result;
    }

    /**
     * JZ15 二进制中1的个数
     */
    private static int JZ15 = 1;

    public static int NumberOf1(int n) {
        if (n == 0) return 0;
        if ((n & n - 1) == 0) return JZ15;
        else {
            JZ15++;
            return NumberOf1(n & n - 1);
        }
    }

    /**
     * JZ65 不用加减乘除做加法
     * 与运算:可以提供相加后的进位信息,左移一位则为进位后的数字
     * 异或运算:可以提供该位相加后的数字
     * 一直递归,直到进位信息(与运算后左移一位)为0,返回两个数字异或后的答案
     */
    public static int Add(int num1, int num2) {
        int a = (num1 & num2) << 1;
        int b = (num1 ^ num2);
        if (a == 0) {
            return b;
        } else {
            return Add(a, b);
        }
    }

    /**
     * JZ64 求1+2+3+...+n
     * 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句(A?B:C)
     * 1.利用Math.pow计算阶乘，利用右移进行/2
     * 2.利用短路 将判断放到前面
     * <p>
     * 1. return (int)(Math.pow(n,2)+n)>>1;
     * 2. 利用或短路逻辑
     * boolean flag=(n==1)||((n+=Sum_Solution(n-1))>-1);
     * return n;
     * 3. 利用与短路逻辑
     */
    public static int Sum_Solution(int n) {
        boolean flag = (n > 1) && ((n += Sum_Solution(n - 1)) > -1);
        return n;
    }

    /**
     * JZ63 买卖股票的最好时机(一)
     * 0≤n≤10 0≤val≤10
     * 买卖只可以进行一次
     * 记录一个最小值，记录一个比较后的最大值，轮训一遍即可得出最大差
     * 3,2,4,8,1,3 return 6
     */
    public int maxProfit(int[] prices) {
        int max = 0;
        int min = prices[0];
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < min) min = prices[i];
            if (prices[i] - min > max) max = prices[i] - min;
        }
        return max;
    }

    /**
     * JZ70 矩形覆盖
     * 开头竖着放，相当于f(n-1),开头横着放两个,相当于f(n-2),总数量f(n) = f(n-1) + f(n-2)
     *
     * @param target
     * @return
     */
    public static int rectCover(int target) {
        if (target <= 3 && target >= 0) return target;
        return rectCover(target - 1) + rectCover(target - 2);
    }

    /**
     * JZ71 跳台阶扩展问题
     *
     * @param target
     * @return
     */
    public static int jumpFloorII(int target) {
        if (target == 1) return 1;
        return jumpFloorII(target - 1) * 2;
    }


    /**
     * JZ19 正则表达式匹配
     * <p>
     * "aaa","a.*a"
     * true
     * 说明：中间的*可以出现任意次的a，所以可以出现1次a，能匹配上
     * Si代表S的第i个字符，Pj代表P的第j个字符，f(i,j)代表以i结尾和j结尾的S和P匹配情况
     * 第1种情况：Si和Pj都是普通字符结尾，则 f(i,j) = f(i-1,j-1) && Si == Pj
     * 第2种情况：Pj为.结尾 f(i,j) = f(i-1,j-1) && Pj == .
     * 第3种情况：Pj为*结尾
     * 0次匹配：f(i,j) = f(i,j-2)
     * 1次匹配：f(i,j) = f(i-1,j-2) && (Si == P(j-1) || P(j-1) == .)
     * 2次匹配：f(i,j) = f(i-2,j-2) && ( ( Si == Pj-1 && Si-1 == Pj-1 ) || P(j-1) == . )
     * f(i,j) = 多次匹配 0 || 1 || 2 ... 只要其中一个匹配则认为其匹配
     * f(i,j) = f(i,j-2) || f(i-1,j-2) && (Si匹配P(j-1) || P(j-1) == .) || f(i-2,j-2) && ( ( S[i-1:i]匹配Pj-1 ) || P(j-1) == . )
     * i = i-1 带入
     * f(i-1,j) =           f(i-1,j-2) || f(i-2,j-2) && (S[i-1]匹配P(j-1) || Pj-1) == .) || f(i-3,j-2) && ((S[i-2:i-1]匹配Pj-1) || P(j-1) == .)
     * 分析可得，f(i,j) 后面每个项比f(i-1,j)多一个：Si匹配P(j-1)
     * f(i,j) = f(i,j-2) || (f(i-1,j) && Si匹配P(j-1) || P(j-1) == .)
     */
    public static boolean match(String str, String pattern) {
        int lenS = str.length();
        int lenP = pattern.length();
        str = " " + str;
        pattern = " " + pattern;
        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();
        boolean[][] f = new boolean[s.length][p.length];//lenS = 2;lenP = 2;
        f[0][0] = true;
        for (int i = 0; i <= lenS; i++) {
            for (int j = 1; j <= lenP; j++) {
                if (j + 1 <= lenP && p[j + 1] == '*') continue;
                if (i - 1 >= 0 && p[j] != '*') {
                    f[i][j] = f[i - 1][j - 1] && (s[i] == p[j] || p[j] == '.');
                } else if (p[j] == '*') {
                    f[i][j] = (j - 2 >= 0 && f[i][j - 2]) || (i - 1 >= 0 && f[i - 1][j] && (s[i] == p[j - 1] || p[j - 1] == '.'));
                }
            }
        }
        return f[lenS][lenP];
    }

    /**
     * JZ10 斐波那契数列
     * <p>
     * n=1 sum=1
     * n=2 sum=1
     */
    public static int Fibonacci(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return Fibonacci(n - 1) + Fibonacci(n - 2);
        }
    }

    /**
     * JZ69 跳台阶
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个 n 级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
     */
    public static int jumpFloor(int target) {
        if (target == 0) {
            return 0;
        } else if (target == 1) {
            return 1;
        } else if (target == 2) {
            return 2;
        } else {
            return jumpFloor(target - 1) + jumpFloor(target - 2);
        }
    }

    /**
     * JZ85 连续子数组的最大和(二)
     * {1,-2,3,10,-4,7,2,-5}
     * 返回{3,10,-4,7,2}
     */

    public static int[] FindGreatestSumOfSubArray_array(int[] array) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        int dummy = 0;
        int max = array[0];//都为负数的情况，不能设置为0，否则就是0了
        for (int i = 0; i < array.length; i++) {
            if (dummy < 0) {
                dummy = array[i];
                list.clear();
                list.add(list.size(), array[i]);
            } else {
                dummy += array[i];
                list.add(list.size(), array[i]);
            }
            if (dummy >= max) {
                if ((dummy > max) || (dummy == max && list.size() > list1.size())) {
                    list1 = new ArrayList<>(list);
                }
                max = dummy;
            }
        }
        int[] result = new int[list1.size()];
        for (int i = 0; i < list1.size(); i++) {
            result[i] = list1.get(i);
        }
        return result;
    }

    /**
     * JZ42 连续子数组的最大和
     * <p>
     * 输入一个长度为n的整型数组array，数组中的一个或连续多个整数组成一个子数组，子数组最小长度为1。求所有子数组的和的最大值。
     * {1,-2,3,10,-4,7,2,-5}
     */

    /**
     * 动态规划dp[i] = 连续数组最大和
     * dp[i]=Math.max(dp[i-1]+array[i],array[i])
     * sum+array[i]和array[i],比较如果sum为负数，则抛弃，取array[i],因为这样取和一定会
     */
    public int FindGreatestSumOfSubArray_返回最大值(int[] array) {
        int max = array[0];
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum = Math.max(sum + array[i], array[i]);
            max = Math.max(sum, max);
        }
        return max;
    }

    /**
     * {1,-2,3,10,-4,7,2,-5}
     * 变小就是因为有负数，所以有负数就抛弃,如果是正的，前面加上一定会更大，所以不抛弃
     * 循环，保存最大值，设置一个dummy一直向后加，如果为负数则直接取下一个，如果大于sum则更新sum
     */
    public int FindGreatestSumOfSubArray_暴力(int[] array) {
        int dummy = 0;
        int max = array[0];//都为负数的情况，不能设置为0，否则就是0了
        for (int i = 0; i < array.length; i++) {
            if (dummy < 0) dummy = array[i];
            else {
                dummy += array[i];
            }
            if (dummy > max) {
                max = dummy;
            }
        }
        return max;
    }

    /**
     * JZ44 数字序列中某一位的数字
     * 数字以 0123456789101112131415... 的格式作为一个字符序列，
     * 在这个序列中第 2 位（从下标 0 开始计算）是 2 ，第 10 位是 1 ，第 13 位是 1 ，以此类题，请你输出第 n 位对应的数字。
     * 0
     * 1-9          位数=1，个数=1*9
     * 10-99        位数=2，个数=2*90
     * 100-99       位数=3，个数=3*900
     * 1000-9999    位数=4，个数=4*9000
     */
// 22-9=13
    public static int findNthDigit(int n) {
        if (n < 10) return n;
        int digit = 1, count = 9;
        while (n > count) {
            n = n - count;
            digit++;
            count = (int) (digit * 9 * Math.pow(10, digit - 1));
        }
        int a = n / digit;//0
        int b = n % digit;//1
        int c = (int) (Math.pow(10, digit - 1) + a);//10
        if (b > 0) {
            return Integer.parseInt(String.valueOf(String.valueOf(c).charAt(b - 1)));
        } else {
            return Integer.parseInt(String.valueOf(String.valueOf(c - 1).charAt(String.valueOf(c).length() - 1)));
        }
    }

    /**
     * JZ38 字符串的排列
     * <p>
     * <p>
     * 递归算法，循环str中的每个字符，依次将一个字符加入cur中，直到str为空，则将cur加入到result中
     */
    public static ArrayList<String> Permutation(String str) {
        ArrayList<String> result = new ArrayList<>();
        if (str.length() == 0) {
            return result;
        }

        Permutation_Recursion(str, "", result);
        for (String s : result) {
            System.out.println(s);
        }
        return result;
    }

    public static void Permutation_Recursion(String str, String cur, ArrayList<String> result) {
        if ((str.length() == 0 || str == null) && !result.contains(cur)) result.add(cur);
        for (int i = 0; i < str.length(); i++) {
            Permutation_Recursion(str.substring(0, i) + str.substring(i + 1, str.length()), cur + str.charAt(i), result);
        }
    }


    /**
     * JZ11 旋转数组的最小数字
     * 有一个长度为 n 的非降序数组，比如[1,2,3,4,5]，
     * 将它进行旋转，即把一个数组最开始的若干个元素搬到数组的末尾
     * 变成一个旋转数组，比如变成了[3,4,5,1,2]，或者[4,5,1,2,3]这样的
     * 请问，给定这样一个旋转数组，求数组中的最小值
     * 二分法，
     * 找到左大，右大的即为最小 a>b<c b为最小
     */
    public int minNumberInRotateArray(int[] array) {
        if (array.length == 1) return array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[0] > array[i]) {
                return array[i];
            }
        }
        return array[0];
    }

    /**
     * JZ4 二维数组中的查找
     * 在一个二维数组array中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     *
     * <P>  * [[1,2,8,9],  </P>
     * <P>  * [2,4,9,12],  </P>
     * <P>  * [4,7,10,13], </P>
     * <P>  * [6,8,11,15]] </P>
     */
    public static boolean Find(int target, int[][] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].length == 0) return false;
            if (array[i][0] == target) return true;
            if (array[0][0] > target) return false;
            if (array[i][0] > target || ((i == array.length - 1) && (array[i][0] < target))) {
                if (i == array.length - 1) i = i + 1;
                for (int j = 0; j < array[0].length; j++) {
                    if (array[i - 1][j] == target) return true;
                    if (array[i - 1][j] > target) {
                        for (int k = 0; k < i; k++) {
                            if (array[k][j] == target) return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * JZ53 数字在升序数组中出现的次数
     * 数据范围：0 <= n <= 1000 , 0 <= k <= 1000≤n≤1000,0≤k≤100，数组中每个元素的值满足 0 <= val <= 1000≤val≤100
     * 要求：空间复杂度 O(1)，时间复杂度 O(logn)
     * 1.暴力循环
     * 2.二分法
     */
    public static int GetNumberOfK(int[] array, int k) {
        int count = 0;
        for (int a : array) {
            if (a == k) {
                count++;
            } else if (a > k) {
                continue;
            }
        }
        return count;
    }

    /**
     * JZ53
     * 二分法解决 排序数组计数问题
     */
    public static int GetNumberOfK2(int[] array, int k) {
        int count = 0;
        int index = -1;
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            if (array[left] == k) {
                index = left;
                break;
            } else if (array[right] == k) {
                index = right;
                break;
            } else if (array[(left + right) / 2] == k) {
                index = (left + right) / 2;
                break;
            } else if (array[(left + right) / 2] > k) {
                right = (left + right) / 2 - 1;
                left = left + 1;
            } else if (array[(left + right) / 2] < k) {
                left = (left + right) / 2 + 1;
                right = right - 1;
            }
        }
        if (index == -1) return 0;
        for (int i = index; i <= right; i++) {
            if (array[i] == k) {
                count++;
            }
        }
        for (int i = left; i < index; i++) {
            if (array[i] == k) {
                count++;
            }
        }
        return count;
    }

    /**
     * JZ18
     * 删除链表中指定的元素 JZ18
     */
    public static ListNode deleteNode(ListNode head, int val) {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(2);
        ListNode d = new ListNode(3);
        ListNode e = new ListNode(3);
        ListNode f = new ListNode(3);
        ListNode g = new ListNode(7);

        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;
        f.next = g;


        ListNode origin = new ListNode(0);
        ListNode tail = origin;
        while (head != null) {
            if (head.val != val) {
                tail.next = head;
                tail = head;
            }
            head = head.next;
        }
        return origin.next;
    }

    /**
     * JZ76
     * 删除链表中重复的节点
     * <p>
     * 使用map存储，重新构建链表
     */
    public static ListNode deleteDuplication(ListNode pHead) {
        HashMap<Integer, Integer> map = new HashMap<>();
        while (pHead != null) {
            if (map.get(pHead.val) != null) {
                map.put(pHead.val, map.get(pHead.val) + 1);
            } else {
                map.put(pHead.val, 1);
            }
            pHead = pHead.next;
        }
        ListNode pHead1 = new ListNode(0), tail = pHead1;
        for (Integer i : map.keySet()) {
            if (map.get(i) == 1) {
                ListNode newNode = new ListNode(i);
                tail.next = newNode;
                tail = newNode;
            }
        }
        return pHead1.next;
    }

    /**
     * JZ35
     * 深拷贝一个复杂链表
     * <p>
     * 先创建一个 原始node（带random）和新node（不带random）的hashmap映射关系，再建立一个新node构成的copy链表对象
     * 再遍历pHead，根据pHead中的random在map中get出不带random的新node，设置给copy的每个node中的random
     * <p>
     * 最后返回copy对象的next即可
     */
    public RandomListNode Clone(RandomListNode pHead) {
        RandomListNode temp = pHead;
        RandomListNode copyObject = new RandomListNode(0);
        RandomListNode tail = copyObject;
        HashMap<RandomListNode, RandomListNode> map = new HashMap<>();
        while (temp != null) {
            RandomListNode newNode = new RandomListNode(temp.label);
            map.put(temp, newNode);
            tail.next = newNode;
            tail = newNode;
            temp = temp.next;
        }
        tail = copyObject.next;
        while (pHead != null) {
            if (pHead.random != null) tail.random = map.get(pHead.random);
            pHead = pHead.next;
            tail = tail.next;
        }

        return copyObject.next;
    }

    /**
     * JZ22
     * 链表中倒数最后k个结点,输入一个长度为 n 的链表，设链表中的元素的值为 ai ，返回该链表中倒数第k个节点。
     * 如果该链表长度小于k，请返回一个长度为 0 的链表。
     * <p>
     * 统计len，减去k，之后循环
     */
    public ListNode FindKthToTail(ListNode pHead, int k) {
        ListNode temp = pHead;
        int len = 0;
        while (temp != null) {
            temp = temp.next;
            len++;
        }
        if (len < k) return null;
        len = len - k;//
        while (len != 0) {
            pHead = pHead.next;
            len--;
        }
        return pHead;
    }

    /**
     * JZ23
     * 找出环的第一个节点
     * <p>
     * 1-2-3-4-5-3
     * p：环起点，q:相遇点
     * a = slow走过的距离，b = 从环起点到相遇点的距离，c = 从相遇点到环起点的距离
     * 设置一个快慢指针，二倍速，当fast，slow相遇则，2(a+b)=a+n(b+c)+b => a+b=n(b+c) => a=(n-1)(b+c)+c
     * 则：a的距离等于 转n圈+c的距离
     * 则：如果从起点再放一个slow1指针，则slow1重新走a的距离，等于slow绕n圈后+c的距离，正好slow在q上，则这两个指针相遇即可。
     */
    public static ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null || pHead.next == null) return null;
        if (pHead == pHead.next) return pHead;

        ListNode fast = pHead;
        ListNode slow = pHead;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                ListNode slow1 = pHead;
                while (slow != slow1) {
                    slow = slow.next;
                    slow1 = slow1.next;
                }
                return slow1;
            }
        }
        return null;

    }

    /**
     * JZ52
     * 两个链表的第一个公共结点
     * <p>
     * 循环的方式？循环pHead1，碰到和list2相同的头结点直接取list2
     * 1-2-3-4-5  6-4-5
     */
    public static ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        int len1 = 0;
        int len2 = 0;
        ListNode temp1 = pHead1;
        ListNode temp2 = pHead2;
        while (temp1 != null) {
            len1++;
            temp1 = temp1.next;
        }
        while (temp2 != null) {
            len2++;
            temp2 = temp2.next;
        }
        if (len1 > len2) {
            int len = len1 - len2;
            while (len != 0) {
                pHead1 = pHead1.next;
                len--;
            }
        } else {
            int len = len2 - len1;
            while (len != 0) {
                pHead2 = pHead2.next;
                len--;
            }
        }
        while (pHead1 != null) {
            if (pHead1 == pHead2) {
                break;
            } else {
                pHead1 = pHead1.next;
                pHead2 = pHead2.next;
            }
        }
        return pHead1;
    }


    /**
     * JZ25
     * 合并两个排序的链表
     * 1-3-5 2-4-6 -----> 1 2 3 4 5 6
     * 设置一个头结点,设置一个尾节点
     * 尾结点.next = 新的比较出val的新节点
     * 设置最后一个节点为尾节点，继续循环，知道list1，list2其中一个为空，则设置尾节点的next为剩下的list1或者list2
     */
    public static ListNode Merge(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(0);
        ListNode lastNode = head;

        while (list1 != null && list2 != null) {
            ListNode tempSum = new ListNode(0);
            tempSum.next = new ListNode(0);
            if (list1.val < list2.val) {
                tempSum.val = list1.val;
                list1 = list1.next;
            } else {
                tempSum.val = list2.val;
                list2 = list2.next;
            }
            lastNode.next = tempSum;
            lastNode = lastNode.next;
        }
        if (list1 == null) {
            lastNode.next = list2;
        }
        if (list2 == null) {
            lastNode.next = list1;
        }
        return head.next;
    }

    /**
     * JZ24
     * 逆转一个链表 a->b->c->d->e => e-d-c->b->a
     * 设置一个临时节点，接收head.next
     * 设置newNode为head.next,相当于把newNode一直挂在新head的next，实现反转效果
     * 将head赋值给newNode
     * head.next 赋值给 head
     */
    public ListNode reverseListNode(ListNode head) {
        ListNode newNode = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = newNode;
            newNode = head;
            head = temp;
        }
        return newNode;
    }

    /**
     * JZ6
     * 输入链表，反向输出结果
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        while (listNode != null) {
            array.add(0, listNode.val);//尾插法
            listNode = listNode.next;
        }
        return array;
    }

    /**
     * 返回树的前序数组
     *
     * @return
     */
    public static String preTree(TreeNode root) {
        StringBuffer str = new StringBuffer();
        if (root != null) {
            str.append(root.val).append(preTree(root.left)).append(preTree(root.right));
        } else {
            return "#";
        }
        return str.toString();
    }

    /**
     * 中序
     *
     * @param root
     * @return
     */
    public static StringBuffer inorderTree(TreeNode root) {
        StringBuffer str = new StringBuffer();
        if (root == null) return new StringBuffer("#");
        return inorderTree(root.left).append(root.val).append(inorderTree(root.right));
    }

    /**
     * 后续遍历
     *
     * @param root
     * @return
     */
    public static StringBuffer postTree(TreeNode root) {
        StringBuffer str = new StringBuffer();
        if (root == null) return new StringBuffer("#");
        return postTree(root.left).append(postTree(root.right)).append(root.val);
    }

    /**
     * Depth first search
     * <p>
     *
     * @return
     */
    static List<List<Integer>> lists = new LinkedList<>(new LinkedList<>());

    public static List<List<Integer>> DFS(TreeNode treeNode) {

        List<Integer> path = new LinkedList<>();

        recursion_DFS(treeNode, path);

        return lists;
    }

    public static void recursion_DFS(TreeNode root, List<Integer> path) {
        path.add(root.val);
        if (root.left == null && root.right == null) {
            List<Integer> temp = new LinkedList<>(path);
            lists.add(temp);
            return;
        }
        List<Integer> left = new LinkedList<>(path);
        List<Integer> right = new LinkedList<>(path);
        if (root.left != null) recursion_DFS(root.left, left);
        if (root.right != null) recursion_DFS(root.right, right);
    }

    /**
     * Breadth first search
     *
     * @return
     */
    public static List<List<Integer>> BFS(TreeNode treeNode) {
        List<List<Integer>> lists = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(treeNode);
        while (queue.size() != 0) {
            int size = queue.size();
            List<Integer> list = new LinkedList<>();
            while (size > 0) {
                TreeNode temp = queue.poll();
                if (temp.left != null) queue.add(temp.left);
                if (temp.right != null) queue.add(temp.right);
                list.add(temp.val);
                size--;
            }
            lists.add(list);
        }
        return lists;
    }

    public static List<List<Integer>> BFS1(TreeNode treeNode) {

        List<List<Integer>> lists = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList();
        queue.offer(treeNode);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new LinkedList<>();
            while (size > 0) {
                TreeNode tmp = queue.poll();
                list.add(tmp.val);
                size--;
                if (tmp.left != null) queue.offer(tmp.left);
                if (tmp.right != null) queue.offer(tmp.right);
            }
            lists.add(list);
        }
        return lists;
    }


    /**
     * 冒泡排序
     */
    public static int[] sort_bubble(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                if (a[j] > a[i]) {
                    swap(a, i, j);
                }
            }
        }
        return a;
    }

    /**
     * 快速排序
     */
    public static void quickSort(int[] nums, int start, int end) {
        if (end - start < 1) return;
        int pivot = getPivot(nums, start, end);
        quickSort(nums, start, pivot - 1);
        quickSort(nums, pivot + 1, end);
    }

    /**
     * 将nums从start到end分区，左边区域比基数小，右边区域比基数大，然后返回中间值的下标
     */
    public static int getPivot(int[] nums, int start, int end) {
        int pivot = nums[end];
        int end1 = end;
        if (end - start < 1) {
            return start;
        }
        while (start < end) {
            while (start < end && nums[start] <= pivot) start++;
            while (start < end && nums[end - 1] > pivot) end--;
            if (start < end) {
                swap(nums, start, end - 1);
                start++;
                end--;
            }
        }
        if (nums[start] > pivot) {
            swap(nums, start, end1);
        } else {
            swap(nums, end, end1);
        }
        return start;
    }

    /**
     * 交换方法
     */
    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 归并排序
     * 1.先两两分开
     * 2.最后排序
     * 3.排序的时候该区域分成(left,mid) 和 (mid+1,right)使用排序两个有序数组的方法排序
     * 4.新建一个right-left+1长度的temp数组，从左开始，依次设置为对比后小的值，知道某一数组到达mid和right的边界
     * 5.如果是左边到边界，右边依次设置为后面的值，如果是右边，反之
     * 6.将temp数组依次设置到原数组的left到right区域
     */
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int indexLeft = left;
        int indexRight = mid + 1;
        int[] temp = new int[right - left + 1];
        int indexTemp = 0;
        while (indexLeft <= mid && indexRight <= right) {
            temp[indexTemp++] = arr[indexLeft] < arr[indexRight] ? arr[indexLeft++] : arr[indexRight++];
        }
        while (indexLeft <= mid) {
            temp[indexTemp++] = arr[indexLeft++];
        }
        while (indexRight <= mid) {
            temp[indexTemp++] = arr[indexRight++];
        }
        for (int i = 0; i < temp.length; i++) {
            arr[i + left] = temp[i];
        }
    }

    public static void mergeSort1(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort1(arr, left, mid);
            mergeSort1(arr, mid + 1, right);
            merge1(arr, left, mid, right);
        }
    }

    private static void merge1(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int p1 = left;
        int p2 = mid + 1;
        int k = 0;
        while (p1 <= mid && p2 <= right) {
            temp[k++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            temp[k++] = arr[p1++];
        }
        while (p2 <= right) {
            temp[k++] = arr[p2++];
        }
        for (int i = 0; i < temp.length; i++) {
            arr[i + left] = temp[i];
        }
    }

    /**
     * 将两个有序序列排序,升序
     */
    public static int[] sortSortedArrays(int[] a, int[] b) {
        int A = 0;
        int B = 0;
        int C = 0;
        int[] c = new int[a.length + b.length];

        while (A < a.length && B < b.length) {
            c[C++] = a[A] < b[B] ? a[A++] : b[B++];
        }
        while (A < a.length) {
            c[C++] = a[A++];
        }
        while (B < b.length) {
            c[C++] = b[B++];
        }
        return c;
    }

    /**
     * 堆排序，根节点下标为0，第一个叶子节点下标为n/2,第一个非叶子节点为(n/2)-1
     * 从右向左，从下到上，依次判断是否符合大顶堆的性质，如果不符合则将子节点最大的与之交换，
     * 依次将交换的都做对比如果不交换则继续，直到遍历到跟根节点，将根节点与末尾节点交换，
     * 然后依次交换受影响的节点
     * 大顶堆：父节点>子节点，小顶堆：父节点<子节点
     */

    public static void heapSort(int[] arr) {
        if (arr.length == 0 || arr == null) return;
        int len = arr.length;
        buildMaxHeap(arr, len);
        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0, i);
            len--;
            exchangeParentNode(arr, 0, len);
        }
    }

    /**
     * 依次转换父节点(最后一个非叶子节点下标为len/2-1)为大顶堆，从右到左，从下到上
     */
    private static void buildMaxHeap(int[] arr, int len) {
        for (int i = len / 2 - 1; i >= 0; i--) {
            exchangeParentNode(arr, i, len);
        }
    }

    /**
     * 将节点i转换为大顶堆，受到影响的子节点继续转换，直到没有变化为止
     */
    private static void exchangeParentNode(int[] arr, int i, int len) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        //记录是否变化的index
        int index = i;
        if (left < len && arr[index] < arr[left]) index = left;
        if (right < len && arr[index] < arr[right]) index = right;
        if (index != i) {
            swap(arr, i, index);
            exchangeParentNode(arr, index, len);
        }
    }
}