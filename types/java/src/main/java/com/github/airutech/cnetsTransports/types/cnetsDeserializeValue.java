package com.github.airutech.cnetsTransports.types;

import java.io.IOException;

public interface cnetsDeserializeValue {
  public <T> T deserializeValue(Class<T> t) throws IOException;
}
