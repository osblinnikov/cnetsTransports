
package com.github.airutech.cnetsTransports.msgpackExample.msgpack;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.msgpackExample.*;
import com.github.airutech.cnetsTransports.msgpack.*;
import com.github.airutech.cnetsTransports.types.*;
import java.io.IOException;
public class msgpack  implements cnetsMessagePackable {
  @Override
  public boolean serializeWith(cnetsSerializeValue s, Object target){
    com.github.airutech.cnetsTransports.msgpackExample.msgpackExample that = (com.github.airutech.cnetsTransports.msgpackExample.msgpackExample)target;

    if(!s.serializeValue(that.testInt)){ return false; }
    if (!s.serializeValue((int)that.testFloatArr.length)) { return false; }
    if (that.testFloatArr != null){
      for(int i=0; i<that.testFloatArr.length; i++) {
        if (!s.serializeValue(that.testFloatArr[i])) { return false; }
      }
    }
    if(that.testNestedExample != null){
      if (!(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack()).serializeWith(s,that.testNestedExample)) { return false;}
    }
    if (!s.serializeValue((int)that.testDoubleArr.length)) { return false; }
    if (that.testDoubleArr != null){
      for(int i=0; i<that.testDoubleArr.length; i++) {
        if (!s.serializeValue(that.testDoubleArr[i])) { return false; }
      }
    }
    if (!s.serializeValue((int)that.testNestedExampleArr.length)) { return false; }
    if(that.testNestedExampleArr != null){
      for(int i=0; i<that.testNestedExampleArr.length; i++) {
        if(that.testNestedExampleArr[i] != null){
          if (!(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack()).serializeWith(s,that.testNestedExampleArr[i])) { return false;}
        }
      }
    }
    return true;
  }

  @Override
  public boolean deserializeWith(cnetsDeserializeValue d, Object target) {
    com.github.airutech.cnetsTransports.msgpackExample.msgpackExample that = (com.github.airutech.cnetsTransports.msgpackExample.msgpackExample)target;
    try {

      that.testInt = d.deserializeValue(that.testInt);
      if (that.testFloatArr != null){
        for(int i=0, lastI = d.deserializeValue((int)that.testFloatArr.length); i<lastI; i++) {
          if(i<that.testFloatArr.length){
            that.testFloatArr[i] = d.deserializeValue(that.testFloatArr[i]);
          }
        }
      }
      if(that.testNestedExample != null){
        if (!(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack()).deserializeWith(d,that.testNestedExample)) { return false;}
      }
      if (that.testDoubleArr != null){
        for(int i=0, lastI = d.deserializeValue((int)that.testDoubleArr.length); i<lastI; i++) {
          if(i<that.testDoubleArr.length){
            that.testDoubleArr[i] = d.deserializeValue(that.testDoubleArr[i]);
          }
        }
      }
      if(that.testNestedExampleArr != null){
        for(int i=0, lastI = d.deserializeValue((int)that.testNestedExampleArr.length); i<lastI; i++) {
          if(i<that.testNestedExampleArr.length && that.testNestedExampleArr[i] != null){
            if (!(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack()).deserializeWith(d,that.testNestedExampleArr[i])) { return false;}
          }
        }
      }
    } catch (IOException e) {return false;}
    return true;
  }
/*[[[end]]] (checksum: ecdbacb894c11eea477e0f44824382ee)*/

  @Override
  public void fillNodeIds(cnetsProtocol outputMetaData, Object target) {
    //com.github.airutech.cnetsTransports.msgpackExample.msgpackExample that = (com.github.airutech.cnetsTransports.msgpackExample.msgpackExample)target;
    //outputMetaData.setNodeUniqueIds(new int[]{});
    outputMetaData.setPublished(true);
  }

  @Override
  public void fromNodeId(int id, Object target) {
    //com.github.airutech.cnetsTransports.msgpackExample.msgpackExample that = (com.github.airutech.cnetsTransports.msgpackExample.msgpackExample)target;
  }
}

