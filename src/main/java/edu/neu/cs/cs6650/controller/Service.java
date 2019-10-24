package edu.neu.cs.cs6650.controller;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public abstract class Service {

  private static final String SQL_CONN_USERNAME = "root";
  private static final String SQL_CONN_PW = "root";

  private static final String JDBC_URL = "jdbc:mysql://localhost:3306/skierapi?useSSL=false&serverTimezone=UTC";

  protected HikariDataSource hikariDS;

  public Service() {
    this.hikariDS = initHikari();
  }

  private HikariDataSource initHikari() {
    HikariConfig config = new HikariConfig();
      config.setJdbcUrl(JDBC_URL);
      config.setUsername(SQL_CONN_USERNAME);
      config.setPassword(SQL_CONN_PW);
      config.setDriverClassName("com.mysql.cj.jdbc.Driver");
      config.addDataSourceProperty("cachePrepStmts", "true");
      config.addDataSourceProperty("prepStmtCacheSize", "250");
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    return new HikariDataSource(config);
  }
}
