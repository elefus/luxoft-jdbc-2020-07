package com.luxoft.trainings.jva005.day_4.helper.jdbc;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class JDBCHelper {


    public <T> List<T> query(String sql, Function<ResultSet, T> rowMapper, Object... args) {
        return query(sql, rowMapper, __ -> true, args);
    }
    public <T> List<T> query(String sql, Function<ResultSet, T> rowMapper, Predicate<ResultSet> filter, Object... args) {
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareCall(sql)) {

            for (int i = 0; i < args.length; ++i) {
                addArgument(stmt, i, args[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<T> result = new ArrayList<>();
                while (rs.next()) {
                    if (filter.test(rs)) {
                        T value = rowMapper.apply(rs);
                        result.add(value);
//                    rowMapper.andThen(result::add).apply(rs);
                    }
                }
                return result;
            }
        } catch (SQLException exception) {
            throwException(exception);
            throw new AssertionError("Unreachable code");
        }
    }

    public <T> Optional<T> queryUnique(String sql, Function<ResultSet, T> rowMapper, Object... args) {
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareCall(sql)) {

            for (int i = 0; i < args.length; ++i) {
                addArgument(stmt, i + 1, args[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rowMapper.apply(rs));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException exception) {
            throwException(exception);
            throw new AssertionError("Unreachable code");
        }
    }

    public int executeUpdate(String sql, Object... args) {
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareCall(sql)) {

            for (int i = 0; i < args.length; ++i) {
                addArgument(stmt, i + 1, args[i]);
            }

            return stmt.executeUpdate();
        } catch (SQLException exception) {
            throwException(exception);
            throw new AssertionError("Unreachable code");
        }
    }


    private void addArgument(PreparedStatement ps, int index, Object arg) throws SQLException {
        if (arg == null) {
            ps.setNull(index, Types.NULL);
        } else if (arg instanceof CharSequence) {
            ps.setString(index, arg.toString());
        } else if (arg instanceof Integer) {
            ps.setInt(index, (Integer) arg);
        } else if (arg instanceof Long) {
            ps.setLong(index, (Long) arg);
            // ...
        } else if (arg instanceof LocalDate) {
            ps.setDate(index, Date.valueOf((LocalDate) arg));
        } else if (arg instanceof LocalDateTime) {
            ps.setTimestamp(index, Timestamp.valueOf((LocalDateTime) arg));
        } else {
            throw new SQLException("Not supported value type at for the argument with index [" + index + "]");
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void throwException(Throwable exception, Object dummy) throws T {
        throw (T) exception;
    }

    public static void throwException(Throwable exception) {
        JDBCHelper.<RuntimeException>throwException(exception, null);
    }
}
