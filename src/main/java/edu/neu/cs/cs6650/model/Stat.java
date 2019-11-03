package edu.neu.cs.cs6650.model;

public class Stat {
  private String url;
  private String operation;
  private double responseTime;

  public Stat(String url, String operation, double responseTime) {
    this.url = url;
    this.operation = operation;
    this.responseTime = responseTime;
  }

  public String getUrl() {
    return url;
  }

  public String getOperation() {
    return operation;
  }

  public double getResponseTime() {
    return responseTime;
  }
}
