package com.github.airutech.cnetsTransports.webSocket;

/**
 * Created by oleg on 6/6/14.
 */
public class nodeBufIndex {
  private int dstBufferIndex;
  private String writerName;
  private int nodeUniqueId;

  public void setDstBufferIndex(int dstBufferIndex) {
    this.dstBufferIndex = dstBufferIndex;
  }

  public int getDstBufferIndex() {
    return dstBufferIndex;
  }

  public void setWriterName(String writerName) {
    this.writerName = writerName;
  }

  public String getWriterName() {
    return writerName;
  }

  public int getNodeUniqueId() {
    return nodeUniqueId;
  }

  public void setNodeUniqueId(int nodeUniqueId) {
    this.nodeUniqueId = nodeUniqueId;
  }
}
