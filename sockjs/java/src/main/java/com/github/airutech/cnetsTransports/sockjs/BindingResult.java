package com.github.airutech.cnetsTransports.sockjs;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;

/**
 * Created by oleg on 10/10/14.
 */
public class BindingResult implements Handler<AsyncResult<HttpServer>> {
  private final int bindPort;
  private final sockjs sockjs;

  BindingResult(int bindPort, sockjs sockjs){
    this.bindPort = bindPort;
    this.sockjs = sockjs;
  }
  @Override
  public void handle(AsyncResult<HttpServer> httpServerAsyncResult) {
    if(httpServerAsyncResult.failed()){
      System.err.println("sockjs: vertx can't listen on "+bindPort);
      sockjs.resetServer();
    }else if(httpServerAsyncResult.succeeded()){
      System.out.println("sockjs: vertx listen on "+bindPort);
    }else{
      System.err.println("sockjs: unknown listening result "+httpServerAsyncResult.cause());
      sockjs.resetServer();
    }
  }
}
