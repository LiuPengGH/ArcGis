package cm.mileagePath;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 */
public class HttpClienHelper {


    public static String httpClienPost(String post1) throws IOException {

        HttpPost post;
        HttpClient httpClient = HttpClients.createDefault();

        post = new HttpPost(post1);
        // 构造消息头
        post.setHeader("Content-type", "application/json; charset=utf-8");

        HttpResponse response = httpClient.execute(post);

        String html = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

        try {

            post.clone();

        } catch (CloneNotSupportedException e) {

            e.printStackTrace();

        }

        return html;
    }

}
