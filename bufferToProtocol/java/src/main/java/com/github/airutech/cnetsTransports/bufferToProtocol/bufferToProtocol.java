
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
public class bufferToProtocol implements RunnableStoppable{
  reader[] readers;serializeStreamCallback[] callbacks;int bufferIndexOffset;writer w0;
  
  public bufferToProtocol(reader[] readers,serializeStreamCallback[] callbacks,int bufferIndexOffset,writer w0){
    this.readers = readers;
    this.callbacks = callbacks;
    this.bufferIndexOffset = bufferIndexOffset;
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
/*[[[end]]] (checksum: 310516faac102f95fd829d3a8985afd6) */
  private final static long timeStart = System.currentTimeMillis()/1000;
  private bufferReadData readData = null;

  cnetsProtocol writeProtocol = null;
  long[] bunchIds;

  selector readersSelectorFromArr;
  reader rSelectFromArr;

  private void onKernels() {

  }

  private void onCreate(){
    if(readers != null && callbacks != null){
      if(readers.length != callbacks.length){System.err.println("bufferToProtocol: length of callbacks and readers doesn't match");}
      this.readersSelectorFromArr = new selector(readers);
      this.rSelectFromArr = readersSelectorFromArr.getReader(0,-1);
      bunchIds = new long[readers.length];
      for(int i=0;i<bunchIds.length;i++){
        bunchIds[i] = 0;
      }

    }
  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    Thread.currentThread().setName("bufferToProtocol");

    if(writeProtocol == null) {
      writeProtocol = (cnetsProtocol) w0.writeNext(true);
      if (writeProtocol == null) {
        return;
      }
    }

    readData = rSelectFromArr.readNextWithMeta(true);
    if (readData.getData() == null) {return;}

    long packet = 0;
    long packets_count = 1;
    while(packet < packets_count){
      /*** initialization logic ***/
      writeProtocol.setTimeStart(timeStart);
      writeProtocol.setBufferIndex(bufferIndexOffset + readData.getNested_buffer_id());
      writeProtocol.setBunchId(bunchIds[(int)readData.getNested_buffer_id()]++);
      if(writeProtocol.getBunchId() < 0){writeProtocol.setBunchId(writeProtocol.getBunchId() - Long.MIN_VALUE);}
      writeProtocol.reserveForHeader();
      /******/

      /*** packets logic: callback should increase packet number, when needed ***/
      writeProtocol.setPacket(packet);
      writeProtocol.setPackets_grid_size(packets_count);
      if(!callbacks[(int) readData.getNested_buffer_id()].serializeNext(readData.getData(), writeProtocol)){ break; }
      packets_count = writeProtocol.getPackets_grid_size();
      packet = writeProtocol.getPacket();
      /****/
      w0.writeFinished();

      /*trying to get one another buffer for writing*/
      writeProtocol = null;
      while(writeProtocol == null) {
        writeProtocol = (cnetsProtocol) w0.writeNext(true);
      }
    };

    rSelectFromArr.readFinished();
  }

  @Override
  public void onStop(){

  }

}

