package com.luxoft.trainings.jva005.day_3;

import com.luxoft.trainings.jva005.Template;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class TransactionExample {

    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(Template.class.getResourceAsStream("/db.properties"));

        try (Connection conn = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties)) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement("UPDATE shop.ORDER_ITEMS SET QUANTITY = 100 WHERE SKU='0001' AND ORDERID=1");
                 PreparedStatement stmt2 = conn.prepareStatement("UPDATE shop.ORDER_ITEMS SET QUANTITY = 100 WHERE SKU='0004' AND ORDERID=1")) {

                if (stmt1.executeUpdate() != 1) {
                    throw new SQLException();
                }

                try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SHOP.ORDER_ITEMS WHERE SKU='0001' AND ORDERID=1")) {
                    while (rs.next()) {
                        System.out.println(rs.getString("sku") + " = " + rs.getInt("quantity"));
                    }
                }

                if (stmt2.executeUpdate() != 1) {
                    throw new SQLException();
                }
            }
            conn.commit();
        }
    }
}
