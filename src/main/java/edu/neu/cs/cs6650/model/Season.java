package edu.neu.cs.cs6650.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Season {
  private String season;
  private int vertical;
  private int resortId;

  public Season() {}

  public Season(String season, int vertical, int resortId) {
    this.season = season;
    this.vertical = vertical;
    this.resortId = resortId;
  }

  public String getSeason() {
    return season;
  }

  public int getVertical() {
    return vertical;
  }

  public int getResortId() {
    return resortId;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public void setVertical(int vertical) {
    this.vertical = vertical;
  }

  public void setResortId(int resortId) {
    this.resortId = resortId;
  }

  @Override
  public String toString() {
    return "Season{" +
        "season='" + season + '\'' +
        ", vertical=" + vertical +
        ", resortId=" + resortId +
        '}';
  }
}
