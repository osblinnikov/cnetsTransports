package com.github.airutech.cnetsTransports.protocolToBuffer;

import com.github.airutech.cnetsTransports.types.IntBoxer;

import java.nio.ByteBuffer;

/**
 * Created by oleg on 5/6/14.
 */
public class deserializeCallbackIntBoxer implements deserializeCallback {
  @Override
  public boolean deserialize(ByteBuffer input, Object bufDataObj, int packetId, int packets_grid_size, boolean isNewBunch, int nodeId) {
    IntBoxer intBoxer = (IntBoxer)bufDataObj;
    if(!intBoxer.deserialize(input)){return false;}
    return true;
  }
}
