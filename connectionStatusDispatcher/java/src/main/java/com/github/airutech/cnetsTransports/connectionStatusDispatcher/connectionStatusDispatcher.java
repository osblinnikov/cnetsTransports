
package com.github.airutech.cnetsTransports.connectionStatusDispatcher;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnetsTransports.types.*;
public class connectionStatusDispatcher implements RunnableStoppable{
  writer[] connectionStatusReceivers;int maxNodesCount;writer w0;writer w1;reader r0;
  
  public connectionStatusDispatcher(writer[] connectionStatusReceivers,int maxNodesCount,writer w0,writer w1,reader r0){
    this.connectionStatusReceivers = connectionStatusReceivers;
    this.maxNodesCount = maxNodesCount;
    this.w0 = w0;
    this.w1 = w1;
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
/*[[[end]]] (checksum: daa2dd6a53956a82edec74ec28b186fc)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    Thread.currentThread().setName("connectionStatusDispatcher");
    connectionStatus conStatusReceived = (connectionStatus) r0.readNext(-1);
    if(conStatusReceived == null){return;}

    /*send to the specific, responsible for the node protocolToBuffer*/
    if(connectionStatusReceivers != null) {
      double nodesStored = Math.floor((double)maxNodesCount/(double)connectionStatusReceivers.length);
      int processorId = (int)Math.floor((double)(conStatusReceived.getId()%maxNodesCount)/nodesStored);
      if(processorId >= connectionStatusReceivers.length){
        processorId = connectionStatusReceivers.length - 1;//for the last element it is required
      }
      writer w = connectionStatusReceivers[processorId];
      resendTo(w,conStatusReceived);
    }

    /*publish locally to the connector*/
    resendTo(w0,conStatusReceived);
    /*publish externally to the connector*/
    resendTo(w1,conStatusReceived);

    r0.readFinished();
  }

  private void resendTo(writer w, connectionStatus conStatusReceived){
    if(w == null || conStatusReceived == null){return;}
    connectionStatus conStatus = null;
    while (conStatus == null) {conStatus = (connectionStatus) w.writeNext(-1);}
    conStatus.set(conStatusReceived);
    w.writeFinished();
  }

  @Override
  public void onStop(){

  }

}

