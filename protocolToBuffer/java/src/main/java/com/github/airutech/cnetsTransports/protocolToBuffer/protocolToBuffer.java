
package com.github.airutech.cnetsTransports.protocolToBuffer;

import com.github.airutech.cnets.types.bufferReadData;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.types.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
public class protocolToBuffer implements RunnableStoppable{
  String[] subscribedBuffersNames;writer[] writers;deserializeStreamCallback[] callbacks;int nodesIndexOffset;int protocolToBuffersGridSize;int maxNodesCount;reader r0;reader r1;reader r2;reader rSelect;selector readersSelector;
  
  public protocolToBuffer(String[] subscribedBuffersNames,writer[] writers,deserializeStreamCallback[] callbacks,int nodesIndexOffset,int protocolToBuffersGridSize,int maxNodesCount,reader r0,reader r1,reader r2){
    this.subscribedBuffersNames = subscribedBuffersNames;
    this.writers = writers;
    this.callbacks = callbacks;
    this.nodesIndexOffset = nodesIndexOffset;
    this.protocolToBuffersGridSize = protocolToBuffersGridSize;
    this.maxNodesCount = maxNodesCount;
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
/*[[[end]]] (checksum: 114685977d7b9c06756543a08469225e) */

  private void onKernels() {

  }

  int nodesStored = 0;
  bufferOfNode[] nodes = null;
  int nodesOnline = 0;

  private void onCreate(){
    if(writers==null || subscribedBuffersNames==null){return;}
    assert writers.length == subscribedBuffersNames.length;
    if(protocolToBuffersGridSize <= 0){return;}

    nodesStored = (int)Math.floor((double)maxNodesCount/(double)protocolToBuffersGridSize);
    if(nodesIndexOffset+nodesStored*2 > maxNodesCount){/*in case this is last nodes processor, we need to set it to ceil value*/
      nodesStored = (int)Math.ceil((double)maxNodesCount/(double)protocolToBuffersGridSize);
    }

    nodes = new bufferOfNode[nodesStored * writers.length];
    for(int i=0; i<nodes.length; i++){
      nodes[i] = new bufferOfNode();
    }
    for (int i = 0; i < nodesStored; i++) {
      for (int bufferIndx = 0; bufferIndx < writers.length; bufferIndx++) {
        bufferOfNode node = nodes[i * writers.length + bufferIndx];
        node.setInit(false);
        node.setBufferObj(null);
        if(writers[bufferIndx]!=null) {
          node.setW0(writers[bufferIndx].copy());
        }
        node.setCallback(callbacks[bufferIndx]);
        node.setBunchId(0);
        node.setTimeStart(0);
        node.setDstBufferIndex(-1);
        node.setNodeId(-1);
        node.setSubscribedName(subscribedBuffersNames[bufferIndx]);
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
    bufferReadData r = rSelect.readNextWithMeta(-1);
    if(r.getData()==null) {
      checkTimedOutBuffers();
      return;
    }
    try {
      switch ((int) r.getNested_buffer_id()) {
        case 0:
          processStatus((connectionStatus) r.getData());
          break;
        case 1:
          processRepositoryUpdate((nodeRepositoryProtocol) r.getData());
          break;
        case 2:
          processData((cnetsProtocol) r.getData());
          break;
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    rSelect.readFinished();
  }

  /*TODO: make sure that this function will be called often enough, and timestamp is validated inside the function*/
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

  private void processStatus(connectionStatus data) {
    /*finish processes with the buffers*/
    int id = data.getId();
    int internalNodeIndex = (id%maxNodesCount);
    /*filter node index, as not belonged to me, because I only responsible for the subset of nodes*/
    if(internalNodeIndex<nodesIndexOffset || internalNodeIndex>=nodesIndexOffset+nodesStored){return;}
    internalNodeIndex = internalNodeIndex%protocolToBuffersGridSize;
    for (int bufferIndx = 0; bufferIndx < writers.length; bufferIndx++) {
      bufferOfNode node = nodes[internalNodeIndex * writers.length + bufferIndx];
      tryToFinishWriting(node);
      if(!data.isOn()){
        node.setDstBufferIndex(-1);
      }
    }
  }

//  private void sendRepositoryUpdateToDestination(int id){
//    nodeRepositoryProtocol protocol = null;
//    while(protocol == null) {
//      protocol = (nodeRepositoryProtocol) w0.writeNext(-1);
//    }
//    protocol.setDestinationUniqueNodeId(id);
//    w0.writeFinished();
//  }

  private void processData(cnetsProtocol currentlyReceivedProtocol){
    if (currentlyReceivedProtocol == null) {return;}
    System.out.println("protocolToBuffer: processData\n");
    /* trying to find the local index for the received index */
    if(currentlyReceivedProtocol.getNodeUniqueIds()[0] < 0){
      System.err.printf("protocolToBuffer: processData: incoming nodeUid=%d is out of allowed range [0, %d)\n",currentlyReceivedProtocol.getNodeUniqueIds()[0],maxNodesCount);
      return;
    }

    int id = currentlyReceivedProtocol.getNodeUniqueIds()[0];
    int internalNodeIndex = (id%maxNodesCount);
    if(internalNodeIndex<nodesIndexOffset || internalNodeIndex>=nodesIndexOffset+nodesStored){return;}
    internalNodeIndex = internalNodeIndex%nodesStored;/*we select subset of nodes, which length is nodesStored*/

    bufferOfNode node = null;
    /*searching for the buffer for arrived data from the node: internalNodeIndex*/
    for (int bufferIndx = 0; bufferIndx < writers.length; bufferIndx++) {
      node = nodes[internalNodeIndex * writers.length + bufferIndx];
      /*compare received dst buffer index and our local dst buffer index for the node*/
      if(currentlyReceivedProtocol.getBufferIndex() == 0 || currentlyReceivedProtocol.getBufferIndex() == node.getDstBufferIndex()){
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
      System.out.printf("protocolToBuffer: processData: bunch from the past or reboot detected\n");
      return;
    }
    /*update local bunches hash*/
    node.setBunchId(currentlyReceivedProtocol.getBunchId());

    /*get next buffer for writing*/
    while(node.getBufferObj() == null) {
      node.setBufferObj(node.getW0().writeNext(-1));
    }

    /*send bytes statistics*/
    r2.incrementBytesCounter(currentlyReceivedProtocol.getData().remaining());

    System.out.println(".protocolToBuffer recv from "+node.getDstBufferIndex());
    /*Stateful deserialization into the provided object node.getBufferObj()*/
    boolean isLastPacket = node.getCallback().deserializeNext(node.getBufferObj(), currentlyReceivedProtocol);
    if(isLastPacket){
      tryToFinishWriting(node);
    }
  }

  private void processRepositoryUpdate(nodeRepositoryProtocol update){
    System.out.printf("protocolToBuffer: processRepositoryUpdate\n");
    int id = update.nodeId;
    int internalNodeIndex = (id%maxNodesCount);
    if(internalNodeIndex<nodesIndexOffset || internalNodeIndex>=nodesIndexOffset+nodesStored){return;}
    internalNodeIndex = internalNodeIndex%nodesStored;
    String[] names = update.publishedNames;
    /*searching locally names equal to remote buffer names*/
    for(int i=0; i<names.length; i++){
      for(int bufferIndx=0; bufferIndx<writers.length; bufferIndx++){
        bufferOfNode node = nodes[internalNodeIndex * writers.length + bufferIndx];
        if(node.getSubscribedName().equals(names[i])){
          tryToFinishWriting(node);
          node.setDstBufferIndex(i);
          node.setNodeId(id);
          break;
        }
      }
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

