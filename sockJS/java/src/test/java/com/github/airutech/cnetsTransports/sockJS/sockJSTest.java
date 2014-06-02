
package com.github.airutech.cnetsTransports.sockJS;
import org.junit.Test;
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
public class sockJSTest {
  @Test
  public void sockJSTest(){
    sockJS classObj = new sockJS(new Long[1],0,0,null,0,null,null,null,null);
    runnablesContainer runnables = classObj.getRunnables();
    runnables.launch(false);
    runnables.stop();
    
  }
}

