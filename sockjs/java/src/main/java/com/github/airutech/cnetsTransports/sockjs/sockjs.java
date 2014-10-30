
package com.github.airutech.cnetsTransports.sockjs;


import com.github.airutech.cnets.types.bufferReadData;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol;
import com.github.airutech.cnetsTransports.types.cnetsConnections;
import com.github.airutech.cnetsTransports.types.cnetsProtocol;
import com.github.airutech.cnetsTransports.types.connectionStatus;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.DenyAllFilter;
import org.apache.log4j.varia.LevelMatchFilter;
import org.apache.log4j.varia.StringMatchFilter;
import org.vertx.java.core.*;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.WebSocket;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSServer;
import org.vertx.java.core.sockjs.SockJSSocket;
import javax.net.ssl.SSLContext;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.types.*;
import com.github.airutech.cnets.mapBuffer.*;
public class sockjs implements RunnableStoppable{
  String[] publishedBuffersNames;int maxNodesCount;String initialConnection;int bindPort;SSLContext sslContext;writer w0;writer w1;reader r0;reader r1;reader rSelect;selector readersSelector;
  
  public sockjs(String[] publishedBuffersNames,int maxNodesCount,String initialConnection,int bindPort,SSLContext sslContext,writer w0,writer w1,reader r0,reader r1){
    this.publishedBuffersNames = publishedBuffersNames;
    this.maxNodesCount = maxNodesCount;
    this.initialConnection = initialConnection;
    this.bindPort = bindPort;
    this.sslContext = sslContext;
    this.w0 = w0;
    this.w1 = w1;
    this.r0 = r0;
    this.r1 = r1;
    reader[] arrReaders = new reader[2];
    arrReaders[0] = r0;
    arrReaders[1] = r1;
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
/*[[[end]]] (checksum: a4e0c3a4b1f1aaa50c0dec444b85a9f9) */

//  private nodeBufIndex[] nodes;
  private connectionsRegistry conManager = null;
  private AtomicBoolean makeReconnection = new AtomicBoolean(false);

  private void onCreate(){
    PatternLayout pattern = new PatternLayout();
    pattern.setConversionPattern("%-4r [%t] %-5p %c %x - %m%n");
    org.apache.log4j.Appender appender = new ConsoleAppender(pattern,"System.out");
    appender.addFilter(new DenyAllFilter());
    BasicConfigurator.configure(appender);
    conManager = new connectionsRegistry(maxNodesCount);
    if(publishedBuffersNames == null){return;}
    // if(nodesReceivers != null) {
    //   if(maxNodesCount < nodesReceivers.length){
    //     System.err.printf("sockjs: onCreate: maxNodesCount < nodesReceivers.length\n");
    //   }
    // }
    /*local storage for all nodes and all localBuffers*/
//    nodes = new nodeBufIndex[maxNodesCount*publishedBuffersNames.length];
//    for(int i=0; i<nodes.length; i++){
//      nodes[i] = new nodeBufIndex();
//    }
//    for(int i=0; i<maxNodesCount; i++){
//      for(int bufferIndex=0; bufferIndex<publishedBuffersNames.length; bufferIndex++){
//        nodeBufIndex node = nodes[i * publishedBuffersNames.length + bufferIndex];
//        node.setConnected(false);
//        node.setDstBufferIndex(-1);
//        node.setPublishedName(publishedBuffersNames[bufferIndex]);
//      }
//    }
  }

  public void onOpen(String hashKey, SockJSSocket sockJsConn, WebSocket wsConn) {
    System.out.println("sockjs: new "+sockJsConn+" "+wsConn+" connection "+hashKey);
    conManager.addConnection(hashKey,sockJsConn, wsConn);
    int id = conManager.findUniqueConnectionId(hashKey);
    if(id < 0){return;}
    if(publishedBuffersNames == null){return;}
//    for(int bufferIndex=0; bufferIndex<publishedBuffersNames.length; bufferIndex++) {
//      nodeBufIndex node = nodes[(id%maxNodesCount) * publishedBuffersNames.length + bufferIndex];
//      node.setNodeUniqueId(id);
////      node.setConnected(true);
//    }
    sendConnStatus(id, true, false);
  }

  public void onClose(String hashKey) {
    System.out.println("sockjs: closed connection "+hashKey);
    int id = conManager.findUniqueConnectionId(hashKey);
    if(id < 0){return;}
    conManager.removeConnection(hashKey);
    if(publishedBuffersNames == null){return;}
//    for(int bufferIndex=0; bufferIndex<publishedBuffersNames.length; bufferIndex++) {
//      nodeBufIndex node = nodes[(id%maxNodesCount) * publishedBuffersNames.length+bufferIndex];
//      if(node.isConnected()) {
//        node.setDstBufferIndex(-1);
//      }
//      node.setConnected(false);
//    }
    sendConnStatus(id, false, false);
  }

  private void sendConnStatus(int id, boolean status, boolean receivedRepo){
    if(w0 != null) {
      connectionStatus conStatus = null;
      while (conStatus == null) {
        conStatus = (connectionStatus) w0.writeNext(-1);
      }
      conStatus.setId(id);
      conStatus.setOn(status);
      conStatus.setReceivedRepo(receivedRepo);
      w0.writeFinished();
    }
  }

  public void onMessage(String hashKey, ByteBuffer msg) {
    int id = conManager.findUniqueConnectionId(hashKey);
    if(id<0){
      System.err.println("sockjs: onMessage: connection for "+hashKey+" was not found");
      return;
    }
    // if(nodesReceivers == null) {return;}
    // double nodesStored = Math.floor((double)maxNodesCount/(double)nodesReceivers.length);
    // int processorId = (int)Math.floor((double)(id%maxNodesCount)/nodesStored);
    // if(processorId >= nodesReceivers.length){
    //   processorId = nodesReceivers.length - 1;//for the last element it is required
    // }
    // writer receiver = nodesReceivers[processorId];
    if(w1 == null){return;}
    cnetsProtocol receivedProtocol = null;
    while(receivedProtocol == null) {
      receivedProtocol = (cnetsProtocol) w1.writeNext(-1);
    }
    receivedProtocol.setData(msg);

    // receivedProtocol.deserialize();
//    System.out.println(".sockjs recv from "+receivedProtocol.getBufferIndex());
//    System.out.printf("%d %d %d\n",receivedProtocol.getBufferIndex(), receivedProtocol.getBunchId(), receivedProtocol.getPacket());
    // if(receivedProtocol.getNodeUniqueIds() == null){
    receivedProtocol.setNodeUniqueIds(new int[]{id});
    // }
    // receivedProtocol.getNodeUniqueIds()[0] = id;

    w1.writeFinished();
  }

  private void onKernels(){

  }

  @Override
  public void onStart(){
    connect();
  }

  @Override
  public void run(){
    Thread.currentThread().setName("sockjs");

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
//      case 2:
//        processRepositoryUpdate((nodeRepositoryProtocol) r.getData());
//        break;
    }
    rSelect.readFinished();
  }

