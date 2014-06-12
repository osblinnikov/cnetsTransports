
package com.github.airutech.cnetsTransports.protocolToBuffer;

import com.github.airutech.cnets.types.bufferReadData;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.types.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
public class protocolToBuffer implements RunnableStoppable{
  writer[] writers;deserializeStreamCallback[] callbacks;int protocolToBuffersGridSize;int maxNodesCount;writer w0;reader r0;reader r1;reader r2;reader rSelect;selector readersSelector;
  
  public protocolToBuffer(writer[] writers,deserializeStreamCallback[] callbacks,int protocolToBuffersGridSize,int maxNodesCount,writer w0,reader r0,reader r1,reader r2){
    this.writers = writers;
    this.callbacks = callbacks;
    this.protocolToBuffersGridSize = protocolToBuffersGridSize;
    this.maxNodesCount = maxNodesCount;
    this.w0 = w0;
    this.r0 = r0;
    this.r1 = r1;
    this.r2 = r2;
    reader[] arrReaders = new reader[3];
    arrReaders[0] = r0;
    arrReaders[1] = r1;
    arrReaders[2] = r2;
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
/*[[[end]]] (checksum: 92110f05990c43c998bfa9f8b72e7dfb) */

  private void onKernels() {

  }

  int nodesStored = 0;
  bufferOfNode[] nodes = null;
  int nodesOnline = 0;

  private void onCreate(){
    if(protocolToBuffersGridSize <= 0){return;}
    nodesStored = (int)Math.ceil(maxNodesCount/protocolToBuffersGridSize);
    nodes = new bufferOfNode[nodesStored * writers.length];
    for (int i = 0; i < nodesStored; i++) {
      for (int bufferIndx = 0; bufferIndx < writers.length; bufferIndx++) {
        bufferOfNode node = nodes[i * writers.length + bufferIndx];
        node = new bufferOfNode();
        node.setInit(false);
        node.setBufferObj(null);
        node.setW0(writers[bufferIndx].copy());
        node.setCallback(callbacks[bufferIndx]);
        node.setBunchId(0);
        node.setTimeStart(0);
        node.setDstBufferIndex(-1);
        node.setNodeId(-1);
        node.setOwnBufferIndex(bufferIndx);
      }
    }
  }

  @Override
  public void onStart(){

  }

  @Override
  public void run() {
    Thread.currentThread().setName("protocolToBuffer");

    /*** get next structure for reading ****/
    bufferReadData r = rSelect.readNextWithMeta(true);
    if(r.getData()==null) {
      checkTimedOutBuffers();
      return;
    }

    switch ((int) r.getNested_buffer_id()) {
      case 0:
        /*todo: make sure received status belongs to me, because it could be addressed to another instance, responsible for the node*/
        processStatus((connectionStatus) r.getData());
        break;
      case 1:
        processRepositoryUpdate((nodeRepositoryProtocol) r.getData());
        break;
      case 2:
        if(((cnetsProtocol) r.getData()).getBufferIndex() == 0){
          receiveRepositoryUpdate((cnetsProtocol) r.getData());
        }else{
          processData((cnetsProtocol) r.getData());
        }
        break;
    }
    rSelect.readFinished();
  }

  private void checkTimedOutBuffers() {
    if(nodesOnline <= 0){return;}
    for (int i = 0; i < nodesStored; i++) {
      for (int bufferIndx = 0; bufferIndx < writers.length; bufferIndx++) {
        bufferOfNode node = nodes[i * writers.length + bufferIndx];
        if(node.getBufferObj()!=null){
          tryToFinishWriting(node);
        }
      }
    }
  }

  private void processRepositoryUpdate(nodeRepositoryProtocol update){
    int internalNodeIndex = (update.getDestinationUniqueNodeId()%maxNodesCount)%protocolToBuffersGridSize;
    String[] names = update.getBufferNames();
    /*searching locally names equal to remote buffer names*/
    for(int i=0; i<names.length; i++){
      for(int bufferIndx=0; bufferIndx<writers.length; bufferIndx++){
        bufferOfNode node = nodes[internalNodeIndex * writers.length + bufferIndx];
        if(node.getW0().uniqueId().equals(names[i])){
          tryToFinishWriting(node);
          node.setDstBufferIndex(i);
          break;
        }
      }
    }
  }

  private void receiveRepositoryUpdate(cnetsProtocol repositoryUpdateProtocol) {
    int internalNodeIndex = (repositoryUpdateProtocol.getNodeUniqueIds()[0]%maxNodesCount)%protocolToBuffersGridSize;
    int bufferIndx = 0;
    bufferOfNode node = nodes[internalNodeIndex * writers.length + bufferIndx];
    deserializeForNode(repositoryUpdateProtocol, node);
  }

  private void processStatus(connectionStatus data) {
    /*finish processes with the buffers*/
    int internalNodeIndex = (data.getId()%maxNodesCount)%protocolToBuffersGridSize;
    for (int bufferIndx = 0; bufferIndx < writers.length; bufferIndx++) {
      bufferOfNode node = nodes[internalNodeIndex * writers.length + bufferIndx];
      tryToFinishWriting(node);
    }

    if(data.isOn()) {
      nodeRepositoryProtocol protocol = null;
      while(protocol == null) {
        protocol = (nodeRepositoryProtocol) w0.writeNext(true);
      }
      protocol.setDestinationUniqueNodeId(data.getId());
      w0.writeFinished();
    }
  }

  private void processData(cnetsProtocol currentlyReceivedProtocol){
    if (currentlyReceivedProtocol == null) {return;}
    
    /* trying to find the local index for the received index */
    if(currentlyReceivedProtocol.getNodeUniqueIds()[0] < 0){
      System.err.printf("protocolToBuffer: processData: incoming nodeUid=%d is out of allowed range [0, %d)\n",currentlyReceivedProtocol.getNodeUniqueIds()[0],maxNodesCount);
      return;
    }

    int internalNodeIndex = (currentlyReceivedProtocol.getNodeUniqueIds()[0]%maxNodesCount)%protocolToBuffersGridSize;

    bufferOfNode node = null;
    /*searching for the buffer for arrived data from the node: internalNodeIndex*/
    for (int bufferIndx = 0; bufferIndx < writers.length; bufferIndx++) {
      node = nodes[internalNodeIndex * writers.length + bufferIndx];
      /*compare received dst buffer index and our local dst buffer index for the node*/
      if(currentlyReceivedProtocol.getBufferIndex() == node.getDstBufferIndex()){
        break;
      }
      node = null;
    }
    if(node == null){
      System.err.printf("protocolToBuffer: processData: incoming data, has buffer id which is not correspond to any buffer in repository\n");
      return;
    }
    deserializeForNode(currentlyReceivedProtocol, node);
  }

  void deserializeForNode(cnetsProtocol currentlyReceivedProtocol, bufferOfNode node){
    currentlyReceivedProtocol.setBufferIndex(node.getOwnBufferIndex());

    /*check the node reboot*/
    if (!node.isInit()
        || node.getTimeStart() < currentlyReceivedProtocol.getTimeStart()
        || Math.abs(node.getTimeStart() - currentlyReceivedProtocol.getTimeStart()) > Long.MAX_VALUE / 2
        ){
      /*reboot detected*/
      tryToFinishWriting(node);
      node.setInit(true);
      node.setTimeStart(currentlyReceivedProtocol.getTimeStart());
      node.setBunchId(currentlyReceivedProtocol.getBunchId());
    }
    /*check if the bunch from the past*/
    if (node.getTimeStart() != currentlyReceivedProtocol.getTimeStart() || node.getBunchId() > currentlyReceivedProtocol.getBunchId()) {
      System.out.printf("nodesRepository: processData: bunch from the past\n");
      return;
    }
    node.setBunchId(currentlyReceivedProtocol.getBunchId());

    /*get next buffer for writing*/
    while(node.getBufferObj() == null) {
      node.setBufferObj(node.getW0().writeNext(true));
    }

    /*Stateful deserializaion to the object provided*/
    boolean isLastPacket = node.getCallback().deserializeNext(node.getBufferObj(), currentlyReceivedProtocol);
    if(isLastPacket){
      tryToFinishWriting(node);
    }
  }

  void tryToFinishWriting(bufferOfNode node){
    if(node.getBufferObj()!=null){
      node.getW0().writeFinished();
      node.setBufferObj(null);
    }
  }

  @Override
  public void onStop() {

  }

}

