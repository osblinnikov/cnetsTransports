
package com.github.airutech.cnetsTransports.nodeRepositoryProtocol.msgpack;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnetsTransports.msgpack.*;
import com.github.airutech.cnetsTransports.types.*;
import java.io.IOException;
public class msgpack  implements cnetsMessagePackable {
  @Override
  public boolean serializeWith(cnetsSerializeValue s, Object target){
    com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol that = (com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol)target;

    if (!s.serializeValue((int)that.bufferNames.length)) { return false; }
    if (that.bufferNames != null){
      for(int i=0; i<that.bufferNames.length; i++) {
        if (!s.serializeValue(that.bufferNames[i])) { return false; }
      }
    }
    return true;
  }

  @Override
  public boolean deserializeWith(cnetsDeserializeValue d, Object target) {
    com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol that = (com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol)target;
    try {

      if (that.bufferNames != null){
        for(int i=0, lastI = d.deserializeValue((int)that.bufferNames.length); i<lastI; i++) {
          if(i<that.bufferNames.length){
            that.bufferNames[i] = d.deserializeValue(that.bufferNames[i]);
          }
        }
      }
    } catch (IOException e) {return false;}
    return true;
  }
/*[[[end]]] (checksum: 6c99dd48102f50e1787216bbe667b4f3)*/

  @Override
  public void fillNodeIds(cnetsProtocol outputMetaData, Object target) {
    //com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol that = (com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol)target;
    //outputMetaData.setNodeUniqueIds(new int[]{});
    outputMetaData.setPublished(true);
  }

  @Override
  public void fromNodeId(int id, Object target) {
    //com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol that = (com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol)target;
  }
}

