package com.github.airutech.cnetsTransports.protocolToBuffer;

import com.github.airutech.cnetsTransports.types.IntBoxer;

import java.nio.ByteBuffer;

public class deserializeCallbackIntBoxer implements deserializeCallback {
  @Override
  public boolean deserialize(ByteBuffer input, Object bufDataObj, int packetId, int packets_grid_size, boolean isNewBunch) {
    IntBoxer intBoxer = (IntBoxer)bufDataObj;
    if(!intBoxer.deserialize(input)){return false;}
    return true;
  }
}
