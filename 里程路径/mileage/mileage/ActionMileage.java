package cm.mileage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActionMileage {

    public static void main(String[] args) throws SQLException, IOException {

        //String path = "C:\\Users\\Administrator\\Documents\\javaJAR\\ActionMileage.properties";
        String getPhoneSQL = "SELECT * FROM day_length";//读取手机号定义sql
        ResultSet resultSet = MySQLHelper.GetSqlVal(getPhoneSQL);

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date1).substring(0, 10);
        String dateNew = simpleDateFormat.format(date1);

        while (resultSet.next()) {

            String phone = resultSet.getString("mobile_phone");
            System.out.println("------------------------------" + phone + "------------------------------");
            if (phone.equals("") || phone.isEmpty()) {
                continue;
            }
            String trailSql = GetTrailSql.getTrailSql(resultSet, format, phone);

            if (trailSql.equals("")) {
                System.out.println("时间不在8：30--17：30");
                continue;
            }

            String arcGISUrl = GetArcGISUrl.getArcGISUrl(trailSql,dateNew,phone);

            if (arcGISUrl.equals("")) {
                continue;
            }

            String responseJson = HttpClienHelper.httpClienPost(arcGISUrl);  //根据url获取里程Total_Length
           // System.out.println("返回数据 ： "+responseJson);
            if (responseJson.contains("error")) {

                System.out.println("返回错误 ！  ");
                String upSql = " update  day_length set date = '" + dateNew + "' ,length = '" +
                        0 + "' WHERE mobile_phone = '" + phone + "'";
                MySQLHelper.UpdateMySQL(upSql);
                continue;
            }
            String upSql = Jsonhandle.getJsonHandle(responseJson, phone, dateNew); //Json串处理
            MySQLHelper.UpdateMySQL(upSql);

        }
    }
}
