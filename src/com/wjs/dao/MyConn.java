package com.wjs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConn {


    static {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConn() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://123.60.94.31:3306/otp?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&connectTimeout=1000&socketTimeout=6000"
                    , "root", "root");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
