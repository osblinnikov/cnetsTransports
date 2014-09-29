
package com.github.airutech.cnetsTransports.connectionStatusDispatcher;
import com.github.airutech.cnetsTransports.connectionStatusDispatcher.connectionStatusDispatcher;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: c7e257df165e4b8b959105ac4db9d1a7)*/
public class main{
  public static void main(String[] args){
    connectionStatusDispatcher classObj = new connectionStatusDispatcher(new writer[1],0,null,null,null);
  }
}
