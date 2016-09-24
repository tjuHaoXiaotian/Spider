package cn.edu.tju.scs.haoxiaotian;

import java.util.Set;

/**
 * Created by haoxiaotian on 2016/9/24 11:06.
 */
public class BfsSpider {

    private static int imageNum = 0;
    /**
     * 使用种子初始化URL队列
     */
    private void initCrawlerWithSeeds(String [] seeds){
        for(int i = 0; i < seeds.length;i++){
            LinkQueue.addUnVisitedUrl(seeds[i]);
        }
    }

    /**
     * 抓取过程
     */
    public void crawling(String [] seeds){
        // 定义过滤器，提取以http//www.baidu.com 开头的链接
        LinkFilter filter = new LinkFilter() {
            @Override
            public boolean accept(String url) {
//                return url.startsWith("http://image.baidu.com/");
                return true;
            }
        };
        // 初始化URL队列
        initCrawlerWithSeeds(seeds);
        DownLoadFile downLoadFile = new DownLoadFile();

        // 循环条件：带抓取的链接不空且抓取的网页不多于1000
//        while(!LinkQueue.unVisitedUrlsEmpty() && LinkQueue.getVisitedUrlNum() < 100){
        while(!LinkQueue.unVisitedUrlsEmpty() && imageNum < 300){
            // URL出队列
            String nextUrl = LinkQueue.unVisitedUrlDeQueue();

            if(nextUrl == null){
                continue;
            }


            // 下载网页
            if(isImage(nextUrl)){
                downLoadFile.downloadFile(nextUrl);
                imageNum++;
            }else{
                // 提取出下载网页中的URL
                Set<String> links = HtmlParserTool.extracLinks(nextUrl,filter);
                for(String link:links){
                    LinkQueue.addUnVisitedUrl(link);
                }
            }

            // 将该url放入已访问的url中
            LinkQueue.addVisitedUrl(nextUrl);

        }
    }

    public static void main(String [] args){
        BfsSpider main = new BfsSpider();
        main.crawling(new String[]{
               "http://vip.qovod.com/one/LLP/index.shtml"
        });
    }

    public boolean isImage(String url){
       return url.endsWith("jpg")|| url.endsWith("jpeg") || url.endsWith("png") || url.endsWith("svg")|| url.endsWith("JPG")|| url.endsWith("JPEG") || url.endsWith("PNG") || url.endsWith("SVG");
    }
}
