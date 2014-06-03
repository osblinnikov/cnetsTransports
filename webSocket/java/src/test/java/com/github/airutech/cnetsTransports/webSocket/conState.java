package com.github.airutech.cnetsTransports.webSocket;

import com.github.airutech.cnetsTransports.types.connectionStatusCallback;

import java.util.Arrays;

public class conState implements connectionStatusCallback {
  @Override
  public void onConnect(int uniqueNodeId) {
    System.out.println("connected " + uniqueNodeId);
  }

  @Override
  public void onDisconnect(int uniqueNodeId) {
    System.out.println("disconnected "+uniqueNodeId);
  }
}
