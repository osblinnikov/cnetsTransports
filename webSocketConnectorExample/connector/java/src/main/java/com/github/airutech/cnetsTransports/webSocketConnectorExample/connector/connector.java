
package com.github.airutech.cnetsTransports.webSocketConnectorExample.connector;

import com.github.airutech.cnetsTransports.types.connectionStatus;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnetsTransports.webSocket.*;
import com.github.airutech.cnetsTransports.protocolToBuffer.*;
import com.github.airutech.cnetsTransports.protocolToBuffer.*;
import com.github.airutech.cnetsTransports.bufferToProtocol.*;
import com.github.airutech.cnetsTransports.bufferToProtocol.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import no.eyasys.mobileAlarm.types.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.types.*;
public class connector implements RunnableStoppable{
  connectionStatus[] _connectionStatusBuffer_forNodes_0_Arr;cnetsProtocol[] _inputProtocolBuffer_forNodes_0_Arr;byte[] _inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers;writer[] _protocolToBuffer_0_writers;deserializeStreamCallback[] _protocolToBuffer_0_writers_callbacks;connectionStatus[] _connectionStatusBuffer_forNodes_1_Arr;cnetsProtocol[] _inputProtocolBuffer_forNodes_1_Arr;byte[] _inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers;writer[] _protocolToBuffer_1_writers;deserializeStreamCallback[] _protocolToBuffer_1_writers_callbacks;reader[] _bufferToProtocol_0_readers;serializeStreamCallback[] _bufferToProtocol_0_readers_callbacks;reader[] _bufferToProtocol_1_readers;serializeStreamCallback[] _bufferToProtocol_1_readers_callbacks;writer[] _nodesReceivers_writers;writer[] _connectionStatusReceivers_writers;reader[] _allBuffers_readers;nodeRepositoryProtocol[] _nodeRepositoryProtocolBufferArr;cnetsProtocol[] _outputProtocolBuffer_Arr;byte[] _outputProtocolBuffer_Arr_BinaryBuffers;cnetsProtocol[] _connectionStatusBuffer_publish_Arr;byte[] _connectionStatusBuffer_publish_Arr_BinaryBuffers;cnetsConnections[] _connectionsBufferArr;String moduleUniqueName;int maxNodesCount;int buffersLengths;String serverUrl;int bindPort;int binBuffersSize;int statsInterval;writer w0;reader r0;
  
