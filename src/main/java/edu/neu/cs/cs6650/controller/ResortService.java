package edu.neu.cs.cs6650.controller;

import edu.neu.cs.cs6650.Util.UtilDB;
import edu.neu.cs.cs6650.model.Resort;
import edu.neu.cs.cs6650.model.ResortList;
import edu.neu.cs.cs6650.model.SeasonList;
import edu.neu.cs.cs6650.sql.SqlConnection;
import edu.neu.cs.cs6650.sql.SqlResultSet;
import edu.neu.cs.cs6650.sql.SqlRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResortService {
  private static final Logger logger = LogManager.getLogger(ResortService.class.getName());

  private static final String SQL_CONN_SERVER = "localhost:3306";
  private static final String SQL_CONN_DB = "skierapi";
  private static final String SQL_CONN_USERNAME = "root";
  private static final String SQL_CONN_PW = "root";

  public ResortList getAllResorts() throws SQLException {
    String sqlStmt = "SELECT * FROM Resorts";

    logger.info("Executing: " + sqlStmt);
    SqlResultSet result;
    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
      result = sqlConn.query(sqlStmt);
    }

    List<Resort> resortList = new ArrayList<>();
    for (SqlRow row : result) {
      resortList.add(new Resort(row.getField("id"), row.getField("resort_name")));
    }

    return new ResortList(resortList);
  }

  public SeasonList getSeasonsByResort(int resortId) throws SQLException {
    String sqlStmt = "SELECT * FROM Seasons WHERE resort_id = " + resortId;

    logger.info("Executing: " + sqlStmt);
    SqlResultSet result;
    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
      result = sqlConn.query(sqlStmt);
    }

    List<String> seasons = new ArrayList<>();
    for (SqlRow row : result) {
      seasons.add(row.getField("season"));
    }

    return new SeasonList(seasons);
  }

  public boolean addNewSeason(int resortId, String season) throws SQLException {
    String sqlStmt = "INSERT INTO Seasons (season, resort_id) VALUES (";
      sqlStmt += season + ", ";
      sqlStmt += resortId + ")";

      System.out.println("Executing: " + sqlStmt);
    return UtilDB.createDBEntry(sqlStmt);
  }
}
