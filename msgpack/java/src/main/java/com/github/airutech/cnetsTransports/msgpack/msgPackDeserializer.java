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
  private cnetsMessagePackable callback;
  private Object data;
  private cnetsProtocol inputMetaData = null;

  /***** PACKING *****/
  private ByteBuffer bufPack;
  private MessagePack msgpack = new MessagePack();
  private BufferUnpacker unpacker;

  public msgPackDeserializer(cnetsMessagePackable callback){
    this.callback = callback;
    assert callback != null;
  }

  private void suspend(boolean isLastPacket) {
    this.isLastPacket = isLastPacket;
    callback.fromNodeId(inputMetaData.getNodeUniqueIds()[0], data);
    Continuation.suspend();
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
      if(callback.deserializeWith(this,data)){
        suspend(true);
      }else{
        return;
      }
    }
  }
}
