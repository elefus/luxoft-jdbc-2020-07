package com.luxoft.trainings.jva005.day_4.pool.third;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Launcher {

    public static void main(String[] args) throws SQLException {
        ConnectionPool pool = new ConnectionPool();


        pool.execute(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT ...")) {
                stmt.execute();////
            }
            return null;
        });
    }
}
