package cm.mileage;

import com.alibaba.fastjson.JSONObject;

import java.text.DecimalFormat;

public class Jsonhandle {

    public static String getJsonHandle(String responseJson, String phone, String dateNew) {


        JSONObject jsonObject = JSONObject.parseObject(responseJson);

        String Total_LengthNew = jsonObject.getJSONObject("routes")
                .getJSONArray("features").getJSONObject(0)
                .getJSONObject("attributes").getString("Total_Length");
        int totalLength =(int) GetTotalLength.getTotalLength(phone, Total_LengthNew);


        String upSql = " update  day_length set date = '" + dateNew + "' ,length = '" +
                totalLength + "' WHERE mobile_phone = '" + phone + "'";
//        String insetSQL = "INSERT INTO day_len_detail (mobile_phone,date,length) VALUES ('"+ phone+"','"+dateNew+"','"+totalLength+"')";
//        MySQLHelper.UpdateMySQL(insetSQL);

        return upSql;
    }
}
