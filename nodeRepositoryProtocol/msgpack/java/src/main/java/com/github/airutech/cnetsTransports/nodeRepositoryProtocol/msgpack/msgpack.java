
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
    try {

      if (!s.serializeValue((int)that.subscribedNames.length)) { return false; }
      for(int i=0; i<that.subscribedNames.length; i++) {
        if (!s.serializeValue(that.subscribedNames[i])) { return false; }
      }
      if (!s.serializeValue((int)that.publishedNames.length)) { return false; }
      for(int i=0; i<that.publishedNames.length; i++) {
        if (!s.serializeValue(that.publishedNames[i])) { return false; }
      }
    } catch (Exception e) {e.printStackTrace();return false;}
    return true;
  }

  @Override
  public boolean deserializeWith(cnetsDeserializeValue d, Object target) {
    com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol that = (com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol)target;
    try {

      that.subscribedNames = new String[d.deserializeValue(int.class)];
      for(int i=0; i<that.subscribedNames.length; i++) {
        that.subscribedNames[i] = d.deserializeValue(String.class);
      }
      that.publishedNames = new String[d.deserializeValue(int.class)];
      for(int i=0; i<that.publishedNames.length; i++) {
        that.publishedNames[i] = d.deserializeValue(String.class);
      }
    } catch (Exception e) {e.printStackTrace();return false;}
    return true;
  }
/*[[[end]]] (checksum: f517cf6b1de87a0f3b2f31659b24f91d)*/

  @Override
  public void fillNodeIds(cnetsProtocol outputMetaData, Object target) {
    com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol that = (com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol)target;
    if(that.nodeId < 0){
      outputMetaData.setPublished(true);
    }else {
      outputMetaData.setNodeUniqueIds(new int[]{that.nodeId});
      outputMetaData.setPublished(false);
    }
  }

  @Override
  public void fromNodeId(int id, Object target) {
    com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol that = (com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol)target;
    that.nodeId = id;
  }
}

