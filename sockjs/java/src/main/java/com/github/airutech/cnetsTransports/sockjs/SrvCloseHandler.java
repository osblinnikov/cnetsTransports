package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.sockjs.SockJSSocket;

/**
 * Created by oleg on 10/10/14.
 */
public class SrvCloseHandler implements Handler<Void> {
  private final SockJSSocket sock;
  private final sockjs that;

  public SrvCloseHandler(SockJSSocket sock, sockjs that) {
    this.sock = sock;
    this.that = that;
  }

  @Override
  public void handle(Void aVoid) {
    that.onClose(sock.writeHandlerID());
  }
}
