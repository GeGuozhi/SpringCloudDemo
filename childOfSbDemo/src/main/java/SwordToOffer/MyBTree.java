package SwordToOffer;

import lombok.Data;

import java.util.*;

/**
 * B-树
 *
 * @author ggz on 2022/9/5
 */

public class MyBTree<K> {

    /**
     * 关键字查询结果类
     */
    private static class SearchResult {
        /**
         * 关键字所在结点
         */
        private final Node node;
        /**
         * 是否存在
         */
        private final boolean isExist;
        /**
         * 下标
         */
        private final int index;

        public SearchResult(boolean isExist, int index, Node node) {
            this.isExist = isExist;
            this.index = index;
            this.node = node;
        }

        public boolean isExist() {
            return isExist;
        }

        public int getIndex() {
            return index;
        }

        public Node getNode() {
            return node;
        }
    }

    /**
     * B-树的结点类
     */
    private static class Node {

        /**
         * 关键字列表
         */
        private final List<Object> keyList;
        /**
         * 孩子结点列表
         */
        private final List<Node> childNodesList;
        /**
         * 是否叶子结点
         */
        private boolean isLeaf;
        /**
         * 双亲结点
         */
        private Node parentNode;

        /**
         * 键值比较函数对象，如果采用倒序或者其它排序方式，传入该对象
         */
        private Comparator<Object> kComparator;

        public Node() {
            this.keyList = new LinkedList<>();
            this.childNodesList = new LinkedList<>();
            this.isLeaf = false;
        }

        /**
         * 自定义K排序方式的构造函数
         */
        public Node(Comparator<Object> kComparator) {
            this();
            this.kComparator = kComparator;
        }

        /**
         * 比较两个key，如果没有传入自定义排序方式则采用默认的升序
         */
        private int compare(Object key1, Object key2) {
            return this.kComparator == null ? ((Comparable<Object>) key2).compareTo(key1) : kComparator.compare(key1, key2);
        }


        public void setIsLeaf(boolean isLeaf) {
            this.isLeaf = isLeaf;
        }

        public boolean getIsLeaf() {
            return this.isLeaf;
        }

        public void setParentNode(Node parentNode) {
            this.parentNode = parentNode;
        }

        public Node getParentNode() {
            return parentNode;
        }

        /**
         * 结点中关键字的个数
         */
        public int keySize() {
            return this.keyList.size();
        }

        /**
         * 采用二分查找在结点内查找关键字
         */
        public SearchResult searchResult(Object key) {
            int begin = 0;
            int end = this.keySize() - 1;
            int mid = (begin + end) / 2;
            boolean isExist = false;
            int index = 0;
            //二分查找
            while (begin < end) {
                mid = (begin + end) / 2;
                Object midValue = this.keyList.get(mid);
                int compareRe = compare(midValue, key);
                //找到了
                if (compareRe == 0) {
                    break;
                } else {
                    if (compareRe > 0) {
                        //在中点右边
                        begin = mid + 1;
                    } else {
                        end = mid - 1;
                    }
                }
            }
            //二分查找结束，判断结果;三个元素以上才是正经二分，只有两个或一个元素属于边界条件要着重考虑
            if (begin < end) {
                //找到了
                isExist = true;
                index = mid;
            } else if (begin == end) {
                Object midKey = this.keyList.get(begin);
                int comRe = compare(midKey, key);
                if (comRe == 0) {
                    isExist = true;
                    index = begin;
                } else if (comRe > 0) {
                    index = begin + 1;
                } else {
                    index = begin;
                }
            } else {
                index = begin;
            }
            return new SearchResult(isExist, index, null);
        }

    }

    /**
     * 默认3阶树
     */
    private final Integer DEFAULT_ORDER = 3;

    /**
     * 树阶
     */
    private int order = DEFAULT_ORDER;

    /**
     * 结点中关键字个数的最大值
     */
    private int maxKeySize = order - 1;

    /**
     * 结点的最小关键字数
     */
    private int nonLeafMinKeys = (int) Math.ceil(order / 2.0) - 1;

    /**
     * 根结点
     */
    private Node root;

    /**
     * 比较函数对象
     */
    private Comparator<Object> kComparator;

    /**
     * 构造一棵自然排序的B树
     */
    MyBTree() {
        Node root = new Node();
        this.root = root;
        root.setIsLeaf(true);
    }

    /**
     * 构造一棵order阶 的B树
     */
    MyBTree(int order) {
        this();
        this.order = order;
        this.maxKeySize = order - 1;
        this.nonLeafMinKeys = (int) Math.ceil(order / 2.0) - 1;
    }

