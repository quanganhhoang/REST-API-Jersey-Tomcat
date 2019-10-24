package edu.neu.cs.cs6650.Util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.neu.cs.cs6650.controller.SkierService;
import edu.neu.cs.cs6650.sql.SqlConnection;
import edu.neu.cs.cs6650.sql.SqlResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilDB {
  private static final Logger logger = LogManager.getLogger(UtilDB.class.getName());

  private static final String SQL_CONN_SERVER = "localhost:3306";
  private static final String SQL_CONN_DB = "skierapi";
  private static final String SQL_CONN_USERNAME = "root";
  private static final String SQL_CONN_PW = "root";

  private static final String DB_CONFIG = "src/main/resources/db.properties";
//  private static final HikariConfig config = new HikariConfig(DB_CONFIG);
  private static final String JDBC_URL = "jdbc:mysql://localhost:3306/skierapi?useSSL=false&serverTimezone=UTC";

  private static final HikariDataSource hikariDS = new HikariDataSource();
    static {
      hikariDS.setJdbcUrl(JDBC_URL);
      hikariDS.setUsername(SQL_CONN_USERNAME);
      hikariDS.setPassword(SQL_CONN_PW);
    }

  public static boolean createDBEntry(String sqlStmt) throws SQLException {
    int rowAffected;
    try (Connection conn = hikariDS.getConnection()) {
      PreparedStatement pst = conn.prepareStatement(sqlStmt);
      rowAffected = pst.executeUpdate();

      return rowAffected == 1;
    }
//    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
//      rowAffected = sqlConn.update(sqlStmt);
//    }
//
//    return rowAffected == 1;
  }
}
