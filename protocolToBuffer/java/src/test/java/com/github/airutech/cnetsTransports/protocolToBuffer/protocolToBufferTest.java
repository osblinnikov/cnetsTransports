
package com.github.airutech.cnetsTransports.protocolToBuffer;
import com.github.airutech.cnetsTransports.types.cnetsProtocolBinary;
import org.junit.Test;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 6a1822acecac5b2e4bb33f8a1c92128e) */
public class protocolToBufferTest {
  @Test
  public void protocolToBufferTest(){

    int bufSizes = 1000;
/*Buffer for Data*/
    Object buffers[] = new Object[bufSizes];
    for(int i=0; i<buffers.length; i++){
      buffers[i] = new IntBoxer(10);
    }
    long timeout_milisec = 1000;
    long uniqueId = 1;
    int readers_grid_size = 1;
    mapBuffer m = new mapBuffer(buffers, timeout_milisec, uniqueId, readers_grid_size);
    reader r0 = m.getReader(0);
    final writer w0 = m.getWriter(0);

/*Buffer for protocol**/
    Object buffersProtocol[] = new Object[bufSizes];
    for(int i=0; i<buffersProtocol.length; i++){
      buffersProtocol[i] = new cnetsProtocolBinary();
    }
    long timeout_milisecProtocol = 1000;
    long uniqueIdProtocol = 2;
    int readers_grid_sizeProtocol = 1;
    mapBuffer mProtocol = new mapBuffer(buffersProtocol, timeout_milisecProtocol, uniqueIdProtocol, readers_grid_sizeProtocol);
    reader pr0 = mProtocol.getReader(0);
    final writer pw0 = mProtocol.getWriter(0);

/*Initialize Kernels**/
    protocolWriter writerKernel = new protocolWriter(uniqueId, pw0);
    protocolToBuffer classObj = new protocolToBuffer(1,new writer[]{w0},new deserializeCallback[]{new deserializeCallbackIntBoxer()},pr0);
    intBoxerReader readerKernel = new intBoxerReader(r0);

/*running kernels*/
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[3];
    arrContainers[0] = writerKernel.getRunnables();
    arrContainers[1] = classObj.getRunnables();
    arrContainers[2] = readerKernel.getRunnables();
    runnables.setContainers(arrContainers);
    runnables.launch(false);

/*waiting*/
    try {
      Thread.sleep(3000);
      runnables.stop();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}

