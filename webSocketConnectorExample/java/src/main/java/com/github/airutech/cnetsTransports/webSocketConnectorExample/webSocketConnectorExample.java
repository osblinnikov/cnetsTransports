
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
  int countNodesProcessors = 2;int countBuffersProcessors = 2;int maxNodesCount = 5;int buffersLengths = 5;int binBuffersSize = 128;long timeoutInterval = 1000L;String moduleUniqueName;String serverUrl;int bindPort;int statsInterval;writer w0;writer w1;writer w2;reader r0;reader r1;reader r2;reader rSelect;selector readersSelector;
  
  public webSocketConnectorExample(String moduleUniqueName,String serverUrl,int bindPort,int statsInterval,writer w0,writer w1,writer w2,reader r0,reader r1,reader r2){
    this.moduleUniqueName = moduleUniqueName;
    this.serverUrl = serverUrl;
    this.bindPort = bindPort;
    this.statsInterval = statsInterval;
    this.w0 = w0;
    this.w1 = w1;
    this.w2 = w2;
    this.r0 = r0;
    this.r1 = r1;
    this.r2 = r2;
    this.countNodesProcessors = 2;
    this.countBuffersProcessors = 2;
    this.maxNodesCount = 5;
    this.buffersLengths = 5;
    this.binBuffersSize = 128;
    this.timeoutInterval = 1000L;
    reader[] arrReaders = new reader[3];
    arrReaders[0] = r0;
    arrReaders[1] = r1;
    arrReaders[2] = r2;
    this.readersSelector = new selector(arrReaders);
    this.rSelect = readersSelector.getReader(0,-1);
    onCreate();
    initialize();
  }
com.github.airutech.cnetsTransports.webSocketConnectorExample.connector.connector _connector;
  private void initialize(){
    
    onKernels();
    
    _connector = new com.github.airutech.cnetsTransports.webSocketConnectorExample.connector.connector(moduleUniqueName+"._connector",maxNodesCount,buffersLengths,serverUrl,bindPort,binBuffersSize,statsInterval,this.w0,this.r0);
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[1];
    arrContainers[0] = _connector.getRunnables();

    runnables.setContainers(arrContainers);
    return runnables;
  }
/*[[[end]]] (checksum: 83030a5c05155d9dbfa3d16967e41ce1)*/

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

