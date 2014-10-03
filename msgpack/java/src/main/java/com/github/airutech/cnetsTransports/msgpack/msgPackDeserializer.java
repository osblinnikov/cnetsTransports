package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.*;
import org.apache.commons.javaflow.Continuation;
import org.msgpack.MessagePack;
import org.msgpack.unpacker.BufferUnpacker;

import java.io.IOException;
import java.nio.ByteBuffer;

public class msgPackDeserializer implements deserializeStreamCallback, Runnable, cnetsDeserializeValue {
  private Continuation c = null;

  private boolean isLastPacket = false;
  private long previousPacketId = -1;
  private long curBunchId = 0;
  private cnetsMessagePackable callback;
  private Object data;
  private cnetsProtocol inputMetaData = null;

  /***** PACKING *****/
  private ByteBuffer bufPack;
  private MessagePack msgpack = new MessagePack();
  private BufferUnpacker unpacker;
  private boolean errorDetected = false;

  public msgPackDeserializer(cnetsMessagePackable cb){
    callback = cb;
//    assert callback != null;
  }

  private void suspend(boolean lastPacket) {
    isLastPacket = lastPacket;
    if(inputMetaData==null) {
      System.err.printf("msgPackDeserializer: suspend: inputMetaData==null\n");
    }else if(inputMetaData.getNodeUniqueIds() == null){
      System.err.printf("msgPackDeserializer: suspend: inputMetaData.getNodeUniqueIds() == null\n");
    }else{
      callback.fromNodeId(inputMetaData.getNodeUniqueIds()[0], data);
    }
    Continuation.suspend();
  }

  @Override
  public boolean deserializeNext(Object bufDataObj, cnetsProtocol input) {
//    assert inputMetaData == null || bufDataObj == this.data;
//    assert bufDataObj != null && input != null;

    data = bufDataObj;
    inputMetaData = input;

    bufPack = inputMetaData.getData();
    unpacker = msgpack.createBufferUnpacker(bufPack);

    isLastPacket = false;

    if(c == null && inputMetaData.getPacket() != 0) {
      System.err.printf("continueWith deserializer " + callback + " c==null && inputMetaData.getPacket() != 0\n");
      inputMetaData = null;
      return false;
    }

    if(input.getPacket() != 0 && curBunchId != input.getBunchId()) {
      System.err.printf("continueWith deserializer " + callback + " input.getPacket()!=0 && curBunchId (%d) != input.getBunchId() (%d)\n", curBunchId, input.getBunchId());
      c = null;/*we received not the first packet, but bunch is not that one which we are currently receiving - need to drop currently receiving bunch*/
      inputMetaData = null;
      return false;
    }

    if(c != null && input.getPacket() - 1 != previousPacketId) {
      System.err.printf("continueWith deserializer "+callback+" input.getPacket() (%d) != previousPacketId + 1 (%d)\n",input.getPacket(),previousPacketId + 1);
      if(input.getPacket() - 1 > previousPacketId) {
        c = null;/*reset only in case of lost packets between previousPacketId and input.getPacket()*/
      }
      inputMetaData = null;
      return false;
    }

    curBunchId = input.getBunchId();
    if (c == null) {
      c = Continuation.startWith(this);
    } else {
      c = Continuation.continueWith(c);
    }

    if(errorDetected){
      c = null;
      inputMetaData = null;
      return false;
    }

    previousPacketId = inputMetaData.getPacket();

    inputMetaData = null;
    if(isLastPacket){
      previousPacketId = -1;
    }
    return isLastPacket;
  }

  @Override
  public <T> T deserializeValue(Class<T> t) throws IOException {
    int lastPosition = bufPack.position();
    T value;
    try {
      value = unpacker.read(t);
    } catch (IOException e) {
      bufPack.position(lastPosition);
      suspend(false);
      value = unpacker.read(t);
    }
    return value;
  }

  @Override
  public void run() {
    while(true) {
      errorDetected = false;
      if(callback.deserializeWith(this,data)){
        suspend(true);
      }else{
        System.err.println("deserializer run error in "+callback);
        errorDetected = true;
        Continuation.suspend();
      }
    }
  }
}
