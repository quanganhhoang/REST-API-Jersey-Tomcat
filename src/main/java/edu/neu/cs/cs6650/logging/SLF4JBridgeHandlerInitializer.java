package edu.neu.cs.cs6650.logging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class SLF4JBridgeHandlerInitializer {
  public SLF4JBridgeHandlerInitializer() throws IOException {
    String loggingConfigurationString = "handlers = " + SLF4JBridgeHandler.class.getName();
    InputStream inputStream = new ByteArrayInputStream(loggingConfigurationString.getBytes());
    LogManager.getLogManager().readConfiguration(inputStream);
  }
}