package cm.updataSZ.checkForUpdates;

import java.sql.*;

public class MysqlUtil {


    //设置mysql驱动和url
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://58.211.227.180:3366/ltzjk";
    static final String DB_URL = "jdbc:mysql://58.211.227.180:3366/ltzjk?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    //设置用户名和密码
    static final String USER = "root";
    static final String PASS = "Infra5@Gep0int";
    //初始化
    static Connection conn = null;
    static Statement stmt = null;


    public static ResultSet select(String isnertSQL) {


        try {
            //注册JDBC驱动

            Class.forName(JDBC_DRIVER);

            //打开链接

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询

            stmt = conn.createStatement();

            //01 新增  02变更  03删除

            ResultSet resultSet = stmt.executeQuery(isnertSQL);
           return resultSet;

        } catch (Exception se) {
            // 处理 JDBC 错误

            se.printStackTrace();
            return null;


        }// 处理 Class.forName 错误

    }


    public static boolean UpdateMySQL(String upDataMysql) {
        boolean b = false;
        try {
            //注册JDBC驱动
            Class.forName(JDBC_DRIVER);

            //打开链接
            //System.out.println("连接到数据库");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            //System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();

            int iUp = stmt.executeUpdate(upDataMysql);

            if (iUp != -1) {
                b = true;
            }
        } catch (Exception se) {
            // 处理 JDBC 错误
            se.printStackTrace();

        }// 处理 Class.forName 错误
        finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return b;
    }


}
