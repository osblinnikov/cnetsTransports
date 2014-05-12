
package com.github.airutech.cnetsTransports.bufferToProtocol;


import java.nio.ByteBuffer;

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
public class bufferToProtocol implements RunnableStoppable{
  reader[] readers;serializeCallback[] callbacks;writer w0;
  
  public bufferToProtocol(reader[] readers,serializeCallback[] callbacks,writer w0){
    this.readers = readers;
    this.callbacks = callbacks;
    this.w0 = w0;
    onCreate();
    initialize();
  }

  private void initialize(){
    
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnables.setCore(this);
    return runnables;
  }
/*[[[end]]] (checksum: 7d6df14fb2a8978dda7eaf658b6e8d20) */
  private final static long timeStart = System.currentTimeMillis()/1000;
  private bufferReadData readData = null;
  cnetsProtocol currentlySendingProtocol = new cnetsProtocol();
  static Long bunchId = new Long(0);
  int packet_grid_id = 0;
  int packet_grid_size = 0;
  cnetsProtocolBinary writeProtocol = null;

  selector readersSelectorFromArr;
  reader rSelectFromArr;

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
    if(writeProtocol == null) {
      writeProtocol = (cnetsProtocolBinary) w0.writeNext(true);
      if(writeProtocol == null) {return;}
    }
    if(readData == null || readData.getData() == null) {
      readData = rSelectFromArr.readNextWithMeta(true);
      if (readData == null || readData.getData() == null) {return;}
      synchronized (bunchId) {
        currentlySendingProtocol.setBunchId(bunchId++);
        if(bunchId<0){bunchId = 0L;}
      }
      packet_grid_id = packet_grid_size = 0;
      currentlySendingProtocol.setBufferId(readers[(int)readData.getNested_buffer_id()].uniqueId());
      currentlySendingProtocol.setTimeStart(timeStart);
    }
    currentlySendingProtocol.setNodeId(-1);
    currentlySendingProtocol.setPacket(packet_grid_id++);

    int data_size = callbacks[(int) readData.getNested_buffer_id()].serialize(writeProtocol.getData(), readData.getData(), currentlySendingProtocol);
    if(data_size>0) {
      writeProtocol.setData_size(data_size);
      writeProtocol.setBufferId(currentlySendingProtocol.getBufferId());
      writeProtocol.setNodeId(currentlySendingProtocol.getNodeId());
      w0.writeFinished();
      writeProtocol = null;
    }else{
      rSelectFromArr.readFinished();
      readData = null;
    }
  }

  @Override
  public void onStop(){

  }

}

