package com.github.airutech.cnetsTransports.msgpack;

import java.io.IOException;

public class TestMessage implements cnetsMessagePackable {
  public int testValue;
  public int[] testArr = new int[]{1,2,3,4,5,6,7,8,9,0};

  @Override
  public boolean serializeWith(msgPackSerializer s){
    if(!s.serializeValue(((TestMessage) s.getData()).testValue)){ return false; }
    for(int i=0; i<testArr.length; i++) {
      if (!s.serializeValue(((TestMessage) s.getData()).testArr[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean deserializeWith(msgPackDeserializer d) {
    try {
      ((TestMessage) d.getData()).testValue = d.deserializeValue(d, ((TestMessage) d.getData()).testValue);
      for(int i=0; i<testArr.length; i++) {
        ((TestMessage) d.getData()).testArr[i] = d.deserializeValue(d, ((TestMessage) d.getData()).testArr[i]);
      }
    } catch (IOException e) {return false;}
    return true;
  }
}
