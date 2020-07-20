package com.luxoft.trainings.jva005;

import java.sql.*;

public class StatementExample {

    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:qwe:test")) {

            Statement simple = conn.createStatement();
            simple.executeQuery("SELECT * FROM USERS WHERE USERID = 5");


            try (PreparedStatement prepared = conn.prepareStatement("SELECT * FROM USERS WHERE USERID = ?")) {

                prepared.executeQuery();
            }

        }
    }
}
