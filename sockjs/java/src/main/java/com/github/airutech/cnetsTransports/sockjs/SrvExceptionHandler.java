package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.sockjs.SockJSSocket;

/**
 * Created by oleg on 10/10/14.
 */
public class SrvExceptionHandler implements Handler<Throwable> {
  private final SockJSSocket sock;

  public SrvExceptionHandler(SockJSSocket sock, sockjs that) {
    this.sock = sock;
  }

  @Override
  public void handle(Throwable throwable) {

  }
}
