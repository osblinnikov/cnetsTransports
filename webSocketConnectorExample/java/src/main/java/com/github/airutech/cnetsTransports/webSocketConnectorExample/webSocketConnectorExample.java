
package com.github.airutech.cnetsTransports.webSocketConnectorExample;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import no.eyasys.mobileAlarm.types.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.types.*;
public class webSocketConnectorExample implements RunnableStoppable{
  int maxNodesCount;int buffersLengths;String serverUrl;int bindPort;int binBuffersSize;writer w0;reader r0;
  
  public webSocketConnectorExample(int maxNodesCount,int buffersLengths,String serverUrl,int bindPort,int binBuffersSize,writer w0,reader r0){
    this.maxNodesCount = maxNodesCount;
    this.buffersLengths = buffersLengths;
    this.serverUrl = serverUrl;
    this.bindPort = bindPort;
    this.binBuffersSize = binBuffersSize;
    this.w0 = w0;
    this.r0 = r0;
    onCreate();
    initialize();
  }
com.github.airutech.cnetsTransports.webSocketConnectorExample.connector.connector _connector;
  private void initialize(){
    
    onKernels();
    
    _connector = new com.github.airutech.cnetsTransports.webSocketConnectorExample.connector.connector(maxNodesCount,buffersLengths,serverUrl,bindPort,binBuffersSize,this.w0,this.r0);
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[1];
    arrContainers[0] = _connector.getRunnables();

    runnables.setContainers(arrContainers);
    return runnables;
  }
/*[[[end]]] (checksum: 938009efd7c57b9f0c5b8f16b6b88eb7)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    
    _connector.run();
  }

  @Override
  public void onStop(){

  }

}

