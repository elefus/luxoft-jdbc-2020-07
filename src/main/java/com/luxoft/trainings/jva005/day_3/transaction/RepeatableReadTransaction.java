package com.luxoft.trainings.jva005.day_3.transaction;

import com.luxoft.trainings.jva005.domain.OrderItems;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepeatableReadTransaction {

    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(RepeatableReadTransaction.class.getResourceAsStream("/db.properties"));

        try (Connection conn1 = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties);
             PreparedStatement insert = conn1.prepareStatement("INSERT INTO shop.ORDER_ITEMS (sku, orderid, quantity) VALUES ('0003', 1, 100)")) {
            DatabaseMetaData dbMeta = conn1.getMetaData();
            if (dbMeta.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ)) {
                conn1.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            }
            conn1.setAutoCommit(false);

            try (Connection conn2 = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties)) {
                conn2.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                conn2.setAutoCommit(false);

                System.out.println("Conn1 #1" + findOrderItemsByOrderId(conn1, 1));
                System.out.println("Conn2 #2" + findOrderItemsByOrderId(conn2, 1));

                if (insert.executeUpdate() != 1) {
                    throw new AssertionError();
                }

                System.out.println("Conn1 #1" + findOrderItemsByOrderId(conn1, 1));
                System.out.println("Conn2 #2" + findOrderItemsByOrderId(conn2, 1)); // Phantom row could be visible in this transaction

                conn1.commit();

                System.out.println("Conn1 #1" + findOrderItemsByOrderId(conn1, 1));
                System.out.println("Conn2 #2" + findOrderItemsByOrderId(conn2, 1));
            }
        }
    }

    private static List<OrderItems> findOrderItemsByOrderId(Connection conn, long orderId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT sku, quantity FROM shop.ORDER_ITEMS WHERE ORDERID=?")) {
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<OrderItems> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new OrderItems(
                            rs.getString("sku"),
                            orderId,
                            rs.getInt("quantity")
                    ));
                }
                return result;
            }
        }
    }
}


