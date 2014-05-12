
package com.github.airutech.cnetsTransports.protocolToBuffer;
import com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 6a1822acecac5b2e4bb33f8a1c92128e)*/
public class main{
  public static void main(String[] args){
    protocolToBuffer classObj = new protocolToBuffer(0,new writer[1],new deserializeCallback[1],null);
    runnablesContainer runnables = classObj.getRunnables();
    runnables.launch(true);
    
  }
}
