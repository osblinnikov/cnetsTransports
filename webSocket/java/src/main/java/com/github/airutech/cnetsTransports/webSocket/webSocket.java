
package com.github.airutech.cnetsTransports.webSocket;

import com.github.airutech.cnets.types.bufferReadData;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.java_websocket.server.DefaultSSLWebSocketServerFactory;
import org.java_websocket.server.WebSocketServer.WebSocketServerFactory;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.mapBuffer.*;
public class webSocket implements RunnableStoppable{
  Long[] inBuffersIds;int maxNodesCount;int maxBuffersCount;String initialConnection;int bindPort;connectionStatusCallback connectionStatus;SSLContext sslContext;writer w0;reader r0;reader r1;reader rSelect;selector readersSelector;
  
  public webSocket(Long[] inBuffersIds,int maxNodesCount,int maxBuffersCount,String initialConnection,int bindPort,connectionStatusCallback connectionStatus,SSLContext sslContext,writer w0,reader r0,reader r1){
    this.inBuffersIds = inBuffersIds;
    this.maxNodesCount = maxNodesCount;
    this.maxBuffersCount = maxBuffersCount;
    this.initialConnection = initialConnection;
    this.bindPort = bindPort;
    this.connectionStatus = connectionStatus;
    this.sslContext = sslContext;
    this.w0 = w0;
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
/*[[[end]]] (checksum: 6f3331cf6328cafacef3f386e011087a) */
  private connectionsRegistry conManager = null;
  private Lock connectionsLock = new ReentrantLock();

  private AtomicBoolean makeReconnection = new AtomicBoolean(false);
  private WSServer server = null;
  private volatile WSClient client = null;

  private void onKernels() {

  }
  
  private void onCreate(){
    conManager = new connectionsRegistry(maxNodesCount,maxBuffersCount);
  }

  @Override
  public synchronized void onStart(){
    connect();
  }

  @Override
  public void run(){
    Thread.currentThread().setName("webSocket");
    bufferReadData r = rSelect.readNextWithMeta(true);
    if(r.getData()!=null){
      switch ((int) r.getNested_buffer_id()){
        case 0:
          cnetsProtocolBinary binary = (cnetsProtocolBinary) r.getData();
          connectionsLock.lock();
          conManager.sendToNode(binary.getNodeId(), binary.getBufferId(), binary.getData(), binary.getData_size());
          connectionsLock.unlock();
          break;
        case 1:
          cnetsConnections connectionsConfig = (cnetsConnections) r.getData();
          break;
      }
      rSelect.readFinished();
    }

    if(makeReconnection.getAndSet(false)) {
      disconnect();
      connect();
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
        connectionsLock.lock();
        if(conManager.getCountOfConnections() > 0) {
          connectionsLock.unlock();
          client.closeBlocking();
        }else {
          connectionsLock.unlock();
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
    connectionsLock.lock();
    conManager.addConnection(hashKey,webSocket);

    if(inBuffersIds!=null) {
      /*necessary to create a new thread because webSockets implementation has bugs*/
      final byte[] bytes = bufferUtils.serializeBuffers(inBuffersIds);
      if (bytes != null) {
        new Thread() {
          @Override
          public void run() {
            connectionsLock.lock();
            webSocketConnection c = null;
            while ((c = conManager.nextNewConnection()) != null) {
              c.send(bytes, bytes.length);
            }
            connectionsLock.unlock();
          }
        }.start();
      }
    }
    if(connectionStatus != null) {
      int id = conManager.findUniqueConnectionId(hashKey);
      connectionStatus.onConnect(id, null);
    }
    connectionsLock.unlock();
  }

  public void onClose(String hashKey) {
    connectionsLock.lock();
    if(connectionStatus!=null) {
      int id = conManager.findUniqueConnectionId(hashKey);
      connectionStatus.onDisconnect(id);
    }
    conManager.removeConnection(hashKey);
    connectionsLock.unlock();
  }

  public void onMessage(String hashKey, String msg) {

  }

  public void onMessage(String hashKey, ByteBuffer msg) {
    if(inBuffersIds!=null) {
      Long[] ids = bufferUtils.deserializeBuffers(msg);
      if (ids != null) {
        connectionsLock.lock();
        if(connectionStatus!=null) {
          int id = conManager.findUniqueConnectionId(hashKey);
          connectionStatus.onConnect(id, ids);
        }
        conManager.setBuffers(hashKey, ids);
        connectionsLock.unlock();
        return;
      }
      msg.position(0);
    }
    connectionsLock.lock();
    int nodeId = conManager.findUniqueConnectionId(hashKey);
    connectionsLock.unlock();
    if(nodeId<0){
      System.err.println("webSocket: onMessage: connection for "+hashKey+" was not found");
      return;
    }
    cnetsProtocolBinary bProtocol = null;
    while (bProtocol == null) {
      bProtocol = (cnetsProtocolBinary) w0.writeNext(true);
      if(bProtocol==null){
        System.err.println("webSocket: onMessage: timeout on writeNext cnetsProtocolBinary exceed, I will try again");
      }
    }
    bProtocol.setData(msg.array(),msg.limit());
    bProtocol.setNodeId(nodeId);
    bProtocol.setBufferId(-1);/*we don't know it here yet*/
    w0.writeFinished();
  }
}

