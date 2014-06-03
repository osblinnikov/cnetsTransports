package com.github.airutech.cnetsTransports.bufferToProtocol;

import com.github.airutech.cnetsTransports.types.cnetsProtocol;

import java.nio.ByteBuffer;

public interface serializeCallback {
  public boolean serialize(Object data, ByteBuffer outputBuffer, cnetsProtocol currentlySendingProtocol);
}
