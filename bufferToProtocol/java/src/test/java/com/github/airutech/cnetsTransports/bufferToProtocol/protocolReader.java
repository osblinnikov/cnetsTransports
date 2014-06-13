package com.github.airutech.cnetsTransports.bufferToProtocol;

import com.github.airutech.cnets.readerWriter.reader;
import com.github.airutech.cnets.runnablesContainer.RunnableStoppable;
import com.github.airutech.cnets.runnablesContainer.runnablesContainer;
import com.github.airutech.cnetsTransports.types.cnetsProtocol;

class protocolReader implements RunnableStoppable {
  reader r0;
  protocolReader(reader r0){
    this.r0 = r0;
  }

  public runnablesContainer getRunnables(){

    runnablesContainer runnables = new runnablesContainer();
    runnables.setCore(this);
    return runnables;
  }

  int packIterator = 0, lastSecPack = 0;
  long curtime = System.currentTimeMillis();
  long endtime_sec = curtime + 1000;/*1sec*/

  @Override
  public void onStart() {

  }

  @Override
  public void run(){
    cnetsProtocol d = (cnetsProtocol) r0.readNext(-1);
    if (d != null) {
      d.getData();
      r0.readFinished();
      packIterator++;
      curtime = System.currentTimeMillis();
      if (endtime_sec <= curtime) {
        endtime_sec = curtime + 1000;
        System.out.printf("r thread FPS: %d\n", packIterator - lastSecPack);
        lastSecPack = packIterator;
      }
    }
  }

  @Override
  public void onStop() {

  }
}
