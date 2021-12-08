package cm.mileagePath;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpPostClientTest {
	private static final Logger logger = LoggerFactory.getLogger(HttpPostClientTest.class);
	
//	/*苏州区县深度对接调用新增接口*/
	public static void main(String[] args) throws ClientProtocolException, UnsupportedEncodingException{

		CloseableHttpClient httpclient = HttpClients.createDefault();
		String str="[{\"objects\":\"01\",\"uniquecode\":\"20180530001123\",\"attribute\":\"13120699999\",\"longitude\":\"120.64993709961755\",\"latitude\":\"31.290636200000677\"}]";
		String base64list = Base64Util.encode(str);
		System.out.println(base64list);
		ZfEventList object=new ZfEventList();
		object.setList(base64list);
		object.setVersion("1.0");
		object.setTimestamp("1525697471895");
		String json= JSONObject.fromObject(object).toString();
		HttpPost post = new HttpPost("http://www.zhcgdsj.suzhou.gov.cn/szAPIGateway/DataResourceCms/gpsSync.do");
		post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
		post.setHeader("key", "cb15bc072bfc47df801c6cc4b6059f01");
		post.setHeader("secret", "ffc9fe4c50814aa1829e8b6276a06a12");
		String signkey="597f00e3a1274217b978b016774fcb56";
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		GetAPISign getSign=new GetAPISign();
		String sign=getSign.getSign(json, signkey);
		JSONObject jsonobject=JSONObject.fromObject(json);
		jsonobject.put("sign", sign);
		json=jsonobject.toString();
		System.out.println("json = " + json);
		list.add(new BasicNameValuePair("data",json));
		try {
			post.setEntity(new UrlEncodedFormEntity(list,"utf-8"));
			CloseableHttpResponse response = httpclient.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity = response.getEntity();
				String res = EntityUtils.toString(entity);
				System.out.println("res = " +res);
			}else{
				logger.info("返回错误，错误代码:"+response.getStatusLine().getStatusCode());
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}

	}
}
