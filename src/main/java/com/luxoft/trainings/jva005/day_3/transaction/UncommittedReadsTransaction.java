package com.luxoft.trainings.jva005.day_3.transaction;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class UncommittedReadsTransaction {

    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProperties = new Properties();
        dbProperties.load(UncommittedReadsTransaction.class.getResourceAsStream("/db.properties"));

        try (Connection conn1 = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties)) {
            DatabaseMetaData dbMeta = conn1.getMetaData();
            System.out.println(dbMeta.getDefaultTransactionIsolation());
            System.out.println(dbMeta.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED));

            conn1.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            conn1.setAutoCommit(false);

            long countOrderedItems = getCountOrderedItems(conn1);
            System.out.println("Count before = " + countOrderedItems);

            try (Connection conn2 = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties);
                 PreparedStatement stmt = conn2.prepareStatement("UPDATE shop.ORDER_ITEMS SET QUANTITY=0")) {
                conn2.setAutoCommit(false);

                stmt.executeUpdate();

                System.out.println("Count after update #2 = " + getCountOrderedItems(conn2));

                countOrderedItems = getCountOrderedItems(conn1);
                System.out.println("Count before = " + countOrderedItems);

            }
//            System.out.println("Count after conn2 close #1 = " + getCountOrderedItems(conn1));
            System.out.println("....");
        }
    }

    private static long getCountOrderedItems(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT SUM(quantity) AS count_ordered_items FROM SHOP.ORDER_ITEMS");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("count_ordered_items");
            } else {
                throw new AssertionError();
            }
        }
    }
}
