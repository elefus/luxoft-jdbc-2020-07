package com.luxoft.trainings.jva005.day_2;

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
             ResultSet rs = stmt.executeQuery("SELECT USERID, NAME, ADDRESS, PASSWORD, ACCESSLEVEL FROM SHOP.USERS")) {

            while (rs.next()) {
                long userid = rs.getLong("USERID");
                String name = rs.getString("NAME");
                String address = rs.getString("ADDRESS");
                String password = rs.getString("PASSWORD");
                int accesslevel = rs.getInt("ACCESSLEVEL");
                result.add(new User(userid, name, address, password, accesslevel));
            }
        }
        return result;
    }

    private static class User {

        private long userid;
        private String name;
        private String address;
        private String password;
        private int accesslevel;

        public User(long userid, String name, String address, String password, int accesslevel) {
            this.userid = userid;
            this.name = name;
            this.address = address;
            this.password = password;
            this.accesslevel = accesslevel;
        }

        public long getUserid() {
            return userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getAccesslevel() {
            return accesslevel;
        }

        public void setAccesslevel(int accesslevel) {
            this.accesslevel = accesslevel;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userid=" + userid +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", password='" + password + '\'' +
                    ", accesslevel=" + accesslevel +
                    '}';
        }
    }
}
