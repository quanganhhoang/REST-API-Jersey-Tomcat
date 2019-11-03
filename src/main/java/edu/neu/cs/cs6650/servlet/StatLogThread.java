package edu.neu.cs.cs6650.servlet;

import edu.neu.cs.cs6650.redis.RedisPool;
import java.util.Map;
import redis.clients.jedis.Jedis;

public class StatLogThread implements Runnable {
  private static final String COUNT = "count";
  private static final String MAX_LATENCY = "maxLatency";
  private static final String MIN_LATENCY = "meanLatency";

  private String url;
  private String requestType;
  private double responseTime;

  public StatLogThread(String url, String requestType, double responseTime) {
    this.url = url;
    this.requestType = requestType;
    this.responseTime = responseTime;
  }

  @Override
  public void run() {
    // update the cache
    try (Jedis conn = RedisPool.getResource()) {
      // update current count, mean and max
      String key = url + "-" + requestType;
      Map<String, String> cacheValue = conn.hgetAll(key);
      int curCount = Integer.valueOf(cacheValue.get(COUNT));
      double curMax = Integer.valueOf(cacheValue.get(MAX_LATENCY));
      double curMean = Double.valueOf(cacheValue.get(MIN_LATENCY));

      conn.hincrBy(key, COUNT, 1);
      if (this.responseTime > curMax) {

      }




    }
  }
}


//  Map<String, String> avalue = new
//      HashMap<String, String>();
//avalue.put(a, a1);
//    avalue.put(b, b1);
//    avalue.put(c, c1);
//    avalue.put(d,d1)
//    jedis.hmset(key, avalue);