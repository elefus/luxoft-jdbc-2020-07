package com.luxoft.trainings.jva005.day_3;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ReverseResultSetExample {

    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(ReverseResultSetExample.class.getResourceAsStream("/db.properties"));

        try (Connection conn = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM shop.users", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setFetchDirection(ResultSet.FETCH_REVERSE);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.afterLast();

                while (rs.previous()) {
                    System.out.println(rs.getLong("userid"));
                }
            }
        }
    }
}
