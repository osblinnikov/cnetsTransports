package com.github.airutech.cnetsTransports.webSocket;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;

public class webSocketConnection {
  WebSocket server = null;
  WebSocketClient client = null;
  public webSocketConnection(WebSocket server){
    this.server = server;
  }
  public webSocketConnection(WebSocketClient client){
    this.client = client;
  }
  public void send(ByteBuffer bb){
    bb.position(0);
    if(client!=null){
      client.sendFragmentedFrame(Framedata.Opcode.BINARY, ByteBuffer.wrap(new byte[1]),false);/*necessary because websockets implementation has bug with binary data receiving*/
      client.sendFragmentedFrame(Framedata.Opcode.BINARY, bb, true);
    }else if(server!=null){
      server.sendFragmentedFrame(Framedata.Opcode.BINARY, ByteBuffer.wrap(new byte[1]),false);/*necessary because websockets implementation has bug with binary data receiving*/
      server.sendFragmentedFrame(Framedata.Opcode.BINARY, bb,true);
    }
  }
}
