package com.luxoft.trainings.jva005;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class InjectionExample {

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
        dbProp.load(InjectionExample.class.getResourceAsStream("/db.properties"));

        String url = dbProp.getProperty("url");
        int updateCount = 0;

        try (Connection conn = DriverManager.getConnection(url, dbProp)) {
            Statement statement = conn.createStatement();
            updateCount = statement.executeUpdate("UPDATE SHOP.USERS SET PASSWORD = '" + password + "' WHERE USERNAME = '" + username + "'");
            if (updateCount != 1) {
                throw new SQLException();
            }
        }
    }
}
