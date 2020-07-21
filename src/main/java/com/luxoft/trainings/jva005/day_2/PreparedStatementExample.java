package com.luxoft.trainings.jva005.day_2;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class PreparedStatementExample {

    public static void main(String[] args) throws IOException, SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имя пользователя: ");
        String username = in.nextLine();
        System.out.println("Введите новый пароль пользователя: ");
        String password = in.nextLine();

        updatePassword(username, password);
    }

    private static void updatePassword(String username, String password) throws IOException, SQLException {
        Properties dbProp = new Properties();
        dbProp.load(PreparedStatementExample.class.getResourceAsStream("/db.properties"));

        String url = dbProp.getProperty("url");

        try (Connection conn = DriverManager.getConnection(url, dbProp);
             PreparedStatement stmt = conn.prepareStatement("UPDATE SHOP.USERS SET PASSWORD=? WHERE USERNAME=?")) {
            stmt.setString(1, password);
            stmt.setString(2, username);

            int touchedRows = stmt.executeUpdate();

            if (touchedRows != 1) {
                throw new SQLException();
            }
        }
    }
}
