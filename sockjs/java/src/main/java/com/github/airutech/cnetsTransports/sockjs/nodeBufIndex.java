package com.github.airutech.cnetsTransports.sockjs;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class nodeBufIndex {
//  private AtomicInteger dstBufferIndex = new AtomicInteger(0);
//  private AtomicReference<String> publishedName = new AtomicReference<String>(null);
  private AtomicInteger nodeUniqueId = new AtomicInteger(0);
//  private AtomicBoolean isConnected = new AtomicBoolean(false);
//
//  public void setDstBufferIndex(int dstBufferIndex) {
//    this.dstBufferIndex.set(dstBufferIndex);
//  }
//
//  public int getDstBufferIndex() {
//    return dstBufferIndex.get();
//  }

//  public void setPublishedName(String publishedName) {
//    this.publishedName.set(publishedName);
//  }
//
//  public String getPublishedName() {
//    return publishedName.get();
//  }

  public int getNodeUniqueId() {
    return nodeUniqueId.get();
  }

  public void setNodeUniqueId(int nodeUniqueId) {
    this.nodeUniqueId.set(nodeUniqueId);
  }
//
//  public boolean isConnected() {
//    return isConnected.get();
//  }
//
//  public void setConnected(boolean isConnected) {
//    this.isConnected.set(isConnected);
//  }
}
