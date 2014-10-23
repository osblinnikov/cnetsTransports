package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.WebSocket;

import java.nio.ByteBuffer;

/**
 * Created by oleg on 10/10/14.
 */
public class WsDataHandler implements Handler<Buffer> {
  private final sockjs sockjs;
  private final WebSocket webSocket;

  public WsDataHandler(WebSocket webSocket, sockjs sockjs) {
    this.sockjs = sockjs;
    this.webSocket = webSocket;
  }


  @Override
  public void handle(Buffer buffer) {
    ByteBuffer bb = ByteBuffer.wrap(buffer.getBytes());
    bb.position(0);
    bb.limit(bb.capacity());
    sockjs.onMessage(webSocket.binaryHandlerID(), bb);
  }
}
