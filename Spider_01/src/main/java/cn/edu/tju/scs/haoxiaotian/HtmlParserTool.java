package cn.edu.tju.scs.haoxiaotian;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.sql.SQLSyntaxErrorException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by haoxiaotian on 2016/9/24 10:51.
 */
public class HtmlParserTool {
    // 获取网站上的链接，filter 用于过滤链接
    public static Set<String> extracLinks(String url,LinkFilter filter){
        Set<String> links = new HashSet<>();

        try{
            Parser parser = new Parser(url);
            parser.setEncoding("gb2312");
            // 过滤 <frame> 标签的filter，用来提取 frame 标签里的src 属性
            NodeFilter frameFilter = new NodeFilter() {
                @Override
                public boolean accept(Node node) {
                    if(node.getText().startsWith("frame src=")){
                        return true;
                    }else{
                        return false;
                    }
                }
            };

            // OrFilter 来设置过滤 <a> 标签 和 <frame> 标签
//            OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class),frameFilter);
            OrFilter imageFilter = new OrFilter(new NodeClassFilter(LinkTag.class),new NodeClassFilter(ImageTag.class));
            // 得到所有经过过滤后的标签
//            NodeList list = parser.extractAllNodesThatMatch(linkFilter);
            NodeList list = parser.extractAllNodesThatMatch(imageFilter);

            for(int i = 0;i < list.size();i++){
                Node tag = list.elementAt(i);
                if(tag instanceof  LinkTag){  // <a> 标签
//                    System.out.println("is <a>");
                    LinkTag link = (LinkTag) tag;
                    String linkUrl = link.getLink();//URL
                    if(filter.accept(linkUrl)){
                        links.add(linkUrl);
                    }
                }if(tag instanceof  ImageTag){
//                    System.out.println("is <image>"+": "+ ((ImageTag) tag).getImageURL());
                    ImageTag link = (ImageTag) tag;
                    String linkUrl = link.getImageURL();
                    if(filter.accept(linkUrl)){
                        links.add(linkUrl);
                    }
                }

//                else{  // <frame> 标签
//                    // 提取 frame 里src属性链接，如 <frame src = "test.html" />
//                    String frame = tag.getText();
//                    int start = frame.indexOf("src=");
//                    frame = frame.substring(start);
//                    int end = frame.indexOf(" ");
//                    if(end == -1){
//                        end = frame.indexOf(">");
//                    }
//
//                    String frameUrl = frame.substring(5,end - 1);
//                    if(filter.accept(frameUrl)){
//                        links.add(frameUrl);
//                    }
//
//                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return links;
    }
}
