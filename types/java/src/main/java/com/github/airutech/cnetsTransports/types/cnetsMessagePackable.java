package com.github.airutech.cnetsTransports.types;

public interface cnetsMessagePackable{
  public boolean serializeWith(cnetsSerializeValue s, Object target);
  public boolean deserializeWith(cnetsDeserializeValue d, Object target);
}
