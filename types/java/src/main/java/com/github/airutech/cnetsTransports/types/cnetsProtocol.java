package com.github.airutech.cnetsTransports.types;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
* Created by oleg on 4/29/14.
*/
public class cnetsProtocol {
  long prefix = bufferUtils.dataPrefix;
  long bufferId;
  long timeStart;
  long bunchId;
  int packet;
  int packets_grid_size;
  private int nodeId = -1;/*not used for binarization*/

  public void setBufferId(long bufferId) {
    this.bufferId = bufferId;
  }
  public long getBufferId() {
    return bufferId;
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

  public ByteBuffer getByteBuffer(byte[] data){
    if(data.length < 28){
      System.err.println("getByteBuffer: data_size is too small");
      return null;
    }
    ByteBuffer output = ByteBuffer.wrap(data);
    try {
      types.writeUInt32(prefix,output);
      types.writeUInt32(bufferId,output);
      types.writeUInt32(timeStart,output);
      types.writeUInt32(bunchId,output);
      types.writeUInt32(packet,output);
      types.writeUInt32(packets_grid_size,output);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return output;
  }

  public ByteBuffer setFromBytes(byte[] data, int data_size) {
    if(data_size < 28){
      System.err.println("setFromBytes: data_size is too small");
      return null;
    }
    ByteBuffer input = ByteBuffer.wrap(data);
    try {
      prefix = types.readUInt32(input);
      bufferId = types.readUInt32(input);
      timeStart = types.readUInt32(input);
      bunchId = types.readUInt32(input);
      packet = (int)types.readUInt32(input);
      packets_grid_size = (int)types.readUInt32(input);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return input;
  }

  public int getNodeId() {
    return nodeId;
  }

  public void setNodeId(int nodeId) {
    this.nodeId = nodeId;
  }
}
