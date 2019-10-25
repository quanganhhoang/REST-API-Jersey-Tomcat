package edu.neu.cs.cs6650.controller;

import edu.neu.cs.cs6650.Util.UtilDB;
import edu.neu.cs.cs6650.model.SeasonVertical;
import edu.neu.cs.cs6650.model.SeasonVerticalList;

import edu.neu.cs.cs6650.sql.HikariDS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SkierService {
  private static final Logger logger = LogManager.getLogger(SkierService.class.getName());

  public SkierService() {}

  public int getVertical(int resortId, String season, int dayId, int skierId) throws SQLException {
    String sqlStmt = "SELECT SUM(vertical) FROM LiftRides WHERE ";
      sqlStmt += "skier_id = " + skierId + " AND ";
      sqlStmt += "season = '" + season + "' AND ";
      sqlStmt += "day_id = " + dayId + " AND ";
      sqlStmt += "resort_id = " + resortId;

//    SqlResultSet result;
//    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
//      result = sqlConn.query(sqlStmt);
//    }
//
//    return Integer.valueOf(result.getRow(0).getField(0));
    logger.info("Executing: " + sqlStmt);
    try (Connection conn = HikariDS.getConnection();
         PreparedStatement pst = conn.prepareStatement(sqlStmt);
         ResultSet rs = pst.executeQuery()) {

      if (rs.next()) return rs.getInt(1);
      else throw new SQLException();
    }
  }

  public boolean postVertical(int resortId, String season, int dayId,
          int skierId, int time, int liftId, int vertical) throws SQLException {
    String sqlStmt = "INSERT INTO LiftRides (skier_id, resort_id, season, day_id, lift_id, ride_time, vertical) VALUES (";
      sqlStmt += skierId + ", ";
      sqlStmt += resortId + ", ";
      sqlStmt += season + ", ";
      sqlStmt += dayId + ", ";
      sqlStmt += liftId + ", ";
      sqlStmt += time + ", ";
      sqlStmt += vertical + ");";

    logger.info("Executing: " + sqlStmt);
    return UtilDB.createDBEntry(sqlStmt);
  }

  public int getTotalVerticalByResort(int skierId, String resortName, String season) throws SQLException {
    String sqlStmt = "SELECT SUM(vertical) AS total_vertical FROM LiftRides WHERE ";
      sqlStmt += "skier_id = " + skierId + " AND ";
      sqlStmt += "resort_id = (SELECT id FROM Resorts WHERE resort_name = '" + resortName + "') AND ";
      sqlStmt += "season = '" + season + "'";

    System.out.println(("Executing: " + sqlStmt));
    try (Connection conn = HikariDS.getConnection();
        PreparedStatement pst = conn.prepareStatement(sqlStmt);
        ResultSet rs = pst.executeQuery()) {

      if (rs.next()) return rs.getInt(1);
      else throw new SQLException();
    }
//    SqlResultSet result;
//    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
//      result = sqlConn.query(sqlStmt);
//    }
//
//    return Integer.valueOf(result.getRow(0).getField(0));
  }

  public SeasonVerticalList getTotalVerticalByResort(int skierId, String resortName) throws SQLException {
    String sqlStmt = "SELECT season, SUM(vertical) AS total_vertical FROM LiftRides WHERE ";
      sqlStmt += "skier_id = " + skierId + " AND ";
      sqlStmt += "resort_id = (SELECT id FROM Resorts WHERE resort_name = '" + resortName + "')";
      sqlStmt += " GROUP BY season;";

    System.out.println(("Executing: " + sqlStmt));

    try (Connection conn = HikariDS.getConnection();
        PreparedStatement pst = conn.prepareStatement(sqlStmt);
        ResultSet rs = pst.executeQuery()) {

      List<SeasonVertical> seasonStats = new ArrayList<>();
      while (rs.next()) {
        seasonStats.add(new SeasonVertical(rs.getString("season"), rs.getInt("total_vertical")));
      }

      return new SeasonVerticalList(seasonStats);
    }

//    SqlResultSet result;
//    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
//      result = sqlConn.query(sqlStmt);
//    }

  }
}
