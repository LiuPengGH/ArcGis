package cm.mileagePath;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTotalLength {
    public static double getTotalLength(String phone, String Total_LengthNew) {

        double Total_Length;
        ResultSet resultSet1 = MySQLHelper.GetSqlVal("SELECT length from day_length where mobile_phone ='" + phone + "'");
        try {

            resultSet1.next();
            String length = resultSet1.getString("length");

            double v = Double.parseDouble(Total_LengthNew);
            System.out.println("最新里程 = " + v);
            double v1 = Double.parseDouble(length);

            System.out.println("历史里程 = " + v1);
            BigDecimal b1 = new BigDecimal(v);
            BigDecimal b2 = new BigDecimal(v1);
            Total_Length = b1.add(b2).doubleValue();

            System.out.println("总里程 = " + Total_Length);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0.0;
        }
        return Total_Length;
    }
}
