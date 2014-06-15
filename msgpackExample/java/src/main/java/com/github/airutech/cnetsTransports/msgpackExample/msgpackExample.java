
package com.github.airutech.cnetsTransports.msgpackExample;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.msgpack.*;
import java.io.IOException;
public class msgpackExample  implements cnetsMessagePackable {
  int testInt;float[] testFloatArr;com.github.airutech.cnetsTransports.msgpackExample.msgpackExample testNestedExample = null;double[] testDoubleArr;
  
  public msgpackExample(double[] testDoubleArr){
    this.testDoubleArr = testDoubleArr;
    this.testFloatArr = new float[10];
    this.testNestedExample = null;
    onCreate();
  }
 @Override
  public boolean serializeWith(msgPackSerializer s){

    if(!s.serializeValue(((msgpackExample) s.getData()).testInt)){ return false; }
    for(int i=0; i<testFloatArr.length; i++) {
      if (!s.serializeValue(((msgpackExample) s.getData()).testFloatArr[i])) { return false; }
    }
    if(!s.serializeValue(((msgpackExample) s.getData()).testNestedExample)){ return false; }
    for(int i=0; i<testDoubleArr.length; i++) {
      if (!s.serializeValue(((msgpackExample) s.getData()).testDoubleArr[i])) { return false; }
    }
    return true;
  }

  @Override
  public boolean deserializeWith(msgPackDeserializer d) {
    try {

      ((msgpackExample) d.getData()).testInt = d.deserializeValue(d, ((msgpackExample) d.getData()).testInt);
      for(int i=0; i<testFloatArr.length; i++) {
        ((msgpackExample) d.getData()).testFloatArr[i] = d.deserializeValue(d, ((msgpackExample) d.getData()).testFloatArr[i]);
      }
      ((msgpackExample) d.getData()).testNestedExample = d.deserializeValue(d, ((msgpackExample) d.getData()).testNestedExample);
      for(int i=0; i<testDoubleArr.length; i++) {
        ((msgpackExample) d.getData()).testDoubleArr[i] = d.deserializeValue(d, ((msgpackExample) d.getData()).testDoubleArr[i]);
      }
    } catch (IOException e) {return false;}
    return true;
  }
/*[[[end]]] (checksum: b4e1dbd6e8de8fd0d8e4aa28d33980af) */

  private void onCreate(){

  }

  private void onKernels(){

  }


}

