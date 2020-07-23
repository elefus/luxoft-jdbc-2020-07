package com.luxoft.trainings.jva005.day_4.pool.second;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {

    private static ConnectionPool instance;
    private ArrayList<Connection> freeConnections = new ArrayList<>();
    private String URL;
    private String user;
    private String password;
    private int maxConn;

    private ConnectionPool(String URL, String user, String password, int maxConn) {
        this.URL = URL;
        this.user = user;
        this.password = password;
        this.maxConn = maxConn;
    }

    public static synchronized ConnectionPool getInstance(String URL, String user, String password, int maxConn) {
        if (instance == null) {
            instance = new ConnectionPool(URL, user, password, maxConn);
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        Connection con;
        if (!freeConnections.isEmpty()) {
            con = freeConnections.remove(freeConnections.size() - 1);
            try {
                if (con.isClosed()) {
                    con = getConnection();
                }
            } catch (Exception e) {
                con = getConnection();
            }
        } else {
            con = newConnection();
        }
        return con;
    }

    private Connection newConnection() {
        Connection con;
        try {
            if (user == null) {
                con = DriverManager.getConnection(URL);
            } else {
                con = DriverManager.getConnection(URL, user, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    public synchronized void freeConnection(Connection con) {
        if ((con != null) && (freeConnections.size() <= maxConn)) {
            freeConnections.add(con);
        }
    }

    public synchronized void release() {
        for (Connection con : freeConnections) {
            try {
                con.close();
                // "Closed connection for pool „
            } catch (SQLException e) {
                // "Can't close connection for pool „
            }
        }
        freeConnections.clear();
    }
}