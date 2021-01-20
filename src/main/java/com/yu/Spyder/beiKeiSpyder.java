package com.yu.Spyder;

import com.yu.mapper.bkMapper;
import com.yu.utlis.MybatisUtli;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


@SuppressWarnings("all")
public class beiKeiSpyder {
    public static void runSpyder()  {
        SqlSession sqlSession = MybatisUtli.getSqlSession();
        bkMapper mapper = sqlSession.getMapper(bkMapper.class);
        mapper.clean();
        sqlSession.commit();
        System.out.println("数据清除完成！");
        LinkedList<String> urlList = new LinkedList<String>();
        for (int i = 1; i <= 100; i++) {
            String url = "https://cd.ke.com/ershoufang/qingyang/pg"+i+"/";
            urlList.add(url);
        }
        System.out.println("开始爬取数据");
        ExecutorService ex = Executors.newFixedThreadPool(10);
        long startTime=new Date().getTime();

        for (String s : urlList) {
            ex.submit(new _Thread(s));
        }
        ex.shutdown();
       while(true){
           if(ex.isTerminated()){
               System.out.println("共耗时:"+(System.currentTimeMillis()-startTime)/1000.0+"s");
               break;
           }
       }
        System.out.println("爬取完成");
    }
}
@SuppressWarnings("all")
class _Thread implements Runnable {
    private String url;
    public _Thread(String url){
        this.url = url;
    }
    @Override
    public void run() {
        Map<String,String> headers=new HashMap<String,String>();
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.67 Safari/537.36 Edg/87.0.664.47");
        Connection connect = Jsoup.connect(url);
        connect.headers(headers);
        SqlSession sqlSession = MybatisUtli.getSqlSession();
        bkMapper mapper = sqlSession.getMapper(bkMapper.class);
        Date data = new Date();
        try {
            Document document = connect.timeout(20000).get();
            System.out.println("正在爬取"+url);
            Elements regions = document.select(".positionInfo a");
            Elements moneys = document.select(".unitPrice span");
            for (int i = 0; i < regions.size(); i++) {
                String region = regions.get(i).text();
                String money = moneys.get(i).text();
                money = money.split("价")[1].split("元")[0];
                String time = data.toLocaleString().replace(" ", "");
                mapper.add(region,Integer.parseInt(money),time);
            }
            sqlSession.commit();
            System.out.println(url+"爬取完成！！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}