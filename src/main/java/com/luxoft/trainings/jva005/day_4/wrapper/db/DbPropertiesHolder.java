package com.luxoft.trainings.jva005.day_4.wrapper.db;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isAnyEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbPropertiesHolder {

  private static final Logger LOG = LoggerFactory.getLogger(DbPropertiesHolder.class);
  private static final int DEFAULT_POOL_SIZE = 10;
  private static final int DEFAULT_CONNECTION_TIMEOUT = initDefaultTimeout();

  private final String hostname;
  private final String sid;
  private final String port;
  private final String username;
  private final String password;
  private final String jdbcUrl;
  private final int poolSize;
  private final int connectionTimeout;

  private DbPropertiesHolder(String hostname,
                             String sid,
                             String port,
                             String username,
                             String password,
                             String jdbcUrl,
                             int poolSize,
                             int connectionTimeout) {
    this.hostname = hostname;
    this.sid = sid;
    this.port = port;
    this.username = username;
    this.password = password;
    this.jdbcUrl = jdbcUrl;
    this.poolSize = poolSize;
    this.connectionTimeout = connectionTimeout;
  }

  public static Builder buildForJdbcUrl(String url) {
    return new Builder(url);
  }

  public static Builder buildForHostname(String hostname, String sid, String port) {
    return new Builder(hostname, sid, port);
  }

  public static Builder buildForHostname(String hostname, String sid, int port) {
    return buildForHostname(hostname, sid, String.valueOf(port));
  }

  private static int initDefaultTimeout() {
    return ofNullable(System.getProperty("dbclient"))
        .filter("ucp"::equals)
        .map(c -> 60)
        .orElse(60_000);
  }

  public String getJdbcUrl() {
    if (jdbcUrl == null) {
      return String.format("jdbc:oracle:thin:@%s:%s:%s", hostname, port, sid);
    } else {
      return jdbcUrl;
    }
  }

  public String getHostname() {
    return hostname;
  }

  public String getSid() {
    return sid;
  }

  public String getPort() {
    return port;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public int getPoolSize() {
    return poolSize;
  }

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DbPropertiesHolder that = (DbPropertiesHolder) o;
    return poolSize == that.poolSize
        && connectionTimeout == that.connectionTimeout
        && Objects.equals(hostname, that.hostname)
        && Objects.equals(sid, that.sid)
        && Objects.equals(port, that.port)
        && Objects.equals(username, that.username)
        && Objects.equals(password, that.password)
        && Objects.equals(jdbcUrl, that.jdbcUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostname, sid, port, username, password, jdbcUrl, poolSize, connectionTimeout);
  }

  @Override
  public String toString() {
    return getJdbcUrl();
  }

  public static class Builder {

    private final String hostname;
    private final String sid;
    private final String port;
    private final String jdbcUrl;

    private String username = null;
    private String password = null;

    private int poolSize = DEFAULT_POOL_SIZE;
    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

    private Builder(String jdbcUrl) {
      this.jdbcUrl = jdbcUrl;
      this.hostname = null;
      this.sid = null;
      this.port = null;
    }

    public Builder(String hostname, String sid, String port) {
      this.jdbcUrl = null;
      this.hostname = hostname;
      this.sid = sid;
      this.port = port;
    }

    public Builder setUsername(String username) {
      this.username = username;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder setPoolSize(String poolSize) {
      if (isEmpty(poolSize)) {
        LOG.warn(String.format("No proper data provided for pool size. Default value [%d] loaded", DEFAULT_POOL_SIZE));
        return this;
      }
      try {
        this.poolSize = Integer.parseInt(poolSize);
      } catch (NumberFormatException e) {
        LOG.warn(String.format("Data provided for pool size can not be parsed. Default value [%d] loaded", DEFAULT_POOL_SIZE));
        return this;
      }
      return this;
    }

    public Builder setPoolSize(int poolSize) {
      this.poolSize = poolSize;
      return this;
    }

    public Builder setConnectionTimeout(int connectionTimeout) {
      this.connectionTimeout = connectionTimeout;
      return this;
    }

    public Builder setConnectionTimeout(String connectionTimeout) {
      if (isEmpty(connectionTimeout)) {
        LOG.warn(String.format("No proper data provided for connection timeout. Default value [%d] loaded",DEFAULT_CONNECTION_TIMEOUT));
        return this;
      }
      try {
        this.connectionTimeout = Integer.parseInt(connectionTimeout);
      } catch (NumberFormatException e) {
        LOG.warn(String.format("Data provided for connection timeout can not be parsed. Default value [%d] loaded",DEFAULT_CONNECTION_TIMEOUT));
        return this;
      }
      return this;
    }

    public DbPropertiesHolder build() {
      if (isAnyEmpty(username, password)) {
        throw new IllegalStateException("DB properties must contain username and password");
      }
      return new DbPropertiesHolder(
          hostname,
          sid,
          port,
          username,
          password,
          jdbcUrl,
          poolSize,
          connectionTimeout);
    }
  }
}
