
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
  int maxNodesCount;String initialConnection;int bindPort;SSLContext sslContext;writer[] nodesReceivers;writer[] connectionStatusReceivers;reader r0;reader r1;reader rSelect;selector readersSelector;
  
  public webSocket(int maxNodesCount,String initialConnection,int bindPort,SSLContext sslContext,writer[] nodesReceivers,writer[] connectionStatusReceivers,reader r0,reader r1){
    this.maxNodesCount = maxNodesCount;
    this.initialConnection = initialConnection;
    this.bindPort = bindPort;
    this.sslContext = sslContext;
    this.nodesReceivers = nodesReceivers;
    this.connectionStatusReceivers = connectionStatusReceivers;
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
/*[[[end]]] (checksum: 079aa7d3375bebe1e26e9060c32f62e8) */
  private connectionsRegistry conManager = null;

  private AtomicBoolean makeReconnection = new AtomicBoolean(false);
  private WSServer server = null;
  private volatile WSClient client = null;

  private void onKernels() {

  }
  
  private void onCreate(){
    conManager = new connectionsRegistry(maxNodesCount);
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
        cnetsProtocol writeProtocol = (cnetsProtocol) r.getData();
        writeProtocol.serialize();
        conManager.sendToNode(writeProtocol.getNodeUniqueId(), writeProtocol.getData());
        break;
      case 1:
        cnetsConnections connectionsConfig = (cnetsConnections) r.getData();
        break;
    }

    rSelect.readFinished();
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

