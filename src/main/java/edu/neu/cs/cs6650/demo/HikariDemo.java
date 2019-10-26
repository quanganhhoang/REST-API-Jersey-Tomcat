package edu.neu.cs.cs6650.demo;

import edu.neu.cs.cs6650.sql.HikariDS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HikariDemo {
  private static Logger logger = LogManager.getLogger(HikariDemo.class.getName());

  public static void main(String[] args) {

    try (Connection conn = HikariDS.getConnection();
      PreparedStatement pst = conn.prepareStatement("SELECT * FROM Resorts");
      ResultSet rs = pst.executeQuery()) {

      while (rs.next()) {
        System.out.format("%d %s %n", rs.getInt(1), rs.getString(2));
      }
    } catch (SQLException e) {
        logger.info("ERROR: ", e);
    }
  }
}