  public connector(String moduleUniqueName,int maxNodesCount,int buffersLengths,String serverUrl,int bindPort,int binBuffersSize,int statsInterval,writer w0,reader r0){
    this.moduleUniqueName = moduleUniqueName;
    this.maxNodesCount = maxNodesCount;
    this.buffersLengths = buffersLengths;
    this.serverUrl = serverUrl;
    this.bindPort = bindPort;
    this.binBuffersSize = binBuffersSize;
    this.statsInterval = statsInterval;
    this.w0 = w0;
    this.r0 = r0;
    this._connectionStatusBuffer_forNodes_0_Arr = new connectionStatus[5];
    this._inputProtocolBuffer_forNodes_0_Arr = new cnetsProtocol[5];
    this._inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers = new byte[640];
    this._protocolToBuffer_0_writers = new writer[3];
    this._protocolToBuffer_0_writers_callbacks = new deserializeStreamCallback[3];
    this._connectionStatusBuffer_forNodes_1_Arr = new connectionStatus[5];
    this._inputProtocolBuffer_forNodes_1_Arr = new cnetsProtocol[5];
    this._inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers = new byte[640];
    this._protocolToBuffer_1_writers = new writer[2];
    this._protocolToBuffer_1_writers_callbacks = new deserializeStreamCallback[2];
    this._bufferToProtocol_0_readers = new reader[1];
    this._bufferToProtocol_0_readers_callbacks = new serializeStreamCallback[1];
    this._bufferToProtocol_1_readers = new reader[1];
    this._bufferToProtocol_1_readers_callbacks = new serializeStreamCallback[1];
    this._nodesReceivers_writers = new writer[2];
    this._connectionStatusReceivers_writers = new writer[2];
    this._allBuffers_readers = new reader[2];
    this._nodeRepositoryProtocolBufferArr = new nodeRepositoryProtocol[2];
    this._outputProtocolBuffer_Arr = new cnetsProtocol[5];
    this._outputProtocolBuffer_Arr_BinaryBuffers = new byte[640];
    this._connectionStatusBuffer_publish_Arr = new cnetsProtocol[5];
    this._connectionStatusBuffer_publish_Arr_BinaryBuffers = new byte[640];
    this._connectionsBufferArr = new cnetsConnections[5];
    onCreate();
    initialize();
  }
com.github.airutech.cnets.mapBuffer.mapBuffer _nodeRepositoryProtocolBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionsBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _outputProtocolBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_publish;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_forNodes_0;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_forNodes_1;com.github.airutech.cnets.mapBuffer.mapBuffer _inputProtocolBuffer_forNodes0;com.github.airutech.cnets.mapBuffer.mapBuffer _inputProtocolBuffer_forNodes1;com.github.airutech.cnetsTransports.webSocket.webSocket _webSocket;com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer _protocolToBuffer_0;com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer _protocolToBuffer_1;com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol _bufferToProtocol_0;com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol _bufferToProtocol_1;
  private void initialize(){
    
    _nodeRepositoryProtocolBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_nodeRepositoryProtocolBufferArr,1000L,moduleUniqueName+"_nodeRepositoryProtocolBuffer",5,statsInterval);
    reader _nodeRepositoryProtocolBufferr0 = _nodeRepositoryProtocolBuffer.getReader(0);
    reader _nodeRepositoryProtocolBufferr1 = _nodeRepositoryProtocolBuffer.getReader(1);
    reader _nodeRepositoryProtocolBufferr2 = _nodeRepositoryProtocolBuffer.getReader(2);
    reader _nodeRepositoryProtocolBufferr3 = _nodeRepositoryProtocolBuffer.getReader(3);
    reader _nodeRepositoryProtocolBufferr4 = _nodeRepositoryProtocolBuffer.getReader(4);
    writer _nodeRepositoryProtocolBufferw0 = _nodeRepositoryProtocolBuffer.getWriter(0);
    _connectionsBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionsBufferArr,1000L,moduleUniqueName+"_connectionsBuffer",1,statsInterval);
    reader _connectionsBufferr0 = _connectionsBuffer.getReader(0);
    writer _connectionsBufferw0 = _connectionsBuffer.getWriter(0);
    _outputProtocolBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_outputProtocolBuffer_Arr,1000L,moduleUniqueName+"_outputProtocolBuffer",1,statsInterval);
    reader _outputProtocolBufferr0 = _outputProtocolBuffer.getReader(0);
    writer _outputProtocolBufferw0 = _outputProtocolBuffer.getWriter(0);
    writer _outputProtocolBufferw1 = _outputProtocolBuffer.getWriter(1);
    _connectionStatusBuffer_publish = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_publish_Arr,1000L,moduleUniqueName+"_connectionStatusBuffer_publish",2,statsInterval);
    reader _connectionStatusBuffer_publishr0 = _connectionStatusBuffer_publish.getReader(0);
    reader _connectionStatusBuffer_publishr1 = _connectionStatusBuffer_publish.getReader(1);
    writer _connectionStatusBuffer_publishw0 = _connectionStatusBuffer_publish.getWriter(0);
    _connectionStatusBuffer_forNodes_0 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_0_Arr,1000L,moduleUniqueName+"_connectionStatusBuffer_forNodes_0",1,statsInterval);
    reader _connectionStatusBuffer_forNodes_0r0 = _connectionStatusBuffer_forNodes_0.getReader(0);
    _connectionStatusBuffer_forNodes_1 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_1_Arr,1000L,moduleUniqueName+"_connectionStatusBuffer_forNodes_1",1,statsInterval);
    reader _connectionStatusBuffer_forNodes_1r0 = _connectionStatusBuffer_forNodes_1.getReader(0);
    _inputProtocolBuffer_forNodes0 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_0_Arr,1000L,moduleUniqueName+"_inputProtocolBuffer_forNodes0",1,statsInterval);
    reader _inputProtocolBuffer_forNodes0r0 = _inputProtocolBuffer_forNodes0.getReader(0);
    _inputProtocolBuffer_forNodes1 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_1_Arr,1000L,moduleUniqueName+"_inputProtocolBuffer_forNodes1",1,statsInterval);
    reader _inputProtocolBuffer_forNodes1r0 = _inputProtocolBuffer_forNodes1.getReader(0);
    onKernels();
    
    _webSocket = new com.github.airutech.cnetsTransports.webSocket.webSocket(maxNodesCount,serverUrl,bindPort,null,_nodesReceivers_writers,_connectionStatusReceivers_writers,_allBuffers_readers,_connectionStatusBuffer_publishw0,_outputProtocolBufferr0,_connectionsBufferr0,_nodeRepositoryProtocolBufferr0);
    _protocolToBuffer_0 = new com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer(_protocolToBuffer_0_writers,_protocolToBuffer_0_writers_callbacks,0,2,5,_nodeRepositoryProtocolBufferw0,_connectionStatusBuffer_forNodes_0r0,_nodeRepositoryProtocolBufferr1,_inputProtocolBuffer_forNodes0r0);
    _protocolToBuffer_1 = new com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer(_protocolToBuffer_1_writers,_protocolToBuffer_1_writers_callbacks,3,2,5,_nodeRepositoryProtocolBufferw0,_connectionStatusBuffer_forNodes_1r0,_nodeRepositoryProtocolBufferr2,_inputProtocolBuffer_forNodes1r0);
    _bufferToProtocol_0 = new com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol(_bufferToProtocol_0_readers,_bufferToProtocol_0_readers_callbacks,0,5,_outputProtocolBufferw0,_connectionStatusBuffer_publishr0,_nodeRepositoryProtocolBufferr3);
    _bufferToProtocol_1 = new com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol(_bufferToProtocol_1_readers,_bufferToProtocol_1_readers_callbacks,1,5,_outputProtocolBufferw0,_connectionStatusBuffer_publishr1,_nodeRepositoryProtocolBufferr4);
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[5];
    arrContainers[0] = _webSocket.getRunnables();
    arrContainers[1] = _protocolToBuffer_0.getRunnables();
    arrContainers[2] = _protocolToBuffer_1.getRunnables();
    arrContainers[3] = _bufferToProtocol_0.getRunnables();
    arrContainers[4] = _bufferToProtocol_1.getRunnables();

    runnables.setContainers(arrContainers);
    return runnables;
  }
/*[[[end]]] (checksum: 633a477ec51ebdd7ddf5e22127c48e13)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    /*
    _webSocket.run();
    _protocolToBuffer_0.run();
    _protocolToBuffer_1.run();
    _bufferToProtocol_0.run();
    _bufferToProtocol_1.run();*/
  }

  @Override
  public void onStop(){

  }

}

