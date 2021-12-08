package cm.test;


import cm.arcGIS.arcGISInSJava.LoggerInClass;
import cm.arcGIS.arcGISUpJava.LoggerClass;
import cm.arcGIS.arcGISUpJava.PropertiesUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class httpClientPost {


    public static boolean httpClienPost(String jsonStrData,String path) throws IOException {
        LoggerInClass.info("---开始同步数据");
        boolean b =false;
        HttpPost post = null;
        HttpClient httpClient = HttpClients.createDefault();



        //post = new HttpPost("http://221.224.13.41:8088/GIS_GMapCacheProxys/bjData/partsChange");
        String post1 = PropertiesUtil.getPropertyParam("POST", path);
        String key = PropertiesUtil.getPropertyParam("KEY", path);
        String secret = PropertiesUtil.getPropertyParam("SECRET", path);
        post = new HttpPost(post1);
        // 构造消息头
        LoggerClass.info("构造消息头...");
        post.setHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("key", key);
        post.setHeader("secret",secret);

        // 构建消息实体
        StringEntity entity = new StringEntity(jsonStrData, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求\

        LoggerClass.info("发送JSON格式数据请求...");
        entity.setContentType("application/json");
        post.setEntity(entity);

        HttpResponse response = httpClient.execute(post);

        String html = EntityUtils.toString(response.getEntity(),Charset.forName("utf-8"));

        if (html.equals("{\"message\":\"同步成功\",\"code\":200}")){
            LoggerClass.info("数据 = " + jsonStrData + "\n" + html);
            b = true;
        }else {
            LoggerClass.error("同步失败+++++++"+html);
            return b;
        }

        try {
            post.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Administrator\\Documents\\javaJAR\\mc.properties";
        httpClientPost.httpClienPost("",path);





    }

}
