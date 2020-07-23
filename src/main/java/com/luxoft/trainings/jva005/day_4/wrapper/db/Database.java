package com.luxoft.trainings.jva005.day_4.wrapper.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

public enum Database {
  INSTANCE;

  private final Logger LOG = LoggerFactory.getLogger(Database.class);
  private final HikariDataSource dataSource;

  Database() {
    dataSource = initDataSource();
  }

  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  private HikariDataSource prepareHikari(DbPropertiesHolder config) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setUsername(config.getUsername());
    hikariConfig.setPassword(config.getPassword());
    hikariConfig.setJdbcUrl(config.getJdbcUrl());
    hikariConfig.setMaximumPoolSize(config.getPoolSize());
    hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
    return new HikariDataSource(hikariConfig);
  }

  private HikariDataSource initDataSource() {
    LOG.info("Initializing Hikari pool connection");
    String config = System.getProperty("db.config");
    if (config == null) {
      throw new IllegalArgumentException("There is no db.config ");
    }
    DbPropertiesHolder propHolder = Optional.ofNullable(DbPropertiesReader.read(config))
                                            .orElseThrow(() -> new RuntimeException("DB properties wasn't provided!"));
    return prepareHikari(propHolder);
  }
}
