package com.github.airutech.cnetsTransports.types;

public interface cnetsMessagePackable{
  public boolean serializeWith(cnetsSerializeValue s);
  public boolean deserializeWith(cnetsDeserializeValue d);
}
