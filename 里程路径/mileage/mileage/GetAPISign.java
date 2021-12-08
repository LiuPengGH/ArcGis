package cm.mileage;

import java.util.Map;
import java.util.TreeMap;
import cm.mileagePath.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSON;
import static java.util.Objects.isNull;

public class GetAPISign {
	private static final Logger logger = LoggerFactory.getLogger(GetAPISign.class);
	public String getSign(String json,String signkey) {
		JSONObject jsonObject = JSONObject.fromObject(json);	     	     
	     jsonObject.put("sign", "");
	     String jsonnew=jsonObject.toString();
		 Map<String, String> params=(Map<String, String>) JSON.parse(jsonnew);
		//将所有参数放入treeMap中（默认升序）
		   Map<String, String> sortMap = new TreeMap<String, String>();
		   sortMap.putAll(params);
		   // 以k1=v1&k2=v2...方式拼接参数
		   StringBuilder builder = new StringBuilder();
		   for (Map.Entry<String, String> s : sortMap.entrySet()) {
		       String k = s.getKey();
		       String v = s.getValue();
		       if (isNull(v)) {// 过滤空值
		           continue;
		       }
		       builder.append(k).append("=").append(v).append("&");
		   }
		   if (!sortMap.isEmpty()) {
		       builder.deleteCharAt(builder.length() - 1);
		   }
		   String stringSignTemp=builder.toString()+"&key="+signkey;
		   logger.info("临时值---------------------------"+stringSignTemp);
		   return MD5.strToMD5(stringSignTemp).toUpperCase();
		}

}
