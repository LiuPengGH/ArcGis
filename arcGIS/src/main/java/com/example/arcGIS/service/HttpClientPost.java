package com.example.arcGIS.service;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 远程数据同步
 */
public class HttpClientPost {


    //加载DAO
    @Resource
    private static final Logger LOGGER = LoggerFactory.getLogger(ArcGISService.class);
    /**
     *
     * @param jsonStrData
     * @return
     * @throws IOException
     */
    public static boolean httpClienPost(String jsonStrData,String path) throws IOException {

        LOGGER.info("---开始同步数据");
        boolean b =false;
        HttpPost post = null;
        HttpClient httpClient = HttpClients.createDefault();


        String post1 = PropertiesUtil.getPropertyParam("POST", path);
        String key = PropertiesUtil.getPropertyParam("KEY", path);
        String secret = PropertiesUtil.getPropertyParam("SECRET", path);
        post = new HttpPost(post1);
        // 构造消息头
        LOGGER.info("---构造消息头");
        post.setHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("key", key);
        post.setHeader("secret",secret);

        // 构建消息实体
        StringEntity entity = new StringEntity(jsonStrData, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/json");
        post.setEntity(entity);

        LOGGER.info("数据同步");
        HttpResponse response = httpClient.execute(post);

        String html = EntityUtils.toString(response.getEntity(),Charset.forName("utf-8"));
       // System.out.println(html);

        if (html.equals("{\"message\":\"同步成功\",\"code\":200}")){

        //    LOGGER.info("数据同步成功" + "\n" +"数据 = " + jsonStrData + "\n" + html);
            b = true;
        }else {

            LOGGER.error("同步失败+++++++"+html);
            return b;
        }

        try {
            post.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return b;
    }
}
