package com.github.airutech.cnetsTransports.sockjs;

import com.github.airutech.cnets.queue.queue;
import com.github.airutech.cnets.types.QueueEmptyException;
import com.github.airutech.cnets.types.QueueFullException;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.WebSocket;
import org.vertx.java.core.sockjs.SockJSSocket;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class connectionsRegistry {
  class conContainer{
    public WebSocket webSocketConnection = null;
    public SockJSSocket sockJsConnection = null;
    public String keyCode = null;
    public int uniqueId;
  }
  private Lock connectionsLock = new ReentrantLock();
  private queue connectionsIdsQueue = null;
  //  private queue newConnections = null;
  private conContainer[] arrContainers = null;
  public connectionsRegistry(int capacity){
    connectionsIdsQueue = new queue(capacity);
//    newConnections = new queue(capacity);
    arrContainers = new conContainer[capacity];
    for(int i=0 ; i<capacity; i++){
      try {
        connectionsIdsQueue.enqueue(new Integer(i));
      } catch (QueueFullException e) {
        e.printStackTrace();
      }
      arrContainers[i] = new conContainer();
      arrContainers[i].uniqueId = i-arrContainers.length;
    }
  }

//  public webSocketConnection nextNewConnection(){
//    try {
//      int id = (int)newConnections.dequeue();
//      return arrContainers[id].connection;
//    } catch (QueueEmptyException e) {
//      return null;
//    }
//  }

  public boolean addConnection(String keyCode, SockJSSocket connection, WebSocket webSocket){
    connectionsLock.lock();
    int id = findConnectionId(keyCode);
    boolean res = false;
    if(id <0) {
      try {
        id = (int) connectionsIdsQueue.dequeue();
      } catch (QueueEmptyException e) {
        e.printStackTrace();
        connectionsLock.unlock();
        return res;
      }
      arrContainers[id].webSocketConnection = webSocket;
      arrContainers[id].sockJsConnection = connection;
      arrContainers[id].keyCode = keyCode;
      arrContainers[id].uniqueId += arrContainers.length;
      if(arrContainers[id].uniqueId<0){arrContainers[id].uniqueId = id;}
//      try {
//        newConnections.enqueue(new Integer(id));
//      } catch (QueueFullException e) {
//        e.printStackTrace();
//      }
      res = true;
    }else{
      System.err.printf("addConnection: socket container already registered\n");
      res = false;
    }
    connectionsLock.unlock();
    return res;
  }

  public boolean removeConnection(String keyCode){
    Integer id = findConnectionId(keyCode);
    if(id <0) {
      System.err.printf("removeConnection: socket container was not registered\n");
      return false;
    }
    connectionsLock.lock();
    arrContainers[id].keyCode = null;
    arrContainers[id].sockJsConnection = null;
    arrContainers[id].webSocketConnection = null;
    try {
      connectionsIdsQueue.enqueue(id);
    } catch (QueueFullException e) {
      e.printStackTrace();
    }
    connectionsLock.unlock();
    return true;
  }

//  public webSocketConnection findConnection(String keyCode){
//    if(keyCode==null){
//      return null;
//    }
//    int id = findConnectionId(keyCode);
//    if(id<0){return null;}
//    return arrContainers[id].connection;
//  }

  public int findUniqueConnectionId(String hashKey) {
    int id = findConnectionId(hashKey);
    connectionsLock.lock();
    if(id<0){connectionsLock.unlock();return -1;}
    int uid = arrContainers[id].uniqueId;
    connectionsLock.unlock();
    return uid;
  }

  private int findConnectionId(String keyCode){
    if(keyCode==null){
      return -1;
    }
    connectionsLock.lock();
    for(int i=0; i<arrContainers.length; i++){
      if(arrContainers[i].keyCode!=null && arrContainers[i].keyCode.equals(keyCode)){
        connectionsLock.unlock();
        return i;
      }
    }
    connectionsLock.unlock();
    return -1;
  }

  public int getCountOfConnections() {
    int countOfCounnections = 0;
    connectionsLock.lock();
    for(int i=0; i<arrContainers.length; i++){
      if(arrContainers[i].keyCode!=null){
        countOfCounnections++;
      }
    }
    connectionsLock.unlock();
    return countOfCounnections;
  }

  public void sendToNode(int nodeId, ByteBuffer bb){
    connectionsLock.lock();
    if(nodeId>=0){
      int nodeIndx = nodeId%arrContainers.length;
      if(arrContainers[nodeIndx].uniqueId != nodeId){
        System.err.printf("sendToNode: node unique %d id do not match %d\n",arrContainers[nodeIndx].uniqueId,nodeId);
        connectionsLock.unlock();
        return;
      }
      Buffer buf = new Buffer();
      buf.appendBytes(bb.array(),0,bb.limit());
      if(arrContainers[nodeIndx].sockJsConnection != null) {
        arrContainers[nodeIndx].sockJsConnection.write(buf);
        System.out.println("sockJsConnection");
        if (arrContainers[nodeIndx].sockJsConnection.writeQueueFull()) {
          arrContainers[nodeIndx].sockJsConnection.pause();
          System.out.println("pause");
        }
      }else if(arrContainers[nodeIndx].webSocketConnection != null){
        arrContainers[nodeIndx].webSocketConnection.write(buf);
        System.out.println("webSocketConnection");
        if (arrContainers[nodeIndx].webSocketConnection.writeQueueFull()) {
          arrContainers[nodeIndx].webSocketConnection.pause();
          System.out.println("pause");
        }
      }else{
        System.err.println("sockjs: sendToNode: neither webSocketConnection nor sockJsConnection found\n");
      }
    }

/*this is wrong,because not every node can have the buffer transferred
    else {
      for (int i = 0; i < arrContainers.length; i++) {
        arrContainers[i].connection.send(bb);
      }
    }
*/
    connectionsLock.unlock();
  }

}
