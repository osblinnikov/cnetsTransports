package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.*;

public class msgPackSerializer implements serializeStreamCallback {
  @Override
  public boolean serializeNext(Object data, cnetsProtocol outputMetaData) {
    return false;
  }
}
