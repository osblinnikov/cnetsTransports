package com.github.airutech.cnetsTransports.types;

import java.io.IOException;
import java.nio.ByteBuffer;

public class cnetsProtocol {
  ByteBuffer bb = null;
  private long bufferIndex;
  private long timeStart;
  private long bunchId;
  private long packet;
  private long packets_grid_size;
  private int nodeUniqueId; /*it's not going to be serialized*/

  private cnetsProtocol(){}

  public cnetsProtocol(ByteBuffer bb){
    this.bb = bb;
  }

  public cnetsProtocol(int dataSize){
    bb = ByteBuffer.wrap(new byte[(1 + dataSize)*4]);
  }


  public void setFrom(cnetsProtocol in) {
    bb = in.bb;
    timeStart = in.timeStart;
    bunchId = in.bunchId;
    packet = in.packet;
    packets_grid_size = in.packets_grid_size;
    bufferIndex = in.bufferIndex;
    nodeUniqueId = in.nodeUniqueId;
  }

  public void setData(ByteBuffer bb) {
    this.bb = bb;
  }

  public ByteBuffer getData() {
    return bb;
  }

  public int getNodeUniqueId() {
    return nodeUniqueId;
  }

  public void setNodeUniqueId(int nodeUniqueId) {
    this.nodeUniqueId = nodeUniqueId;
  }

  public void setTimeStart(long timeStart) {
    this.timeStart = timeStart;
  }
  public long getTimeStart() {
    return timeStart;
  }

  public void setBunchId(long bunchId) {
      this.bunchId = bunchId;
  }
  public long getBunchId() {
      return bunchId;
  }

  public void setPacket(long packet) {
      this.packet = packet;
  }
  public long getPacket() {
      return packet;
  }

  public void setPackets_grid_size(long packets_grid_size) {
      this.packets_grid_size = packets_grid_size;
  }
  public long getPackets_grid_size() {
      return packets_grid_size;
  }

  public static int fullSize(){
    return 5*4;
  }


  /*
  * ByteBuffer structure
  *
  * prefixSize() bytes: for cnetsProtocol
  * rest bytes: for data payload
  *
  * */
  public boolean serialize(){
    int oldLimit = bb.limit();
    bb.position(0);
    try {
      types.writeUInt32(bufferIndex,bb);
      types.writeUInt32(timeStart,bb);
      types.writeUInt32(bunchId,bb);
      types.writeUInt32(packet,bb);
      types.writeUInt32(packets_grid_size,bb);
      bb.limit(oldLimit);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean deserialize() {
    try {
      bb.position(0);
      bufferIndex = types.readUInt32(bb);
      timeStart = types.readUInt32(bb);
      bunchId = types.readUInt32(bb);
      packet = types.readUInt32(bb);
      packets_grid_size = types.readUInt32(bb);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public void reserveForHeader() {
    getData().clear();
    getData().limit(cnetsProtocol.fullSize());
    getData().position(cnetsProtocol.fullSize());
  }

  public long getBufferIndex() {
    return bufferIndex;
  }

  public void setBufferIndex(long bufferIndex) {
    this.bufferIndex = bufferIndex;
  }

  public void incrementPacket() {
    this.packet++;
  }
}
