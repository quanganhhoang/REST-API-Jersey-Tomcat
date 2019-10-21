package edu.neu.cs.cs6650.sql;

public class SqlHeader {
  private String name;
  private String type;

  public SqlHeader(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return name + " (" + type + ")";
  }
}
