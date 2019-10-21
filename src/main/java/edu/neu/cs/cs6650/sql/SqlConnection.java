package edu.neu.cs.cs6650.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlConnection implements AutoCloseable {
  private static final String DEFAULT_SQL_CONNECTOR = "com.mysql.cj.jdbc.Driver";
//    private static final String DEFAULT_SQL_CONNECTOR = "com.mysql.jdbc.Driver";
//  private static final String POSTGRES_SQL_DRIVER = "org.postgresql.Driver";
//  private static final String MARIA_DB_DRIVER = "org.mariadb.jdbc.Driver";

  private boolean closed;
  private String jdbcConnectString;
  private Connection jdbcConnection;

  // DEFAULT MySql Driver
  public SqlConnection(String server, String database, String userID, String password) {
    this.closed = true;

    this.jdbcConnectString = "jdbc:mysql://" + server + "/" + database + "?useSSL=false&serverTimezone=UTC";

    // Load the SQL Server JDBC driver and create the JDBC connection
    try {
      Class.forName(DEFAULT_SQL_CONNECTOR);
      this.jdbcConnection = DriverManager.getConnection(jdbcConnectString, userID, password);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("Unable to find JDBC driver class in classpath: " + DEFAULT_SQL_CONNECTOR);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IllegalStateException("Unable to connect to SQL database: " + jdbcConnectString);
    }

    this.closed = false;
  }

  public SqlConnection(String jdbcConnectString, String jdbcClassname) {
    this.closed = true;
    this.jdbcConnectString = jdbcConnectString;

    try {
      Class.forName(jdbcClassname);
      this.jdbcConnection = DriverManager.getConnection(jdbcConnectString);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("Unable to find JDBC driver class in classpath: " + jdbcClassname);
    } catch (SQLException e) {
      throw new IllegalStateException("Unable to connect to SQL database: " + jdbcConnectString);
    }

    this.closed = false;
  }

  public SqlResultSet query(String query, String ...args) throws SQLException {
//    if (closed) {
//      // TODO: throw what exception?
//    }
    PreparedStatement stmt = this.jdbcConnection.prepareStatement(query);

    for (int i = 0; i < args.length; i++) {
      String arg = args[i];
      stmt.setString(i+1, arg);
    }
//    System.out.println(stmt.toString());

    // if escape scanning is on (default),
    // the driver will do escape substitution before sending SQL statement to DB
    stmt.setEscapeProcessing(true);
    // JDBC driver must apply this limit to execute, executeQuery and executeUpdate
    stmt.setQueryTimeout(10);

//    ResultSet rs = stmt.executeQuery();
    ResultSet rs = stmt.executeQuery();
    SqlResultSet result = new SqlResultSet();
    while (rs.next()) {
      SqlRow row = new SqlRow(result);
      int columnCount = rs.getMetaData().getColumnCount();
      // fill header information
      if (rs.isFirst()) {
        for (int i = 1; i <= columnCount; i++) {
          String fieldName = rs.getMetaData().getColumnName(i);
          String fieldType = rs.getMetaData().getColumnTypeName(i).toUpperCase();
          result.addHeader(fieldName, fieldType);
        }
      }

      // fill each row
      for (int i = 1; i <= columnCount; i++) {
        String value = rs.getString(i);
        row.add(value);
      }
      result.add(row);
    }

    return result;
  }

  public int update(String query, String ...args) throws SQLException {
//    if (closed) {
//      // TODO: what to do here?
//    }

    PreparedStatement stmt = jdbcConnection.prepareStatement(query);
    for (int i = 0; i < args.length; i++) {
      stmt.setString(i+1, args[i]);
    }

    stmt.setEscapeProcessing(true);
    stmt.setQueryTimeout(10);

    return stmt.executeUpdate();
  }

  @Override
  public void close() {
    try {
      jdbcConnection.close();
    } catch (SQLException e) {
      // TODO:
    }
    this.closed = true;
  }
}
