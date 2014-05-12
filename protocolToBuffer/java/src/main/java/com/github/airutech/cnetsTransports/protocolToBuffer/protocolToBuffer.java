
package com.github.airutech.cnetsTransports.protocolToBuffer;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


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
public class protocolToBuffer implements RunnableStoppable{
  int nodesCount;writer[] writers;deserializeCallback[] callbacks;reader r0;
  
  public protocolToBuffer(int nodesCount,writer[] writers,deserializeCallback[] callbacks,reader r0){
    this.nodesCount = nodesCount;
    this.writers = writers;
    this.callbacks = callbacks;
    this.r0 = r0;
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
/*[[[end]]] (checksum: 797defe7e3f689d2dcb846b18f44f3be)  */

  private bufferOfNode[] nodes = null;
  private Lock[] locks = null;

  private void onCreate(){
    if(nodesCount>0 && writers!=null && r0 != null){
      locks = new Lock[writers.length];
      nodes = new bufferOfNode[nodesCount*writers.length];

      for(int i=0;i<writers.length;i++){
        locks[i] = new ReentrantLock();
      }

      for (int i = 0; i < nodesCount; i++) {
        for(int bufferIndx=0;bufferIndx<writers.length; bufferIndx++) {
          int nodeIndx = i * writers.length + bufferIndx;
          nodes[nodeIndx] = new bufferOfNode();
          nodes[nodeIndx].setInit(false);
          nodes[nodeIndx].setW0(writers[bufferIndx]);
          nodes[nodeIndx].setLock(locks[bufferIndx]);
          nodes[nodeIndx].setCallback(callbacks[bufferIndx]);
        }
      }

    }
  }

  @Override
  public void onStart(){

  }

  cnetsProtocol currentlyReceivedProtocol = new cnetsProtocol();
  cnetsProtocolBinary cBin = null;
  @Override
  public void run() {
    if(nodes==null){return;}
    if(cBin == null) {
      cBin = (cnetsProtocolBinary) r0.readNext(true);
      if (cBin == null || cBin.getData() == null) {
        finishRead();
        return;
      }
    }

    ByteBuffer buf = currentlyReceivedProtocol.setFromBytes(cBin.getData(), cBin.getData_size());
    int bufferIndx = findBufferIndx(currentlyReceivedProtocol.getBufferId());

    /*check the node in the nodes hash*/
    if (nodesCount <= cBin.getNodeId() || null == buf || bufferIndx < 0 ) {
      System.err.printf("protocolToBuffer: wrong protocol parameters\n");
      System.err.printf("nodesCount: %d, nodeId: %d\n", nodesCount, cBin.getNodeId());
      finishRead();
      return;
    }

    bufferOfNode node = nodes[cBin.getNodeId() * writers.length + bufferIndx];
    node.getLock().lock();
    /*check the node reboot*/
    if (!node.isInit()
        || node.getTimeStart() < currentlyReceivedProtocol.getTimeStart()
        || Math.abs(node.getTimeStart() - currentlyReceivedProtocol.getTimeStart()) > Long.MAX_VALUE / 2
        ) {
      /*reboot detected*/
      finishWrite(node);
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
      if(finishWrite(node)){
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
    node.getCallback().deserialize(buf,node.getBufObj(),currentlyReceivedProtocol.getPacket(),currentlyReceivedProtocol.getPackets_grid_size(),isNewBunch, cBin.getNodeId());
    if(currentlyReceivedProtocol.getPacket() == currentlyReceivedProtocol.getPackets_grid_size() - 1){
      finishWrite(node);
    }
    finishRead();
    node.getLock().unlock();
  }

  private boolean finishWrite(bufferOfNode node) {
    if(node.getBufObj() != null){
      node.getW0().writeFinished();
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
  public void onStop(){

  }

  private int findBufferIndx(long uniqueId){
    if(writers==null || uniqueId < 0){return -1;}
    for(int i=0; i<writers.length;i++ ){
      if(writers[i].uniqueId() == uniqueId){return i;}
    }
    return -1;
  }

}

