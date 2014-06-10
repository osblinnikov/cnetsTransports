
package com.github.airutech.cnetsTransports.msgpack;
import org.apache.commons.javaflow.Continuation;
import org.junit.Test;
import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/
/*[[[end]]] (checksum: 503d02cfa8b93b58544ae7dd214861e5)*/

public class msgpackTest {
  @Test
  public void msgpackTest() throws IOException {
    Continuation c = Continuation.startWith(new MyRunnable());
    System.out.println("returned a continuation");
    while(c!=null) {
      c = Continuation.continueWith(c);
    }

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
    System.out.printf("bytes count %d \n", bytes.length);
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
}

