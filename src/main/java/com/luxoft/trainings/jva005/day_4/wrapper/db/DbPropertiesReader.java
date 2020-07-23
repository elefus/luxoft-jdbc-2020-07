package com.luxoft.trainings.jva005.day_4.wrapper.db;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbPropertiesReader {

  private final static Logger LOG = LoggerFactory.getLogger(DbPropertiesReader.class);
  public final static String DB_PROPS_KEY_JDBC_URL = "url";
  public final static String DB_PROPS_KEY_HOSTNAME = "HostName";
  public final static String DB_PROPS_KEY_SID = "SID";
  public final static String DB_PROPS_KEY_PORT = "Port";
  public final static String DB_PROPS_KEY_USERNAME = "UserName";
  public final static String DB_PROPS_KEY_PASSWORD = "Password";
  public final static String DB_PROPS_KEY_ENC_MODE = "encryption.mode";
  public final static String DB_PROPS_KEY_ENC_PRIVATE_KEY_PATH = "encryption.private.key.path";
  public final static String DB_PROPS_KEY_ENC_PASS_PATH = "encryption.encrypted.password.path";
  public final static String DB_PROPS_KEY_ALGORITHM = "ALGORITHM";
  public final static String DB_PROPS_KEY_KEY_ALGORITHM = "KEY_ALGORITHM";
  public final static String DB_PROPS_KEY_KEY_LENGTH = "KEY_LENGTH";
  public final static String DB_PROPS_KEY_ITERATION = "ITERATION";
  public final static String DB_PROPS_KEY_CIPHER_TRANSFORMATION = "CIPHER_TRANSFORMATION";
  public final static String DB_PROPS_KEY_CONNECTION_TIMEOUT = "core.connection.pool.timeout";
  public final static String DB_PROPS_KEY_CONNECTION_POOLS_SIZE = "core.connection.pool.size";

  public static DbPropertiesHolder read(Properties properties) {
    String hostname = properties.getProperty(DB_PROPS_KEY_HOSTNAME);
    String sid = properties.getProperty(DB_PROPS_KEY_SID);
    String port = properties.getProperty(DB_PROPS_KEY_PORT);
    String jdbcUrl = properties.getProperty(DB_PROPS_KEY_JDBC_URL);

    DbPropertiesHolder.Builder builder;
    if (jdbcUrl != null) {
      builder = DbPropertiesHolder.buildForJdbcUrl(jdbcUrl);
    } else if (allNotNull(hostname, sid, port)) {
      builder = DbPropertiesHolder.buildForHostname(hostname, sid, port);
    } else {
      throw new IllegalArgumentException("Properties must contain hostname + port + sid or jdbcUrl");
    }

    String username = readOrThrow(DB_PROPS_KEY_USERNAME, properties);
    String password = readOrThrow(DB_PROPS_KEY_PASSWORD, properties);
    boolean useEncryption = readOrThrow(DB_PROPS_KEY_ENC_MODE, properties).equalsIgnoreCase("true");

    return builder.setUsername(username)
                  .setPoolSize(properties.getProperty(DB_PROPS_KEY_CONNECTION_POOLS_SIZE))
                  .setConnectionTimeout(properties.getProperty(DB_PROPS_KEY_CONNECTION_TIMEOUT))
                  .setPassword(useEncryption ? decryptPassword(password, properties) : password)
                  .build();
  }

  private static String decryptPassword(String password, Properties properties) {
    // Logic of decrypting password
    return password;
  }

  private static String readOrThrow(String key, Properties properties) {
    String value = properties.getProperty(key);
    if (value == null) {
      throw new RuntimeException("Necessary option [" + key + "] was not found in properties");
    }
    return value;
  }

  public static DbPropertiesHolder read(String filename) {
    try (InputStream is = Files.newInputStream(Paths.get(filename))) {
      Properties properties = new Properties();
      properties.load(is);
      return read(properties);
    } catch (IOException e) {
      LOG.error("IO Exception while reading file", e);
      return null;
    }
  }

}
