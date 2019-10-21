package edu.neu.cs.cs6650.model;

public class Stat {
  private String api;
  private String operation;
  private double meanLatency;
  private double maxLatency;

  public Stat(String api, String operation, double meanLatency, double maxLatency) {
    this.api = api;
    this.operation = operation;
    this.meanLatency = meanLatency;
    this.maxLatency = maxLatency;
  }

  public String getApi() {
    return api;
  }

  public String getOperation() {
    return operation;
  }

  public double getMeanLatency() {
    return meanLatency;
  }

  public double getMaxLatency() {
    return maxLatency;
  }
}
