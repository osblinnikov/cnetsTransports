package com.github.airutech.cnetsTransports.types;

public class connectionStatus {
  private int id;
  private boolean on;

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setOn(boolean on) {
    this.on = on;
  }

  public boolean isOn() {
    return on;
  }
}
