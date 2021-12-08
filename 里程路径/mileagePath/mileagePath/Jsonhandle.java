package cm.mileagePath;

import com.alibaba.fastjson.JSONObject;

public class Jsonhandle {

    public static String getJsonHandle(String responseJson, String phone, String dateNew) {

        JSONObject jsonObject = JSONObject.parseObject(responseJson);

        String Total_LengthNew = jsonObject.getJSONObject("routes")
                .getJSONArray("features").getJSONObject(0)
                .getJSONObject("attributes").getString("Total_Length");
        int totalLength =(int) GetTotalLength.getTotalLength(phone, Total_LengthNew);

        System.out.println("totalLength = " + totalLength);
        return " update  day_length set date = '" + dateNew + "' ,length = '" +
                totalLength + "' WHERE mobile_phone = '" + phone + "'";
    }
}
