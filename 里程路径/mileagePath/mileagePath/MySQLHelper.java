package cm.mileagePath;

import cm.arcGIS.arcGISUpJava.LoggerClass;
import java.sql.*;

/**
 * :Mysql 链接
 */
public class MySQLHelper {

    //设置mysql驱动和url
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://58.211.227.180:3366/ltzjk";
     //static final String DB_URL = "jdbc:mysql://58.211.227.180:3366/ltzjk?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
     static final String DB_URL =  "jdbc:mysql://58.211.227.180:3366/ltzjk?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    //设置用户名和密码
    static final String USER = "root";
    static final String PASS = "Infra5@Gep0int";
    //初始化
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    /**
     * @return map
     */
    public static ResultSet GetSqlVal(String Ssql) {


        try {
            //注册JDBC驱动
            //LoggerClass.info("注册JDBC驱动： " + JDBC_DRIVER);
            Class.forName(JDBC_DRIVER);

            //打开链接
            //LoggerClass.info("连接到数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            //LoggerClass.info(" 实例化Statement对象...");
            stmt = conn.createStatement();
            //LoggerClass.info("执行sql...");
            rs = stmt.executeQuery(Ssql);

        } catch (Exception se) {
            // 处理 JDBC 错误
            se.printStackTrace();
            LoggerClass.error(se.toString());
        }// 处理 Class.forName 错误

        //System.out.println("Goodbye!");
        return rs;
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
