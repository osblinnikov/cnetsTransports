
package com.github.airutech.cnetsTransports.nodeRepositoryProtocol;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

public class nodeRepositoryProtocol{
  public String[] bufferNames;
  
  public nodeRepositoryProtocol(String[] bufferNames){
    this.bufferNames = bufferNames;
    onCreate();
    initialize();
  }

  private void initialize(){
    
    onKernels();
    
  }
/*[[[end]]] (checksum: 3b6cd4acf8848adc4168f8fdf202fcc1)*/

  public nodeRepositoryProtocol(){
    bufferNames = null;
    onCreate();
    initialize();
  }

  public int nodeId;
  private void onCreate(){
    nodeId = -1;
  }

  private void onKernels(){

  }


}

