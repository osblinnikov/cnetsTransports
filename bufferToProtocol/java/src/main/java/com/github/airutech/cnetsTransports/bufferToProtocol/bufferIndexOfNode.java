package com.github.airutech.cnetsTransports.bufferToProtocol;

import com.github.airutech.cnets.readerWriter.reader;

public class bufferIndexOfNode {
  private reader r0;
  private int dstBufferIndex;
  private boolean connected;
  private String publishedName;
  private boolean receivedRepo;

  public reader getR0() {
    return r0;
  }

  public void setR0(reader r0) {
    this.r0 = r0;
  }

  public void setDstBufferIndex(int dstBufferIndex) {
    this.dstBufferIndex = dstBufferIndex;
  }

  public int getDstBufferIndex() {
    return dstBufferIndex;
  }

  public void setConnected(boolean connected) {
    this.connected = connected;
  }

  public boolean isConnected() {
    return connected;
  }

  public void setPublishedName(String publishedName) {
    this.publishedName = publishedName;
  }

  public String getPublishedName() {
    return publishedName;
  }

  public void setReceivedRepo(boolean receivedRepo) {
    this.receivedRepo = receivedRepo;
  }

  public boolean isReceivedRepo() {
    return receivedRepo;
  }
}
