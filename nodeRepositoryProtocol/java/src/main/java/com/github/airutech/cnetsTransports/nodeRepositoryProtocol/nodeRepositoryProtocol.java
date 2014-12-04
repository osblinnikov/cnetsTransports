
package com.github.airutech.cnetsTransports.nodeRepositoryProtocol;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

public class nodeRepositoryProtocol{
  public String[] subscribedNames; public String[] publishedNames;
  public nodeRepositoryProtocol(String[] subscribedNames,String[] publishedNames){
    this.subscribedNames = subscribedNames;
    this.publishedNames = publishedNames;
    onCreate();
    initialize();
  }

  private void initialize(){
    
    onKernels();
    
  }
/*[[[end]]] (checksum: 6d2dfaa433625a11f5d271ae886f39cd)*/

  public nodeRepositoryProtocol(){}
  public int nodeId;
  private void onCreate(){
    nodeId = -1;
  }

  private void onKernels(){

  }


}

