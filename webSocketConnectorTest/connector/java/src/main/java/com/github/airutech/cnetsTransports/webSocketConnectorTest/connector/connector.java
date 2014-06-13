
package com.github.airutech.cnetsTransports.webSocketConnectorTest.connector;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.webSocketConnector.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.mapBuffer.*;
public class connector implements RunnableStoppable{
  int maxNodesCount;int buffersLengths;String serverUrl;int bindPort;int binBuffersSize;writer w0;reader r0;
  
  public connector(int maxNodesCount,int buffersLengths,String serverUrl,int bindPort,int binBuffersSize,writer w0,reader r0){
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
com.github.airutech.cnetsTransports.webSocketConnector _connector;
  private void initialize(){
    
    onKernels();
    
    _connector = new com.github.airutech.cnetsTransports.webSocketConnector(serverUrl,bindPort,maxNodesCount,buffersLengths,binBuffersSize,this.w0,this.r0);
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[1];
    arrContainers[0] = _connector.getRunnables();

    runnables.setContainers(arrContainers);
    return runnables;
  }
/*[[[end]]] (checksum: bb8079c10881eef4f234bf64d432f7c8)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    
  }

  @Override
  public void onStop(){

  }

}

