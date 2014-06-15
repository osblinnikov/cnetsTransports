
package com.github.airutech.cnetsTransports.msgpackExample;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.msgpack.*;
import java.io.IOException;
public class msgpackExample  implements cnetsMessagePackable {
  int testInt;float testFloatArr;com.github.airutech.cnetsTransports.msgpackExample.msgpackExample testNestedExample = null;
  
  public msgpackExample(){
    this.testNestedExample = null;
    onCreate();
  }
 @Override
  public boolean serializeWith(msgPackSerializer s){

    if(!s.serializeValue(((msgpackExample) s.getData()).testInt)){ return false; }
    if(!s.serializeValue(((msgpackExample) s.getData()).testFloatArr)){ return false; }
    if(!s.serializeValue(((msgpackExample) s.getData()).testNestedExample)){ return false; }
    return true;
  }

  @Override
  public boolean deserializeWith(msgPackDeserializer d) {
    try {

      ((msgpackExample) d.getData()).testInt = d.deserializeValue(d, ((msgpackExample) d.getData()).testInt);
      ((msgpackExample) d.getData()).testFloatArr = d.deserializeValue(d, ((msgpackExample) d.getData()).testFloatArr);
      ((msgpackExample) d.getData()).testNestedExample = d.deserializeValue(d, ((msgpackExample) d.getData()).testNestedExample);
    } catch (IOException e) {return false;}
    return true;
  }
/*[[[end]]] (checksum: d34b2247bd0b9b8292067323ea9c6f55) */

  private void onCreate(){

  }

  private void onKernels(){

  }


}

