package edu.neu.cs.cs6650.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatList {
  private List<Stat> statList;

  public StatList() {}

  public StatList(List<Stat> statList) {
    this.statList = statList;
  }

  public List<Stat> getStatList() {
    return statList;
  }

  public void setStatList(List<Stat> statList) {
    this.statList = statList;
  }
}
