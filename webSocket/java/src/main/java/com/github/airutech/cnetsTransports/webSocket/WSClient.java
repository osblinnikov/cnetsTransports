package com.github.airutech.cnetsTransports.webSocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by oleg on 5/7/14.
 */
public class WSClient extends WebSocketClient {
  private webSocket parent;

  public WSClient(String uri, webSocket parent) throws URISyntaxException {
    super( new URI( uri));
    this.parent = parent;
  }
  @Override
  public void onOpen(ServerHandshake serverHandshake) {
    parent.onOpen("" + this, new webSocketConnection(this));
  }

  @Override
  public void onClose(int i, String s, boolean b) {
    parent.onClose("" + this);
    parent.reconnect();
  }

  @Override
  public void onMessage(String s) {
    System.out.println("client onMessage "+s);
    parent.onMessage("" + this, s);
  }

  @Override
  public void onFragment(org.java_websocket.framing.Framedata fragment){
    if(fragment.getPayloadData().limit()==1){/*necessary because websockets implementation has bug with binary data receiving*/
      return;
    }
    System.out.println("on Fragment in client");
    parent.onMessage("" + this, fragment.getPayloadData());
  }

  @Override
  public void onError(Exception e) {
    e.printStackTrace();
  }
}
