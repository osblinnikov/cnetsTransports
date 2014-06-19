package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.*;
import org.apache.commons.javaflow.Continuation;
import org.msgpack.MessagePack;
import org.msgpack.packer.BufferPacker;

import java.io.IOException;
import java.nio.ByteBuffer;

public class msgPackSerializer implements serializeStreamCallback, Runnable, cnetsSerializeValue {
  Continuation c = null;

  cnetsProtocol outputMetaData = null;
  cnetsMessagePackable callback = null;
  Object data = null;
  boolean isLastPacket = false;

  /***** PACKING *****/
  ByteBuffer bufPack;
  MessagePack msgpack = new MessagePack();
  BufferPacker packer;

  public msgPackSerializer(cnetsMessagePackable callback){
    this.callback = callback;
    assert callback != null;
  }

  private void sendPacket(boolean isLastPacket){
    if(isLastPacket) {
      /*prevent receiver from waiting for more packets*/
      outputMetaData.setPackets_grid_size(outputMetaData.getPacket() + 1);
    }else{
      /*keep receiver receiving packets*/
      outputMetaData.setPackets_grid_size(outputMetaData.getPacket() + 2);
    }
    this.isLastPacket = isLastPacket;
    callback.fillNodeIds(outputMetaData,data);
    Continuation.suspend();
    assert data != null && outputMetaData != null;
  }

  @Override
  public <T> boolean serializeValue(T value){
    int lastPosition = bufPack.position();
    try {
      packer.write(value);
    }catch (IOException e) {
      bufPack.position(lastPosition);
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
  public boolean serializeNext( Object data, cnetsProtocol outputMetaData) {
    assert this.outputMetaData == null || data == this.data;
    assert data != null && outputMetaData != null;

    this.data = data;
    this.outputMetaData = outputMetaData;

    bufPack = outputMetaData.getData();
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
      if(callback.serializeWith(this,data)){
        sendPacket(true);
      }else{
        return;
      }
    }
  }
}
