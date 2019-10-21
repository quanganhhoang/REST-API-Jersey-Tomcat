package edu.neu.cs.cs6650.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SeasonVertical {
  private String season;
  private int totalVert;

  public SeasonVertical() {}

  public SeasonVertical(String season, int totalVert) {
    this.season = season;
    this.totalVert = totalVert;
  }

  public String getSeason() {
    return season;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public int getTotalVert() {
    return totalVert;
  }

  public void setTotalVert(int totalVert) {
    this.totalVert = totalVert;
  }

  @Override
  public String toString() {
    return "SeasonVertical{" +
        "season='" + season + '\'' +
        ", totalVert=" + totalVert +
        '}';
  }
}
