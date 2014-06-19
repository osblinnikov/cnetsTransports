
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
import com.github.airutech.cnetsTransports.msgpackExample.msgpack.*;
import com.github.airutech.cnetsTransports.msgpack.*;
public class webSocketConnectorExample implements RunnableStoppable{
  int countNodesProcessors = 2;int countBuffersProcessors = 2;int maxNodesCount = 5;int buffersLengths = 8;int binBuffersSize = 128;long timeoutInterval = 1000L;String serverUrl;int bindPort;int statsInterval;writer w0;writer w1;writer w2;writer w3;reader r0;reader r1;reader r2;reader r3;reader rSelect;selector readersSelector;

  private String[] subscribedBuffersNames = null;
  private writer[] allWriters = null;
  private reader[] allReaders = null;
  private deserializeStreamCallback[] allWriters_callbacks = null;
  private serializeStreamCallback[] allReaders_callbacks = null;
  public webSocketConnectorExample(String serverUrl,int bindPort,int statsInterval,writer w0,writer w1,writer w2,writer w3,reader r0,reader r1,reader r2,reader r3){
    this.serverUrl = serverUrl;
    this.bindPort = bindPort;
    this.statsInterval = statsInterval;
    this.w0 = w0;
    this.w1 = w1;
    this.w2 = w2;
    this.w3 = w3;
    this.r0 = r0;
    this.r1 = r1;
    this.r2 = r2;
    this.r3 = r3;
    this.countNodesProcessors = 2;
    this.countBuffersProcessors = 2;
    this.maxNodesCount = 5;
    this.buffersLengths = 8;
    this.binBuffersSize = 128;
    this.timeoutInterval = 1000L;
    reader[] arrReaders = new reader[4];
    arrReaders[0] = r0;
    arrReaders[1] = r1;
    arrReaders[2] = r2;
    arrReaders[3] = r3;
    this.readersSelector = new selector(arrReaders);
    this.rSelect = readersSelector.getReader(0,-1);
    onCreate();
    initialize();
  }
com.github.airutech.cnetsTransports.webSocketConnectorExample.connector.connector _connector;
  private void initialize(){
    allWriters = new writer[3];
    allWriters[0] = w1;
    allWriters_callbacks[0] = new msgPackDeserializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    allWriters[1] = w2;
    allWriters_callbacks[1] = new msgPackDeserializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    allWriters[2] = w3;
    allWriters_callbacks[2] = new msgPackDeserializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    subscribedBuffersNames = new String[3];
    allReaders = new reader[3];
    subscribedBuffersNames[0] = "exampleToSend0";
    allReaders[0] = r1;
    allReaders_callbacks[0] = new msgPackSerializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    subscribedBuffersNames[1] = "exampleToSend1";
    allReaders[1] = r2;
    allReaders_callbacks[1] = new msgPackSerializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    subscribedBuffersNames[2] = "exampleToSend2";
    allReaders[2] = r3;
    allReaders_callbacks[2] = new msgPackSerializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    
    onKernels();
    
    _connector = new com.github.airutech.cnetsTransports.webSocketConnectorExample.connector.connector(subscribedBuffersNames,allWriters,allReaders,allWriters_callbacks,allReaders_callbacks,serverUrl,bindPort,this.w0,this.r0);
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[1];
    arrContainers[0] = _connector.getRunnables();

    runnables.setContainers(arrContainers);
    return runnables;
  }
/*[[[end]]] (checksum: 7f5ec8a509321590d592301c1c161b0c) */

  private void onCreate(){
    readersSelector.onDestroy();
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

