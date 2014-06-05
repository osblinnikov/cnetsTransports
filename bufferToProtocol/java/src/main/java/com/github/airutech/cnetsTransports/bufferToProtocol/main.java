
package com.github.airutech.cnetsTransports.bufferToProtocol;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: a792ebfae3564bd0cebb788d5f307ed9)*/
public class main{
  public static void main(String[] args){
    bufferToProtocol classObj = new bufferToProtocol(new reader[1],new serializeStreamCallback[1],0,null);
    runnablesContainer runnables = classObj.getRunnables();
    runnables.launch(true);
    
  }
}
