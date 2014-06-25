
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
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnetsTransports.types.*;
public class webSocket implements RunnableStoppable{
  String[] publishedBuffersNames;int maxNodesCount;String initialConnection;int bindPort;SSLContext sslContext;writer[] nodesReceivers;writer w0;reader r0;reader r1;reader r2;reader rSelect;selector readersSelector;
  
  public webSocket(String[] publishedBuffersNames,int maxNodesCount,String initialConnection,int bindPort,SSLContext sslContext,writer[] nodesReceivers,writer w0,reader r0,reader r1,reader r2){
    this.publishedBuffersNames = publishedBuffersNames;
    this.maxNodesCount = maxNodesCount;
    this.initialConnection = initialConnection;
    this.bindPort = bindPort;
    this.sslContext = sslContext;
    this.nodesReceivers = nodesReceivers;
    this.w0 = w0;
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
/*[[[end]]] (checksum: ce5d05bfa955adced362cf7d683a253c) */

  private nodeBufIndex[] nodes;
  private connectionsRegistry conManager = null;

  private AtomicBoolean makeReconnection = new AtomicBoolean(false);
  private WSServer server = null;
  private volatile WSClient client = null;

  private void onKernels() {

  }
  
  private void onCreate(){
    conManager = new connectionsRegistry(maxNodesCount);
    if(publishedBuffersNames == null){return;}
    /*local storage for all nodes and all localBuffers*/
    nodes = new nodeBufIndex[maxNodesCount*publishedBuffersNames.length];
    for(int i=0; i<nodes.length; i++){
      nodes[i] = new nodeBufIndex();
    }
    for(int i=0; i<maxNodesCount; i++){
      for(int bufferIndex=0; bufferIndex<publishedBuffersNames.length; bufferIndex++){
        nodeBufIndex node = nodes[i * publishedBuffersNames.length + bufferIndex];
        node.setDstBufferIndex(-1);
        node.setPublishedName(publishedBuffersNames[bufferIndex]);
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
    bufferReadData r = rSelect.readNextWithMeta(-1);
    if(r.getData()==null){return;}

    switch ((int) r.getNested_buffer_id()){
      case 0:
        sendToNodes((cnetsProtocol) r.getData());
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

  private void sendToNodes(cnetsProtocol writeProtocol) {
    writeProtocol.serialize();
    if(writeProtocol.isPublished()) {
      for (int i = 0; i < maxNodesCount; i++) {
        nodeBufIndex node = nodes[i * publishedBuffersNames.length + (int)writeProtocol.getBufferIndex()];
        if(node.getDstBufferIndex()>=0){
          conManager.sendToNode(node.getNodeUniqueId(), writeProtocol.getData());
        }
      }
    }else{
      for(int i=0; i<writeProtocol.getNodeUniqueIds().length; i++) {
        if(writeProtocol.getNodeUniqueIds()[i] < 0){break;}
        int nodeIndex = writeProtocol.getNodeUniqueIds()[i] % maxNodesCount;
        nodeBufIndex node = nodes[nodeIndex * publishedBuffersNames.length + (int) writeProtocol.getBufferIndex()];
        if (node.getDstBufferIndex() >= 0) {
          conManager.sendToNode(writeProtocol.getNodeUniqueIds()[i], writeProtocol.getData());
        } else {
          System.err.printf("webSocket: sendToNode: sending to node %d of %d nodes with buffer index %d FAILED, " +
                  "because destination doesn't have this buffer entry (strange error, packet should be filtered out in " +
                  "bufferToProtocol module)\n",
              writeProtocol.getNodeUniqueIds()[i],
              maxNodesCount,
              writeProtocol.getBufferIndex()
          );
        }
      }
    }
  }

  private void processConnectionsConfig(cnetsConnections data) {

  }

  private void processRepositoryUpdate(nodeRepositoryProtocol update) {
    int internalNodeIndex = (update.nodeId%maxNodesCount);
    String[] names = update.bufferNames;
    /*searching locally names equal to remote buffer names*/
    for(int i=0; i<names.length; i++){
      for(int bufferIndx=0; bufferIndx<publishedBuffersNames.length; bufferIndx++){
        nodeBufIndex node = nodes[internalNodeIndex * publishedBuffersNames.length + bufferIndx];
        node.setDstBufferIndex(-1);
        if(node.getPublishedName().equals(names[i])){
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
    int nodeIndex = conManager.findConnectionId(hashKey);
    int id = conManager.findUniqueConnectionId(hashKey);

    for(int bufferIndex=0; bufferIndex<publishedBuffersNames.length; bufferIndex++) {
      nodes[id * publishedBuffersNames.length + bufferIndex].setNodeUniqueId(id);
      nodes[id * publishedBuffersNames.length + bufferIndex].setDstBufferIndex(-1);
    }
    sendConnStatus(nodeIndex, id, true);
  }

  public void onClose(String hashKey) {
    int id = conManager.findUniqueConnectionId(hashKey);
    int nodeIndex = conManager.findConnectionId(hashKey);
    conManager.removeConnection(hashKey);
    for(int bufferIndex=0; bufferIndex<publishedBuffersNames.length; bufferIndex++) {
      nodes[id * publishedBuffersNames.length+bufferIndex].setDstBufferIndex(-1);
    }
    sendConnStatus(nodeIndex, id, false);
  }

  private void sendConnStatus(int nodeIndex, int id, boolean status){
    connectionStatus conStatus = null;
    if(w0 != null) {
      conStatus = null;
      while (conStatus == null) {
        conStatus = (connectionStatus) w0.writeNext(-1);
      }
      conStatus.setNodeIndex(nodeIndex);
      conStatus.setId(id);
      conStatus.setOn(status);
      w0.writeFinished();
    }
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
      receivedProtocol = (cnetsProtocol) receiver.writeNext(-1);
    }
    receivedProtocol.setData(msg);

    receivedProtocol.deserialize();

    receivedProtocol.getNodeUniqueIds()[0] = nodeIndex;

    receiver.writeFinished();
  }
}

