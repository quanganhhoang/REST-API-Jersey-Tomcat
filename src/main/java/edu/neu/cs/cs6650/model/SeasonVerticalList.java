package edu.neu.cs.cs6650.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SeasonVerticalList {
  private List<SeasonVertical> resorts;

  public SeasonVerticalList() {}

  public SeasonVerticalList(List<SeasonVertical> resorts) {
    this.resorts = resorts;
  }

  public List<SeasonVertical> getResorts() {
    return resorts;
  }

  public void setResorts(List<SeasonVertical> resorts) {
    this.resorts = resorts;
  }

  @Override
  public String toString() {
    return "SeasonVerticalList{" +
        "resorts=" + resorts +
        '}';
  }
}