  private void sendToNodes(cnetsProtocol writeProtocol) {
    if(writeProtocol.getNodeUniqueIds() == null){
      writeProtocol.setNodeUniqueIds(new int[1]);
    }
//    System.out.println(".sockjs send from "+writeProtocol.getBufferIndex()+" to "+writeProtocol.isPublished()+" "+ Arrays.toString(writeProtocol.getNodeUniqueIds()));

    if(writeProtocol.isPublished()) {
//      for (int i = 0; i < maxNodesCount; i++) {
//        nodeBufIndex node = nodes[i * publishedBuffersNames.length + (int)writeProtocol.getBufferIndex()];
//        if(writeProtocol.getBufferIndex() == 0 || node.getDstBufferIndex()>=0){
//        if(node.getNodeUniqueId() >= 0) {
      conManager.sendToNode(-1, writeProtocol.getData());
//        }
//        }
//      }
    }else{
      for(int i=0; i<writeProtocol.getNodeUniqueIds().length; i++) {
        if(writeProtocol.getNodeUniqueIds()[i] < 0){break;}
//        int nodeIndex = writeProtocol.getNodeUniqueIds()[i] % maxNodesCount;
//        nodeBufIndex node = nodes[nodeIndex * publishedBuffersNames.length + (int) writeProtocol.getBufferIndex()];
//        if (writeProtocol.getBufferIndex() == 0 || node.getDstBufferIndex() >= 0) {
//          System.out.println(".sockjs sending from  "+writeProtocol.isPublished());
        conManager.sendToNode(writeProtocol.getNodeUniqueIds()[i], writeProtocol.getData());
//        } else {
//          System.err.printf("sockjs: sendToNode: sending to node %d of %d nodes with buffer index %d FAILED, " +
//                  "because destination doesn't have this buffer entry (strange error, packet should be filtered out in " +
//                  "bufferToProtocol module)\n",
//              writeProtocol.getNodeUniqueIds()[i],
//              maxNodesCount,
//              writeProtocol.getBufferIndex()
//          );
//        }
      }
    }
  }

