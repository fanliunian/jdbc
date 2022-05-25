package com.master.jdbc;

/**
 * @Author: cf
 * @Date: 2021/3/19 14:39
 */

import java.sql.*;


public class Jdbc {

    public static void main(String[] args) throws Exception {

//        System.out.println("format: type jdbc user passWord dbName tableName");
//        args = new String[]{"oracle","jdbc:oracle:thin:@//172.20.238.5:1521/orcl","dp_test","dp_test","DP_TEST","DP_DATE_CONFIG"};
//        args = new String[]{"mysql","jdbc:mysql://localhost:3306/aj_report?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false","root","123456","aj_report","access_user"};

        String type = args[0];
        String jdbc = args[1];
        String user = args[2];
        String passWord = args[3];
        String dbName = args[4];
        String tableName = args[5];

        System.out.println(jdbc);
        System.out.println(user);
        System.out.println(passWord);
        System.out.println(dbName);
        System.out.println(tableName);

        System.out.println("begin try connect.................");

        Connection con = null;
        if (type.toLowerCase().contains("mysql")){
            con= isMysqlConnectable(jdbc,user,passWord);
        }else {
            con= isOracleConnectable(jdbc,user,passWord);
        }

        if (con != null){
            System.out.println("query table column.................");
            executeQuery(con,dbName,tableName);
        }


    }



    public static Connection isMysqlConnectable(String jdbc,String user,String password)  {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(jdbc, user, password);
            System.out.println("success-----------------------------------");

        }catch (Exception e) {
            System.out.println("connect failure-----------------------------------");
            System.out.println("jdbc:mysql://172.20.238.126:3306/test");
            e.printStackTrace();
        }
        return con;
    }

    public static Connection isOracleConnectable(String jdbc,String user,String password) {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(jdbc, user, password);
            System.out.println("success-----------------------------------");
        }catch (Exception e){
            System.out.println("connect failure-----------------------------------");
            System.out.println("jdbc:oracle:thin:@//172.16.16.51:1521/ORCL");
            e.printStackTrace();
        }
        return con;
    }

    public static Object executeQuery(Connection connection,String dbName,String tableName) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        DatabaseMetaData metaData = connection.getMetaData();

        try {
            rs = metaData.getColumns(dbName, null, tableName, "%");
            while (rs.next()) {
                System.out.println(rs.getString(4)+" --- "+ rs.getString(6)+" --- "+ rs.getInt(7));
            }
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
