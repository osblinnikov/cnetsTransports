
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
    try {

      if(!s.serializeValue(that.testInt)){ return false; }
      if (!s.serializeValue((int)that.testFloatArr.length)) { return false; }
      for(int i=0; i<that.testFloatArr.length; i++) {
        if (!s.serializeValue(that.testFloatArr[i])) { return false; }
      }
      if (!(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack()).serializeWith(s,that.testNestedExample)) { return false;}
      if (!s.serializeValue((int)that.testDoubleArr.length)) { return false; }
      for(int i=0; i<that.testDoubleArr.length; i++) {
        if (!s.serializeValue(that.testDoubleArr[i])) { return false; }
      }
      if (!s.serializeValue((int)that.testNestedExampleArr.length)) { return false; }
      for(int i=0; i<that.testNestedExampleArr.length; i++) {
        if (!(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack()).serializeWith(s,that.testNestedExampleArr[i])) { return false;}
      }
    } catch (Exception e) {e.printStackTrace();return false;}
    return true;
  }

  @Override
  public boolean deserializeWith(cnetsDeserializeValue d, Object target) {
    com.github.airutech.cnetsTransports.msgpackExample.msgpackExample that = (com.github.airutech.cnetsTransports.msgpackExample.msgpackExample)target;
    try {

      that.testInt = d.deserializeValue(int.class);
      that.testFloatArr = new float[d.deserializeValue(int.class)];
      for(int i=0; i<that.testFloatArr.length; i++) {
        that.testFloatArr[i] = d.deserializeValue(float.class);
      }
      if (!(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack()).deserializeWith(d,that.testNestedExample)) { return false;}
      that.testDoubleArr = new double[d.deserializeValue(int.class)];
      for(int i=0; i<that.testDoubleArr.length; i++) {
        that.testDoubleArr[i] = d.deserializeValue(double.class);
      }
      that.testNestedExampleArr = new com.github.airutech.cnetsTransports.msgpackExample.msgpackExample[d.deserializeValue(int.class)];
      for(int i=0; i<that.testNestedExampleArr.length; i++) {
        that.testNestedExampleArr[i] = new com.github.airutech.cnetsTransports.msgpackExample.msgpackExample();
        if (!(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack()).deserializeWith(d,that.testNestedExampleArr[i])) { return false;}
      }
    } catch (Exception e) {e.printStackTrace();return false;}
    return true;
  }
/*[[[end]]] (checksum: 3c0ed53e5e4e0d738dd972e40bd2cdc7)*/

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

