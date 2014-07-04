package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.*;
import org.apache.commons.javaflow.Continuation;
import org.msgpack.MessagePack;
import org.msgpack.packer.BufferPacker;

import java.io.IOException;
import java.nio.ByteBuffer;

public class msgPackSerializer implements serializeStreamCallback, Runnable, cnetsSerializeValue {
  private Continuation c = null;

  private boolean isLastPacket = false;
  private cnetsMessagePackable callback;
  private Object data;
  private cnetsProtocol outputMetaData;

  /***** PACKING *****/
  private MessagePack msgpack = new MessagePack();
  private BufferPacker packer;

  public msgPackSerializer(cnetsMessagePackable cb){
    callback = cb;
//    assert callback != null;
  }

  private void sendPacket(boolean lastPacket){
    //getter and setter is not working in android for some reason... there is VerifyError exception thrown on calling the constructor of current class
    outputMetaData.packets_grid_size = lastPacket? outputMetaData.packet + 1: outputMetaData.packet + 2;

    isLastPacket = lastPacket;
    callback.fillNodeIds(outputMetaData, data);
    Continuation.suspend();
//    assert data != null && outputMetaData != null;
  }

  @Override
  public <T> boolean serializeValue(T value){
    int lastPosition = outputMetaData.getData().position();
    try {
      packer.write(value);
    }catch (IOException e) {
      outputMetaData.getData().position(lastPosition);
      sendPacket(false);
      try {
        packer.write(value);
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
  public boolean serializeNext( Object bufDataObj, cnetsProtocol output) {
//    assert this.outputMetaData == null || data == this.data;
//    assert data != null && outputMetaData != null;

    data = bufDataObj;
    outputMetaData = output;

    packer = msgpack.createBufferPacker(outputMetaData.getData());

    isLastPacket = false;

    if(c==null){
      c = Continuation.startWith(this);
    }else{
      c = Continuation.continueWith(c);
    }

    outputMetaData = null;

    return isLastPacket;
  }

  @Override
  public void run() {
    while(true) {
      if(callback.serializeWith(this,data)){
        sendPacket(true);
      }else{
        return;
      }
    }
  }
}
