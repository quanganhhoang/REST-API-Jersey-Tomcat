package edu.neu.cs.cs6650.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stat {
  private String url;
  private String operation;
  private long count;
  private long maxLatency;
  private long meanLatency;

  public Stat() {}

  public Stat(String url, String operation, long count, long maxLatency, long meanLatency) {
    this.url = url;
    this.operation = operation;
    this.count = count;
    this.maxLatency = maxLatency;
    this.meanLatency = meanLatency;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public long getMaxLatency() {
    return maxLatency;
  }

  public void setMaxLatency(long maxLatency) {
    this.maxLatency = maxLatency;
  }

  public long getMeanLatency() {
    return meanLatency;
  }

  public void setMeanLatency(long meanLatency) {
    this.meanLatency = meanLatency;
  }
}
