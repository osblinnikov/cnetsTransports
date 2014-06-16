package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.*;
import org.apache.commons.javaflow.Continuation;
import org.msgpack.MessagePack;
import org.msgpack.unpacker.BufferUnpacker;

import java.io.IOException;
import java.nio.ByteBuffer;

public class msgPackDeserializer implements deserializeStreamCallback, Runnable, cnetsDeserializeValue {
  Continuation c = null;

  boolean isLastPacket = false;
  private cnetsMessagePackable data;
  private cnetsProtocol inputMetaData = null;

  /***** PACKING *****/
  ByteBuffer bufPack;
  MessagePack msgpack = new MessagePack();
  BufferUnpacker unpacker;

  public msgPackDeserializer(){

  }

  private void suspend(boolean isLastPacket) {
    this.isLastPacket = isLastPacket;
    Continuation.suspend();
  }

  @Override
  public boolean deserializeNext(cnetsMessagePackable bufDataObj, cnetsProtocol input) {
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

  @Override
  public <T> T deserializeValue(T value) throws IOException {
    int lastPosition = bufPack.position();
    try {
      value = unpacker.read(value);
    }catch (IOException e) {
      bufPack.position(lastPosition);
      suspend(false);
      value = unpacker.read(value);
    }
    return value;
  }

  @Override
  public void run() {
    while(true) {
      if(data.deserializeWith(this)){
        suspend(true);
      }else{
        return;
      }
    }
  }
}
