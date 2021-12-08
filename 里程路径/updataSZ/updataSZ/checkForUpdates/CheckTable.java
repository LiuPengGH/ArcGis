package cm.updataSZ.checkForUpdates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.ResultSet;

public class CheckTable {


    public static void main(String[] args) throws Exception {

        String path = "C:\\Users\\lp\\Documents\\javaJAR\\mc.properties";

        System.out.println(path);
        String logSQL = "SELECT synchronization_log.rowGuid,synchronization_log.rowGuid,synchronization_log.`Code`,componentinfofront.Update_Data FROM `synchronization_log`  JOIN componentinfofront " +
                "ON synchronization_log.rowGuid = componentinfofront.RowGuid WHERE `Code` = '200' AND Message = '同步成功' AND Update_Data  is NOT NULL ";
        ResultSet selectLog = MysqlUtil.select(logSQL);
        System.out.println(logSQL);
        while (selectLog.next()){

            String updateData = selectLog.getString("Update_Data");
            String rowGuid = selectLog.getString("rowGuid");
            System.out.println(rowGuid);
            System.out.println(updateData);
            boolean b1 = HttpInsertPost.httpClienPost(updateData, path);
            if (b1 ){
                String upMe = "update synchronization_log set Message = '同步更新成功' where rowGuid = '"+rowGuid+"' " ;
                boolean b = MysqlUtil.UpdateMySQL(upMe);
                System.out.println(b);
            }else System.out.println("同步失败");

        }

    }
}
