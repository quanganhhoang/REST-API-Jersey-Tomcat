package edu.neu.cs.cs6650.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SqlResultSet implements Iterable<SqlRow> {

  private final List<SqlHeader> headers;
  private final List<SqlRow> rows;

  public SqlResultSet() {
    this.rows = new ArrayList<>();
    this.headers = new ArrayList<>();
  }

  public SqlResultSet add(SqlRow row) {
    rows.add(row);
    return this;
  }

  public SqlResultSet addHeader(String name, String type) {
    headers.add(new SqlHeader(name, type));
    return this;
  }

  public SqlRow getRow(int i) {
    return rows.get(i);
  }

  public int getNumRows() {
    return rows.size();
  }

  public List<SqlRow> getRows() {
    return Collections.unmodifiableList(rows);
  }

  public List<SqlHeader> getHeaders() {
    return Collections.unmodifiableList(headers);
  }

  public int getHeaderIndex(String name) {
    for (int i=0; i < headers.size(); i++) {
      SqlHeader header = headers.get(i);
      if (header.getName().equalsIgnoreCase(name)) {
        return i;
      }
    }
    return -1;
  }

  public String getHeadersString() {
    StringBuilder headerString = new StringBuilder();
    for (SqlHeader header : headers) {
      headerString.append("|");
      headerString.append(header.toString());
      headerString.append("\t");
    }
    return headerString.toString();
  }

  public int size() {
    return rows.size();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getHeadersString());
    sb.append("\n");
    for (int i=0; i < getHeadersString().length()*2; i++) {
      sb.append("-");
    }
    sb.append("\n");
    for (SqlRow row : rows) {
      sb.append(row.toString());
      sb.append("\n");
    }
    return sb.toString();
  }

  // Supports for-each looping
  @Override
  public Iterator<SqlRow> iterator() {
    return new SqlResultsIterator();
  }

  // Inner class to support for-each looping
  class SqlResultsIterator implements Iterator<SqlRow> {
    int currentIndex = 0;

    @Override
    public boolean hasNext() {
      return currentIndex < getRows().size();
    }

    @Override
    public SqlRow next() {
      return getRows().get(currentIndex++);
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

  }

}