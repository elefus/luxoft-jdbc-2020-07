package com.luxoft.trainings.jva005.day_3;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class UpdatableResultSetExample {

    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(UpdatableResultSetExample.class.getResourceAsStream("/db.properties"));

        try (Connection conn = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties);
             PreparedStatement stmt = conn.prepareStatement("SELECT USERID, NAME, ADDRESS, PASSWORD, ACCESSLEVEL FROM SHOP.USERS", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                System.out.println("UserID: " + rs.getLong("userid"));
                int accesslevel = rs.getInt("accesslevel");
                System.out.println("Before update: " + accesslevel);

                rs.updateInt("accesslevel", accesslevel + 1);
                rs.updateString("password", "abc");

                rs.updateRow();

                System.out.println("After update: " + rs.getInt("accesslevel"));
            }

        }
    }
}
