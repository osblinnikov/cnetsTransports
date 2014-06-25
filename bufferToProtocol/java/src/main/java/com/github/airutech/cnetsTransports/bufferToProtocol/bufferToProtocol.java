
package com.github.airutech.cnetsTransports.bufferToProtocol;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnets.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
public class bufferToProtocol implements RunnableStoppable{
  String[] publishedBuffersNames;reader[] readers;serializeStreamCallback[] callbacks;int bufferIndexOffset;int maxNodesCount;writer w0;reader r0;reader r1;reader rSelect;selector readersSelector;
  
  public bufferToProtocol(String[] publishedBuffersNames,reader[] readers,serializeStreamCallback[] callbacks,int bufferIndexOffset,int maxNodesCount,writer w0,reader r0,reader r1){
    this.publishedBuffersNames = publishedBuffersNames;
    this.readers = readers;
    this.callbacks = callbacks;
    this.bufferIndexOffset = bufferIndexOffset;
    this.maxNodesCount = maxNodesCount;
    this.w0 = w0;
    this.r0 = r0;
    this.r1 = r1;
    reader[] arrReaders = new reader[2 + readers.length];
    arrReaders[0] = r0;
    arrReaders[1] = r1;
    int totalLength = 2;
    for(int i=0;i<readers.length; i++){
      arrReaders[totalLength + i] = readers[i];
    }
    this.readersSelector = new selector(arrReaders);
    this.rSelect = readersSelector.getReader(0,-1);
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
/*[[[end]]] (checksum: cbcd253d29938a6880ec052c40d9d4e5)*/

  private long timeStart;
  private bufferIndexOfNode[] nodes = null;
  cnetsProtocol writeProtocol = null;
  long[] bunchIds;

  private void onKernels() {

  }

  private void onCreate(){
    timeStart = System.currentTimeMillis()/1000;
    if(readers != null && callbacks != null){
      if(readers.length != callbacks.length){
        System.err.println("bufferToProtocol: length of callbacks and readers doesn't match");
      }
      bunchIds = new long[readers.length];
      for(int i=0;i<bunchIds.length;i++){
        bunchIds[i] = 0;
      }
      nodes = new bufferIndexOfNode[maxNodesCount*readers.length];
      for(int i=0;i<nodes.length;i++){
        nodes[i] = new bufferIndexOfNode();
      }
      for(int i=0; i<maxNodesCount; i++){
        for(int bufferIndx=0; bufferIndx<readers.length; bufferIndx++) {
          bufferIndexOfNode node = nodes[i * readers.length + bufferIndx];
          node.setR0(readers[bufferIndx]);
          node.setDstBufferIndex(-1);
          node.setConnected(false);
          /*TODO: need to think what do we need here, subscribers or publishers*/
          System.out.printf("offset %d, length %d index %d readersLen %d\n",bufferIndexOffset,publishedBuffersNames.length, bufferIndexOffset+bufferIndx, readers.length);
          node.setPublishedName(publishedBuffersNames[bufferIndexOffset + bufferIndx]);
        }
      }
    }
  }

  @Override
  public void onStart(){

  }

  @Override
  public void run() {
    Thread.currentThread().setName("bufferToProtocol");

    bufferReadData r = rSelect.readNextWithMeta(-1);
    if (r.getData() == null) {return;}

    switch ((int) r.getNested_buffer_id()) {
      case 0:
        processStatus((connectionStatus) r.getData());
        break;
      case 1:
        processRepositoryUpdate((nodeRepositoryProtocol) r.getData());
        break;
      default:
        processBufferObject(r.getData(), (int)r.getNested_buffer_id() - 2);/*shift -2 for compensation of first two readers items*/
        break;
    }
    rSelect.readFinished();
  }

  private void processRepositoryUpdate(nodeRepositoryProtocol update) {
    /*we store all nodes but not all buffers, only our own buffers*/
    int internalNodeIndex = (update.nodeId%maxNodesCount);//%protocolToBuffersGridSize;
    String[] names = update.bufferNames;
    /*searching locally names equal to remote buffer names*/
    for(int i=0; i<names.length; i++){
      for(int bufferIndx=0; bufferIndx<readers.length; bufferIndx++){
        bufferIndexOfNode node = nodes[internalNodeIndex * readers.length + bufferIndx];
        if(node.getPublishedName().equals(names[i])){
          //tryToFinishWriting(node);
          node.setDstBufferIndex(i);
          node.setConnected(true);
          break;
        }
      }
    }
  }

  private void processStatus(connectionStatus data) {
    /*finish processes with the buffers*/
    int internalNodeIndex = (data.getId()%maxNodesCount);//%protocolToBuffersGridSize;
    for (int bufferIndx = 0; bufferIndx < readers.length; bufferIndx++) {
      bufferIndexOfNode node = nodes[internalNodeIndex * readers.length + bufferIndx];
      if(data.isOn() && !node.isConnected()) {
        /*if it was not connected, need to reset buffers indexes*/
        node.setDstBufferIndex(-1);
      }
      node.setConnected(data.isOn());
    }
  }

  void processBufferObject(Object bufferObj, int localBufferIndex){
    long packet = 0;
    long packets_count = 1;
    boolean isLastPacket;
    boolean isAllowedToSend;
    do{
      while(writeProtocol == null) {
        writeProtocol = (cnetsProtocol) w0.writeNext(-1);
      }
      /*** initialization logic ***/
      writeProtocol.setTimeStart(timeStart);
      writeProtocol.setBufferIndex(bufferIndexOffset + localBufferIndex);
      writeProtocol.setBunchId(bunchIds[localBufferIndex]++);
      if(writeProtocol.getBunchId() < 0){writeProtocol.setBunchId(writeProtocol.getBunchId() - Long.MIN_VALUE);}
      /******/

      /*** packets logic: callback should increase packet number, when needed ***/
      do{
        writeProtocol.reserveForHeader();
        writeProtocol.setPacket(packet);
        writeProtocol.setPackets_grid_size(packets_count);
        isLastPacket = callbacks[localBufferIndex].serializeNext(bufferObj, writeProtocol);
        packets_count = writeProtocol.getPackets_grid_size();
        packet = writeProtocol.getPacket();
        isAllowedToSend = writeProtocol.isPublished() || isAllowedToSend(writeProtocol.getNodeUniqueIds(),localBufferIndex);
      }while(!isAllowedToSend && !isLastPacket);
      /****/
      if(isAllowedToSend) {
        w0.incrementBytesCounter(writeProtocol.getData().position() - cnetsProtocol.fullSize());//length without header
        w0.writeFinished();
        /*to get yet another buffer for writing*/
        writeProtocol = null;
      }
    }while(!isLastPacket);
  }

  private boolean isAllowedToSend(int[] nodeUniqueIds, int localBufferIndex) {
    if(nodeUniqueIds == null){return true;}
    for(int i=0; i<nodeUniqueIds.length; i++) {
      if(nodeUniqueIds[i] < 0){return false;}
      int nodeIndex = nodeUniqueIds[i] % maxNodesCount;
      bufferIndexOfNode node = nodes[nodeIndex * readers.length + localBufferIndex];
      if (node.isConnected() && node.getDstBufferIndex() >= 0) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void onStop(){

  }

}

