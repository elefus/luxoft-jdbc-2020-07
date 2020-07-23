package com.luxoft.trainings.jva005.day_4.wrapper.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SqlWrapper<T> {

  private final Database db = Database.INSTANCE;

  private final static Logger LOG = LoggerFactory.getLogger(SqlWrapper.class);

  protected abstract String getQuery();

  protected abstract T parse(ResultSet rs) throws SQLException;

  public List<T> query(Object...variables) {
    try (Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(getQuery())) {

      for (int i = 0; i < variables.length; i++) {
        addVariable(ps, i + 1, variables[i]);
      }

      try (ResultSet rs = ps.executeQuery()) {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
          list.add(parse(rs));
        }
        return list;
      }
    } catch (SQLException e) {
      LOG.error("Exception while querying db", e);
    }
    return Collections.emptyList();
  }


  private void addVariable(PreparedStatement ps, int index, Object o) throws SQLException {
    if (o == null)
      ps.setNull(index, Types.VARCHAR);
    else if (o instanceof String)
      ps.setString(index, (String) o);
    else if (o instanceof Integer)
      ps.setInt(index, (Integer) o);
    else if (o instanceof Double)
      ps.setDouble(index, (Double) o);
    else if (o instanceof Long)
      ps.setLong(index, (Long) o);
    else if (o instanceof Float)
      ps.setFloat(index, (Float) o);
    else if (o instanceof LocalDate) {
      ps.setDate(index, Date.valueOf((LocalDate) o));
    } else if (o instanceof LocalDateTime) {
      ps.setTimestamp(index, Timestamp.valueOf((LocalDateTime) o));
    } else
      throw new SQLException(String.format("Not supported value type at position %d", index));
  }
}
