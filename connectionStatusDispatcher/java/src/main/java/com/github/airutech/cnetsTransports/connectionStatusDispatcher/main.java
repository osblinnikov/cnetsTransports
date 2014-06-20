
package com.github.airutech.cnetsTransports.connectionStatusDispatcher;
import com.github.airutech.cnetsTransports.connectionStatusDispatcher.connectionStatusDispatcher;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnetsTransports.types.*;
/*[[[end]]] (checksum: 55bb89016d67f065d23f5f005910a668)*/
public class main{
  public static void main(String[] args){
    connectionStatusDispatcher classObj = new connectionStatusDispatcher(new writer[1],null,null,null);
  }
}
