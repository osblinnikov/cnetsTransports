package com.github.airutech.cnetsTransports.bufferToProtocol;

import com.github.airutech.cnetsTransports.types.cnetsProtocol;

import java.nio.ByteBuffer;

/**
 * Created by oleg on 4/29/14.
 */
public interface serializeCallback {
  public int serialize(byte[] dataBuf, Object dataObj, cnetsProtocol currentlySendingProtocol);
}
