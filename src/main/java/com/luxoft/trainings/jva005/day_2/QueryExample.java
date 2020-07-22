package com.luxoft.trainings.jva005.day_2;

import com.luxoft.trainings.jva005.domain.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QueryExample {

    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(QueryExample.class.getResourceAsStream("/db.properties"));

        List<User> users = getUsers(dbProperties);

        users.forEach(user -> System.out.println(user));
    }

    private static List<User> getUsers(Properties dbProperties) throws SQLException {
        ArrayList<User> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT USERID, NAME, ADDRESS, USERNAME, PASSWORD, ACCESSLEVEL, RATING FROM SHOP.USERS")) {

            while (rs.next()) {
                long userid = rs.getLong("USERID");
                String name = rs.getString("NAME");
                String address = rs.getString("ADDRESS");
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                int accesslevel = rs.getInt("ACCESSLEVEL");
                int rating = rs.getInt("RATING");
                result.add(new User(userid, name, address, username, password, accesslevel, rating));
            }
        }
        return result;
    }
}
