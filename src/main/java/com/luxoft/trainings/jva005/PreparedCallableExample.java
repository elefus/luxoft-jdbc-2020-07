package com.luxoft.trainings.jva005;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class PreparedCallableExample {

    public static void main(String[] args) throws IOException, SQLException {
        Properties dbProp = new Properties();
        dbProp.load(PreparedCallableExample.class.getResourceAsStream("/db.properties"));

        String url = dbProp.getProperty("url");

        try (Connection conn = DriverManager.getConnection(url, dbProp);
             CallableStatement stmt = conn.prepareCall("{ CALL ? = NEXT_PRIME(?)}")) {

            stmt.registerOutParameter(1, JDBCType.BIGINT);
            stmt.setLong(2, 11);

            if (stmt.execute()) {
                long result = stmt.getLong(1);
            }
        }

    }
}
