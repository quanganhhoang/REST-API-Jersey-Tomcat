package edu.neu.cs.cs6650.servlet;

import edu.neu.cs.cs6650.redis.RedisPool;
import edu.neu.cs.cs6650.sql.HikariDS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class StatUpdateJob implements Runnable {
  private static final Logger logger = LogManager.getLogger(StatUpdateJob.class.getName());

  private static final String COUNT = "count";
  private static final String MAX_LATENCY = "maxLatency";
  private static final String MEAN_LATENCY = "meanLatency";

  @Override
  public void run() {
    // persist data from the cache into database
    System.out.println("Update job running...");
    try (Jedis cacheConn = RedisPool.getResource()) {
      ScanParams scanParams = new ScanParams().count(10).match("*");
      String cur = ScanParams.SCAN_POINTER_START;
      do {
        ScanResult<String> scanResult = cacheConn.scan(cur, scanParams);

        // work with result
        for (String key : scanResult.getResult()) {
          System.out.println("key: " + key);
          String[] parts = key.split("-");
          String url = parts[0];
          String method = parts[1];
          Map<String, String> stats = cacheConn.hgetAll(key);

          String sqlStmt = "INSERT INTO Stats (url, method, count, mean_latency, max_latency) "
              + "VALUES ('" + url + "', '" + method + "', " + stats.get(COUNT) + ", " + stats.get(MAX_LATENCY) + ", " + stats.get(MEAN_LATENCY) + ") "
              + "ON DUPLICATE KEY UPDATE "
              + "count = " + stats.get(COUNT)
              + ", max_latency = " + stats.get(MAX_LATENCY)
              + ", mean_latency = " + stats.get(MEAN_LATENCY);

          logger.info("Executing: " + sqlStmt);
          try (Connection sqlConn = HikariDS.getConnection();
              PreparedStatement pst = sqlConn.prepareStatement(sqlStmt)) {
            int rowAffected = pst.executeUpdate();

            if (rowAffected == 1) logger.info("Inserted...");
            else if (rowAffected == 2) logger.info("Row updated");
            else logger.info("Update failed...");
          } catch (SQLException e) {
            logger.info("ERROR: ", e);
          }
        }
//        scanResult.getResult().stream().forEach(System.out::println);
        cur = scanResult.getCursor();
      } while (!cur.equals(ScanParams.SCAN_POINTER_START));
    }
  }
}
