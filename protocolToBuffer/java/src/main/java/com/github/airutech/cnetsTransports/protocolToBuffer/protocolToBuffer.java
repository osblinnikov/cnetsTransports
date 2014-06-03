
package com.github.airutech.cnetsTransports.protocolToBuffer;


/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.nodesRepository.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
public class protocolToBuffer implements RunnableStoppable{
  writer[] writers;deserializeCallback[] callbacks;nodesRepository nodesBuffers;reader r0;
  
  public protocolToBuffer(writer[] writers,deserializeCallback[] callbacks,nodesRepository nodesBuffers,reader r0){
    this.writers = writers;
    this.callbacks = callbacks;
    this.nodesBuffers = nodesBuffers;
    this.r0 = r0;
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
/*[[[end]]] (checksum: 8a74a3dc328d009209021776e2252988)  */

  private void onKernels() {

  }

  private void onCreate(){

  }

  @Override
  public void onStart(){

  }

  cnetsProtocol currentlyReceivedProtocol = new cnetsProtocol();
  cnetsProtocolBinary cBin = null;
  @Override
  public void run() {
    Thread.currentThread().setName("protocolToBuffer");
    if(cBin == null) {
      cBin = (cnetsProtocolBinary) r0.readNext(true);
      if (cBin == null || cBin.getData() == null) {
        finishRead();
        return;
      }
    }

    if(!currentlyReceivedProtocol.deserialize(cBin.getData())){
      System.err.printf("protocolToBuffer: currentlyReceivedProtocol.deserialize failed\n");
    }
    if(cBin.getNodeIdsSize() <= 0){
      System.err.printf("protocolToBuffer: cBin.getNodeIdsSize() <= 0 \n");
    }
    bufferOfNode node = nodesBuffers.getNode(currentlyReceivedProtocol.getBufferIndex(), cBin.getNodeIds()[0]);

    node.getLock().lock();
    /*check the node reboot*/
    if (!node.isInit()
        || node.getTimeStart() < currentlyReceivedProtocol.getTimeStart()
        || Math.abs(node.getTimeStart() - currentlyReceivedProtocol.getTimeStart()) > Long.MAX_VALUE / 2
        ) {
      /*reboot detected*/
      finishWrite(node,cBin.getNodeIds()[0]);
      node.setInit(true);
      node.setTimeStart(currentlyReceivedProtocol.getTimeStart());
      node.setBunchId(currentlyReceivedProtocol.getBunchId());
    }
    /*check if the bunch from the past*/
    if (node.getTimeStart() != currentlyReceivedProtocol.getTimeStart() || node.getBunchId() > currentlyReceivedProtocol.getBunchId()) {
      System.out.printf("protocolToBuffer: buffer from the past\n");
      finishRead();
      node.getLock().unlock();
      return;
    }
    /*check if the new bunch received*/
    if(node.getBunchId()<currentlyReceivedProtocol.getBunchId()){
      if(finishWrite(node,cBin.getNodeIds()[0])){
        System.out.printf("protocolToBuffer: bunch %d was not fully received, new bunch is %d\n",node.getBunchId(),currentlyReceivedProtocol.getBunchId());
      }
      node.setBunchId(currentlyReceivedProtocol.getBunchId());
    }
    /*get structure for deserialization*/
    boolean isNewBunch = false;
    if(node.getBufObj() == null){
      node.setBufObj(node.getW0().writeNext(true));
      if(node.getBufObj() == null){
        System.out.printf("protocolToBuffer: writeNext failed\n");
        node.getLock().unlock();
        return;
      }
      isNewBunch = true;
    }
    callbacks[node.nodeId()].deserialize(cBin.getData(), node.getBufObj(), currentlyReceivedProtocol.getPacket(), currentlyReceivedProtocol.getPackets_grid_size(), isNewBunch);
    if(currentlyReceivedProtocol.getPacket() == currentlyReceivedProtocol.getPackets_grid_size() - 1){
      finishWrite(node,cBin.getNodeIds()[0]);
    }
    finishRead();
    node.getLock().unlock();
  }

  private boolean finishWrite(bufferOfNode node, int sourceNode) {
    if(node.getBufObj() != null){
      node.getW0().writeFinished(sourceNode,null);
      node.setBufObj(null);
      return true;
    }
    return false;
  }

  private void finishRead() {
    if(cBin != null && cBin.getData() != null) {
      r0.readFinished();
    }
    cBin = null;
  }

  @Override
  public void onStop() {

  }

}

