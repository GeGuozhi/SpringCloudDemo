package com.ggz.childMavenProject;

public class SingleLinkedList<T> {
    private SingleLinkedNode head;

    /**
     * 头插法
     * @param node
     */
    public void headInsert(T t){
        SingleLinkedNode node = new SingleLinkedNode(t);
        if(head == null){
            head = node;
            return;
        }
        node.next = head;
        head = node;
    }

    /**
     * 尾插法
     * @param node
     */
    public void tailInsert(T t){
        SingleLinkedNode node = new SingleLinkedNode(t);
        if(head == null){
            head = node;
            return;
        }

        SingleLinkedNode temp = head;
        while(temp.next != null){
            temp = temp.next;
        }
        temp.next = node;
    }

    /**
     * 输出单链表
     */
    public void printSingleLinkedList(){
        SingleLinkedNode temp = new SingleLinkedNode(null);
        temp = head;
        while(temp != null){
            System.out.println(temp.val);
            temp = temp.next;
        }
    }

    class SingleLinkedNode<T> {
        public T val;
        public SingleLinkedNode next;
        public SingleLinkedNode(T t) {
            this.val = t;
        }

        public SingleLinkedNode() {

        }
    }
}
