package com.example.arcGIS.service;

import java.sql.*;

public class MysqlUpObjID {


    //设置mysql驱动和url
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://xx.xx.xx.xx.180:3366/ltzjk";
    static final String DB_URL = "jdbc:mysql://xx.xx.xx.xx:3366/ltzjk?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    //设置用户名和密码
    static final String USER = "root";
    static final String PASS = "Infra5@Gep0int";
    //初始化
    static Connection conn = null;
    static Statement stmt = null;


    /**
     * @param
     * @return map
     */
    public static void GetSqlVal(String upSQL) {


        try {
            //注册JDBC驱动

            Class.forName(JDBC_DRIVER);

            //打开链接

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询

            stmt = conn.createStatement();

            //01 新增  02变更  03删除

            stmt.executeUpdate(upSQL);

        } catch (Exception se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        }// 处理 Class.forName 错误


    }
    public static void Insert(String isnertSQL) {


        try {
            //注册JDBC驱动

            Class.forName(JDBC_DRIVER);

            //打开链接

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询

            stmt = conn.createStatement();

            //01 新增  02变更  03删除

            stmt.executeUpdate(isnertSQL);

        } catch (Exception se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        }// 处理 Class.forName 错误


    }


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


}
