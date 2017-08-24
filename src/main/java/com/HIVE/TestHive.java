package com.HIVE;


import java.sql.*;

/**
 * Created by Administrator on 2017/7/20.  可以成功运行
 */
public class TestHive {

    // set the impalad host
    private static final String IMPALAD_HOST = "127.0.0.1";

    // port 21050 is the default impalad JDBC port
    private static final String IMPALAD_JDBC_PORT = "10000";

    private static final String CONNECTION_URL = "jdbc:hive2://" + IMPALAD_HOST + ':' + IMPALAD_JDBC_PORT + "/default";
    private static final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        long startTime = System.currentTimeMillis();
        Class.forName(JDBC_DRIVER_NAME);
        Connection con = DriverManager.getConnection(CONNECTION_URL, "hive", "hive");
        String sql = "select count(*) cnt from lx_app";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("cnt"));
//            System.out.println(rs.getRow());
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
    }
}
