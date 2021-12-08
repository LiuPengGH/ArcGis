package cm.mileage;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class HttpClienHelper {


    public static String httpClienPost(String post1) throws IOException {
        // LoggerInClass.info("---开始同步数据");
        boolean b = false;
        HttpPost post = null;
        HttpClient httpClient = HttpClients.createDefault();


        post = new HttpPost(post1);
        // 构造消息头
        //LoggerClass.info("构造消息头...");
        post.setHeader("Content-type", "application/json; charset=utf-8");


        HttpResponse response = httpClient.execute(post);

        String html = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));

        try {
            post.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return html;
    }

}
