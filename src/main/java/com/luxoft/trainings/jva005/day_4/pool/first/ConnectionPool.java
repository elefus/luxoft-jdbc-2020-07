package com.luxoft.trainings.jva005.day_4.pool.first;

import lombok.experimental.Delegate;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool implements Closeable {

    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private static final Logger log = Logger.getLogger(ConnectionPool.class.getName());

    private List<PooledConnection> freeConnections;

    private String url;
    private String username;
    private String password;

    private Integer connectionNumber;
    private Semaphore semaphore;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setConnectionNumber(Integer connectionNumber) {
        this.connectionNumber = connectionNumber;
    }

    private ConnectionPool() {}

    public void initConnections() throws SQLException {
        semaphore = new Semaphore(connectionNumber);
        freeConnections = new ArrayList<>(connectionNumber);
        for (int i = 0; i < connectionNumber; i++) {
            freeConnections.add(new PooledConnection(DriverManager.getConnection(url, username, password)));
        }
        log.log(Level.INFO, connectionNumber + " connections created");
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws InterruptedException {
        try {
            semaphore.acquire();
            return freeConnections.remove(freeConnections.size() - 1);
        } catch (InterruptedException ex) {
            log.log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    @Override
    public void close() {
        int releaseNumber = connectionNumber - semaphore.availablePermits();
        semaphore.release(releaseNumber);
    }

    private class PooledConnection implements Connection {

        @Delegate(excludes = AutoCloseable.class)
        private final Connection connection;

        public PooledConnection(Connection c) throws SQLException {
            this.connection = c;
            this.connection.setAutoCommit(true);
        }

        @Override
        public void close() throws SQLException {
            connection.setReadOnly(false);
            freeConnections.add(this);
            semaphore.release();
        }
    }
}