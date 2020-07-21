package com.luxoft.trainings.jva005.day_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class UpdateStatementExample {

    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(UpdateStatementExample.class.getResourceAsStream("/db.properties"));

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the SKU: ");
        String sku = in.readLine();
        System.out.println("Enter new price: ");
        float price = Float.parseFloat(in.readLine());

        updatePrice(dbProperties, sku, price);
    }

    private static void updatePrice(Properties dbProperties, String sku, float price) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties);
             Statement stat = conn.createStatement()) {
            int touchedRows = stat.executeUpdate("UPDATE SHOP.CATALOG_ITEMS SET PRICE=" + price + " WHERE SKU='" + sku + "'");
            if (touchedRows != 1) {
                throw new SQLException();
            }
        }
    }
}
