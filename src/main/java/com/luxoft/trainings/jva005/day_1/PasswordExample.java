package com.luxoft.trainings.jva005.day_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class PasswordExample {

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        char[] password = askPasswordFromUser();


        Properties dbProps = new Properties();
        dbProps.put("password", password);
        dbProps.put("user", "sa");

        Connection connection = DriverManager.getConnection("jdbc:qwe:test", dbProps);

        for (int i = 0; i < password.length; i++) {
            password[i] = '\0';
        }
        password = null;
        System.gc();


        System.out.println(connection.isClosed());



        TimeUnit.MINUTES.sleep(5);


        connection.close();
    }

    private static char[] askPasswordFromUser() throws IOException {
        System.out.println("Please enter the password:");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        char[] pass = new char[12];
        in.read(pass);

        return pass;
    }
}
