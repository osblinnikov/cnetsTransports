package com.github.airutech.cnetsTransports.types;

import java.nio.ByteBuffer;

public class cnetsProtocolBinary {
  ByteBuffer bb = null;
  private int[] nodeIds;
  private int nodeIdsSize;

  public cnetsProtocolBinary(){}
  public cnetsProtocolBinary(int dataSize){
    bb = ByteBuffer.wrap(new byte[(1 + dataSize)*4]);
  }

  public void setData(ByteBuffer bb) {
    this.bb = bb;
  }

  public ByteBuffer getData() {
    return bb;
  }

  public int[] getNodeIds() {
    return nodeIds;
  }

  public void setNodeIds(int[]  nodeIds) {
    this.nodeIds = nodeIds;
  }

  public void setNodeIdsSize(int nodeIdsSize) {
    this.nodeIdsSize = nodeIdsSize;
  }

  public int getNodeIdsSize() {
    return nodeIdsSize;
  }
}
