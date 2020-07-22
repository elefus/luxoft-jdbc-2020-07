package com.luxoft.trainings.jva005.day_3;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class SortedInsertableResultSetExample {

    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(SortedInsertableResultSetExample.class.getResourceAsStream("/db.properties"));

        try (Connection conn = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties);
             PreparedStatement stmt = conn.prepareStatement("SELECT USERID, USERNAME, NAME, ADDRESS, PASSWORD, ACCESSLEVEL FROM SHOP.USERS ORDER BY ACCESSLEVEL", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = stmt.executeQuery()) {

            rs.moveToInsertRow();
            rs.deleteRow();

            rs.updateString("name", "U_TEST_NAME");
            rs.updateString("username", "U_TEST_USERNAME");
            rs.updateString("address", "U_TEST_ADDRESS");
            rs.updateString("password", "U_TEST_PASSWORD");
            rs.updateInt("accesslevel", 0);

            rs.insertRow();
        }
    }
}
