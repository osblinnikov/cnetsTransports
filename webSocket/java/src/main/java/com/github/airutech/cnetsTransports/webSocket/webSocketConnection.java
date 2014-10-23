package com.github.airutech.cnetsTransports.webSocket;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;

public class webSocketConnection {
  public WebSocket getServer() {
    return server;
  }

  public WebSocketClient getClient() {
    return client;
  }

  private WebSocket server = null;
  private WebSocketClient client = null;
  public webSocketConnection(WebSocket server){
    this.server = server;
  }
  public webSocketConnection(WebSocketClient client){
    this.client = client;
  }
  public void send(ByteBuffer bb){
    /*in case we already read the buffer fully*/
    bb.rewind();
    if(client!=null){
      client.sendFragmentedFrame(Framedata.Opcode.BINARY, ByteBuffer.wrap(new byte[1]),false);/*necessary because websockets implementation has bug with binary data receiving*/
      client.sendFragmentedFrame(Framedata.Opcode.BINARY, bb, true);
    }else if(server!=null){
      server.sendFragmentedFrame(Framedata.Opcode.BINARY, ByteBuffer.wrap(new byte[1]),false);/*necessary because websockets implementation has bug with binary data receiving*/
      server.sendFragmentedFrame(Framedata.Opcode.BINARY, bb,true);
    }
    if(bb.remaining() != 0){
      System.err.printf("webSocketConnection: it should be allowed to reuse buffer immediately, but obviously it is not allowed!\n");
    }
  }
}
