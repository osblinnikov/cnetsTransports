
package com.github.airutech.cnetsTransports.types;

import java.io.IOException;
import java.nio.ByteBuffer;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

public class types{
  
  public types(){
    onCreate();
    initialize();
  }

  private void initialize(){
    
    onKernels();
    
  }
/*[[[end]]] (checksum: 4b7cc7563e521f3b3333436a3386ca9e) */

  private void onKernels() {

  }

  private void onCreate(){

  }

  static public void writeUInt32(
      long uint32,
      ByteBuffer stream
  ) throws IOException
  {
    stream.putInt( (int) uint32 );
  }

  static public long readUInt32(
      ByteBuffer stream
  ) throws IOException
  {
    long retVal = stream.getInt( );

    return retVal & 0x00000000FFFFFFFFL;
  }
}

