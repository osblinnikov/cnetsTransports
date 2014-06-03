
package com.github.airutech.cnetsTransports.bufferToProtocol;


import java.io.IOException;
import java.nio.ByteBuffer;
import com.github.airutech.cnetsTransports.types.types;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.nodesRepository.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
public class bufferToProtocol implements RunnableStoppable{
  reader[] readers;serializeCallback[] callbacks;nodesRepository nodesBuffers;writer w0;
  
  public bufferToProtocol(reader[] readers,serializeCallback[] callbacks,nodesRepository nodesBuffers,writer w0){
    this.readers = readers;
    this.callbacks = callbacks;
    this.nodesBuffers = nodesBuffers;
    this.w0 = w0;
    onCreate();
    initialize();
  }

  private void initialize(){
    
    onKernels();
    
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnables.setCore(this);
    return runnables;
  }
/*[[[end]]] (checksum: 322ea343840b50f111fe396e0905d733) */
  private final static long timeStart = System.currentTimeMillis()/1000;
  private bufferReadData readData = null;
  cnetsProtocol currentlySendingProtocol = new cnetsProtocol();
  static Long bunchId = new Long(0);
  int packet_grid_id = 0;
  int packet_grid_size = 0;
  cnetsProtocolBinary writeProtocol = null;

  selector readersSelectorFromArr;
  reader rSelectFromArr;

  private void onKernels() {

  }

  private void onCreate(){
    if(readers!=null && callbacks!=null){
      if(readers.length!=callbacks.length){System.err.println("bufferToProtocol: length of callbacks and readers doesn't match");}
      this.readersSelectorFromArr = new selector(readers);
      this.rSelectFromArr = readersSelectorFromArr.getReader(0,-1);
    }
  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    Thread.currentThread().setName("bufferToProtocol");
    if(writeProtocol == null) {
      writeProtocol = (cnetsProtocolBinary) w0.writeNext(true);
      if(writeProtocol == null) {return;}
    }
    if(readData == null || readData.getData() == null) {
      readData = rSelectFromArr.readNextWithMeta(true);
      if (readData == null || readData.getData() == null) {return;}
      synchronized (bunchId) {
        currentlySendingProtocol.setBunchId(bunchId++);
        currentlySendingProtocol.setBufferIndex(readData.getNested_buffer_id());
        if(bunchId<0){bunchId = 0L;}
      }
      packet_grid_id = packet_grid_size = 0;
      currentlySendingProtocol.setTimeStart(timeStart);
    }
    currentlySendingProtocol.setPacket(packet_grid_id++);

    writeProtocol.getData().clear();
    cnetsProtocol.reserve(writeProtocol.getData());

    writeProtocol.setNodeIdsSize(readData.fillNodes(writeProtocol.getNodeIds()));
    if(nodesBuffers!=null) {
      writeProtocol.setNodeIdsSize(nodesBuffers.getNodesIdsForBuffer(writeProtocol.getNodeIds(), readData.getNested_buffer_id()));
    }

    boolean isLastPacket = callbacks[(int) readData.getNested_buffer_id()].serialize(
        readData.getData(),
        writeProtocol.getData(),
        currentlySendingProtocol
    );

    currentlySendingProtocol.serialize(writeProtocol.getData());

    w0.writeFinished(0,null);
    writeProtocol = null;
    if(isLastPacket){
      rSelectFromArr.readFinished();
      readData = null;
    }
  }

  @Override
  public void onStop(){

  }

}

