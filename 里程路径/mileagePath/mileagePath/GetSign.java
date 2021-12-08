package cm.mileagePath;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class GetSign {

    /**
     * Description:MD5工具生成token
     * @param value
     * @return
     */
    public String getMD5Value(String value){

       //return DigestUtils.md5Hex(value);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5ValueByteArray = messageDigest.digest(value.getBytes());
            BigInteger bigInteger = new BigInteger(1 , md5ValueByteArray);
            return bigInteger.toString(32).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 生成签名
     * @param map
     * @return
     */
    public String getSignToken(Map<String, String> map) {
        String result = "";

        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }
            }

            result = sb.toString()+"&signkey=597f00e3a1274217b978b016774fcb56";
            System.out.println(result);


            //进行MD5加密
            result = getMD5Value("list=W3sib2JqZWN0cyI6IjAxIiwidW5pcXVlY29kZSI6IjIwMTgwNTMwMDAxMTIzIiwiYXR0cmlidXRl\n" +
                    "IjoiMTMxMjA2OTk5OTkiLCJsb25naXR1ZGUiOiIxMjAuNjQ5OTM3MDk5NjE3NTUiLCJsYXRpdHVk\n" +
                    "ZSI6IjMxLjI5MDYzNjIwMDAwMDY3NyJ9XQ==&sign=&timestamp=1525697471895&version=1.0&key=597f00e3a1274217b978b016774fcb56");
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}
