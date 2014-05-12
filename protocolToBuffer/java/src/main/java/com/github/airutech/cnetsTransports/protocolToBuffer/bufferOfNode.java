package com.github.airutech.cnetsTransports.protocolToBuffer;

import com.github.airutech.cnets.readerWriter.writer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by oleg on 4/30/14.
 */
public class bufferOfNode {
  private long bunchId;
  private long timeStart;
  private writer w0;
  private Lock lock = null;
  private boolean init;
  private Object bufObj;
  private deserializeCallback callback;

  bufferOfNode(){
  }


    public writer getW0() {
        return w0;
    }

    public void setW0(writer w0) {
        this.w0 = w0;
    }

  public long getBunchId() {
        return bunchId;
    }

    public void setBunchId(long bunchId) {
        this.bunchId = bunchId;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

  public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }


  public boolean isInit() {
    return init;
  }

  public void setInit(boolean init) {
    this.init = init;
  }

  public Object getBufObj() {
    return bufObj;
  }

  public void setBufObj(Object bufObj) {
    this.bufObj = bufObj;
  }

  public void setCallback(deserializeCallback callback) {
    this.callback = callback;
  }

  public deserializeCallback getCallback() {
    return callback;
  }
}
