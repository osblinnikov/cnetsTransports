package com.github.airutech.cnetsTransports.types;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by oleg on 5/7/14.
 */
public class IntBoxer {
  public IntBoxer() {}
  public IntBoxer(int startValue) {
    this.value = startValue;
  }
  public int value;
  public boolean serialize(ByteBuffer out) {
    try{
      types.writeUInt32(value, out);
    }catch (IOException e){
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean deserialize(ByteBuffer input) {
    try{
      value = (int)types.readUInt32(input);
    }catch (IOException e){
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
