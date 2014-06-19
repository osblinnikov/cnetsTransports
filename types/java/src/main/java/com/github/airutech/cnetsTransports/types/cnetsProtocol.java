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
  private int[] nodeUniqueIds; /*it's not going to be serialized*/
  private boolean published; /*it's not going to be serialized*/

  public cnetsProtocol(){
    bb = null;
    nodeUniqueIds = null;
  }

  public cnetsProtocol(int dataSize, int nodesSize){
    bb = ByteBuffer.wrap(new byte[dataSize]);
    nodeUniqueIds = new int[nodesSize];
  }


  public void setFrom(cnetsProtocol in) {
//    bb = in.bb;
    timeStart = in.timeStart;
    bunchId = in.bunchId;
    packet = in.packet;
    packets_grid_size = in.packets_grid_size;
    bufferIndex = in.bufferIndex;
//    nodeUniqueIds = in.nodeUniqueIds;
//    published = in.published;
  }

  public void setData(ByteBuffer bb) {
    this.bb = bb;
  }

  public ByteBuffer getData() {
    return bb;
  }

  public int[] getNodeUniqueIds() {
    return nodeUniqueIds;
  }

  public void setNodeUniqueIds(int[] nodeUniqueIds) {
    this.nodeUniqueIds = nodeUniqueIds;
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
    int oldPosition = bb.position();
    bb.position(0);
    try {
      types.writeUInt32(bufferIndex,bb);
      types.writeUInt32(timeStart,bb);
      types.writeUInt32(bunchId,bb);
      types.writeUInt32(packet,bb);
      types.writeUInt32(packets_grid_size,bb);
      bb.position(oldPosition);
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
//    getData().limit(cnetsProtocol.fullSize());
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

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }
}
