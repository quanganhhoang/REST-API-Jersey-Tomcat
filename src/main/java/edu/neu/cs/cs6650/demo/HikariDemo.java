package edu.neu.cs.cs6650.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HikariDemo {
  private static Logger logger = LogManager.getLogger(HikariDemo.class.getName());

  public static void main(String[] args) {
    String configFile = "src/main/resources/db.properties";

    HikariConfig config = new HikariConfig((configFile));
    HikariDataSource ds = new HikariDataSource(config);

    try (Connection conn = ds.getConnection()) {
      PreparedStatement pst = null;
      ResultSet rs = null;

      pst = conn.prepareStatement("SELECT * FROM RESORTS");

      rs = pst.executeQuery();
      logger.info("hi");
      while (rs.next()) {
        System.out.format("%d %s %n", rs.getInt(1), rs.getString(2));
      }
    } catch (SQLException e) {

    }
  }
}
