
package com.github.airutech.cnetsTransports.protocolToBuffer;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnets.nodesRepository.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 13a60ac3189382264aa099a4caadb57a)*/
public class main{
  public static void main(String[] args){
    protocolToBuffer classObj = new protocolToBuffer(new writer[1],new deserializeCallback[1],null,null);
    runnablesContainer runnables = classObj.getRunnables();
    runnables.launch(true);
    
  }
}
