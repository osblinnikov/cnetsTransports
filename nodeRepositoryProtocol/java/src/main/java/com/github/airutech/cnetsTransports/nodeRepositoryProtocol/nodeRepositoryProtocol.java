
package com.github.airutech.cnetsTransports.nodeRepositoryProtocol;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

public class nodeRepositoryProtocol{
  public String[] subscribedNames;public String[] publishedNames;

  public nodeRepositoryProtocol(){
    onCreate();
    initialize();
  }
  public nodeRepositoryProtocol(String[] subscribedNames,String[] publishedNames){
    this.subscribedNames = subscribedNames;
    this.publishedNames = publishedNames;
    onCreate();
    initialize();
  }

  private void initialize(){
    
    onKernels();
    
  }
/*[[[end]]] (checksum: 1c4443f078dae61ffa76b8eecb80d85f)*/

  public int nodeId;
  private void onCreate(){
    nodeId = -1;
  }

  private void onKernels(){

  }


}

