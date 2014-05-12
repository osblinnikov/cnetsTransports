
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
    
  }
/*[[[end]]] (checksum: a660cce4b9b7209b21a33c56d070aa69) */

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

