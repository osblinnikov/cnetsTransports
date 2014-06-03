package com.github.airutech.cnetsTransports.webSocket;

import com.github.airutech.cnets.queue.queue;
import com.github.airutech.cnets.types.QueueEmptyException;
import com.github.airutech.cnets.types.QueueFullException;

import java.nio.ByteBuffer;

public class connectionsRegistry {
  class conContainer{
    public webSocketConnection connection = null;
    public String keyCode = null;
    public int uniqueId;
  }
  private queue connectionsIdsQueue = null;
  private queue newConnections = null;
  private conContainer[] arrContainers = null;
  public connectionsRegistry(int capacity){
    connectionsIdsQueue = new queue(capacity);
    newConnections = new queue(capacity);
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

  public webSocketConnection nextNewConnection(){
    try {
      int id = (int)newConnections.dequeue();
      return arrContainers[id].connection;
    } catch (QueueEmptyException e) {
      return null;
    }
  }

  public boolean addConnection(String keyCode, webSocketConnection connection){
    int id = findConnectionId(keyCode);
    if(id <0) {
      try {
        id = (int) connectionsIdsQueue.dequeue();
      } catch (QueueEmptyException e) {
        e.printStackTrace();
        return false;
      }

      arrContainers[id].connection = connection;
      arrContainers[id].keyCode = keyCode;
      arrContainers[id].uniqueId += arrContainers.length;
      if(arrContainers[id].uniqueId<0){arrContainers[id].uniqueId = id;}
      try {
        newConnections.enqueue(new Integer(id));
      } catch (QueueFullException e) {
        e.printStackTrace();
      }
      return true;
    }else{
      System.err.printf("addConnection: socket container already registered\n");
      return false;
    }
  }

  public boolean removeConnection(String keyCode){
    Integer id = findConnectionId(keyCode);
    if(id <0) {
      System.err.printf("removeConnection: socket container was not registered\n");
      return false;
    }
    arrContainers[id].keyCode = null;
    arrContainers[id].connection = null;
    try {
      connectionsIdsQueue.enqueue(id);
    } catch (QueueFullException e) {
      e.printStackTrace();
    }
    return true;
  }

  public webSocketConnection findConnection(String keyCode){
    if(keyCode==null){
      return null;
    }
    int id = findConnectionId(keyCode);
    if(id<0){return null;}
    return arrContainers[id].connection;
  }

  public int findUniqueConnectionId(String hashKey) {
    int id = findConnectionId(hashKey);
    if(id<0){return -1;}
    return arrContainers[id].uniqueId;
  }

  private int findConnectionId(String keyCode){
    if(keyCode==null){
      return -1;
    }
    for(int i=0; i<arrContainers.length; i++){
      if(arrContainers[i].keyCode!=null && arrContainers[i].keyCode.equals(keyCode)){
        return i;
      }
    }
    return -1;
  }

  public int getCountOfConnections() {
    int countOfCounnections = 0;
    for(int i=0; i<arrContainers.length; i++){
      if(arrContainers[i].keyCode!=null){
        countOfCounnections++;
      }
    }
    return countOfCounnections;
  }

  public void sendToNode(int nodeId, ByteBuffer bb){
    if(nodeId>=0){
      int nodeIndx = nodeId;
      if(nodeId > arrContainers.length){
        nodeIndx = nodeId%arrContainers.length;
      }
      if(arrContainers[nodeIndx].uniqueId != nodeId){
        System.err.printf("sendToNode: node unique %d id do not match %d\n",arrContainers[nodeIndx].uniqueId,nodeId);
        return;
      }
      arrContainers[nodeIndx].connection.send(bb);
    }else {
      for (int i = 0; i < arrContainers.length; i++) {
        arrContainers[i].connection.send(bb);
      }
    }
  }

}
