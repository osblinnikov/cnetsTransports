
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
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.types.*;
public class connector implements RunnableStoppable{
  int countNodesProcessors = 2;int countBuffersProcessors = 2;int maxNodesCount = 5;int buffersLengths = 8;int binBuffersSize = 128;int readersCount = 4;int writersCount = 4;com.github.airutech.cnetsTransports.types.connectionStatus[] _connectionStatusBuffer_forNodes_0_Arr;com.github.airutech.cnetsTransports.types.cnetsProtocol[] _inputProtocolBuffer_forNodes_0_Arr;byte[] _inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers;com.github.airutech.cnetsTransports.types.connectionStatus[] _connectionStatusBuffer_forNodes_1_Arr;com.github.airutech.cnetsTransports.types.cnetsProtocol[] _inputProtocolBuffer_forNodes_1_Arr;byte[] _inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers;reader[] _bufferToProtocol_0_readers;com.github.airutech.cnetsTransports.types.serializeStreamCallback[] _bufferToProtocol_0_readers_callbacks;reader[] _bufferToProtocol_1_readers;com.github.airutech.cnetsTransports.types.serializeStreamCallback[] _bufferToProtocol_1_readers_callbacks;writer[] _nodesReceivers_writers;writer[] _connectionStatusReceivers_writers;com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol[] _localNodeRepositoryProtocolBufferArr;com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol[] _dstNodeRepositoryProtocolBufferArr;com.github.airutech.cnetsTransports.types.cnetsProtocol[] _outputProtocolBuffer_Arr;byte[] _outputProtocolBuffer_Arr_BinaryBuffers;com.github.airutech.cnetsTransports.types.connectionStatus[] _connectionStatusBuffer_publish_Arr;com.github.airutech.cnetsTransports.types.connectionStatus[] _dispatchConnStatusBuffer_Arr;String[] publishedBuffersNames;String[] subscribedBuffersNames;writer[] allWriters;reader[] allReaders;deserializeStreamCallback[] allWriters_callbacks;serializeStreamCallback[] allReaders_callbacks;String serverUrl;int bindPort;writer w0;reader r0;
  
