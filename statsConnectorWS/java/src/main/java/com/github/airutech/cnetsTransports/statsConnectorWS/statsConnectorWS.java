
package com.github.airutech.cnetsTransports.statsConnectorWS;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.types.*;
public class statsConnectorWS implements RunnableStoppable{
  String[] args;
  
  public statsConnectorWS(String[] args){
    this.args = args;
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
/*[[[end]]] (checksum: b4c01d3d611e91337ef32b1e07585057)*/

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

