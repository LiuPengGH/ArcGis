package cm.mileagePath;

import java.sql.ResultSet;

public class GetStartTime {


    public  static String getStartTime(String phone){
        String startTime = "";
        String SLQ = "SELECT date FROM day_length WHERE mobile_phone = '15506280389'";
        ResultSet resultSet = MySQLHelper.GetSqlVal(SLQ);
        return startTime;
    }
}
