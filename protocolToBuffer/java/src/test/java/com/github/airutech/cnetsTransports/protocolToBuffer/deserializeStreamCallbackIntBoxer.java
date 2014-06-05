package com.github.airutech.cnetsTransports.protocolToBuffer;

import com.github.airutech.cnetsTransports.types.IntBoxer;
import com.github.airutech.cnetsTransports.types.deserializeStreamCallback;

import java.nio.ByteBuffer;

public class deserializeStreamCallbackIntBoxer implements deserializeStreamCallback {
  @Override
  public boolean deserialize(ByteBuffer input, Object bufDataObj, long packetId, long packets_grid_size, boolean isNewBunch) {
    IntBoxer intBoxer = (IntBoxer)bufDataObj;
    if(!intBoxer.deserialize(input)){return false;}
    return true;
  }
}
