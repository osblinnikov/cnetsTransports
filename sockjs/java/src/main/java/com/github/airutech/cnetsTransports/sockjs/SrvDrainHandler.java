package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.sockjs.SockJSSocket;

/**
 * Created by oleg on 10/10/14.
 */
public class SrvDrainHandler implements Handler<Void> {
  private final SockJSSocket sock;

  public SrvDrainHandler(SockJSSocket sock, sockjs that) {
    this.sock = sock;
  }

  @Override
  public void handle(Void aVoid) {
    sock.resume();
  }
}
