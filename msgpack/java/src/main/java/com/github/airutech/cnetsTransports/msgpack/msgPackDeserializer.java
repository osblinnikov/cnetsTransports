package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.*;

public class msgPackDeserializer implements deserializeStreamCallback {
  @Override
  public boolean deserializeNext(Object bufDataObj, cnetsProtocol input) {
    return false;
  }
}