    /**
     * 在以node为根的树内搜索key项
     */
    private SearchResult search(Node node, Object key) {
        SearchResult re = node.searchResult(key);
        if (re.isExist()) {
            return new SearchResult(true, re.getIndex(), node);
        } else {
            // 叶子结点
            if (node.getIsLeaf()) {
                return new SearchResult(false, re.getIndex(), node);
            }
            int index = re.getIndex();
            //递归搜索子结点--index是在查询结点内关键字的下标 也是子结点的下标
            return search(node.childNodesList.get(index), key);
        }
    }

    public boolean insertKey(Object key) {
        // 查询key在树中的结点情况
        SearchResult searchResult = search(root, key);
        if (searchResult.isExist()) {
            //已存在key，直接返回
            return false;
        }

        // 找出根结点
        if (null == searchResult.getNode().parentNode) {
            return insertKey(root, searchResult.getIndex(), key);
        } else {
            return insertKey(searchResult.getNode(), searchResult.getIndex(), key);
        }
    }

    private boolean insertKey(Node node, int index, Object key) {
        node.keyList.add(index, key);
        if (node.keyList.size() > maxKeySize) {
            // 分裂
            splitNode(node);
        }
        return true;
    }

    /**
     * 分裂结点
     */
    private void splitNode(Node node) {
        //取结点中间key下标
        int midIndex = node.keyList.size() / 2;
        Object key = node.keyList.get(midIndex);

        Node newNode = new Node();
        // 新结点采用原来结点是否是叶子结点的值
        newNode.setIsLeaf(node.getIsLeaf());
        // 新结点取源结点的(midIndex,node.keyList.size()-1]的关键字
        for (int i = midIndex + 1; i < node.keyList.size(); i++) {
            newNode.keyList.add(node.keyList.get(i));
        }
        // 源结点只留下标为[0,midIndex)的关键字
        if (node.keyList.size() > midIndex) {
            node.keyList.subList(midIndex, node.keyList.size()).clear();
        }

        // 若叶子结点，需要将原来结点的孩子结点也进行分裂，新结点取(midIndex,node.keyList.size()-1]的孩子结点
        if (!node.isLeaf) {
            for (int i = midIndex + 1; i < node.childNodesList.size(); i++) {
                newNode.childNodesList.add(node.childNodesList.get(i));
                // 修改孩子结点的双亲结点为新结点
                node.childNodesList.get(i).setParentNode(newNode);
            }
            // 源结点只留下标为[0,midIndex]的孩子结点
            if (node.childNodesList.size() > midIndex + 1) {
                node.childNodesList.subList(midIndex + 1, node.childNodesList.size()).clear();
            }
        }


        Node father = node.getParentNode();

        if (null == father) {
            // 若结点的双亲结点为null,说明是根结点进行的分裂，需要新增结点作为根结点
            father = new Node();
            father.childNodesList.add(node);
            father.setIsLeaf(false);
            father.keyList.add(key);
            father.childNodesList.add(newNode);
            node.parentNode = father;
            newNode.parentNode = father;
        } else {
            newNode.parentNode = father;
            SearchResult re = father.searchResult(key);
            father.keyList.add(re.getIndex(), key);
            father.childNodesList.add(re.getIndex() + 1, newNode);
            // 若双亲的关键字个数超出最大允许值，则继续分裂
            if (father.keyList.size() > maxKeySize) {
                splitNode(father);
            }
        }

        if (father.getParentNode() == null) {
            this.root = father;
        }
    }

    public boolean deleteKey(Object key) {

        SearchResult searchResult = search(root, key);

        // 没有找到key 直接结束
        if (!searchResult.isExist()) {
            return false;
        }

        Node keyInNode = searchResult.getNode();
        // 是否是叶子结点
        if (!keyInNode.getIsLeaf()) {

            // 非叶子结点，取第i个孩子结点中的最大关键字
            Node keyChildNode = keyInNode.childNodesList.get(searchResult.getIndex());
            //如果孩子结点不是叶子结点，去找这个子树到叶子结点中最大的关键字，与key互换位置
            if (!keyChildNode.getIsLeaf()) {
                keyChildNode = getMaxLeaf(keyChildNode);
            }
            Object childMinkey = keyChildNode.keyList.get(keyChildNode.keyList.size() - 1);
            keyInNode.keyList.add(searchResult.getIndex(), childMinkey);
            keyInNode.keyList.remove(key);
            keyChildNode.keyList.remove(childMinkey);
            keyChildNode.keyList.add(key);
            keyInNode = keyChildNode;
        }

        return deleteKey(keyInNode, key);
    }

    /**
     * 获取node结点到最右侧子结点的叶子结点
     *
     * @param node
     * @return
     */
    private Node getMaxLeaf(Node node) {
        Node keyChildNode = node.childNodesList.get(node.childNodesList.size() - 1);
        if (!keyChildNode.getIsLeaf()) {
            getMaxLeaf(keyChildNode);
        }
        return keyChildNode;
    }

