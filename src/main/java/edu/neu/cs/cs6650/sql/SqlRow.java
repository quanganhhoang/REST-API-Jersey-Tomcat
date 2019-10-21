package edu.neu.cs.cs6650.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlRow {
  private SqlResultSet results;
  private List<String> fields;

  public SqlRow(SqlResultSet results) {
    this.results = results;
    this.fields = new ArrayList<>();
  }

  public SqlRow add(String obj) {
    fields.add(obj);
    return this;
  }

  public String getRow() {
    return this.toString();
  }

  public String getField(int i) {
    if (i < 0 || i >= fields.size())
      return "";
    return fields.get(i);
  }

  public String getField(String fieldName) {
    int i = results.getHeaderIndex(fieldName);
    if (i < 0 || i > fields.size())
      return "";
    return fields.get(i);
  }

  public List<String> getFields() {
    return Collections.unmodifiableList(fields);
  }

  public int getNumFields() {
    return fields.size();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (String obj : fields) {
      if (obj != null) {
        sb.append("|");
        sb.append(obj);
      }
      sb.append("\t");
    }
    return sb.toString();
  }

}
