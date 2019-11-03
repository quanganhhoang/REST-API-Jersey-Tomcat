package edu.neu.cs.cs6650.monitoring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

public class MyRequestEventListener implements RequestEventListener {
  private static final Logger logger = LogManager.getLogger(MyRequestEventListener.class.getName());

  private static final String COUNT = "count";
  private static final String MAX_LATENCY = "maxLatency";
  private static final String MIN_LATENCY = "meanLatency";

  private long startTime;

  public MyRequestEventListener() {
    startTime = System.currentTimeMillis();
  }

  @Override
  public void onEvent(RequestEvent event) {
    switch (event.getType()) {
      case RESOURCE_METHOD_START:
        startTime = System.currentTimeMillis();
        logger.info("WHAT THIS? " + event.getUriInfo().getMatchedResourceLocators().get(0));

        logger.info(event.getUriInfo().getPath()
            + " - "
            + event.getUriInfo().getMatchedResourceMethod().getHttpMethod()
            + " started at " + startTime);
        break;
      case FINISHED:
        logger.info(event.getUriInfo().getPath()
            + " - "
            + event.getUriInfo().getMatchedResourceMethod().getHttpMethod()
            + " took " + (System.currentTimeMillis() - startTime)
            + "ms");
        break;

      default:
        break;
    }
  }
}