
package com.github.airutech.cnetsTransports.msgpackExample;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.msgpack.*;
import com.github.airutech.cnetsTransports.types.*;
import java.io.IOException;
public class msgpackExample  implements cnetsMessagePackable {
  int testInt;float[] testFloatArr;com.github.airutech.cnetsTransports.msgpackExample.msgpackExample testNestedExample = null;double[] testDoubleArr;com.github.airutech.cnetsTransports.msgpackExample.msgpackExample[] testNestedExampleArr;
  
  public msgpackExample(double[] testDoubleArr,com.github.airutech.cnetsTransports.msgpackExample.msgpackExample[] testNestedExampleArr){
    this.testDoubleArr = testDoubleArr;
    this.testNestedExampleArr = testNestedExampleArr;
    this.testFloatArr = new float[10];
    this.testNestedExample = null;
    onCreate();
  }
 @Override
  public boolean serializeWith(cnetsSerializeValue s){

    if(!s.serializeValue(testInt)){ return false; }
    if (!s.serializeValue((int)testFloatArr.length)) { return false; }
    if (testFloatArr != null){
      for(int i=0; i<testFloatArr.length; i++) {
        if (!s.serializeValue(testFloatArr[i])) { return false; }
      }
    }
    if(testNestedExample != null){
      if (!testNestedExample.serializeWith(s)) { return false;}
    }
    if (!s.serializeValue((int)testDoubleArr.length)) { return false; }
    if (testDoubleArr != null){
      for(int i=0; i<testDoubleArr.length; i++) {
        if (!s.serializeValue(testDoubleArr[i])) { return false; }
      }
    }
    if (!s.serializeValue((int)testNestedExampleArr.length)) { return false; }
    if(testNestedExampleArr != null){
      for(int i=0; i<testNestedExampleArr.length; i++) {
        if(testNestedExampleArr[i] != null){
          if (!testNestedExampleArr[i].serializeWith(s)) { return false;}
        }
      }
    }
    return true;
  }

  @Override
  public boolean deserializeWith(cnetsDeserializeValue d) {
    try {

      testInt = d.deserializeValue(testInt);
      if (testFloatArr != null){
        for(int i=0, lastI = d.deserializeValue((int)testFloatArr.length); i<lastI; i++) {
          if(i<testFloatArr.length){
            testFloatArr[i] = d.deserializeValue(testFloatArr[i]);
          }
        }
      }
      if(testNestedExample != null){
        if (!testNestedExample.deserializeWith(d)) { return false;}
      }
      if (testDoubleArr != null){
        for(int i=0, lastI = d.deserializeValue((int)testDoubleArr.length); i<lastI; i++) {
          if(i<testDoubleArr.length){
            testDoubleArr[i] = d.deserializeValue(testDoubleArr[i]);
          }
        }
      }
      if(testNestedExampleArr != null){
        for(int i=0, lastI = d.deserializeValue((int)testNestedExampleArr.length); i<lastI; i++) {
          if(i<testNestedExampleArr.length && testNestedExampleArr[i] != null){
            if (!testNestedExampleArr[i].deserializeWith(d)) { return false;}
          }
        }
      }
    } catch (IOException e) {return false;}
    return true;
  }
/*[[[end]]] (checksum: d160a55e29411986f616ba7a2d1ef386)*/

  private void onCreate(){

  }


}

