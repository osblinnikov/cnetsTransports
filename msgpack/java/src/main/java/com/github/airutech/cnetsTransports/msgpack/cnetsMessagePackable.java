package com.github.airutech.cnetsTransports.msgpack;

import java.io.IOException;

interface cnetsMessagePackable{
  public boolean serializeWith(msgPackSerializer s);
  public boolean deserializeWith(msgPackDeserializer d);
}
