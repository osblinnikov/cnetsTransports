
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
  writer[] connectionStatusReceivers;writer w0;writer w1;reader r0;
  
  public connectionStatusDispatcher(writer[] connectionStatusReceivers,writer w0,writer w1,reader r0){
    this.connectionStatusReceivers = connectionStatusReceivers;
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
/*[[[end]]] (checksum: 4d263d56e38eda598348d25e4d6b4761)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    connectionStatus conStatusReceived = (connectionStatus) r0.readNext(-1);
    if(conStatusReceived == null){return;}

    /*send to the specific, responsible for the node protocolToBuffer*/
    if(connectionStatusReceivers != null) {
      writer w = connectionStatusReceivers[conStatusReceived.getNodeIndex() % connectionStatusReceivers.length];
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
    conStatus.copyFrom(conStatusReceived);
    w.writeFinished();
  }

  @Override
  public void onStop(){

  }

}

