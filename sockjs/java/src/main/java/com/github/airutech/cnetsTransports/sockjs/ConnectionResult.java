package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.Handler;

/**
 * Created by oleg on 10/10/14.
 */
public class ConnectionResult implements Handler<Throwable> {
  private final sockjs sockjs;

  public ConnectionResult(sockjs sockjs) {
    this.sockjs = sockjs;
  }

  @Override
  public void handle(Throwable throwable) {
    System.err.println(ConnectionResult.class.getSimpleName()+" handle");
    sockjs.reconnect();
    throwable.printStackTrace();
  }
}
