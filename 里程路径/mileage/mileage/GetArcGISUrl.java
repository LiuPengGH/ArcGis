package cm.mileage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetArcGISUrl {

    public static String getArcGISUrl(String trailSql,String dataNew,String phone) throws SQLException, UnsupportedEncodingException {

        ResultSet resultSet1 = MySQLHelper.GetSqlVal(trailSql);
        String geometry = "";
        resultSet1.last();// 移动到最后
        if (resultSet1.getRow() < 2) {

            String upSql = " update  day_length set date = '" + dataNew + "'  WHERE mobile_phone = '" + phone + "'";
          MySQLHelper.UpdateMySQL(upSql);
            System.out.println("里程更新坐标量 = " + resultSet1.getRow());
            return "";
        }
        resultSet1.beforeFirst();//将结果集指针指回到开始位置，这样才能通过while获取rs中的数据

        while (resultSet1.next()) {

            String gisx = resultSet1.getString("gisx");
            String gisy = resultSet1.getString("gisy");

            geometry += "{\"geometry\":{\"x\":" + gisx + ",\"y\":" + gisy + "}},";
        }
        //System.out.println("坐标数据为 ： " + geometry);
        String features = "{\"features\":[" + geometry + " ]}";
        //System.out.println("坐标数据为 ： "+features);

        String urlStr = URLEncoder.encode(features, "gb2312");
        String url = "http://58.211.255.58:6080/arcgis/rest/services/ZJG/RGNetWork/NAServer/Route/solve?stops=" +
                urlStr + "&returnDirections=false&f=json";
        return url;
    }
}
