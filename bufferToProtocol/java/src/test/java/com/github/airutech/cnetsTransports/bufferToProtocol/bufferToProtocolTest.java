
package com.github.airutech.cnetsTransports.bufferToProtocol;
import org.junit.Test;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnets.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 855ae557614ad6a8c239e2545bed02d3)*/
public class bufferToProtocolTest {
  @Test
  public void bufferToProtocolTest(){
    bufferToProtocol classObj = new bufferToProtocol(new String[1],new reader[1],new serializeStreamCallback[1],0,0,null,null,null);
  }
}

