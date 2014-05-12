package com.github.airutech.cnetsTransports.types;

/**
 * Created by oleg on 4/29/14.
 */
public class cnetsProtocolBinary {
  private byte[] data = null;
  private int data_size = 0;
  private long bufferId = -1;
  private int nodeId = -1;

  public cnetsProtocolBinary(){}
  public cnetsProtocolBinary(int dataSize){
    data = new byte[dataSize];
  }

  public void setBufferId(long bufferId) {
      this.bufferId = bufferId;
  }

  public long getBufferId() {
      return bufferId;
  }

  public void setData(byte[] array, int data_size) {
    this.data = array;
    this.data_size = data_size;
  }

  public void setData_size(int data_size){
     this.data_size = data_size;
  }

  public int getData_size(){
    return data_size;
  }

  public byte[] getData() {
    return data;
  }


  public int getNodeId() {
    return nodeId;
  }

  public void setNodeId(int nodeId) {
    this.nodeId = nodeId;
  }
}
