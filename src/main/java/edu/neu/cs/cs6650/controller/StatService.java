package edu.neu.cs.cs6650.controller;

import edu.neu.cs.cs6650.model.SeasonVertical;
import edu.neu.cs.cs6650.model.SeasonVerticalList;
import edu.neu.cs.cs6650.model.Stat;
import edu.neu.cs.cs6650.model.StatList;
import edu.neu.cs.cs6650.sql.HikariDS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatService {
  private static final Logger logger = LogManager.getLogger(StatService.class.getName());

  public StatList getApiStats() throws SQLException {
    String sqlStmt = "SELECT * FROM Stats";

    logger.info(("Executing: " + sqlStmt));

    try (Connection conn = HikariDS.getConnection();
        PreparedStatement pst = conn.prepareStatement(sqlStmt);
        ResultSet rs = pst.executeQuery()) {

      List<Stat> apiStats = new ArrayList<>();
      while (rs.next()) {
        apiStats.add(new Stat(rs.getString("url"),
            rs.getString("method"),
            rs.getLong("count"),
            rs.getLong("max_latency"),
            rs.getLong("mean_latency")));
      }

      return new StatList(apiStats);
    }
  }
}
