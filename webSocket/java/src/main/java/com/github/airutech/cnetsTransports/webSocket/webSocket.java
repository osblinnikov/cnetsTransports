
package com.github.airutech.cnetsTransports.webSocket;

import com.github.airutech.cnets.types.bufferReadData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.java_websocket.server.DefaultSSLWebSocketServerFactory;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnetsTransports.types.*;
public class webSocket implements RunnableStoppable{
  int maxNodesCount;String initialConnection;int bindPort;SSLContext sslContext;writer[] nodesReceivers;writer[] connectionStatusReceivers;reader[] buffersParameters;reader r0;reader r1;reader r2;reader rSelect;selector readersSelector;
  
  public webSocket(int maxNodesCount,String initialConnection,int bindPort,SSLContext sslContext,writer[] nodesReceivers,writer[] connectionStatusReceivers,reader[] buffersParameters,reader r0,reader r1,reader r2){
    this.maxNodesCount = maxNodesCount;
    this.initialConnection = initialConnection;
    this.bindPort = bindPort;
    this.sslContext = sslContext;
    this.nodesReceivers = nodesReceivers;
    this.connectionStatusReceivers = connectionStatusReceivers;
    this.buffersParameters = buffersParameters;
    this.r0 = r0;
    this.r1 = r1;
    this.r2 = r2;
    reader[] arrReaders = new reader[3];
    arrReaders[0] = r0;
    arrReaders[1] = r1;
    arrReaders[2] = r2;
    this.readersSelector = new selector(arrReaders);
    this.rSelect = readersSelector.getReader(0,-1);
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
/*[[[end]]] (checksum: 495b4910412a64e30235a528533b2fea) */

  private nodeBufIndex[] nodes;
  private connectionsRegistry conManager = null;

  private AtomicBoolean makeReconnection = new AtomicBoolean(false);
  private WSServer server = null;
  private volatile WSClient client = null;

  private void onKernels() {

  }
  
  private void onCreate(){
    conManager = new connectionsRegistry(maxNodesCount);
    if(buffersParameters == null){return;}
    /*local storage for all nodes and all localBuffers*/
    nodes = new nodeBufIndex[maxNodesCount*buffersParameters.length];
    for(int i=0; i<maxNodesCount; i++){
      for(int bufferIndex=0; bufferIndex<buffersParameters.length; bufferIndex++){
        nodeBufIndex node = nodes[i * buffersParameters.length + bufferIndex];
        node.setDstBufferIndex(-1);
        node.setR0(buffersParameters[bufferIndex]);
      }
    }
  }

  @Override
  public synchronized void onStart(){
    connect();
  }

  @Override
  public void run(){
    Thread.currentThread().setName("webSocket");

    /*special task for reconnection*/
    if(makeReconnection.getAndSet(false)) {
      disconnect();
      connect();
    }

    /*get next data to read*/
    bufferReadData r = rSelect.readNextWithMeta(true);
    if(r.getData()==null){return;}

    switch ((int) r.getNested_buffer_id()){
      case 0:
        sendToNode((cnetsProtocol)r.getData());
        break;
      case 1:
        processConnectionsConfig((cnetsConnections) r.getData());
        break;
      case 2:
        processRepositoryUpdate((nodeRepositoryProtocol) r.getData());
        break;
    }
    rSelect.readFinished();
  }

  private void sendToNode(cnetsProtocol writeProtocol) {
    writeProtocol.serialize();
    if(writeProtocol.getNodeUniqueId() < 0) {
      for (int i = 0; i < maxNodesCount; i++) {
        nodeBufIndex node = nodes[i * buffersParameters.length + (int)writeProtocol.getBufferIndex()];
        if(node.getDstBufferIndex()>=0){
          conManager.sendToNode(node.getNodeUniqueId(), writeProtocol.getData());
        }
      }
    }else{
      int nodeIndex = writeProtocol.getNodeUniqueId()%maxNodesCount;
      nodeBufIndex node = nodes[nodeIndex * buffersParameters.length + (int)writeProtocol.getBufferIndex()];
      if(node.getDstBufferIndex() >= 0){
        conManager.sendToNode(writeProtocol.getNodeUniqueId(), writeProtocol.getData());
      }else{
        System.err.printf("webSocket: sendToNode: sending to node %d of %d nodes with buffer index %d FAILED, " +
                "because destination doesn't have this buffer entry (strange error, packet should be filtered out in " +
                "bufferToProtocol module)\n",
            writeProtocol.getNodeUniqueId(),
            maxNodesCount,
            writeProtocol.getBufferIndex()
        );
      }
    }
  }

  private void processConnectionsConfig(cnetsConnections data) {

  }

  private void processRepositoryUpdate(nodeRepositoryProtocol update) {
    int internalNodeIndex = (update.getDestinationUniqueNodeId()%maxNodesCount);
    String[] names = update.getBufferNames();
    /*searching locally names equal to remote buffer names*/
    for(int i=0; i<names.length; i++){
      for(int bufferIndx=0; bufferIndx<buffersParameters.length; bufferIndx++){
        nodeBufIndex node = nodes[internalNodeIndex * buffersParameters.length + bufferIndx];
        node.setDstBufferIndex(-1);
        if(node.getR0().uniqueId().equals(names[i])){
          node.setDstBufferIndex(i);
          break;
        }
      }
    }
  }

  @Override
  public  void onStop(){
    disconnect();
  }

  public void reconnect(){
    makeReconnection.set(true);
  }

  private void disconnect() {
    if (server != null) {
      try {
        server.stop();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      server = null;
    }
    if (client != null) {
      try {

        if(conManager.getCountOfConnections() > 0) {
          client.closeBlocking();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      client = null;
    }
  }

  private void connect() {
    if(bindPort>0) {
      try {
        server = new WSServer(bindPort, this);
        if(sslContext!=null) {
          server.setWebSocketFactory(new DefaultSSLWebSocketServerFactory(sslContext));
        }
      } catch (UnknownHostException e) {
        e.printStackTrace();
        server = null;
      }
    }
    if(server == null && initialConnection != null){
      try {
        client = new WSClient(initialConnection, this);
        if(sslContext!=null) {
          SSLSocketFactory factory = sslContext.getSocketFactory();// (SSLSocketFactory) SSLSocketFactory.getDefault();
          client.setSocket(factory.createSocket());
        }
      } catch (URISyntaxException e) {
        e.printStackTrace();
        client = null;
      } catch (IOException e) {
        e.printStackTrace();
        client = null;
      }
    }
    if(server==null && client==null){
      System.err.println("webSocket: Neither Server nor Client can be setup with the current configuration");
    }


    if(server!=null){
      server.start();
    }
    if(client!=null){
      client.connect();
    }
  }

  public void onOpen(String hashKey, webSocketConnection webSocket) {
    conManager.addConnection(hashKey,webSocket);

    if(connectionStatusReceivers == null) {return;}

    int nodeIndex = conManager.findConnectionId(hashKey);
    writer w = connectionStatusReceivers[nodeIndex%connectionStatusReceivers.length];

    int id = conManager.findUniqueConnectionId(hashKey);
    connectionStatus conStatus = null;
    while(conStatus == null) {
      conStatus = (connectionStatus) w.writeNext(true);
    }
    conStatus.setId(id);
    for(int bufferIndex=0; bufferIndex<buffersParameters.length; bufferIndex++) {
      nodes[id * buffersParameters.length + bufferIndex].setNodeUniqueId(id);
      nodes[id * buffersParameters.length + bufferIndex].setDstBufferIndex(-1);
    }
    conStatus.setOn(true);
    w.writeFinished();
  }

  public void onClose(String hashKey) {
    int nodeIndex = conManager.findConnectionId(hashKey);
    writer w = connectionStatusReceivers[nodeIndex%connectionStatusReceivers.length];
    int id = conManager.findUniqueConnectionId(hashKey);
    conManager.removeConnection(hashKey);

    if(connectionStatusReceivers == null) {return;}

    connectionStatus conStatus = null;
    while(conStatus == null) {
      conStatus = (connectionStatus) w.writeNext(true);
    }
    conStatus.setId(id);
    for(int bufferIndex=0; bufferIndex<buffersParameters.length; bufferIndex++) {
      nodes[id * buffersParameters.length+bufferIndex].setDstBufferIndex(-1);
    }
    conStatus.setOn(false);
    w.writeFinished();
  }

  public void onMessage(String hashKey, String msg) {

  }

  public void onMessage(String hashKey, ByteBuffer msg) {
    int nodeIndex = conManager.findConnectionId(hashKey);
    if(nodeIndex<0){
      System.err.println("webSocket: onMessage: connection for "+hashKey+" was not found");
      return;
    }
    if(nodesReceivers == null) {return;}
    writer receiver = nodesReceivers[nodeIndex%nodesReceivers.length];
    cnetsProtocol receivedProtocol = null;
    while(receivedProtocol == null) {
      receivedProtocol = (cnetsProtocol) receiver.writeNext(true);
    }
    receivedProtocol.setData(msg);

    receivedProtocol.deserialize();

    receivedProtocol.setNodeUniqueId(nodeIndex);

    receiver.writeFinished();
  }
}

