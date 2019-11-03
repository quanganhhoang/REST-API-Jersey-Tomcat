package edu.neu.cs.cs6650.servlet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MainServletContextListener implements ServletContextListener {
  private ScheduledExecutorService scheduler;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(new StatUpdateJob(), 0, 5, TimeUnit.SECONDS);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    scheduler.shutdownNow();
  }
}
