package com.github.airutech.cnetsTransports.webSocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by oleg on 5/7/14.
 */
public class WSServer extends WebSocketServer {
  webSocket parent;

  public WSServer(int port, webSocket parent) throws UnknownHostException {
    super(new InetSocketAddress(port));
    this.parent = parent;
  }

  //
//  public WSServer(InetSocketAddress address) {
//    super( address );
//  }
  @Override
  public void onOpen(WebSocket ws, ClientHandshake clientHandshake) {
    parent.onOpen("" + ws, new webSocketConnection(ws));
  }

  @Override
  public void onClose(WebSocket ws, int code, String reason, boolean remote) {
    parent.onClose("" + ws);
  }

  @Override
  public void onMessage(WebSocket ws, String s) {
    System.out.println("server onMessage " + s);
    parent.onMessage("" + ws, s);
  }

  @Override
  public void onFragment(org.java_websocket.WebSocket ws, org.java_websocket.framing.Framedata fragment){
    if(fragment.getPayloadData().limit()==1){/*necessary because websockets implementation has bug with binary data receiving*/
      return;
    }
    System.out.println("on Fragment in server");
    parent.onMessage("" + ws, fragment.getPayloadData());
  }

  @Override
  public void onError(WebSocket webSocket, Exception e) {
    e.printStackTrace();
  }
}
