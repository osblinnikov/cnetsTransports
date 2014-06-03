package com.github.airutech.cnetsTransports.bufferToProtocol;

import com.github.airutech.cnetsTransports.types.IntBoxer;
import com.github.airutech.cnets.readerWriter.writer;
import com.github.airutech.cnets.runnablesContainer.RunnableStoppable;
import com.github.airutech.cnets.runnablesContainer.runnablesContainer;

class intBoxerWriter implements RunnableStoppable {
  writer w0;
  intBoxerWriter(writer w0){
    this.w0 = w0;
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
    IntBoxer d = (IntBoxer) w0.writeNext(true);
    if (d != null) {
      d.value = packIterator;
      w0.writeFinished(0,null);
      packIterator++;
      curtime = System.currentTimeMillis();
      if (endtime_sec <= curtime) {
        endtime_sec = curtime + 1000;
        System.out.printf("w thread FPS: %d\n", packIterator - lastSecPack);
        lastSecPack = packIterator;
      }
    }
  }

  @Override
  public void onStop() {

  }
}
