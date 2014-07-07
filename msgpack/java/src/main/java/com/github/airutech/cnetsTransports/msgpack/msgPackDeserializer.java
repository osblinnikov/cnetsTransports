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

  public msgPackDeserializer(cnetsMessagePackable cb){
    callback = cb;
//    assert callback != null;
  }

  private void suspend(boolean lastPacket) {
    isLastPacket = lastPacket;
    callback.fromNodeId(inputMetaData.getNodeUniqueIds()[0], data);
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

    if(c==null){
      System.out.println("startWith deserializer "+callback);
      c = Continuation.startWith(this);
    }else{
      System.out.println("continueWith deserializer "+callback);
      c = Continuation.continueWith(c);
    }

    inputMetaData = null;

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
      if(callback.deserializeWith(this,data)){
        suspend(true);
      }else{
        System.out.println("deserializer run error in "+callback);
        Continuation.suspend();
      }
    }
  }
}
