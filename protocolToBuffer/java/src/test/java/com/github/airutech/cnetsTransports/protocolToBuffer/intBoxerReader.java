package com.github.airutech.cnetsTransports.protocolToBuffer;

import com.github.airutech.cnetsTransports.types.IntBoxer;
import com.github.airutech.cnets.readerWriter.reader;
import com.github.airutech.cnets.runnablesContainer.RunnableStoppable;
import com.github.airutech.cnets.runnablesContainer.runnablesContainer;

public class intBoxerReader implements RunnableStoppable {
  reader r0;
  public intBoxerReader(reader r0) {
    this.r0 = r0;
  }

  public runnablesContainer getRunnables() {
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
  public void run() {
    IntBoxer d = (IntBoxer) r0.readNext(true);
    if (d != null) {
//      d.value = packIterator;
//      System.out.println(d.value);
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
