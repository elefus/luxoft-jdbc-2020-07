package com.luxoft.trainings.jva005;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class QueryStatementExample {

    public static void main(String[] args) throws SQLException, IOException, FileNotFoundException {
        System.out.println("MAIN =====");
        Properties dbprop = new Properties();
//        dbprop.load(new FileReader(new File("src/main/resources/resource.properties")));
        dbprop.load(QueryStatementExample.class.getResourceAsStream("/db.properties"));

        String url = (String) dbprop.getProperty("url");
        System.out.println("URL=" + url);

        try (Connection conn = DriverManager.getConnection(url, dbprop)) {
            System.out.println(conn.isClosed());
        }
    }
}
