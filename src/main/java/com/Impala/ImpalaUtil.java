package com.Impala;

/**
 * Created by Administrator on 2017/7/20. 成功实践
 */
import java.sql.*;

public class ImpalaUtil {
    // here is an example query based on one of the Hue Beeswax sample tables
    private static final String SQL_STATEMENT = "select count(*) from lx_app";

    // set the impalad host
    private static final String IMPALAD_HOST = "127.0.0.1";

    // port 21050 is the default impalad JDBC port
    private static final String IMPALAD_JDBC_PORT = "21050";

    private static final String CONNECTION_URL = "jdbc:hive2://" + IMPALAD_HOST + ':' + IMPALAD_JDBC_PORT+"/;auth=noSasl" ;

    private static final String JDBC_DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) {

        System.out.println("\n=============================================");
        System.out.println("Cloudera Impala JDBC Example");
        System.out.println("Using Connection URL: " + CONNECTION_URL);
        System.out.println("Running Query: " + SQL_STATEMENT);

        Connection con = null;

        try {

            Class.forName(JDBC_DRIVER_NAME);

            con = DriverManager.getConnection(CONNECTION_URL,"hive","hive");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL_STATEMENT);

            System.out.println("\n== Begin Query Results ======================");

            // print the results to the console
            while (rs.next()) {
                // the example query returns one String column
                System.out.println(rs.getString(1));
            }

            System.out.println("== End Query Results =======================\n\n");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                // swallow
            }
        }
    }
}

