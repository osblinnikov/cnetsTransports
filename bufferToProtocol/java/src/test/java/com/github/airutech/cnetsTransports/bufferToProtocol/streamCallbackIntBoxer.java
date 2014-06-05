package com.github.airutech.cnetsTransports.bufferToProtocol;

import com.github.airutech.cnetsTransports.types.cnetsProtocol;
import com.github.airutech.cnetsTransports.types.cnetsProtocolBinary;
import com.github.airutech.cnetsTransports.types.serializeStreamCallback;

class streamCallbackIntBoxer implements serializeStreamCallback {
//  @Override
//  public int serializeTo(byte[] dataBuf, Object dataObj, cnetsProtocol currentlySendingProtocol) {
//    currentlySendingProtocol.setNodeUniqueId(-1);
//    currentlySendingProtocol.setPackets_grid_size(1);
//    if(currentlySendingProtocol.getPacket() < 1) {
//      ByteBuffer out = currentlySendingProtocol.getByteBuffer(dataBuf);
//      IntBoxer intBoxer = (IntBoxer)dataObj;
//      if(!intBoxer.serializeTo(out)){return -1;}
//      return out.position();
//    }else{
//      return 0;
//    }
//  }

  @Override
  public boolean serializeNext(Object data, cnetsProtocol currentlySendingProtocol) {
    currentlySendingProtocol.setPackets_grid_size(1);
    outputBuffer.setNodeId(-1);
//    ByteBuffer out = outputBuffer.getData();
//    IntBoxer intBoxer = (IntBoxer)data;
//    if(!intBoxer.serializeTo(out)){return false;}

    return true;
  }
}
