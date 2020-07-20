package com.luxoft.trainings.jva005;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExceptionExample {

    public static void main(String[] args) {

//        throw new RuntimeException("A", new RuntimeException("B"));


//        checkDbOldStyle();
        checkDbTry();
    }

    private static void checkDbOldStyle() {
        Connection connection = null;
        SQLException thrown = null;
        try {
            connection = new BrokenConnection();

            // business-logic used connection
            System.out.println(connection.isClosed());

        } catch (SQLException ex) {
            // log.error("", ex)
            // handle exception
//            ex.printStackTrace();
            thrown = ex;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    // log.error("", ex)
//                    ex.printStackTrace();
                    if (thrown != null) {
                        thrown.addSuppressed(ex);
                    }
                    throw new RuntimeException("From finally", thrown);
                }
            }
        }
    }

    private static void checkDbTry() {
        // try-with-resources
        try (Connection connection = DriverManager.getConnection("asdasd", "asdas", "asd")) {
            // business-logic used connection
            System.out.println(connection.isClosed());
        } catch (SQLException ex) {
            throw new RuntimeException("From catch", ex);
        }
    }
}
