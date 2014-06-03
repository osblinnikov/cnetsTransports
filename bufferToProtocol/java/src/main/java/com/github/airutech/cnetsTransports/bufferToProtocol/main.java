
package com.github.airutech.cnetsTransports.bufferToProtocol;
import com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnets.nodesRepository.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 96fc2e709d97400b8d6067bf88379835)*/
public class main{
  public static void main(String[] args){
    bufferToProtocol classObj = new bufferToProtocol(new reader[1],new serializeCallback[1],null,null);
    runnablesContainer runnables = classObj.getRunnables();
    runnables.launch(true);
    
  }
}
