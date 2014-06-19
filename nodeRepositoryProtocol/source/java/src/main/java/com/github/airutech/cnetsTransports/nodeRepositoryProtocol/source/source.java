
package com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.types.*;
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.*;
import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.mapBuffer.*;
public class source implements RunnableStoppable{
  String[] subscribedBuffersNames;writer w0;reader r0;
  
  public source(String[] subscribedBuffersNames,writer w0,reader r0){
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
/*[[[end]]] (checksum: 2c7a99139b459f8ac092a50ca5e4387f)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    connectionStatus connectionStatus = (com.github.airutech.cnetsTransports.types.connectionStatus) r0.readNext(-1);
    if(connectionStatus==null){return;}

    if(connectionStatus.isOn()) {

      nodeRepositoryProtocol nodeRepositoryProtocol = null;
      while (nodeRepositoryProtocol == null) {
        nodeRepositoryProtocol = (com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol) w0.writeNext(-1);
      }

      nodeRepositoryProtocol.bufferNames = subscribedBuffersNames;
      nodeRepositoryProtocol.nodeId = connectionStatus.getId();

      w0.writeFinished();
    }
    r0.readFinished();
  }

  @Override
  public void onStop(){

  }

}

