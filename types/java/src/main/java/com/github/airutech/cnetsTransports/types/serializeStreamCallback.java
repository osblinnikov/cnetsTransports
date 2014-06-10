package com.github.airutech.cnetsTransports.types;

import com.github.airutech.cnetsTransports.types.*;

public interface serializeStreamCallback {
  public boolean serializeNext(Object data, cnetsProtocol outputMetaData);
}
