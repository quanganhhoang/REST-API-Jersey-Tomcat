package edu.neu.cs.cs6650.sql;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariDS {
  private static final boolean IS_LOCAL = true;
  private static final String SQL_CONN_USERNAME = IS_LOCAL ? "root" : "";
  private static final String SQL_CONN_PW = IS_LOCAL ? "root" : "";

  private static final String JDBC_URL = IS_LOCAL ? "jdbc:mysql://localhost:3306/skierapi?useSSL=false&serverTimezone=UTC"
                                                  : "";

  private static HikariDataSource dataSource = new HikariDataSource();
    static {
      dataSource.setJdbcUrl(JDBC_URL);
      dataSource.setUsername(SQL_CONN_USERNAME);
      dataSource.setPassword(SQL_CONN_PW);
      dataSource.setMaximumPoolSize(20);
      dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
      dataSource.addDataSourceProperty("cachePrepStmts", "true");
      dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
      dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
      dataSource.addDataSourceProperty("useServerPrepStmts", "true");
      dataSource.addDataSourceProperty("useLocalSessionState", "true");
      dataSource.addDataSourceProperty("rewriteBatchedStatements", "true");
      dataSource.addDataSourceProperty("cacheResultSetMetadata", "true");
      dataSource.addDataSourceProperty("cacheServerConfiguration", "true");
      dataSource.addDataSourceProperty("elideSetAutoCommits", "true");
      dataSource.addDataSourceProperty("maintainTimeStats", "false");
    }

  public static Connection getConnection() throws SQLException {
      return dataSource.getConnection();
  }
}