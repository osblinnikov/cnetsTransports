package com.github.airutech.cnetsTransports.msgpack;

import com.github.airutech.cnetsTransports.types.cnetsDeserializeValue;
import com.github.airutech.cnetsTransports.types.cnetsMessagePackable;
import com.github.airutech.cnetsTransports.types.cnetsProtocol;
import com.github.airutech.cnetsTransports.types.cnetsSerializeValue;

import java.io.IOException;

public class TestMessage implements cnetsMessagePackable {
  public int testValue;
  public String[] testArr = new String[]{"1","2","3","4","5","6","7","8","9","0"};

  @Override
  public boolean serializeWith(cnetsSerializeValue s, Object target){
    TestMessage that = (TestMessage)target;
    if(!s.serializeValue(that.testValue)){ return false; }
    if(!s.serializeValue(that.testArr.length)){ return false; }
    for(int i=0; i<that.testArr.length; i++) {
      if (!s.serializeValue(that.testArr[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean deserializeWith(cnetsDeserializeValue d, Object target) {
    TestMessage that = (TestMessage)target;
    try {
      that.testValue = d.deserializeValue(int.class);
      that.testArr = new String[d.deserializeValue(int.class)];
      for(int i=0; i<that.testArr.length; i++) {
        that.testArr[i] = d.deserializeValue(String.class);
      }
    } catch (IOException e) {return false;}
    return true;
  }

  @Override
  public void fillNodeIds(cnetsProtocol outputMetaData, Object target) {
    outputMetaData.setPublished(true);
  }

  @Override
  public void fromNodeId(int id, Object target) {

  }
}