  private void processConnectionsConfig(cnetsConnections data) {

  }

//  private void processRepositoryUpdate(nodeRepositoryProtocol update) {
////    System.out.printf("sockjs: processRepositoryUpdate\n");
//    String[] names = update.subscribedNames;
//    /*searching locally names equal to remote buffer names*/
//    if(names == null){System.err.println("sockjs: bufferNames are null"); return;}
//    boolean isNotLateRepoUpdate = false;
//    for(int i=0; i<names.length; i++){
//      for(int bufferIndx=0; bufferIndx<publishedBuffersNames.length; bufferIndx++){
//        nodeBufIndex node = nodes[(update.nodeId%maxNodesCount) * publishedBuffersNames.length + bufferIndx];
//        if(node.isConnected() && node.getNodeUniqueId() == update.nodeId && node.getPublishedName().equals(names[i])){
//          node.setDstBufferIndex(i);
//          isNotLateRepoUpdate = true;
//          break;
//        }
//      }
//    }
//    if(isNotLateRepoUpdate){
//      sendConnStatus(update.nodeId, true, true);
//    }
//  }

  @Override
  public void onStop(){
    disconnect();
  }

  public void reconnect(){
    makeReconnection.set(true);
  }

  private void disconnect() {
    resetServer();
    resetClient();
  }

  public void resetClient() {
    lockConnector.lock();
    if(client != null){
      client.close();
      client = null;
    }
    lockConnector.unlock();
  }

  final Vertx vertx = VertxFactory.newVertx();
  HttpServer httpServer = null;
  HttpClient client = null;
  SockJSServer server = null;
  Lock lockConnector = new ReentrantLock();

  public void resetServer(){
    lockConnector.lock();
    if(server!=null) {
      server.close();
      httpServer.close();
      httpServer = null;
      server = null;
    }
    lockConnector.unlock();
  }

  private void connect() {
    lockConnector.lock();
    if(server != null || client != null){
      lockConnector.unlock();
      return;
    }

    if(bindPort>0) {
      System.out.println("bind to "+bindPort);
      httpServer = vertx.createHttpServer();
      if(sslContext != null) {
        httpServer.setSSLContext(sslContext);
        httpServer.setSSL(true);
      }
      server = vertx.createSockJSServer(httpServer);
      JsonObject config = new JsonObject();
      config.putString("prefix", "/ws");
      server.installApp(config, new SockJsServerHandler(this));
      httpServer.listen(bindPort, new BindingResult(bindPort, this));

    }else if(initialConnection != null){

      client = vertx.createHttpClient();
      if (sslContext != null) {
        client.setSSLContext(sslContext);
        client.setSSL(true);
      }
      String conStr = initialConnection.replaceAll("ws://", "");
      conStr = conStr.replaceAll("wss://","");
      String[] arr = conStr.split(":");
      if(arr.length > 1) {
        System.out.println("connect to "+initialConnection);
        client.setHost(arr[0]).setPort(Integer.parseInt(arr[1]));
        client.exceptionHandler(new ConnectionResult(this));
        client.connectWebsocket("/ws/websocket", new SockJsClientHandler(this));
      }

    }
    if(server==null && client==null){
      System.err.println("sockjs: Neither Server nor Client can be setup with the current configuration");
    }
    lockConnector.unlock();
  }

}

