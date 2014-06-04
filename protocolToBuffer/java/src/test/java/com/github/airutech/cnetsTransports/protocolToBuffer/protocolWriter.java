package com.github.airutech.cnetsTransports.protocolToBuffer;

import com.github.airutech.cnetsTransports.types.IntBoxer;
import com.github.airutech.cnets.readerWriter.writer;
import com.github.airutech.cnets.runnablesContainer.RunnableStoppable;
import com.github.airutech.cnets.runnablesContainer.runnablesContainer;
import com.github.airutech.cnetsTransports.types.*;

import java.nio.ByteBuffer;

public class protocolWriter implements RunnableStoppable {
  writer w0;
  String uniqueId;
  public protocolWriter(String uniqueId, writer w0) {
    this.uniqueId = uniqueId;
    this.w0 = w0;
  }

  public runnablesContainer getRunnables() {
    runnablesContainer runnables = new runnablesContainer();
    runnables.setCore(this);
    return runnables;
  }

  int packIterator = 0, lastSecPack = 0;
  long curtime = System.currentTimeMillis();
  long endtime_sec = curtime + 1000;/*1sec*/
  cnetsProtocol protocol = new cnetsProtocol();
  long timeStart = System.currentTimeMillis()/1000;
  final int bufSize = 100;

  @Override
  public void onStart() {

  }

  @Override
  public void run() {
    cnetsProtocolBinary d = (cnetsProtocolBinary) w0.writeNext(true);
    if (d != null) {
      /*TODO: serialize data here*/
      d.getNodeIds()[0] = 0;
      d.setNodeIdsSize(1);
      protocol.setTimeStart(timeStart);
      protocol.setBufferIndex(0);
      protocol.setBunchId(packIterator/2);
      protocol.setPacket(packIterator%2);
      protocol.setPackets_grid_size(2);
      if(d.getData() == null) {
        d.setData(ByteBuffer.wrap(new byte[bufSize]));
      }
      protocol.serialize(d.getData());
      IntBoxer boxer = new IntBoxer(packIterator);
      boxer.serialize(d.getData());
      w0.writeFinished();
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
