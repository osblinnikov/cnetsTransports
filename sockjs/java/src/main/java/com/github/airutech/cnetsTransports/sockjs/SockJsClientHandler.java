package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.WebSocket;

/**
 * Created by oleg on 10/10/14.
 */
public class SockJsClientHandler implements Handler<WebSocket> {
  private final sockjs sockjs;

  public SockJsClientHandler(sockjs sockjs) {
    this.sockjs = sockjs;
  }

  @Override
  public void handle(WebSocket webSocket) {
    webSocket.setWriteQueueMaxSize(1024);
    webSocket.endHandler(new WsCloseHandler(webSocket, sockjs));
    webSocket.dataHandler(new WsDataHandler(webSocket, sockjs));
    webSocket.drainHandler(new WsDrainHandler(webSocket, sockjs));
    webSocket.exceptionHandler(new WsExceptionHandler(webSocket, sockjs));
//    Buffer buf = new Buffer();
//    buf.appendString("[s]");
//    webSocket.write(buf);
    sockjs.onOpen(webSocket.binaryHandlerID(),null,webSocket);
  }
}
