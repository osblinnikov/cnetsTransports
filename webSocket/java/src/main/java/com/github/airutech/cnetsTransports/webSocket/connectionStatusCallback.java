package com.github.airutech.cnetsTransports.webSocket;

/**
 * Created by oleg on 5/8/14.
 */
public interface connectionStatusCallback {
  public void onConnect(int uniqueNodeId, Long[] buffersAvailable);
  public void onDisconnect(int uniqueNodeId);
}
