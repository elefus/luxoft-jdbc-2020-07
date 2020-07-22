package com.luxoft.trainings.jva005.day_3.transaction;

import com.luxoft.trainings.jva005.Template;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class BatchExample {


    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(Template.class.getResourceAsStream("/db.properties"));

        try (Connection conn = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO SHOP.USERS (NAME, ADDRESS, USERNAME, PASSWORD, ACCESSLEVEL) VALUES (?, ?, ?, ?, ?)")) {

            conn.setAutoCommit(false);

            for (int i = 0; i < 3; i++) {
                stmt.setString(1, "NAME_" + i);
                stmt.setString(2, "ADDRESS_" + i);
                stmt.setString(3, "USERNAME_" + i);
                stmt.setString(4, "PASSWORD_" + i);
                stmt.setInt(5, 0);
                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();
            System.out.println(Arrays.toString(results));
            conn.commit();
        } catch (BatchUpdateException ex) {
            int[] results = ex.getUpdateCounts();
            for (int i = 0; i < results.length; i++) {
                int currentResult = results[i];
                if (currentResult == Statement.SUCCESS_NO_INFO || currentResult > 0) {
                    System.out.println("OK");
                } else {
                    System.out.println("NO OK");

                    // ....
                }
            }

        }
    }
}
