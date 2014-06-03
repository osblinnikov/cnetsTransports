package com.github.airutech.cnetsTransports.types;

import com.github.airutech.cnetsTransports.types.types;
import org.omg.CORBA.INTERNAL;

import java.io.IOException;
import java.nio.ByteBuffer;

public class bufferUtils {
  public final static Long bufPrefix = (long)Integer.MAX_VALUE;
  public final static Long dataPrefix = 0L;

  public static byte[] serializeBuffers(Long[] buffersIds){
    if(buffersIds==null){return null;}
    ByteBuffer bb = ByteBuffer.wrap(new byte[(1 + buffersIds.length)*4]);
    try {
      types.writeUInt32(bufPrefix, bb);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    for(int i=0; i<buffersIds.length;i++) {
      try {
        types.writeUInt32(buffersIds[i], bb);
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }
    return bb.array();
  }
  public static Long[] deserializeBuffers( byte[] bytes) {
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    return deserializeBuffers(bb);
  }

  public static Long[] deserializeBuffers( ByteBuffer bb){
    try {
      if(types.readUInt32(bb)!=bufPrefix){
        return null;
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    Long[] buffersIds = new Long[(bb.limit() - 1)/4];
    for(int i=0; i<buffersIds.length; i++) {
      try {
        buffersIds[i] = types.readUInt32(bb);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return buffersIds;
  }
}
