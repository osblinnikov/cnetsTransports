package com.github.airutech.cnetsTransports.types;

public interface connectionStatusCallback {
  public void onConnect(int uniqueNodeId);
  public void onDisconnect(int uniqueNodeId);
}
