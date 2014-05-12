package com.github.airutech.cnetsTransports.bufferToProtocol;

import com.github.airutech.cnetsTransports.types.IntBoxer;
import com.github.airutech.cnetsTransports.types.cnetsProtocol;

import java.nio.ByteBuffer;

/**
 * Created by oleg on 5/6/14.
 */
class callbackIntBoxer implements serializeCallback{
  @Override
  public int serialize(byte[] dataBuf, Object dataObj, cnetsProtocol currentlySendingProtocol) {
    currentlySendingProtocol.setNodeId(-1);
    currentlySendingProtocol.setPackets_grid_size(1);
    if(currentlySendingProtocol.getPacket() < 1) {
      ByteBuffer out = currentlySendingProtocol.getByteBuffer(dataBuf);
      IntBoxer intBoxer = (IntBoxer)dataObj;
      if(!intBoxer.serialize(out)){return -1;}
      return out.position();
    }else{
      return 0;
    }
  }
}
