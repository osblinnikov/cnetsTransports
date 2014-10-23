package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.sockjs.SockJSSocket;

/**
 * Created by oleg on 10/10/14.
 */
public class SockJsServerHandler implements Handler<SockJSSocket> {
  private final sockjs sockjs;

  public SockJsServerHandler(sockjs sockjs) {
    this.sockjs = sockjs;
  }

  public void handle(final SockJSSocket sock) {
    sock.setWriteQueueMaxSize(1024);
    sock.endHandler(new SrvCloseHandler(sock, sockjs));
    sock.dataHandler(new SrvDataHandler(sock, sockjs));
    sock.drainHandler(new SrvDrainHandler(sock, sockjs));
    sock.exceptionHandler(new SrvExceptionHandler(sock, sockjs));
    sockjs.onOpen(sock.writeHandlerID(),sock,null);
  }
}
