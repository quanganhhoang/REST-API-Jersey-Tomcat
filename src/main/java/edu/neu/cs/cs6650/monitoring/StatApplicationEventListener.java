package edu.neu.cs.cs6650.monitoring;

import edu.neu.cs.cs6650.redis.RedisPool;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import redis.clients.jedis.Jedis;

public class StatApplicationEventListener implements ApplicationEventListener {
  private static final Logger logger = LogManager.getLogger(StatApplicationEventListener.class.getName());
  private static final String COUNT = "count";
  private static final String MAX_LATENCY = "maxLatency";
  private static final String MEAN_LATENCY = "meanLatency";

  @Override
  public void onEvent(ApplicationEvent event) {
    switch (event.getType()) {
      case INITIALIZATION_FINISHED:
        logger.info("Jersey application started");
        break;
      case DESTROY_FINISHED:
        logger.info("Application "
            + event.getResourceConfig().getApplicationName() + " destroyed.");
        break;
      default:
        break;
    }
  }

  @Override
  public RequestEventListener onRequest(RequestEvent requestEvent) {
    return new RequestEventListener() {
      private volatile long startTime;

      @Override
      public void onEvent(RequestEvent requestEvent) {
        switch (requestEvent.getType()) {
          case RESOURCE_METHOD_START:
            startTime = System.currentTimeMillis();
//            logger.info(requestEvent.getUriInfo().getPath()
//                + " - "
//                + requestEvent.getUriInfo().getMatchedResourceMethod().getHttpMethod()
//                + " started at " + startTime);
            break;
          case FINISHED:

            String url = requestEvent.getUriInfo().getMatchedTemplates().get(0).getTemplate();
            long responseTime = System.currentTimeMillis() - startTime;

            try (Jedis conn = RedisPool.getResource()) {
              // update current count, mean and max
              String key = url + "-" +
                  requestEvent.getUriInfo()
                              .getMatchedResourceMethod()
                              .getHttpMethod();
              if (conn.exists(key)) {
                Map<String, String> cacheValue = conn.hgetAll(key);
                int curCount = Integer.valueOf(cacheValue.get(COUNT));
                long curMax = Long.valueOf(cacheValue.get(MAX_LATENCY));
                long curMean = Long.valueOf(cacheValue.get(MEAN_LATENCY));

                long newMean = (curMean * curCount + responseTime) / (curCount + 1);

                conn.hincrBy(key, COUNT, 1);
                conn.hset(key, MEAN_LATENCY, String.valueOf(newMean));
                if (responseTime > curMax) {
                  conn.hset(key, MAX_LATENCY, String.valueOf(responseTime));
                }
              } else {
                Map<String, String> values = new HashMap<>();
                values.put(COUNT, "1");
                values.put(MAX_LATENCY, String.valueOf(responseTime));
                values.put(MEAN_LATENCY, String.valueOf(responseTime));
                conn.hset(key, values);
              }
            }

//            logger.info(requestEvent.getUriInfo().getPath()
//                + " - "
//                + requestEvent.getUriInfo().getMatchedResourceMethod().getHttpMethod()
//                + " took " + (System.currentTimeMillis() - startTime)
//                + "ms");
          break;

          default:
            break;
        }
      }
    };
  }
}
