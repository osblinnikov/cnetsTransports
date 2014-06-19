
package com.github.airutech.cnetsTransports.protocolToBuffer;
import org.junit.Test;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnets.types.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 0bbbb2f4d23f192792f45a9d3e00db1c)*/
public class protocolToBufferTest {
  @Test
  public void protocolToBufferTest(){
    protocolToBuffer classObj = new protocolToBuffer(new String[1],new writer[1],new deserializeStreamCallback[1],0,0,0,null,null,null);
  }
}

