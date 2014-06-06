
package com.github.airutech.cnetsTransports.sockJS;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.mapBuffer.*;
public class sockJS implements RunnableStoppable{
  Long[] inBuffersIds;int maxNodesCount;int maxBuffersCount;String initialConnection;int bindPort;writer w0;reader r0;reader r1;reader rSelect;selector readersSelector;
  
  public sockJS(Long[] inBuffersIds,int maxNodesCount,int maxBuffersCount,String initialConnection,int bindPort,writer w0,reader r0,reader r1){
    this.inBuffersIds = inBuffersIds;
    this.maxNodesCount = maxNodesCount;
    this.maxBuffersCount = maxBuffersCount;
    this.initialConnection = initialConnection;
    this.bindPort = bindPort;
    this.w0 = w0;
    this.r0 = r0;
    this.r1 = r1;
    reader[] arrReaders = new reader[2];
    arrReaders[0] = r0;
    arrReaders[1] = r1;
    this.readersSelector = new selector(arrReaders);
    this.rSelect = readersSelector.getReader(0,-1);
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
/*[[[end]]] (checksum: e205a642b90284cb2d63fb222b4e3147)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    
  }

  @Override
  public void onStop(){

  }

}

