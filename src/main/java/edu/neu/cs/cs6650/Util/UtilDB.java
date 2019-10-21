package edu.neu.cs.cs6650.Util;

import edu.neu.cs.cs6650.controller.SkierService;
import edu.neu.cs.cs6650.sql.SqlConnection;
import edu.neu.cs.cs6650.sql.SqlResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilDB {
  private static final Logger logger = LogManager.getLogger(SkierService.class.getName());

  private static final String SQL_CONN_SERVER = "localhost:3306";
  private static final String SQL_CONN_DB = "skierapi";
  private static final String SQL_CONN_USERNAME = "root";
  private static final String SQL_CONN_PW = "root";

  public static boolean createDBEntry(String sqlStmt) throws SQLException {
    int rowAffected;
    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
      rowAffected = sqlConn.update(sqlStmt);
    }

    return rowAffected == 1;
  }
}
