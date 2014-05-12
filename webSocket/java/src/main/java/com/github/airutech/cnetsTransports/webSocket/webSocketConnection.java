package com.github.airutech.cnetsTransports.webSocket;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;

/**
 * Created by oleg on 5/8/14.
 */
public class webSocketConnection {
  WebSocket server = null;
  WebSocketClient client = null;
  public webSocketConnection(WebSocket server){
    this.server = server;
  }
  public webSocketConnection(WebSocketClient client){
    this.client = client;
  }
  public void send(byte[] data, int data_size){
    if(client!=null){
      client.sendFragmentedFrame(Framedata.Opcode.BINARY, ByteBuffer.wrap(new byte[1]),false);/*necessary because websockets implementation has bug with binary data receiving*/
      client.sendFragmentedFrame(Framedata.Opcode.BINARY, ByteBuffer.wrap(data,0,data_size), true);
    }else if(server!=null){
      server.sendFragmentedFrame(Framedata.Opcode.BINARY, ByteBuffer.wrap(new byte[1]),false);/*necessary because websockets implementation has bug with binary data receiving*/
      server.sendFragmentedFrame(Framedata.Opcode.BINARY, ByteBuffer.wrap(data,0,data_size),true);
    }
  }
}
