package com.github.airutech.cnetsTransports.webSocket;

import com.github.airutech.cnets.readerWriter.reader;

/**
 * Created by oleg on 6/6/14.
 */
public class nodeBufIndex {
  private int dstBufferIndex;
  private reader r0;
  private int nodeUniqueId;

  public void setDstBufferIndex(int dstBufferIndex) {
    this.dstBufferIndex = dstBufferIndex;
  }

  public int getDstBufferIndex() {
    return dstBufferIndex;
  }

  public void setR0(reader r0) {
    this.r0 = r0;
  }

  public reader getR0() {
    return r0;
  }

  public int getNodeUniqueId() {
    return nodeUniqueId;
  }

  public void setNodeUniqueId(int nodeUniqueId) {
    this.nodeUniqueId = nodeUniqueId;
  }
}
