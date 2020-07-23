package com.luxoft.trainings.jva005.day_4.pool.third;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private final int connectionLimit = 10;
    private final BlockingQueue<Connection> freeConnections = new ArrayBlockingQueue<>(connectionLimit);

    public <T> T execute(ConnectionAction<T> action) throws SQLException {
        Connection conn = freeConnections.poll();
        try {
            return action.execute(conn);
        } finally {
            if (freeConnections.offer(conn)) {

            }
        }
    }

    @FunctionalInterface
    public interface ConnectionAction<T> {
        T execute(Connection conn) throws SQLException;
    }

    @RequiredArgsConstructor
    private static class ConnectionProxy implements Connection {

        @Delegate(excludes = AutoCloseable.class)
        private final Connection connection;

        @Override
        public void close() {
            // return to the freeConnections pool
        }
    }
}