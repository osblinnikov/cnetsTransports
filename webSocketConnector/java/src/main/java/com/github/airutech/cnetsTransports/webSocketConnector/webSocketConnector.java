
package com.github.airutech.cnetsTransports.webSocketConnector;

import com.github.airutech.cnetsTransports.types.connectionStatus;

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
public class webSocketConnector implements RunnableStoppable{
  cnetsProtocol[] _outputProtocolBuffer_0_Arr;nodeRepositoryProtocol[] _nodeRepositoryProtocolBufferArr;cnetsConnections[] _connectionsBufferArr;connectionStatus[] _connectionStatusBuffer_forNodes_0_Arr;cnetsProtocol[] _inputProtocolBuffer_forNodes_0_Arr;int _protocolToBuffer_cnt = 1;writer[] _protocolToBuffer_0_writers;deserializeStreamCallback[] _protocolToBuffer_0_writers_callbacks;reader[] _bufferToProtocol_0_readers;serializeStreamCallback[] _bufferToProtocol_0_readers_callbacks;writer[] _webSocket_nodesReceivers_writers;writer[] _webSocket_connectionStatusReceivers_writers;reader[] _webSocket_buffers_readers;String serverUrl;int bindPort;int maxNodesCount;int buffersLengths;int binBuffersSize;writer w0;reader r0;
  
  public webSocketConnector(String serverUrl,int bindPort,int maxNodesCount,int buffersLengths,int binBuffersSize,writer w0,reader r0){
    this.serverUrl = serverUrl;
    this.bindPort = bindPort;
    this.maxNodesCount = maxNodesCount;
    this.buffersLengths = buffersLengths;
    this.binBuffersSize = binBuffersSize;
    this.w0 = w0;
    this.r0 = r0;
    this._outputProtocolBuffer_0_Arr = new cnetsProtocol[2];
    this._nodeRepositoryProtocolBufferArr = new nodeRepositoryProtocol[2];
    this._connectionsBufferArr = new cnetsConnections[2];
    this._connectionStatusBuffer_forNodes_0_Arr = new connectionStatus[2];
    this._inputProtocolBuffer_forNodes_0_Arr = new cnetsProtocol[2];
    this._protocolToBuffer_cnt = 1;
    this._protocolToBuffer_0_writers = new writer[1];
    this._protocolToBuffer_0_writers_callbacks = new deserializeStreamCallback[1];
    this._bufferToProtocol_0_readers = new reader[1];
    this._bufferToProtocol_0_readers_callbacks = new serializeStreamCallback[1];
    this._webSocket_nodesReceivers_writers = new writer[1];
    this._webSocket_connectionStatusReceivers_writers = new writer[1];
    this._webSocket_buffers_readers = new reader[1];
    onCreate();
    initialize();
  }
com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer _protocolToBuffer_0;com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol _bufferToProtocol_0;com.github.airutech.cnetsTransports.webSocket.webSocket _webSocket;com.github.airutech.cnets.mapBuffer.mapBuffer _outputProtocolBuffer_0;com.github.airutech.cnets.mapBuffer.mapBuffer _nodeRepositoryProtocolBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionsBuffer;com.github.airutech.cnets.mapBuffer.mapBuffer _connectionStatusBuffer_forNodes_0;com.github.airutech.cnets.mapBuffer.mapBuffer _inputProtocolBuffer_forNodes_0;
  private void initialize(){
    
    _outputProtocolBuffer_0 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_outputProtocolBuffer_0_Arr,2000L,"0L",1,1000);
    reader _outputProtocolBuffer_0r0 = _outputProtocolBuffer_0.getReader(0);
    writer _outputProtocolBuffer_0w0 = _outputProtocolBuffer_0.getWriter(0);
    _nodeRepositoryProtocolBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_nodeRepositoryProtocolBufferArr,2000L,"1L",3,1000);
    reader _nodeRepositoryProtocolBufferr0 = _nodeRepositoryProtocolBuffer.getReader(0);
    reader _nodeRepositoryProtocolBufferr1 = _nodeRepositoryProtocolBuffer.getReader(1);
    reader _nodeRepositoryProtocolBufferr2 = _nodeRepositoryProtocolBuffer.getReader(2);
    writer _nodeRepositoryProtocolBufferw0 = _nodeRepositoryProtocolBuffer.getWriter(0);
    _connectionsBuffer = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionsBufferArr,2000L,"2L",1,1000);
    reader _connectionsBufferr0 = _connectionsBuffer.getReader(0);
    writer _connectionsBufferw0 = _connectionsBuffer.getWriter(0);
    _connectionStatusBuffer_forNodes_0 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_connectionStatusBuffer_forNodes_0_Arr,2000L,"3L",1,1000);
    reader _connectionStatusBuffer_forNodes_0r0 = _connectionStatusBuffer_forNodes_0.getReader(0);
    reader _connectionStatusBuffer_forNodes_0r1 = _connectionStatusBuffer_forNodes_0.getReader(1);
    _inputProtocolBuffer_forNodes_0 = new com.github.airutech.cnets.mapBuffer.mapBuffer((Object[])_inputProtocolBuffer_forNodes_0_Arr,2000L,"4L",1,1000);
    reader _inputProtocolBuffer_forNodes_0r0 = _inputProtocolBuffer_forNodes_0.getReader(0);
    onKernels();
    
    _protocolToBuffer_0 = new com.github.airutech.cnetsTransports.protocolToBuffer.protocolToBuffer(_protocolToBuffer_0_writers,_protocolToBuffer_0_writers_callbacks,_protocolToBuffer_cnt,maxNodesCount,_nodeRepositoryProtocolBufferw0,_connectionStatusBuffer_forNodes_0r0,_nodeRepositoryProtocolBufferr0,_inputProtocolBuffer_forNodes_0r0);
    _bufferToProtocol_0 = new com.github.airutech.cnetsTransports.bufferToProtocol.bufferToProtocol(_bufferToProtocol_0_readers,_bufferToProtocol_0_readers_callbacks,0,maxNodesCount,_outputProtocolBuffer_0w0,_connectionStatusBuffer_forNodes_0r1,_nodeRepositoryProtocolBufferr1);
    _webSocket = new com.github.airutech.cnetsTransports.webSocket.webSocket(maxNodesCount,serverUrl,-1,null,_webSocket_nodesReceivers_writers,_webSocket_connectionStatusReceivers_writers,_webSocket_buffers_readers,_outputProtocolBuffer_0r0,_connectionsBufferr0,_nodeRepositoryProtocolBufferr2);
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[3];
    arrContainers[0] = _protocolToBuffer_0.getRunnables();
    arrContainers[1] = _bufferToProtocol_0.getRunnables();
    arrContainers[2] = _webSocket.getRunnables();

    runnables.setContainers(arrContainers);
    return runnables;
  }
/*[[[end]]] (checksum: edd5dd0f1bf391ae69cc38386cdd9f06)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    
    _protocolToBuffer_0.run();
    _bufferToProtocol_0.run();
    _webSocket.run();
  }

  @Override
  public void onStop(){

  }

}

