package com.github.airutech.cnetsTransports.types;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class nodeRepositoryProtocol implements serializeStreamCallback, deserializeStreamCallback {
  String[] bufferNames;
  int destinationUniqueNodeId;
  int maxLengthOfName;
  int currentFullSizeOfNames;
  int lastNameIterator;
  long lastReceivedPacketTime = 0;
  long lastSerializedPacket = -1;

  private nodeRepositoryProtocol(){}

  public nodeRepositoryProtocol(int maxCountOfNames, int maxLengthOfName) {
    this.lastNameIterator = 0;
    this.currentFullSizeOfNames = 0;
    this.maxLengthOfName = maxLengthOfName;
    bufferNames = new String[maxCountOfNames];
    for(int i=0; i<bufferNames.length; i++){
      bufferNames[i] = new String();
    }
  }

  public String[] getBufferNames() {
    return bufferNames;
  }

  public int getDestinationUniqueNodeId() {
    return destinationUniqueNodeId;
  }

  public void setDestinationUniqueNodeId(int destinationUniqueNodeId) {
    this.destinationUniqueNodeId = destinationUniqueNodeId;
  }

  private int getFullSize(){
    return currentFullSizeOfNames;
  }

  private boolean serializeName(int position, ByteBuffer data) {
    if(position > lastNameIterator){return false;}
    if(data.limit() - data.position() < bufferNames[position].length()){
      return false;
    }
    try {
      data.put(bufferNames[position].getBytes(Charset.forName("US-ASCII")));
    }catch (BufferOverflowException e){
      return false;
    }
    return true;
  }

  public int addName(String name){
    if(lastNameIterator >= bufferNames.length){
      return -1;
    }
    if(name.length() > maxLengthOfName){
      return -2;
    }

    bufferNames[lastNameIterator++] = name;
    currentFullSizeOfNames += name.length();
    return 0;
  }

  @Override
  public boolean serializeNext(Object data, cnetsProtocol outputMetaData) {
    if(!this.getClass().isInstance(data)){
      System.out.printf("nodeRepositoryProtocol: serializeNext: wrong serializator for the given object\n");
      return false;
    }

    int availableSpace = outputMetaData.getData().limit() - outputMetaData.getData().position();
    outputMetaData.setPackets_grid_size((long)Math.ceil(currentFullSizeOfNames/availableSpace));
    outputMetaData.setPacket(lastSerializedPacket+1);
    lastSerializedPacket = outputMetaData.getPacket();
    outputMetaData.setNodeUniqueId(-1);


    return true;
  }

  @Override
  public boolean deserializeNext(Object data, cnetsProtocol input) {
    if(!this.getClass().isInstance(data)){
      System.out.printf("nodeRepositoryProtocol: deserializeNext: wrong deserializator for the given object\n");
      return false;
    }
    if(input==null){
      return lastReceivedPacketTime <= 3000;
    }
    lastReceivedPacketTime = System.currentTimeMillis();





    if(input.getPacket() <= input.getPackets_grid_size() - 1){
      return true;
    }else{
      lastSerializedPacket = -1;
      return false;
    }
  }
}
