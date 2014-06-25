package com.github.airutech.cnetsTransports.webSocket;

public class nodeBufIndex {
  private int dstBufferIndex;
  private String publishedName;
  private int nodeUniqueId;

  public void setDstBufferIndex(int dstBufferIndex) {
    this.dstBufferIndex = dstBufferIndex;
  }

  public int getDstBufferIndex() {
    return dstBufferIndex;
  }

  public void setPublishedName(String publishedName) {
    this.publishedName = publishedName;
  }

  public String getPublishedName() {
    return publishedName;
  }

  public int getNodeUniqueId() {
    return nodeUniqueId;
  }

  public void setNodeUniqueId(int nodeUniqueId) {
    this.nodeUniqueId = nodeUniqueId;
  }
}
