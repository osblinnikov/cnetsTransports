
package com.github.airutech.cnetsTransports.statsConnectorSockJS;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnetsTransports.bufferToProtocol.*;
import com.github.airutech.cnetsTransports.protocolToBuffer.*;
import com.github.airutech.cnetsTransports.sockJS.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.types.*;
public class statsConnectorSockJS implements RunnableStoppable{
  reader[] readers;writer[] writers;serializeCallback[] readersCallbacks;deserializeCallback[] writersCallbacks;Long[] inBuffersIds;int maxNodesCount;int maxBuffersCount;String initialConnection;int bindPort;connectionStatusCallback connectionStatus;String[] args;
  
  public statsConnectorSockJS(String[] args){
    this.args = args;
    this.readers = new reader[1];
    this.writers = new writer[1];
    this.readersCallbacks = new serializeCallback[1];
    this.writersCallbacks = new deserializeCallback[1];
    this.inBuffersIds = new Long[2];
    onCreate();
    initialize();
  }
bufferToProtocol statsToProtocol;protocolToBuffer protocolToStats;sockJS sockJsChannel;
  private void initialize(){
    
    onKernels();
    
    statsToProtocol = new bufferToProtocol((reader[])readers,(serializeCallback[])readersCallbacks);
    protocolToStats = new protocolToBuffer((writer[])writers,(deserializeCallback[])writersCallbacks);
    sockJsChannel = new sockJS((long[])inBuffersIds,(int)maxNodesCount,(int)maxBuffersCount,(String)initialConnection,(int)bindPort,(connectionStatusCallback)connectionStatus);
  }
  public runnablesContainer getRunnables(){
    
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[3];
    arrContainers[0] = statsToProtocol.getRunnables();
    arrContainers[1] = protocolToStats.getRunnables();
    arrContainers[2] = sockJsChannel.getRunnables();

    runnables.setContainers(arrContainers);
    return runnables;
  }
/*[[[end]]] (checksum: a43921f894810e1c5cc6b552ba53d9be)*/

  private void onCreate(){

  }

  private void onKernels(){

  }

  @Override
  public void onStart(){

  }

  @Override
  public void run(){
    
    statsToProtocol.run();
    protocolToStats.run();
    sockJsChannel.run();
  }

  @Override
  public void onStop(){

  }

}

