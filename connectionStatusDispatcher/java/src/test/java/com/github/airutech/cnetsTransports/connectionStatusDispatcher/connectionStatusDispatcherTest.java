
package com.github.airutech.cnetsTransports.connectionStatusDispatcher;
import org.junit.Test;
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
public class connectionStatusDispatcherTest {
  @Test
  public void connectionStatusDispatcherTest(){
    connectionStatusDispatcher classObj = new connectionStatusDispatcher(new writer[1],0,null,null,null);
  }
}