    public boolean deleteKey(Node node, Object key) {
        // 如果需要删除关键字的结点，原本的关键字个数超过Math.ceil(order / 2.0) - 1
        if (node.keyList.size() > nonLeafMinKeys) {
            node.keyList.remove(key);
            return true;
        }

        // 如果需要删除关键字的结点，原本的关键字个数不超过Math.ceil(order / 2.0) - 1
        if (node.keyList.size() == nonLeafMinKeys) {
            doManageNode(node, key);
        }

        return true;
    }

    private void doManageNode(Node node, Object key) {
        if (null == node.parentNode) {
            return;
        }
        // 找兄弟结点中是否存在关键字个数超过Math.ceil(order / 2.0) - 1的
        int nodeIndex = node.parentNode.childNodesList.indexOf(node);
        Node leftNode = null;
        Node rightNode = null;
        if (0 <= nodeIndex && nodeIndex < node.parentNode.childNodesList.size() - 1) {
            rightNode = node.parentNode.childNodesList.get(nodeIndex + 1);
        }
        if (0 < nodeIndex) {
            leftNode = node.parentNode.childNodesList.get(nodeIndex - 1);
        }

        if (null != leftNode && leftNode.keyList.size() > nonLeafMinKeys) {
            node.parentNode.keyList.add(nodeIndex - 1, leftNode.keyList.get(leftNode.keyList.size() - 1));
            node.keyList.add(0, node.parentNode.keyList.get(node.parentNode.keyList.size() - 1));
            node.parentNode.keyList.remove(node.parentNode.keyList.size() - 1);
            node.keyList.remove(key);
            leftNode.keyList.remove(leftNode.keyList.get(leftNode.keyList.size() - 1));
            return;
        }

        if (null != rightNode && rightNode.keyList.size() > nonLeafMinKeys) {
            node.parentNode.keyList.add(nodeIndex + 1, rightNode.keyList.get(0));
            node.keyList.add(node.parentNode.keyList.get(nodeIndex));
            node.parentNode.keyList.remove(nodeIndex);
            node.keyList.remove(key);
            rightNode.keyList.remove(rightNode.keyList.get(0));
            return;
        }

        // 左右兄弟结点的关键字个数都不足的话，合并兄弟结点，下放一个双亲结点的关键字
        if (leftNode != null) {
            //合并结点
            node = merge(leftNode, node);
            node.keyList.remove(key);

            if (node.parentNode.keyList.size() < nonLeafMinKeys && null != node.parentNode.parentNode) {
                // 寻找结点合并
                doManageNode(node.parentNode, null);
            }

            if (null == node.parentNode.parentNode && node.parentNode.keyList.isEmpty()) {
                root = node;
            }
            return;
        }

        if (rightNode != null) {
            //合并结点
            node = merge(node, rightNode);
            node.keyList.remove(key);

            if (node.parentNode.keyList.size() < nonLeafMinKeys && null != node.parentNode.parentNode) {
                // 寻找结点合并
                doManageNode(node.parentNode, null);
            }
            if (null == node.parentNode.parentNode && node.parentNode.keyList.isEmpty()) {
                root = node;
            }
        }
    }

    private Node merge(Node leftNode, Node rightNode) {
        int index = leftNode.parentNode.childNodesList.indexOf(leftNode);
        leftNode.keyList.add(leftNode.parentNode.keyList.get(index));
        leftNode.parentNode.keyList.remove(index);
        leftNode.keyList.addAll(rightNode.keyList);
        if (!rightNode.isLeaf) {
            leftNode.childNodesList.addAll(rightNode.childNodesList);
        }
        leftNode.parentNode.childNodesList.remove(rightNode);
        return leftNode;
    }

    public static void main(String[] args) {
        List<Integer> save = new ArrayList<Integer>(30);
        MyBTree<Integer> btree = new MyBTree<>(3);
        save.add(45);
        save.add(24);
        save.add(90);
        save.add(3);
        save.add(27);
        save.add(70);
        save.add(100);
        save.add(62);
        for (int r : save) {
            System.out.print(r + "  ");
            btree.insertKey(r);
        }
        System.out.println("");
        System.out.println("----------------------");
        btree.output();
        System.out.println("----------------------");
        btree.deleteKey(45);
        btree.output();
    }

    public void output() {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(this.root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (int i = 0; i < node.keyList.size(); ++i) {
                System.out.print(node.keyList.get(i) + " ");
            }
            System.out.println();
            if (!node.getIsLeaf()) {
                for (int i = 0; i <= node.childNodesList.size() - 1; ++i) {
                    queue.offer(node.childNodesList.get(i));
                }
            }
        }
    }

}
