
package com.github.airutech.cnetsTransports.webSocketConnectorExample.connector;
import org.junit.Test;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnetsTransports.protocolToBuffer.*;
import com.github.airutech.cnetsTransports.bufferToProtocol.*;
import com.github.airutech.cnetsTransports.webSocket.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.types.*;
import com.github.airutech.cnetsTransports.connectionStatusDispatcher.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 5354f407a804ab0ed5d0d1dc8d16d329)*/
public class connectorTest {
  @Test
  public void connectorTest(){
    connector classObj = new connector(new String[1],new String[1],new writer[1],new reader[1],new deserializeStreamCallback[1],new serializeStreamCallback[1],null,0,null,null);
  }
}

