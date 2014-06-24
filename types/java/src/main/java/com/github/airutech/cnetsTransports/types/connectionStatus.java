package com.github.airutech.cnetsTransports.types;

public class connectionStatus {
  private int id;
  private boolean on;
  private int nodeIndex;

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

  public void setNodeIndex(int nodeIndex) {
    this.nodeIndex = nodeIndex;
  }

  public int getNodeIndex() {
    return nodeIndex;
  }

  public void copyFrom(connectionStatus conStatusReceived) {
    id = conStatusReceived.getId();
    on = conStatusReceived.isOn();
    nodeIndex = conStatusReceived.getNodeIndex();
  }

  public connectionStatus set(connectionStatus in){
    id = in.id;
    on = in.on;
    nodeIndex = in.nodeIndex;
    return this;
  }
}
