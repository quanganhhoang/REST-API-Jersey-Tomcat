package edu.neu.cs.cs6650.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SeasonList {
  private List<String> seasons;

  public SeasonList() {}

  public SeasonList(List<String> seasons) {
    this.seasons = seasons;
  }

  public List<String> getSeasons() {
    return seasons;
  }

  public void setSeasons(List<String> seasons) {
    this.seasons = seasons;
  }

  @Override
  public String toString() {
    return "SeasonList{" +
        "seasons=" + seasons +
        '}';
  }
}
