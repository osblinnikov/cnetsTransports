
package com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source;

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
public class source implements RunnableStoppable{
  String[] publishedBuffersNames;String[] subscribedBuffersNames;writer w0;reader r0;
  
  public source(String[] publishedBuffersNames,String[] subscribedBuffersNames,writer w0,reader r0){
    this.publishedBuffersNames = publishedBuffersNames;
    this.subscribedBuffersNames = subscribedBuffersNames;
    this.w0 = w0;
    this.r0 = r0;
    onCreate();
    initialize();
  }

  private void initialize(){
    
    onKernels();
    
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnables.setCore(this);
    return runnables;
  }
/*[[[end]]] (checksum: 5325d8febcab4c887efa6b4291ae9535)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    Thread.currentThread().setName("nodeRepositoryProtocol.source");
    connectionStatus connectionStatus = (com.github.airutech.cnetsTransports.types.connectionStatus) r0.readNext(-1);
    if(connectionStatus==null){return;}

    if(connectionStatus.isOn() && !connectionStatus.isReceivedRepo()) {
      /*isReceivedRepo() needed because we will receive two messages: first one (when not received repo yet)
        and second one, when finally receive it*/

      nodeRepositoryProtocol nodeRepositoryProtocol = null;
      while (nodeRepositoryProtocol == null) {
        nodeRepositoryProtocol = (com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol) w0.writeNext(-1);
      }

      nodeRepositoryProtocol.subscribedNames = subscribedBuffersNames;
      nodeRepositoryProtocol.publishedNames = publishedBuffersNames;
      nodeRepositoryProtocol.nodeId = connectionStatus.getId();
//      System.out.println(".source send cnt=" + subscribedBuffersNames.length);

      w0.writeFinished();
    }
    r0.readFinished();
  }

  @Override
  public void onStop(){

  }

}

