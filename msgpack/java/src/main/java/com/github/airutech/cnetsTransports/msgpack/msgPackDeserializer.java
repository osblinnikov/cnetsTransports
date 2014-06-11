package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.*;
import org.apache.commons.javaflow.Continuation;
import org.msgpack.MessagePack;
import org.msgpack.packer.BufferPacker;
import org.msgpack.unpacker.BufferUnpacker;

import java.io.IOException;
import java.nio.ByteBuffer;

public class msgPackDeserializer implements deserializeStreamCallback, Runnable {
  Continuation c = null;

  boolean isLastPacket = false;
  private Object data;
  private cnetsProtocol inputMetaData = null;
  private cnetsMessagePackable deserializeObjectCallback = null;

  /***** PACKING *****/
  ByteBuffer bufPack;
  MessagePack msgpack = new MessagePack();
  BufferUnpacker unpacker;

  public msgPackDeserializer(cnetsMessagePackable deserializeObjectCallback){
    this.deserializeObjectCallback = deserializeObjectCallback;
  }

  @Override
  public boolean deserializeNext(Object bufDataObj, cnetsProtocol input) {
    assert inputMetaData == null || bufDataObj == this.data;
    assert bufDataObj != null && input != null;

    this.data = bufDataObj;
    this.inputMetaData = input;

    bufPack = inputMetaData.getData();
    unpacker = msgpack.createBufferUnpacker(bufPack);

    isLastPacket = false;

    if(c==null){
      c = Continuation.startWith(this);
    }else{
      c = Continuation.continueWith(c);
    }

    this.inputMetaData = null;

    return isLastPacket;
  }

  public <T> T deserializeValue(msgPackDeserializer d, T value) throws IOException {
    int lastPosition = getBufPack().position();
    try {
      value = getUnpacker().read(value);
    }catch (IOException e) {
      getBufPack().position(lastPosition);
      suspend(false);
      value = getUnpacker().read(value);
    }
    return value;
  }


  public void suspend(boolean isLastPacket) {
    this.isLastPacket = isLastPacket;
    Continuation.suspend();
  }

  public Object getData() {
    return data;
  }

  private cnetsProtocol getInputMetaData() {
    return inputMetaData;
  }

  public ByteBuffer getBufPack() {
    return bufPack;
  }

  public BufferUnpacker getUnpacker() {
    return unpacker;
  }

  @Override
  public void run() {
    while(true) {
      if(deserializeObjectCallback.deserializeWith(this)){
        suspend(true);
      }else{
        return;
      }
    }
  }
}
