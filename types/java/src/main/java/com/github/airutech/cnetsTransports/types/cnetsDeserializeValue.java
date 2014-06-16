package com.github.airutech.cnetsTransports.types;

import java.io.IOException;

public interface cnetsDeserializeValue {
  public <T> T deserializeValue(T value) throws IOException;
}
