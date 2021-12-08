package cm.mileagePath;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTrailSql {

    public static String getTrailSql(ResultSet resultSet, String format, String phone,String dateNew) {

        String trailSql;
        try {
            String date = resultSet.getString("date");

            if (date == null || !date.substring(0, 10).equals(format)) {

                trailSql = GetTrailPath.getTrailPath(phone, format + " 08:30:00", dateNew);
                String sql = "UPDATE day_length SET length = '0.00000000' WHERE mobile_phone = '" + phone + "'";
                MySQLHelper.UpdateMySQL(sql);

            } else {

                //判断时间是否小于17：30：00
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//创建日期转换对象：年月日 时分秒
                String date2 = format + " 17:00:00"; //每天里程结束时间  17：30：00
                try {

                    Date dateD = sdf.parse(date2);    //结束时间
                    Date today = sdf.parse(date); //获取到的时间
                    Date startday = sdf.parse(format + " 08:30:00");

                    if (dateD.getTime() >= today.getTime() && today.getTime() >= startday.getTime()) {
                        trailSql = GetTrailPath.getTrailPath(phone, date, dateNew);
                    } else {
                        trailSql = GetTrailPath.getTrailPath(phone, format + " 08:30:00", dateNew);
                    }

                } catch (ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    trailSql = "";
                }
            }
            
        } catch (SQLException throwables) {

            throwables.printStackTrace();
            trailSql = GetTrailPath.getTrailPath(phone, format + " 08:30:00", dateNew);

        }

       // System.out.println("执行sql = " + trailSql);
        return trailSql;
    }

}
