package edu.neu.cs.cs6650.Util;

import edu.neu.cs.cs6650.sql.HikariDS;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilDB {
  private static final Logger logger = LogManager.getLogger(UtilDB.class.getName());

  public static boolean createDBEntry(String sqlStmt) throws SQLException {

    logger.info("Executing: " + sqlStmt);

    try (Connection conn = HikariDS.getConnection();
      PreparedStatement pst = conn.prepareStatement(sqlStmt)) {
      int rowAffected = pst.executeUpdate();

      return rowAffected == 1;
    }
//    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
//      rowAffected = sqlConn.update(sqlStmt);
//    }
//
//    return rowAffected == 1;
  }
}
