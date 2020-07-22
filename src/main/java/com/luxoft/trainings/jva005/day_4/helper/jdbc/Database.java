package com.luxoft.trainings.jva005.day_4.helper.jdbc;

import com.luxoft.trainings.jva005.Template;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static final Database INSTANCE = new Database();
    private String url;
    private String user;
    private String password;

    public Database() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.load(Template.class.getResourceAsStream("/db.properties"));
            url = dbProperties.getProperty("url");
            user = dbProperties.getProperty("user");
            password = dbProperties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
