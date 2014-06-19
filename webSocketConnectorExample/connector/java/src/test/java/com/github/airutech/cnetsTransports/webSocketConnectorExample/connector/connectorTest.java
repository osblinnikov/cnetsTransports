
package com.github.airutech.cnetsTransports.webSocketConnectorExample.connector;
import com.github.airutech.cnetsTransports.msgpack.msgPackDeserializer;
import com.github.airutech.cnetsTransports.msgpack.msgPackSerializer;
import org.junit.Test;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source.*;
import com.github.airutech.cnetsTransports.webSocket.*;
import com.github.airutech.cnetsTransports.protocolToBuffer.*;
import com.github.airutech.cnetsTransports.protocolToBuffer.*;
import com.github.airutech.cnetsTransports.bufferToProtocol.*;
import com.github.airutech.cnetsTransports.bufferToProtocol.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import no.eyasys.mobileAlarm.types.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.types.*;
/*[[[end]]] (checksum: 0f9c3e79e7bb3f83475b8355773f39a8) */
public class connectorTest {
  @Test
  public void connectorTest(){
    writer w0 = null, w1 = null, w2 = null, w3 = null;
    reader r0 = null, r1 = null, r2 = null, r3 = null;
    writer[] allWriters = new writer[3];
    allWriters[0] = w1;
    msgPackDeserializer[] allWriters_callbacks = new msgPackDeserializer[3];
    allWriters_callbacks[0] = new msgPackDeserializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    allWriters[1] = w2;
    allWriters_callbacks[1] = new msgPackDeserializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    allWriters[2] = w3;
    allWriters_callbacks[2] = new msgPackDeserializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    String[] subscribedBuffersNames = new String[3];
    reader[] allReaders = new reader[3];
    subscribedBuffersNames[0] = "exampleToSend0";
    allReaders[0] = r1;
    msgPackSerializer[] allReaders_callbacks = new msgPackSerializer[3];
    allReaders_callbacks[0] = new msgPackSerializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    subscribedBuffersNames[1] = "exampleToSend1";
    allReaders[1] = r2;
    allReaders_callbacks[1] = new msgPackSerializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());
    subscribedBuffersNames[2] = "exampleToSend2";
    allReaders[2] = r3;
    allReaders_callbacks[2] = new msgPackSerializer(new com.github.airutech.cnetsTransports.msgpackExample.msgpack.msgpack());

    connector _connector = new connector(subscribedBuffersNames, allWriters, allReaders, allWriters_callbacks, allReaders_callbacks, "server url", -1, w0, r0);
  }
}