  public connector(String[] publishedBuffersNames,String[] subscribedBuffersNames,writer[] allWriters,reader[] allReaders,deserializeStreamCallback[] allWriters_callbacks,serializeStreamCallback[] allReaders_callbacks,String serverUrl,int bindPort,writer w0,reader r0){
    this.publishedBuffersNames = publishedBuffersNames;
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
    this.readersCount = 4;
    this.writersCount = 4;
    this._connectionStatusBuffer_forNodes_0_Arr = new com.github.airutech.cnetsTransports.types.connectionStatus[8];
    this._inputProtocolBuffer_forNodes_0_Arr = new com.github.airutech.cnetsTransports.types.cnetsProtocol[8];
    this._inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers = new byte[1024];
    this._connectionStatusBuffer_forNodes_1_Arr = new com.github.airutech.cnetsTransports.types.connectionStatus[8];
    this._inputProtocolBuffer_forNodes_1_Arr = new com.github.airutech.cnetsTransports.types.cnetsProtocol[8];
    this._inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers = new byte[1024];
    this._bufferToProtocol_0_readers = new reader[2];
    this._bufferToProtocol_0_readers_callbacks = new com.github.airutech.cnetsTransports.types.serializeStreamCallback[2];
    this._bufferToProtocol_1_readers = new reader[2];
    this._bufferToProtocol_1_readers_callbacks = new com.github.airutech.cnetsTransports.types.serializeStreamCallback[2];
    this._nodesReceivers_writers = new writer[2];
    this._connectionStatusReceivers_writers = new writer[2];
    this._localNodeRepositoryProtocolBufferArr = new com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol[2];
    this._dstNodeRepositoryProtocolBufferArr = new com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol[2];
    this._outputProtocolBuffer_Arr = new com.github.airutech.cnetsTransports.types.cnetsProtocol[8];
    this._outputProtocolBuffer_Arr_BinaryBuffers = new byte[1024];
    this._connectionStatusBuffer_publish_Arr = new com.github.airutech.cnetsTransports.types.connectionStatus[8];
    this._dispatchConnStatusBuffer_Arr = new com.github.airutech.cnetsTransports.types.connectionStatus[8];
    onCreate();
    initialize();
  }
com.github.airutech.cnets.mapBuffer.mapBuffer _localNodeRepositoryProtocolBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _dstNodeRepositoryProtocolBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _outputProtocolBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_publish;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_forNodes_0;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_forNodes_1;com.github.airutech.cnets.mapBuffer.mapBuffer _inputProtocolBuffer_forNodes_0;com.github.airutech.cnets.mapBuffer.mapBuffer _inputProtocolBuffer_forNodes_1;com.github.airutech.cnets.mapBuffer.mapBuffer _dispatchConnStatusBuffer;com.github.airutech.cnetsTransports.connectionStatusDispatcher.connectionStatusDispatcher _connStatusDispatcher;com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source.source _localNodeRepositoryProtocolSource;com.github.airutech.cnetsTransports.webSocket.webSocket _webSocket;com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer _protocolToBuffer_0;com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer _protocolToBuffer_1;com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol _bufferToProtocol_0;com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol _bufferToProtocol_1;
  private void initialize(){
    /*init props*/
    int i,l,rIter;
    for(i=0; i<_connectionStatusBuffer_forNodes_0_Arr.length; i++){
      _connectionStatusBuffer_forNodes_0_Arr[i] = new connectionStatus();
    }
    l = _inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers.length/_inputProtocolBuffer_forNodes_0_Arr.length;
    for(i=0; i<_inputProtocolBuffer_forNodes_0_Arr.length; i++){
      _inputProtocolBuffer_forNodes_0_Arr[i] = new cnetsProtocol();
      _inputProtocolBuffer_forNodes_0_Arr[i].setData(ByteBuffer.wrap(_inputProtocolBuffer_forNodes_0_Arr_BinaryBuffers, i*l, l).slice());
    }
    for(i=0; i<_connectionStatusBuffer_forNodes_1_Arr.length; i++){
      _connectionStatusBuffer_forNodes_1_Arr[i] = new connectionStatus();
    }
    l = _inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers.length/_inputProtocolBuffer_forNodes_1_Arr.length;
    for(i=0; i<_inputProtocolBuffer_forNodes_1_Arr.length; i++){
      _inputProtocolBuffer_forNodes_1_Arr[i] = new cnetsProtocol();
      _inputProtocolBuffer_forNodes_1_Arr[i].setData(ByteBuffer.wrap(_inputProtocolBuffer_forNodes_1_Arr_BinaryBuffers, i*l, l).slice());
    }
    for(i=0; i<_dstNodeRepositoryProtocolBufferArr.length; i++){
      _dstNodeRepositoryProtocolBufferArr[i] = new com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol();
    }
    for(i=0; i<_localNodeRepositoryProtocolBufferArr.length; i++){
      _localNodeRepositoryProtocolBufferArr[i] = new com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol();
    }
    for(i=0; i<_connectionStatusBuffer_publish_Arr.length; i++){
      _connectionStatusBuffer_publish_Arr[i] = new com.github.airutech.cnetsTransports.types.connectionStatus();
    }
    l = _outputProtocolBuffer_Arr_BinaryBuffers.length/_outputProtocolBuffer_Arr.length;
    for(i=0; i<_outputProtocolBuffer_Arr.length; i++){
      _outputProtocolBuffer_Arr[i] = new com.github.airutech.cnetsTransports.types.cnetsProtocol();
      _outputProtocolBuffer_Arr[i].setData(ByteBuffer.wrap(_outputProtocolBuffer_Arr_BinaryBuffers, i*l, l).slice());
    }
    for(i=0; i<_dispatchConnStatusBuffer_Arr.length; i++){
      _dispatchConnStatusBuffer_Arr[i] = new com.github.airutech.cnetsTransports.types.connectionStatus();
    }
    /*init buffers*/
    
    _localNodeRepositoryProtocolBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_localNodeRepositoryProtocolBufferArr,1000L,1);
    writer _localNodeRepositoryProtocolBufferw0 = _localNodeRepositoryProtocolBuffer.getWriter(0);
    _dstNodeRepositoryProtocolBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_dstNodeRepositoryProtocolBufferArr,1000L,5);
    reader _dstNodeRepositoryProtocolBufferr0 = _dstNodeRepositoryProtocolBuffer.getReader(0);
    reader _dstNodeRepositoryProtocolBufferr1 = _dstNodeRepositoryProtocolBuffer.getReader(1);
    reader _dstNodeRepositoryProtocolBufferr2 = _dstNodeRepositoryProtocolBuffer.getReader(2);
    reader _dstNodeRepositoryProtocolBufferr3 = _dstNodeRepositoryProtocolBuffer.getReader(3);
    reader _dstNodeRepositoryProtocolBufferr4 = _dstNodeRepositoryProtocolBuffer.getReader(4);
    _outputProtocolBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_outputProtocolBuffer_Arr,1000L,1);
    reader _outputProtocolBufferr0 = _outputProtocolBuffer.getReader(0);
    writer _outputProtocolBufferw0 = _outputProtocolBuffer.getWriter(0);
    writer _outputProtocolBufferw1 = _outputProtocolBuffer.getWriter(1);
    _connectionStatusBuffer_publish = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_publish_Arr,1000L,3);
    reader _connectionStatusBuffer_publishr0 = _connectionStatusBuffer_publish.getReader(0);
    reader _connectionStatusBuffer_publishr1 = _connectionStatusBuffer_publish.getReader(1);
    reader _connectionStatusBuffer_publishr2 = _connectionStatusBuffer_publish.getReader(2);
    writer _connectionStatusBuffer_publishw0 = _connectionStatusBuffer_publish.getWriter(0);
    _connectionStatusBuffer_forNodes_0 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_0_Arr,1000L,1);
    reader _connectionStatusBuffer_forNodes_0r0 = _connectionStatusBuffer_forNodes_0.getReader(0);
    _connectionStatusBuffer_forNodes_1 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_1_Arr,1000L,1);
    reader _connectionStatusBuffer_forNodes_1r0 = _connectionStatusBuffer_forNodes_1.getReader(0);
    _inputProtocolBuffer_forNodes_0 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_inputProtocolBuffer_forNodes_0_Arr,1000L,1);
    reader _inputProtocolBuffer_forNodes_0r0 = _inputProtocolBuffer_forNodes_0.getReader(0);
    _inputProtocolBuffer_forNodes_1 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_inputProtocolBuffer_forNodes_1_Arr,1000L,1);
    reader _inputProtocolBuffer_forNodes_1r0 = _inputProtocolBuffer_forNodes_1.getReader(0);
    _dispatchConnStatusBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_dispatchConnStatusBuffer_Arr,1000L,1);
    reader _dispatchConnStatusBufferr0 = _dispatchConnStatusBuffer.getReader(0);
    writer _dispatchConnStatusBufferw0 = _dispatchConnStatusBuffer.getWriter(0);
    /*init props after buffers*/
    _nodesReceivers_writers[0] = _inputProtocolBuffer_forNodes_0.getWriter(0);
    _connectionStatusReceivers_writers[0] = _connectionStatusBuffer_forNodes_0.getWriter(0);
    _nodesReceivers_writers[1] = _inputProtocolBuffer_forNodes_1.getWriter(0);
    _connectionStatusReceivers_writers[1] = _connectionStatusBuffer_forNodes_1.getWriter(0);
    allWriters[0] = _dstNodeRepositoryProtocolBuffer.getWriter(0);
    allReaders[0] = _localNodeRepositoryProtocolBuffer.getReader(0);
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
    onKernels();
    
    _connStatusDispatcher = new com.github.airutech.cnetsTransports.connectionStatusDispatcher.connectionStatusDispatcher(_connectionStatusReceivers_writers,maxNodesCount,_connectionStatusBuffer_publishw0,this.w0,_dispatchConnStatusBufferr0);
    _localNodeRepositoryProtocolSource = new com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source.source(publishedBuffersNames,subscribedBuffersNames,_localNodeRepositoryProtocolBufferw0,_connectionStatusBuffer_publishr2);
    _webSocket = new com.github.airutech.cnetsTransports.webSocket.webSocket(publishedBuffersNames,maxNodesCount,serverUrl,bindPort,null,_nodesReceivers_writers,_dispatchConnStatusBufferw0,_outputProtocolBufferr0,this.r0,_dstNodeRepositoryProtocolBufferr0);
    _protocolToBuffer_0 = new com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer(subscribedBuffersNames,allWriters,allWriters_callbacks,0,2,maxNodesCount,_connectionStatusBuffer_forNodes_0r0,_dstNodeRepositoryProtocolBufferr1,_inputProtocolBuffer_forNodes_0r0);
    _protocolToBuffer_1 = new com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer(subscribedBuffersNames,allWriters,allWriters_callbacks,2,2,maxNodesCount,_connectionStatusBuffer_forNodes_1r0,_dstNodeRepositoryProtocolBufferr2,_inputProtocolBuffer_forNodes_1r0);
    _bufferToProtocol_0 = new com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol(publishedBuffersNames,_bufferToProtocol_0_readers,_bufferToProtocol_0_readers_callbacks,0,maxNodesCount,_outputProtocolBufferw0,_connectionStatusBuffer_publishr0,_dstNodeRepositoryProtocolBufferr3);
    _bufferToProtocol_1 = new com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol(publishedBuffersNames,_bufferToProtocol_1_readers,_bufferToProtocol_1_readers_callbacks,1,maxNodesCount,_outputProtocolBufferw1,_connectionStatusBuffer_publishr1,_dstNodeRepositoryProtocolBufferr4);
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[7];
    arrContainers[0] = _connStatusDispatcher.getRunnables();
    arrContainers[1] = _localNodeRepositoryProtocolSource.getRunnables();
    arrContainers[2] = _webSocket.getRunnables();
    arrContainers[3] = _protocolToBuffer_0.getRunnables();
    arrContainers[4] = _protocolToBuffer_1.getRunnables();
    arrContainers[5] = _bufferToProtocol_0.getRunnables();
    arrContainers[6] = _bufferToProtocol_1.getRunnables();

    runnables.setContainers(arrContainers);
    return runnables;
  }
/*[[[end]]] (checksum: 2632451b635f9295b77472434c337a02)*/

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

