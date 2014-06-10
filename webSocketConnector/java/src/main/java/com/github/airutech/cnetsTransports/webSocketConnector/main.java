
package com.github.airutech.cnetsTransports.webSocketConnector;
import com.github.airutech.cnetsTransports.webSocketConnector.webSocketConnector;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.bufferToProtocol.*;
import com.github.airutech.cnetsTransports.protocolToBuffer.*;
import com.github.airutech.cnetsTransports.webSocket.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnetsTransports.msgpack.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.types.*;
/*[[[end]]] (checksum: 93450a853b33a41f6bf07fb871643028)*/
public class main{
  public static void main(String[] args){
    webSocketConnector classObj = new webSocketConnector();
    runnablesContainer runnables = classObj.getRunnables();
    runnables.launch(true);
    
  }
}
