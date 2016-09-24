package cn.edu.tju.scs.haoxiaotian;

import java.util.LinkedList;

/**
 * Created by haoxiaotian on 2016/9/24 9:53.
 */
public class Queue {
    // 使用链表实现队列
    private LinkedList<String> queue = new LinkedList<String>();

    // 入队列
    public void enQueue(String t){
        queue.addLast(t);
    }

    // 出队列
    public String deQueue(){
        return queue.removeFirst();
    }

    // 判断是否为空
    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }

    // 判断队列是否包含t
    public boolean contains(String t){
        return queue.contains(t);
    }
}
