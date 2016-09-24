package cn.edu.tju.scs.haoxiaotian;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by haoxiaotian on 2016/9/24 10:00.
 */
public class LinkQueue {
    // 已访问的url集合
    private static Set<String> visitedUrl = new HashSet<>();
    // 带访问的url集合
    private static Queue unVisitedUrl = new Queue();

    // 获得URL队列
    public static Queue getUnVisitedUrl(){
        return unVisitedUrl;
    }

    // 添加已访问过链接
    public static void addVisitedUrl(String url){
        visitedUrl.add(url);
    }

    // 移除访问过的url
    public static void removeVisitedUrl(String url){
        visitedUrl.remove(url);
    }

    // 未访问的url出队列
    public static String unVisitedUrlDeQueue(){
        return unVisitedUrl.deQueue();
    }

    // 保证每个URL只被访问一次
    public static void addUnVisitedUrl(String url){
        if(url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contains(url)) {
            unVisitedUrl.enQueue(url);
        }
    }

    // 获得已经访问过的URL数目
    public static int getVisitedUrlNum(){
        return visitedUrl.size();
    }

    // 判断未访问多的URL队列是否为空
    public static boolean unVisitedUrlsEmpty(){
        return unVisitedUrl.isQueueEmpty();
    }
}
