
package com.github.airutech.cnetsTransports.msgpack;
import com.github.airutech.cnetsTransports.types.cnetsProtocol;
import org.junit.Test;
import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.types.*;
/*[[[end]]] (checksum: 503d02cfa8b93b58544ae7dd214861e5) */

public class msgpackTest {
  @Test
  public void msgpackTest() throws IOException {

//    msgpack classObj = new msgpack();
    MessagePack msgpack = new MessagePack();

    //
    // Serialization
    //
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Packer packer = msgpack.createPacker(out);

    // Serialize values of primitive types
    packer.write(true); // boolean value
    packer.write(10); // int value
    packer.write(10.5); // double value

    // Serialize objects of primitive wrapper types
    packer.write(Boolean.TRUE);
    packer.write(new Integer(10));
    packer.write(new Double(10.5));

    // Serialize various types of arrays
    packer.write(new int[] { 1, 2, 3, 4 });
    packer.write(new Double[] { 10.5, 20.5 });
    packer.write(new String[] { "msg", "pack", "for", "java" });
    packer.write(new byte[] { 0x30, 0x31, 0x32 }); // byte array

    // Serialize various types of other reference values
    packer.write("MesagePack"); // String object
    packer.write(ByteBuffer.wrap(new byte[]{0x30, 0x31, 0x32})); // ByteBuffer object
    packer.write(BigInteger.ONE); // BigInteger object

    //
    // Deserialization
    //
    byte[] bytes = out.toByteArray();
//    System.out.printf("bytes count %d \n", bytes.length);
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    Unpacker unpacker = msgpack.createUnpacker(in);

    // to primitive values
    boolean b = unpacker.readBoolean(); // boolean value
    int i = unpacker.readInt(); // int value
    double d = unpacker.readDouble(); // double value

    // to primitive wrapper value
    Boolean wb = unpacker.read(Boolean.class);
    Integer wi = unpacker.read(Integer.class);
    Double wd = unpacker.read(Double.class);

    // to arrays
    int[] ia = unpacker.read(int[].class);
    Double[] da = unpacker.read(Double[].class);
    String[] sa = unpacker.read(String[].class);
    byte[] ba = unpacker.read(byte[].class);

    // to String object, ByteBuffer object, BigInteger object, List object and Map object
    String ws = unpacker.read(String.class);
    ByteBuffer buf = unpacker.read(ByteBuffer.class);
    BigInteger bi = unpacker.read(BigInteger.class);
  }

  @Test
  public void msgPackSerializerTest(){
    TestMessage dataObject = new TestMessage();
    dataObject.testValue = 10001;
    msgPackSerializer serializer = new msgPackSerializer(new TestMessage());
    cnetsProtocol protocol = new cnetsProtocol(0,0);
    protocol.setPacket(0);
    protocol.setPackets_grid_size(1);
    protocol.setBufferIndex(0);
    protocol.setBunchId(0);
    protocol.setTimeStart(10001);

    boolean isLastPacket = false;
    LinkedList<cnetsProtocol> ll = new LinkedList<cnetsProtocol>();
    while(!isLastPacket) {
      System.out.printf("packet %d\n", protocol.getPacket());
      cnetsProtocol wrProtocol = new cnetsProtocol(30,10);
      wrProtocol.setFrom(protocol);
      wrProtocol.reserveForHeader();
      isLastPacket = serializer.serializeNext(dataObject, wrProtocol);
      wrProtocol.serialize();
      protocol.incrementPacket();
      ll.add(wrProtocol);
    }
/*For test*/
    dataObject.testValue = 0;
    dataObject.testArr[9] = "111";

    msgPackDeserializer deserializer = new msgPackDeserializer(new TestMessage());

    for(cnetsProtocol l: ll) {
      l.getData().flip();
      l.deserialize();
      deserializer.deserializeNext(dataObject, l);
    }

    System.out.printf("VALUE: %d\n",dataObject.testValue);
    System.out.printf("ARR: %s ==== %s\n", Arrays.toString(new TestMessage().testArr), Arrays.toString(dataObject.testArr));
    assertEquals(10001, dataObject.testValue);
    assertArrayEquals(new TestMessage().testArr, dataObject.testArr);
  }
}

