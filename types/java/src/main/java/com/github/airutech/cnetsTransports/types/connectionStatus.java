package com.github.airutech.cnetsTransports.types;

public class connectionStatus {
  private int id;
  private boolean on;
  private boolean receivedRepo;

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

  public void setReceivedRepo(boolean receivedRepo) {
    this.receivedRepo = receivedRepo;
  }

  public boolean isReceivedRepo() {
    return receivedRepo;
  }

  public connectionStatus set(connectionStatus in){
    id = in.id;
    on = in.on;
    receivedRepo = in.receivedRepo;
    return this;
  }
}
