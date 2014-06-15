package com.github.airutech.cnetsTransports.msgpack;

public interface cnetsMessagePackable{
  public boolean serializeWith(msgPackSerializer s);
  public boolean deserializeWith(msgPackDeserializer d);
}
