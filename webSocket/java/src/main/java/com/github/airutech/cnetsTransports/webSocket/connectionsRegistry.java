package com.github.airutech.cnetsTransports.webSocket;

import com.github.airutech.cnets.queue.queue;
import com.github.airutech.cnets.types.QueueEmptyException;
import com.github.airutech.cnets.types.QueueFullException;

/**
 * Created by oleg on 5/8/14.
 */
public class connectionsRegistry {
  class conContainer{
    public webSocketConnection connection = null;
    public String keyCode = null;
    public long[] buffers = null;
    public int uniqueId;
  }
  private queue connectionsIdsQueue = null;
  private queue newConnections = null;
  private conContainer[] arrContainers = null;
  private int buffersCapacity;
  public connectionsRegistry(int capacity, int buffersCapacity){
    this.buffersCapacity = buffersCapacity;
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
      arrContainers[i].buffers = new long[buffersCapacity];
      for(int j=0;j<buffersCapacity;j++){
        arrContainers[i].buffers[j] = -1;
      }
    }
  }

  public webSocketConnection nextNewConnection(){
    try {
      Integer id = (Integer) newConnections.dequeue();
      return arrContainers[id].connection;
    } catch (QueueEmptyException e) {
      return null;
    }
  }

  public boolean addConnection(String keyCode, webSocketConnection connection){
    Integer id = findConnectionId(keyCode);
    if(id <0) {
      try {
        id = (Integer) connectionsIdsQueue.dequeue();
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

  public boolean setBuffers(String hashKey, Long[] ids) {
    int id = findConnectionId(hashKey);
    if(id<0){return false;}
    for(int i=0; i<buffersCapacity;i++){
      if(ids.length > i) {
        arrContainers[id].buffers[i] = ids[i];
      }else{
        arrContainers[id].buffers[i] = -1;
      }
    }
    return true;
  }

  public void sendToNode(int nodeId, long bufferId, byte[] data, int data_size){
    if(nodeId>=0){
      int nodeIndx = nodeId;
      if(nodeId > arrContainers.length){
        nodeIndx = nodeId%arrContainers.length;
      }
      if(arrContainers[nodeIndx].uniqueId != nodeId){
        System.err.printf("sendToNode: node unique %d id do not match %d\n",arrContainers[nodeIndx].uniqueId,nodeId);
        return;
      }
      sendForBufferId(nodeIndx, bufferId,data, data_size);
    }else {
      for (int i = 0; i < arrContainers.length; i++) {
        sendForBufferId(i, bufferId, data, data_size);
      }
    }
  }

  private void sendForBufferId(int nodeIndx, long bufferId,byte[] data, int data_size){
    for(int j=0; j<buffersCapacity; j++){
      if(arrContainers[nodeIndx].buffers[j] == -1){return;}
      if(arrContainers[nodeIndx].buffers[j] == bufferId){
        arrContainers[nodeIndx].connection.send(data, data_size);
        break;
      }
    }
  }

}
