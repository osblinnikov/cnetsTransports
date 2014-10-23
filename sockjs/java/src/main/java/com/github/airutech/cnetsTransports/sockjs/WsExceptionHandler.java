package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.WebSocket;

/**
 * Created by oleg on 10/10/14.
 */
public class WsExceptionHandler implements Handler<Throwable> {
  private final sockjs sockjs;

  public WsExceptionHandler(WebSocket webSocket, sockjs sockjs) {
    this.sockjs = sockjs;
  }

  @Override
  public void handle(Throwable throwable) {

  }
}
