
package com.github.airutech.cnetsTransports.webSocket;
import com.github.airutech.cnetsTransports.webSocket.webSocket;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 2efeebb03bb75b35280c2db6e0fdfea7)*/
public class main{
  public static void main(String[] args){
    webSocket classObj = new webSocket(0,null,0,null,null,null,null,null);
    runnablesContainer runnables = classObj.getRunnables();
    runnables.launch(true);
    
  }
}
