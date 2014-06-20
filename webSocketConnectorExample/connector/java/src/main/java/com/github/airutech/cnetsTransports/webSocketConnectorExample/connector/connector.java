
package com.github.airutech.cnetsTransports.webSocketConnectorExample.connector;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol;
import com.github.airutech.cnetsTransports.types.connectionStatus;
import java.nio.ByteBuffer;

import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnetsTransports.connectionStatusDispatcher.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source.*;
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
  int countNodesProcessors = 2;int countBuffersProcessors = 2;int maxNodesCount = 5;int buffersLengths = 8;int binBuffersSize = 128;int readersCount = 3;int writersCount = 3;connectionStatus[] _connectionStatusBuffer_forNodes_0_Arr;cnetsProtocol[] _inputProtocolBuffer_forNodes_0_Arr;byte[] _inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers;connectionStatus[] _connectionStatusBuffer_forNodes_1_Arr;cnetsProtocol[] _inputProtocolBuffer_forNodes_1_Arr;byte[] _inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers;reader[] _bufferToProtocol_0_readers;serializeStreamCallback[] _bufferToProtocol_0_readers_callbacks;reader[] _bufferToProtocol_1_readers;serializeStreamCallback[] _bufferToProtocol_1_readers_callbacks;writer[] _nodesReceivers_writers;writer[] _connectionStatusReceivers_writers;nodeRepositoryProtocol[] _nodeRepositoryProtocolBufferArr;cnetsProtocol[] _outputProtocolBuffer_Arr;byte[] _outputProtocolBuffer_Arr_BinaryBuffers;cnetsProtocol[] _connectionStatusBuffer_publish_Arr;byte[] _connectionStatusBuffer_publish_Arr_BinaryBuffers;cnetsConnections[] _connectionsBufferArr;com.github.airutech.cnetsTransports.types.connectionStatus[] _dispatchConnStatusBuffer_Arr;String[] subscribedBuffersNames;writer[] allWriters;reader[] allReaders;deserializeStreamCallback[] allWriters_callbacks;serializeStreamCallback[] allReaders_callbacks;String serverUrl;int bindPort;writer w0;reader r0;
  
  public connector(String[] subscribedBuffersNames,writer[] allWriters,reader[] allReaders,deserializeStreamCallback[] allWriters_callbacks,serializeStreamCallback[] allReaders_callbacks,String serverUrl,int bindPort,writer w0,reader r0){
    this.subscribedBuffersNames = subscribedBuffersNames;
    this.allWriters = allWriters;
    this.allReaders = allReaders;
    this.allWriters_callbacks = allWriters_callbacks;
    this.allReaders_callbacks = allReaders_callbacks;
    this.serverUrl = serverUrl;
    this.bindPort = bindPort;
    this.w0 = w0;
    this.r0 = r0;
    this.countNodesProcessors = 2;
    this.countBuffersProcessors = 2;
    this.maxNodesCount = 5;
    this.buffersLengths = 8;
    this.binBuffersSize = 128;
    this.readersCount = 3;
    this.writersCount = 3;
    this._connectionStatusBuffer_forNodes_0_Arr = new connectionStatus[8];
    this._inputProtocolBuffer_forNodes_0_Arr = new cnetsProtocol[8];
    this._inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers = new byte[1024];
    this._connectionStatusBuffer_forNodes_1_Arr = new connectionStatus[8];
    this._inputProtocolBuffer_forNodes_1_Arr = new cnetsProtocol[8];
    this._inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers = new byte[1024];
    this._bufferToProtocol_0_readers = new reader[2];
    this._bufferToProtocol_0_readers_callbacks = new serializeStreamCallback[2];
    this._bufferToProtocol_1_readers = new reader[1];
    this._bufferToProtocol_1_readers_callbacks = new serializeStreamCallback[1];
    this._nodesReceivers_writers = new writer[2];
    this._connectionStatusReceivers_writers = new writer[2];
    this._nodeRepositoryProtocolBufferArr = new nodeRepositoryProtocol[2];
    this._outputProtocolBuffer_Arr = new cnetsProtocol[8];
    this._outputProtocolBuffer_Arr_BinaryBuffers = new byte[1024];
    this._connectionStatusBuffer_publish_Arr = new cnetsProtocol[8];
    this._connectionStatusBuffer_publish_Arr_BinaryBuffers = new byte[1024];
    this._connectionsBufferArr = new cnetsConnections[8];
    this._dispatchConnStatusBuffer_Arr = new com.github.airutech.cnetsTransports.types.connectionStatus[8];
    onCreate();
    initialize();
  }
