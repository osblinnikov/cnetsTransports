package com.github.airutech.cnetsTransports.types;

import java.io.IOException;
import java.nio.ByteBuffer;

public class cnetsProtocol {
  private long bufferIndex;
  private long timeStart;
  private long bunchId;
  private int packet;
  private int packets_grid_size;


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

  public void setPacket(int packet) {
      this.packet = packet;
  }
  public int getPacket() {
      return packet;
  }

  public void setPackets_grid_size(int packets_grid_size) {
      this.packets_grid_size = packets_grid_size;
  }
  public int getPackets_grid_size() {
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
  public boolean serialize(ByteBuffer bb){
    int oldLimit = bb.limit();
    bb.position(0);
    try {
      types.writeUInt32(getBufferIndex(),bb);
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

  public boolean deserialize(ByteBuffer input) {
    try {
      input.position(0);
      setBufferIndex(types.readUInt32(input));
      timeStart = types.readUInt32(input);
      bunchId = types.readUInt32(input);
      packet = (int)types.readUInt32(input);
      packets_grid_size = (int)types.readUInt32(input);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static void reserve(ByteBuffer data) {
    data.limit(cnetsProtocol.fullSize());
    data.position(cnetsProtocol.fullSize());
  }

  public long getBufferIndex() {
    return bufferIndex;
  }

  public void setBufferIndex(long bufferIndex) {
    this.bufferIndex = bufferIndex;
  }
}
