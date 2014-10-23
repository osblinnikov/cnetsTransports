package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.WebSocket;

/**
 * Created by oleg on 10/10/14.
 */
public class WsCloseHandler implements Handler<Void> {
  private final sockjs sockjs;
  private final WebSocket webSocket;

  public WsCloseHandler(WebSocket webSocket, sockjs sockjs) {
    this.sockjs = sockjs;
    this.webSocket = webSocket;
  }

  @Override
  public void handle(Void aVoid) {
    sockjs.onClose(webSocket.binaryHandlerID());
    sockjs.reconnect();
  }
}
