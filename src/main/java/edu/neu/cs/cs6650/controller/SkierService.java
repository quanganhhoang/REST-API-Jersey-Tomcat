package edu.neu.cs.cs6650.controller;

import edu.neu.cs.cs6650.Util.UtilDB;
import edu.neu.cs.cs6650.model.SeasonVertical;
import edu.neu.cs.cs6650.model.SeasonVerticalList;
import edu.neu.cs.cs6650.sql.SqlConnection;
import edu.neu.cs.cs6650.sql.SqlResultSet;
import edu.neu.cs.cs6650.sql.SqlRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SkierService {
  private static final Logger logger = LogManager.getLogger(SkierService.class.getName());

  private static final String SQL_CONN_SERVER = "localhost:3306";
  private static final String SQL_CONN_DB = "skierapi";
  private static final String SQL_CONN_USERNAME = "root";
  private static final String SQL_CONN_PW = "root";

  public int getVertical(int resortId, String season, int dayId, int skierId) throws SQLException {
    String sqlStmt = "SELECT SUM(vertical) FROM LiftRides WHERE ";
      sqlStmt += "skier_id = " + skierId + " AND ";
      sqlStmt += "season = '" + season + "' AND ";
      sqlStmt += "day_id = " + dayId + " AND ";
      sqlStmt += "resort_id = " + resortId;

    SqlResultSet result;
    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
      result = sqlConn.query(sqlStmt);
    }

    return Integer.valueOf(result.getRow(0).getField(0));
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

    System.out.println("Executing: " + sqlStmt);
    return UtilDB.createDBEntry(sqlStmt);
  }

  public int getTotalVerticalByResort(int skierId, String resortName, String season) throws SQLException {
    String sqlStmt = "SELECT SUM(vertical) AS total_vertical FROM LiftRides WHERE ";
      sqlStmt += "skier_id = " + skierId + " AND ";
      sqlStmt += "resort_id = (SELECT id FROM Resorts WHERE resort_name = '" + resortName + "') AND ";
      sqlStmt += "season = '" + season + "'";

    System.out.println(("Executing: " + sqlStmt));
    SqlResultSet result;
    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
      result = sqlConn.query(sqlStmt);
    }

    return Integer.valueOf(result.getRow(0).getField(0));
  }

  public SeasonVerticalList getTotalVerticalByResort(int skierId, String resortName) throws SQLException {
    String sqlStmt = "SELECT season, SUM(vertical) AS total_vertical FROM LiftRides WHERE ";
      sqlStmt += "skier_id = " + skierId + " AND ";
      sqlStmt += "resort_id = (SELECT id FROM Resorts WHERE resort_name = '" + resortName + "')";
      sqlStmt += " GROUP BY season;";

    System.out.println(("Executing: " + sqlStmt));
    SqlResultSet result;
    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
      result = sqlConn.query(sqlStmt);
    }

    List<SeasonVertical> seasonStats = new ArrayList<>();
    for (SqlRow row : result) {
      seasonStats.add(new SeasonVertical(row.getField("season"), Integer.valueOf(row.getField("total_vertical"))));
    }

    return new SeasonVerticalList(seasonStats);
  }
}
