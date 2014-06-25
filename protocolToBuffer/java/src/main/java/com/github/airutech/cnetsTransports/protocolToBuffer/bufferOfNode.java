package com.github.airutech.cnetsTransports.protocolToBuffer;

import com.github.airutech.cnets.readerWriter.writer;
import com.github.airutech.cnetsTransports.types.deserializeStreamCallback;

public class bufferOfNode {
  private long bunchId;
  private long timeStart;
  private writer w0;
  private boolean init;
  private Object bufferObj;
  private int dstBufferIndex;
  private int nodeId;
  private deserializeStreamCallback callback;
  private String subscribedName;

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


  public boolean isInit() {
    return init;
  }

  public void setInit(boolean init) {
    this.init = init;
  }

  public Object getBufferObj() {
    return bufferObj;
  }

  public void setBufferObj(Object bufferObj) {
    this.bufferObj = bufferObj;
  }

  public int getDstBufferIndex() {
    return dstBufferIndex;
  }

  public void setDstBufferIndex(int dstBufferIndex) {
    this.dstBufferIndex = dstBufferIndex;
  }

  public int getNodeId() {
    return nodeId;
  }

  public void setNodeId(int nodeId) {
    this.nodeId = nodeId;
  }

  public void setCallback(deserializeStreamCallback callback) {
    this.callback = callback;
  }

  public deserializeStreamCallback getCallback() {
    return callback;
  }

  public String getSubscribedName() {
    return subscribedName;
  }

  public void setSubscribedName(String subscribedName) {
    this.subscribedName = subscribedName;
  }
}
