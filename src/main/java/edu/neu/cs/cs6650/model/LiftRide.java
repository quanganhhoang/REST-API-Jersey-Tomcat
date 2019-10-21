package edu.neu.cs.cs6650.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LiftRide {
  private int id;
  private int liftId;
  private int skierId;
  private int resortId;
  private int dayId;
  private int time;
  private String season;
  private int vertical;

  public LiftRide() {}

  public LiftRide(int id, int liftId, int skierId, int resortId, int dayId, int time,
      String season, int vertical) {
    this.id = id;
    this.liftId = liftId;
    this.skierId = skierId;
    this.resortId = resortId;
    this.dayId = dayId;
    this.time = time;
    this.season = season;
    this.vertical = vertical;
  }

  public int getId() {
    return id;
  }

  public int getLiftId() {
    return liftId;
  }

  public int getSkierId() {
    return skierId;
  }

  public int getResortId() {
    return resortId;
  }

  public int getDayId() {
    return dayId;
  }

  public int getTime() {
    return time;
  }

  public String getSeason() {
    return season;
  }

  public int getVertical() {
    return vertical;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLiftId(int liftId) {
    this.liftId = liftId;
  }

  public void setSkierId(int skierId) {
    this.skierId = skierId;
  }

  public void setResortId(int resortId) {
    this.resortId = resortId;
  }

  public void setDayId(int dayId) {
    this.dayId = dayId;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public void setVertical(int vertical) {
    this.vertical = vertical;
  }

  @Override
  public String toString() {
    return "LiftRide{" +
        "id=" + id +
        ", liftId=" + liftId +
        ", skierId=" + skierId +
        ", resortId=" + resortId +
        ", dayId=" + dayId +
        ", time=" + time +
        ", season='" + season + '\'' +
        ", vertical=" + vertical +
        '}';
  }
}
