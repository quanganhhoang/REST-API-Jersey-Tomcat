package edu.neu.cs.cs6650.demo;

import edu.neu.cs.cs6650.controller.ResortService;
import edu.neu.cs.cs6650.controller.SkierService;
import edu.neu.cs.cs6650.model.Resort;
import edu.neu.cs.cs6650.model.ResortList;
import edu.neu.cs.cs6650.sql.SqlConnection;
import edu.neu.cs.cs6650.sql.SqlResultSet;
import edu.neu.cs.cs6650.sql.SqlRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqlDemo {
  private static Logger logger = LogManager.getLogger(SqlDemo.class.getName());

  private static final String SQL_CONN_SERVER = "localhost:3306";
  private static final String SQL_CONN_DB = "skierapi";
  private static final String SQL_CONN_USERNAME = "root";
  private static final String SQL_CONN_PW = "root";

  public static void main(String[] args) {

//    testGetVertical();
//    testPostVertical();
//    testGetTotalVerticalByResort();
//    testGetTotalVerticalByResortAndSeason();
    testAddNewSeason();
  }

  public static void testConnection() {
    String sqlStmt = "SELECT * FROM Resorts;";
    logger.info("RUNNING " + sqlStmt);

    SqlResultSet result = null;
    try (SqlConnection sqlConn = new SqlConnection(SQL_CONN_SERVER, SQL_CONN_DB, SQL_CONN_USERNAME, SQL_CONN_PW)) {
      result = sqlConn.query(sqlStmt);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    for (SqlRow row : result) {
      System.out.println(row.getField("resort_name"));
    }
  }

  public static void testGetVertical() {
    SkierService skierService = new SkierService();
    try {
      System.out.println(skierService.getVertical(1, "2019", 1, 1));
    } catch (SQLException e) {
      System.out.println("ERROR: ");
    }
  }

  public static void testPostVertical() {
    SkierService skierService = new SkierService();
    try {
      System.out.println(skierService.postVertical(1, "2019", 1, 1, 20, 5, 50));
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR: ");
    }
  }

  public static void testGetTotalVerticalByResort() {
    SkierService skierService = new SkierService();
    try {
      System.out.println(skierService.getTotalVerticalByResort(1, "Whistler"));
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR: ");
    }
  }

  public static void testGetTotalVerticalByResortAndSeason() {
    SkierService skierService = new SkierService();
    try {
      System.out.println(skierService.getTotalVerticalByResort(1, "Whistler", "2019"));
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR: ");
    }
  }

  public static void testAddNewSeason() {
    ResortService resortService = new ResortService();
    try {
      System.out.println(resortService.addNewSeason(1, "2016"));
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR: ");
    }
  }
}
