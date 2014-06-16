package com.github.airutech.cnetsTransports.types;

import com.github.airutech.cnetsTransports.types.*;

public interface serializeStreamCallback {
  public boolean serializeNext(cnetsMessagePackable data, cnetsProtocol outputMetaData);
}
