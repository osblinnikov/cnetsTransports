package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.*;
import org.apache.commons.javaflow.Continuation;
import org.msgpack.MessagePack;
import org.msgpack.packer.BufferPacker;

import java.io.IOException;
import java.nio.ByteBuffer;

public class msgPackSerializer implements serializeStreamCallback, Runnable {
  Continuation c = null;

  Object data = null;
  cnetsProtocol outputMetaData = null;

  boolean isLastPacket = false;
  private cnetsMessagePackable serializeObjectCallback = null;

  /***** PACKING *****/
  ByteBuffer bufPack;
  MessagePack msgpack = new MessagePack();
  BufferPacker packer;

  public msgPackSerializer(cnetsMessagePackable serializeObjectCallback){
    this.serializeObjectCallback = serializeObjectCallback;
  }

  private cnetsProtocol getOutputMetaData() {
    return outputMetaData;
  }

  public Object getData() {
    return data;
  }


  public ByteBuffer getBufPack() {
    return bufPack;
  }

  public BufferPacker getPacker() {
    return packer;
  }

  public <T> boolean serializeValue(T value){
    int lastPosition = getBufPack().position();
    try {
      getPacker().write(value);
    }catch (IOException e) {
      getBufPack().position(lastPosition);
      sendPacket(false);
      try {
        getPacker().write(value);
      }catch (IOException e1){
        /*it is not allowed to fail twice, it means that buffer size is not big enough*/
        e1.printStackTrace();
        sendPacket(true);
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean serializeNext(Object data, cnetsProtocol outputMetaData) {
    assert this.outputMetaData == null || data == this.data;
    assert data != null && outputMetaData != null;

    this.data = data;
    this.outputMetaData = outputMetaData;

    bufPack = getOutputMetaData().getData();
    packer = msgpack.createBufferPacker(bufPack);

    isLastPacket = false;

    if(c==null){
      c = Continuation.startWith(this);
    }else{
      c = Continuation.continueWith(c);
    }

    this.outputMetaData = null;

    return isLastPacket;
  }

  @Override
  public void run() {
    while(true) {
      if(serializeObjectCallback.serializeWith(this)){
        sendPacket(true);
      }else{
        return;
      }
    }
  }

//  public boolean sendIfNotEnoughSpace(){
//
//  }
//
//  public boolean sendIfNotEnoughSpace(int requiredSpace){
//    ByteBuffer bb = outputMetaData.getData();
//    if(bb.limit() - bb.position() < requiredSpace){
//      return false;
//    }
//    isLastPacket = false;
//    /*keep receiver receiving packets*/
//    outputMetaData.setPackets_grid_size(outputMetaData.getPacket() + 2);
//    Continuation.suspend();
//    assert data != null && outputMetaData != null;
//    return true;
//  }

  public void sendPacket(boolean isLastPacket){
    if(isLastPacket) {
      /*prevent receiver from waiting for more packets*/
      outputMetaData.setPackets_grid_size(outputMetaData.getPacket() + 1);
    }else{
      /*keep receiver receiving packets*/
      outputMetaData.setPackets_grid_size(outputMetaData.getPacket() + 2);
    }
    this.isLastPacket = isLastPacket;
    Continuation.suspend();
    assert data != null && outputMetaData != null;
  }
}
