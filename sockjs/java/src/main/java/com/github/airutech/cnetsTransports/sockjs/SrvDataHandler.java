package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;
import org.vertx.java.core.VoidHandler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.sockjs.SockJSSocket;

import java.nio.ByteBuffer;

/**
 * Created by oleg on 10/10/14.
 */
public class SrvDataHandler implements Handler<Buffer> {
  private final SockJSSocket sock;
  private sockjs that;

  public SrvDataHandler(SockJSSocket sock, sockjs that){
    this.sock = sock;
    this.that = that;
  }
  public void handle(Buffer buffer) {
    ByteBuffer bb = ByteBuffer.wrap(buffer.getBytes());
    bb.position(0);
    bb.limit(bb.capacity());
    that.onMessage(sock.writeHandlerID(), bb);
  }
}
