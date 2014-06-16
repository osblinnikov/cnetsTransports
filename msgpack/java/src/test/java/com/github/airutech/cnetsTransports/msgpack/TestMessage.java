package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.cnetsDeserializeValue;
import com.github.airutech.cnetsTransports.types.cnetsMessagePackable;
import com.github.airutech.cnetsTransports.types.cnetsSerializeValue;

import java.io.IOException;

public class TestMessage implements cnetsMessagePackable {
  public int testValue;
  public int[] testArr = new int[]{1,2,3,4,5,6,7,8,9,0};

  @Override
  public boolean serializeWith(cnetsSerializeValue s){
    if(!s.serializeValue(testValue)){ return false; }
    for(int i=0; i<testArr.length; i++) {
      if (!s.serializeValue(testArr[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean deserializeWith(cnetsDeserializeValue d) {
    try {
      testValue = d.deserializeValue(testValue);
      for(int i=0; i<testArr.length; i++) {
        testArr[i] = d.deserializeValue(testArr[i]);
      }
    } catch (IOException e) {return false;}
    return true;
  }
}
