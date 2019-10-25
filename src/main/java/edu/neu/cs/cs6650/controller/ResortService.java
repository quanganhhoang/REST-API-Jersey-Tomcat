package edu.neu.cs.cs6650.controller;

import edu.neu.cs.cs6650.Util.UtilDB;
import edu.neu.cs.cs6650.model.Resort;
import edu.neu.cs.cs6650.model.ResortList;
import edu.neu.cs.cs6650.model.SeasonList;

import edu.neu.cs.cs6650.sql.HikariDS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResortService {
  private static final Logger logger = LogManager.getLogger(ResortService.class.getName());

  public ResortService() {}

  public ResortList getAllResorts() throws SQLException {
    String sqlStmt = "SELECT * FROM Resorts;";

    logger.info("Executing: " + sqlStmt);

    try (Connection conn = HikariDS.getConnection();
        PreparedStatement pst = conn.prepareStatement(sqlStmt);
        ResultSet rs = pst.executeQuery()) {

      List<Resort> resortList = new ArrayList<>();
      while (rs.next()) {
        resortList.add(new Resort(rs.getString("id"), rs.getString("resort_name")));
      }

      return new ResortList(resortList);
    }
//    SqlResultSet result;
//    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
//      result = sqlConn.query(sqlStmt);
//    }
  }

  public SeasonList getSeasonsByResort(int resortId) throws SQLException {
    String sqlStmt = "SELECT * FROM Seasons WHERE resort_id = " + resortId;

    logger.info("Executing: " + sqlStmt);
    try (Connection conn = HikariDS.getConnection();
        PreparedStatement pst = conn.prepareStatement(sqlStmt);
        ResultSet rs = pst.executeQuery()) {

      List<String> seasons = new ArrayList<>();
      while (rs.next()) {
        seasons.add(rs.getString("season"));
      }

      return new SeasonList(seasons);
    }
//    SqlResultSet result;
//    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
//      result = sqlConn.query(sqlStmt);
//    }
  }

  public boolean addNewSeason(int resortId, String season) throws SQLException {
    String sqlStmt = "INSERT INTO Seasons (season, resort_id) VALUES (";
      sqlStmt += season + ", ";
      sqlStmt += resortId + ")";

    System.out.println("Executing: " + sqlStmt);
    return UtilDB.createDBEntry(sqlStmt);
  }
}
