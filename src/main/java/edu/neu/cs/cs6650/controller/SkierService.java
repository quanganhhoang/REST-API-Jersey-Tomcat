package edu.neu.cs.cs6650.controller;

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

  public int getVertical(int resortId, String season, int dayId, int skierId) throws SQLException {
    String sqlStmt = "SELECT SUM(vertical) FROM LiftRides WHERE ";
      sqlStmt += "skier_id = ? AND season = ? AND ";
      sqlStmt += "day_id = ? AND resort_id = ?;";

    logger.info("Executing: " + sqlStmt);
    try (Connection conn = HikariDS.getConnection();
         PreparedStatement pst = conn.prepareStatement(sqlStmt)) {
      pst.setInt(1, skierId);
      pst.setString(2, season);
      pst.setInt(3, dayId);
      pst.setInt(4, resortId);

      ResultSet rs = pst.executeQuery();
      if (rs.next()) return rs.getInt(1);
      else throw new SQLException();
    }
  }

  public boolean postVertical(int resortId, String season, int dayId,
          int skierId, int time, int liftId, int vertical) throws SQLException {
    String sqlStmt = "INSERT INTO LiftRides"
        + " (skier_id, resort_id, season, day_id, lift_id, ride_time, vertical)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?);";

    logger.info("Executing: " + sqlStmt);
    try (Connection conn = HikariDS.getConnection();
        PreparedStatement pst = conn.prepareStatement(sqlStmt)) {
      pst.setInt(1, skierId);
      pst.setInt(2, resortId);
      pst.setString(3, season);
      pst.setInt(4, dayId);
      pst.setInt(5, liftId);
      pst.setInt(6, time);
      pst.setInt(7, vertical);

      int rowAffected = pst.executeUpdate();

      return rowAffected == 1;
    }
  }

  public int getTotalVerticalByResort(int skierId, String resortName, String season) throws SQLException {
    String sqlStmt = "SELECT SUM(vertical) AS total_vertical FROM LiftRides WHERE ";
      sqlStmt += "skier_id = ? AND ";
      sqlStmt += "resort_id = (SELECT id FROM Resorts WHERE resort_name = ?) AND ";
      sqlStmt += "season = ?";

    logger.info("Executing: " + sqlStmt);
    try (Connection conn = HikariDS.getConnection();
        PreparedStatement pst = conn.prepareStatement(sqlStmt)) {
      pst.setInt(1, skierId);
      pst.setString(2, resortName);
      pst.setString(3, season);

      ResultSet rs = pst.executeQuery();
      if (rs.next()) return rs.getInt(1);
      else throw new SQLException();
    }
  }

  public SeasonVerticalList getTotalVerticalByResort(int skierId, String resortName) throws SQLException {
    String sqlStmt = "SELECT season, SUM(vertical) AS total_vertical FROM LiftRides WHERE ";
      sqlStmt += "skier_id = ? AND ";
      sqlStmt += "resort_id = (SELECT id FROM Resorts WHERE resort_name = ?)";
      sqlStmt += " GROUP BY season;";

    logger.info(("Executing: " + sqlStmt));

    try (Connection conn = HikariDS.getConnection();
        PreparedStatement pst = conn.prepareStatement(sqlStmt)) {
      pst.setInt(1, skierId);
      pst.setString(2, resortName);

      ResultSet rs = pst.executeQuery();
      List<SeasonVertical> seasonStats = new ArrayList<>();
      while (rs.next()) {
        seasonStats.add(new SeasonVertical(rs.getString("season"), rs.getInt("total_vertical")));
      }

      return new SeasonVerticalList(seasonStats);
    }
  }
}
