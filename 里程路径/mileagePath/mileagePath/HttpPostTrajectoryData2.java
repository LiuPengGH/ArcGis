package cm.mileagePath;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HttpPostTrajectoryData2 {

    private static final Logger logger = LoggerFactory.getLogger(HttpPostTrajectoryData2.class);

    public static void postTrajectoryData(String userguid ,String phone, String start_date, String end_date, String path) throws SQLException, ParseException {


        CloseableHttpClient httpclient = HttpClients.createDefault();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = df.parse(start_date);
        Date d2 = df.parse(end_date);
        long l = (d2.getTime() - d1.getTime()) / 1000;
        //System.out.println(l);
        if (path.length() < 4) {
            System.out.println("path 小于4");
        } else {

            String[] splitpath = path.substring(3, path.length() - 3).replaceAll("\\[", "").split("],");

            long l1 = l / (splitpath.length - 1);

            JSONArray jsonArray = new JSONArray();

            String date1 = df.format(new Date());
            //System.out.println("当前时间：" + date1);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = simpleDateFormat.parse(date1);
            long time1 = time.getTime();

            String syntime = null;
            for (int i = 0; i < splitpath.length; i++) {
                if (i == 0) {
                    syntime = start_date;

                } else {
                    Date date = new Date(d1.getTime() + l1 * i * 1000);
                    String sDate = df.format(date);
                    syntime = sDate;
                }
                System.out.println(syntime + "sytime ");

                String attribute = phone;
                String longitude = splitpath[i].split(",")[0];
                String latitude = splitpath[i].split(",")[1];
                JSONObject jo = new JSONObject();
                jo.put("attribute", attribute);
                jo.put("longitude", longitude);
                jo.put("latitude", latitude);
                jo.put("syntime", syntime);
                jo.put("uniquecode", userguid);
                jo.put("objects", "02");
                jsonArray.add(jo);
            }

            //System.out.println(jsonArray);

            String base64list = Base64Util.encode(jsonArray.toString());
            ZfEventList object = new ZfEventList();
            object.setList(base64list);
            object.setVersion("2.0");
            object.setTimestamp(Long.toString(time1));
            String json = net.sf.json.JSONObject.fromObject(object).toString();
            HttpPost post = new HttpPost("http://2.32.249.145:80/szAPIGateway/DataResourceCms/gpsSync.do");
            //HttpPost post = new HttpPost("http://www.zhcgdsj.suzhou.gov.cn/szAPIGateway/DataResourceCms/gpsSync.do");
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader("key", "cb15bc072bfc47df801c6cc4b6059f01");
            post.setHeader("secret", "ffc9fe4c50814aa1829e8b6276a06a12");
            String signkey = "597f00e3a1274217b978b016774fcb56";
            List<NameValuePair> list = new ArrayList<NameValuePair>();

            GetAPISign getSign = new GetAPISign();
            String sign = getSign.getSign(json, signkey);
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(json);
            jsonobject.put("sign", sign);
            json = jsonobject.toString();
            //System.out.println("json = " + json);
            list.add(new BasicNameValuePair("data", json));
            try {
                post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
                CloseableHttpResponse response = httpclient.execute(post);
                if (response.getStatusLine().getStatusCode() == 200) {
                    //HttpEntity entity = response.getEntity();
                   //String res = EntityUtils.toString(entity);
                    System.out.println("StatusCode = 200" );
                    logger.info("StatusCode = 200");
                } else {
                    logger.error("返回错误，错误代码:" + response.getStatusLine().getStatusCode());
                }
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            } catch (ClientProtocolException e) {
                logger.error(e.getMessage(), e);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
