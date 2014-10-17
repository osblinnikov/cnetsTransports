
package com.github.airutech.cnetsTransports.sockjs;
import com.github.airutech.cnets.types.bufferKernelParams;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import java.nio.ByteBuffer;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.types.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 73b0f13afa19ad49cdb54b485e3c8b7d) */
public class sockjsTest {
  @Test
  public void sockjsTest() throws Exception {
    mapBuffer mb0Srv = new mapBuffer(new Object[]{new cnetsProtocol()}, 1000, 1);
    writer w0 = mb0Srv.getWriter(0);

    mapBuffer mb0Clnt = new mapBuffer(new Object[]{new cnetsProtocol()}, 1000, 1);
    reader r0 = mb0Clnt.getReader(0);


    sockjs server = createServer(mb0Srv);
    sockjs client = createClient(mb0Clnt);

    runnablesContainer r2 = client.getRunnables();
    r2.launch(false);
    runnablesContainer r1 = server.getRunnables();
    r1.launch(false);
    Thread.sleep(1000);

    cnetsProtocol res = (cnetsProtocol) w0.writeNext(-1);
    if(res == null){System.out.println("NO STORAGE RECEIVED FOR WRITING");throw new Exception("res == null");}
    res.setBufferIndex(0);
    res.setBunchId(0);
    res.setNodeUniqueIds(null);
    res.setPacket(0);
    res.setPackets_grid_size(1);
    res.setTimeStart(System.currentTimeMillis());
    res.setPublished(true);
    res.setData(ByteBuffer.allocate(512));
    res.reserveForHeader();
    res.getData().put("test string :)".getBytes());
    res.serialize();
    w0.writeFinished();

    cnetsProtocol recv = (cnetsProtocol) r0.readNext(-1);
    if(recv == null){System.out.println("NO STORAGE RECEIVED FOR READING");throw new Exception("recv == null");}
    byte[] dst = new byte[recv.getData().limit()-recv.getData().position()];
    recv.getData().get(dst);
    System.out.println("RESULT OK: "+new String(dst));
    r0.readFinished();

    r2.stop();
    r1.stop();
  }

  private sockjs createServer(mapBuffer mb0Srv) {
    reader r0 = mb0Srv.getReader(0);
    mapBuffer mb1 = new mapBuffer(new Object[]{new cnetsConnections()}, 1000, 1);
    reader r1 = mb1.getReader(0);
    mapBuffer mb2 = new mapBuffer(new Object[]{new nodeRepositoryProtocol()}, 1000, 1);
    reader r2 = mb2.getReader(0);
    mapBuffer mb3 = new mapBuffer(new Object[]{new connectionStatus()}, 1000, 1);
    writer w3 = mb3.getWriter(0);
    mapBuffer mb4 = new mapBuffer(new Object[]{new cnetsProtocol()}, 1000, 1);
    writer w4 = mb4.getWriter(0);
    sockjs server = new sockjs(new String[]{"buf1"}, 1, null, 9911, null, w3, w4, r0, r1, r2);
    return server;
  }
  private sockjs createClient(mapBuffer mb0Clnt) {
    mapBuffer mb0 = new mapBuffer(new Object[]{new cnetsProtocol()},1000,1);
    reader r0 = mb0.getReader(0);
    mapBuffer mb1 = new mapBuffer(new Object[]{new cnetsConnections()},1000,1);
    reader r1 = mb1.getReader(0);
    mapBuffer mb2 = new mapBuffer(new Object[]{new nodeRepositoryProtocol()},1000,1);
    reader r2 = mb2.getReader(0);
    mapBuffer mb3 = new mapBuffer(new Object[]{new connectionStatus()},1000,1);
    writer w3 = mb3.getWriter(0);
//    mapBuffer mb4 = new mapBuffer(new Object[]{new cnetsProtocol()},1000,1);
    writer w4 = mb0Clnt.getWriter(0);
    sockjs client = new sockjs(new String[]{"buf1"},1,"ws://localhost:9911",0,null, w3, w4, r0,r1,r2);
    return client;
  }
}