com.github.airutech.cnets.mapBuffer.mapBuffer _nodeRepositoryProtocolBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionsBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _outputProtocolBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_publish;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_forNodes_0;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_forNodes_1;com.github.airutech.cnets.mapBuffer.mapBuffer _inputProtocolBuffer_forNodes_0;com.github.airutech.cnets.mapBuffer.mapBuffer _inputProtocolBuffer_forNodes_1;com.github.airutech.cnets.mapBuffer.mapBuffer _dispatchConnStatusBuffer;com.github.airutech.cnetsTransports.connectionStatusDispatcher.connectionStatusDispatcher _connStatusDispatcher;com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source.source _nodeRepositoryProtocolSource;com.github.airutech.cnetsTransports.webSocket.webSocket _webSocket;com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer _protocolToBuffer_0;com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer _protocolToBuffer_1;com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol _bufferToProtocol_0;com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol _bufferToProtocol_1;
  private void initialize(){
    /*init props*/
    int i,l,rIter;
    for(i=0; i<_connectionStatusBuffer_forNodes_0_Arr.length; i++){
      _connectionStatusBuffer_forNodes_0_Arr[i] = new connectionStatus();
    }
    l = _inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers.length/_inputProtocolBuffer_forNodes_0_Arr.length;
    for(i=0; i<_inputProtocolBuffer_forNodes_0_Arr.length; i++){
      _inputProtocolBuffer_forNodes_0_Arr[i] = new cnetsProtocol();
      _inputProtocolBuffer_forNodes_0_Arr[i].setData(ByteBuffer.wrap(_inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers, i*l, l));
    }
    for(i=0; i<_connectionStatusBuffer_forNodes_1_Arr.length; i++){
      _connectionStatusBuffer_forNodes_1_Arr[i] = new connectionStatus();
    }
    l = _inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers.length/_inputProtocolBuffer_forNodes_1_Arr.length;
    for(i=0; i<_inputProtocolBuffer_forNodes_1_Arr.length; i++){
      _inputProtocolBuffer_forNodes_1_Arr[i] = new cnetsProtocol();
      _inputProtocolBuffer_forNodes_1_Arr[i].setData(ByteBuffer.wrap(_inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers, i*l, l));
    }
    rIter = 0;
    for(i=0; i<_bufferToProtocol_0_readers.length; i++){
      _bufferToProtocol_0_readers[i] = allReaders[rIter];
      _bufferToProtocol_0_readers_callbacks[i] = allReaders_callbacks[rIter];
      rIter++;
    }
    for(i=0; i<_bufferToProtocol_1_readers.length; i++){
      _bufferToProtocol_1_readers[i] = allReaders[rIter];
      _bufferToProtocol_1_readers_callbacks[i] = allReaders_callbacks[rIter];
      rIter++;
    }
    for(i=0; i<_nodeRepositoryProtocolBufferArr.length; i++){
      _nodeRepositoryProtocolBufferArr[i] = new nodeRepositoryProtocol();
    }
    for(i=0; i<_connectionsBufferArr.length; i++){
      _connectionsBufferArr[i] = new cnetsConnections();
    }
    l = _connectionStatusBuffer_publish_Arr_BinaryBuffers.length/_connectionStatusBuffer_publish_Arr.length;
    for(i=0; i<_connectionStatusBuffer_publish_Arr.length; i++){
      _connectionStatusBuffer_publish_Arr[i] = new cnetsProtocol();
      _connectionStatusBuffer_publish_Arr[i].setData(ByteBuffer.wrap(_connectionStatusBuffer_publish_Arr_BinaryBuffers, i*l, l));
    }
    l = _outputProtocolBuffer_Arr_BinaryBuffers.length/_outputProtocolBuffer_Arr.length;
    for(i=0; i<_outputProtocolBuffer_Arr.length; i++){
      _outputProtocolBuffer_Arr[i] = new cnetsProtocol();
      _outputProtocolBuffer_Arr[i].setData(ByteBuffer.wrap(_outputProtocolBuffer_Arr_BinaryBuffers, i*l, l));
    }
    for(i=0; i<_dispatchConnStatusBuffer_Arr.length; i++){
      _dispatchConnStatusBuffer_Arr[i] = new com.github.airutech.cnetsTransports.types.connectionStatus();
    }
    /*init buffers*/
    
    _nodeRepositoryProtocolBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_nodeRepositoryProtocolBufferArr,1000L,5);
    reader _nodeRepositoryProtocolBufferr0 = _nodeRepositoryProtocolBuffer.getReader(0);
    reader _nodeRepositoryProtocolBufferr1 = _nodeRepositoryProtocolBuffer.getReader(1);
    reader _nodeRepositoryProtocolBufferr2 = _nodeRepositoryProtocolBuffer.getReader(2);
    reader _nodeRepositoryProtocolBufferr3 = _nodeRepositoryProtocolBuffer.getReader(3);
    reader _nodeRepositoryProtocolBufferr4 = _nodeRepositoryProtocolBuffer.getReader(4);
    writer _nodeRepositoryProtocolBufferw0 = _nodeRepositoryProtocolBuffer.getWriter(0);
    _connectionsBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionsBufferArr,1000L,1);
    reader _connectionsBufferr0 = _connectionsBuffer.getReader(0);
    writer _connectionsBufferw0 = _connectionsBuffer.getWriter(0);
    _outputProtocolBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_outputProtocolBuffer_Arr,1000L,1);
    reader _outputProtocolBufferr0 = _outputProtocolBuffer.getReader(0);
    writer _outputProtocolBufferw0 = _outputProtocolBuffer.getWriter(0);
    writer _outputProtocolBufferw1 = _outputProtocolBuffer.getWriter(1);
    _connectionStatusBuffer_publish = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_publish_Arr,1000L,2);
    reader _connectionStatusBuffer_publishr0 = _connectionStatusBuffer_publish.getReader(0);
    reader _connectionStatusBuffer_publishr1 = _connectionStatusBuffer_publish.getReader(1);
    reader _connectionStatusBuffer_publishr2 = _connectionStatusBuffer_publish.getReader(2);
    writer _connectionStatusBuffer_publishw0 = _connectionStatusBuffer_publish.getWriter(0);
    _connectionStatusBuffer_forNodes_0 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_0_Arr,1000L,1);
    reader _connectionStatusBuffer_forNodes_0r0 = _connectionStatusBuffer_forNodes_0.getReader(0);
    _connectionStatusBuffer_forNodes_1 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_1_Arr,1000L,1);
    reader _connectionStatusBuffer_forNodes_1r0 = _connectionStatusBuffer_forNodes_1.getReader(0);
    _inputProtocolBuffer_forNodes_0 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_0_Arr,1000L,1);
    reader _inputProtocolBuffer_forNodes_0r0 = _inputProtocolBuffer_forNodes_0.getReader(0);
    _inputProtocolBuffer_forNodes_1 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_1_Arr,1000L,1);
    reader _inputProtocolBuffer_forNodes_1r0 = _inputProtocolBuffer_forNodes_1.getReader(0);
    _dispatchConnStatusBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_dispatchConnStatusBuffer_Arr,1000L,1);
    reader _dispatchConnStatusBufferr0 = _dispatchConnStatusBuffer.getReader(0);
    writer _dispatchConnStatusBufferw0 = _dispatchConnStatusBuffer.getWriter(0);
    /*init props after buffers*/
    _nodesReceivers_writers[0] = _inputProtocolBuffer_forNodes_0.getWriter(0);
    _connectionStatusReceivers_writers[0] = _connectionStatusBuffer_forNodes_0.getWriter(0);
    _nodesReceivers_writers[1] = _inputProtocolBuffer_forNodes_1.getWriter(0);
    _connectionStatusReceivers_writers[1] = _connectionStatusBuffer_forNodes_1.getWriter(0);
    onKernels();
    
    _connStatusDispatcher = new com.github.airutech.cnetsTransports.connectionStatusDispatcher.connectionStatusDispatcher(_connectionStatusReceivers_writers,_nodeRepositoryProtocolBufferw0,this.w0,_dispatchConnStatusBufferr0);
    _nodeRepositoryProtocolSource = new com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source.source(subscribedBuffersNames,_nodeRepositoryProtocolBufferw0,_connectionStatusBuffer_publishr2);
    _webSocket = new com.github.airutech.cnetsTransports.webSocket.webSocket(subscribedBuffersNames,maxNodesCount,serverUrl,bindPort,null,_nodesReceivers_writers,allReaders,_dispatchConnStatusBufferw0,_outputProtocolBufferr0,_connectionsBufferr0,_nodeRepositoryProtocolBufferr0);
    _protocolToBuffer_0 = new com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer(subscribedBuffersNames,allWriters,allWriters_callbacks,0,2,maxNodesCount,_connectionStatusBuffer_forNodes_0r0,_nodeRepositoryProtocolBufferr1,_inputProtocolBuffer_forNodes_0r0);
    _protocolToBuffer_1 = new com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer(subscribedBuffersNames,allWriters,allWriters_callbacks,3,2,maxNodesCount,_connectionStatusBuffer_forNodes_1r0,_nodeRepositoryProtocolBufferr2,_inputProtocolBuffer_forNodes_1r0);
    _bufferToProtocol_0 = new com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol(subscribedBuffersNames,_bufferToProtocol_0_readers,_bufferToProtocol_0_readers_callbacks,0,maxNodesCount,_outputProtocolBufferw0,_connectionStatusBuffer_publishr0,_nodeRepositoryProtocolBufferr3);
    _bufferToProtocol_1 = new com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol(subscribedBuffersNames,_bufferToProtocol_1_readers,_bufferToProtocol_1_readers_callbacks,2,maxNodesCount,_outputProtocolBufferw0,_connectionStatusBuffer_publishr1,_nodeRepositoryProtocolBufferr4);
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[7];
    arrContainers[0] = _connStatusDispatcher.getRunnables();
    arrContainers[1] = _nodeRepositoryProtocolSource.getRunnables();
    arrContainers[2] = _webSocket.getRunnables();
    arrContainers[3] = _protocolToBuffer_0.getRunnables();
    arrContainers[4] = _protocolToBuffer_1.getRunnables();
    arrContainers[5] = _bufferToProtocol_0.getRunnables();
    arrContainers[6] = _bufferToProtocol_1.getRunnables();

    runnables.setContainers(arrContainers);
    return runnables;
  }
/*[[[end]]] (checksum: 85f663eb53f6a423c622b8230784fafa)*/

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
    _connStatusDispatcher.run();
    _nodeRepositoryProtocolSource.run();
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

