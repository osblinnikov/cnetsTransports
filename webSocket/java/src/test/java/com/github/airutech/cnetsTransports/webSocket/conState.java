package com.github.airutech.cnetsTransports.webSocket;

import com.github.airutech.cnetsTransports.types.connectionStatusCallback;

import java.util.Arrays;

/**
 * Created by oleg on 5/8/14.
 */
public class conState implements connectionStatusCallback {
  @Override
  public void onConnect(int uniqueNodeId, Long[] buffersAvailable) {
    if(buffersAvailable!=null) {
      System.out.println(Arrays.toString(buffersAvailable));
    }else{
      System.out.println("connected " + uniqueNodeId);
    }
  }

  @Override
  public void onDisconnect(int uniqueNodeId) {
    System.out.println("disconnected "+uniqueNodeId);
  }
}
