/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "../include/bufferToProtocol.h"

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_bufferToProtocol_getRunnables(com_github_airutech_cnetsTransports_bufferToProtocol *that);
void com_github_airutech_cnetsTransports_bufferToProtocol_run(void *that);
void com_github_airutech_cnetsTransports_bufferToProtocol_onStart(void *that);
void com_github_airutech_cnetsTransports_bufferToProtocol_onStop(void *that);

void com_github_airutech_cnetsTransports_bufferToProtocol_onCreate(com_github_airutech_cnetsTransports_bufferToProtocol *that);
void com_github_airutech_cnetsTransports_bufferToProtocol_onDestroy(com_github_airutech_cnetsTransports_bufferToProtocol *that);

void com_github_airutech_cnetsTransports_bufferToProtocol_initialize(com_github_airutech_cnetsTransports_bufferToProtocol *that){
  that->getRunnables = com_github_airutech_cnetsTransports_bufferToProtocol_getRunnables;
  that->run = com_github_airutech_cnetsTransports_bufferToProtocol_run;
  com_github_airutech_cnetsTransports_bufferToProtocol_onCreate(that);
}

void com_github_airutech_cnetsTransports_bufferToProtocol_deinitialize(struct com_github_airutech_cnetsTransports_bufferToProtocol *that){
  com_github_airutech_cnetsTransports_bufferToProtocol_onDestroy(that);
}

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_bufferToProtocol_getRunnables(com_github_airutech_cnetsTransports_bufferToProtocol *that){
  
    com_github_airutech_cnets_runnablesContainer_create(runnables)
    RunnableStoppable_create(runnableStoppableObj,that, com_github_airutech_cnetsTransports_bufferToProtocol_)
    runnables.setCore(&runnables,runnableStoppableObj);
    return runnables;
}
/*[[[end]]] (checksum: dc920696e858f5f9a187b3795fa36e55)*/

void com_github_airutech_cnetsTransports_bufferToProtocol_run(void *t){

}

void com_github_airutech_cnetsTransports_bufferToProtocol_onStart(void *t){
  /*com_github_airutech_cnetsTransports_bufferToProtocol *that = (com_github_airutech_cnetsTransports_bufferToProtocol*)t;*/
}

void com_github_airutech_cnetsTransports_bufferToProtocol_onStop(void *that){
  /*com_github_airutech_cnetsTransports_bufferToProtocol *that = (com_github_airutech_cnetsTransports_bufferToProtocol*)t;*/
}


void com_github_airutech_cnetsTransports_bufferToProtocol_onCreate(com_github_airutech_cnetsTransports_bufferToProtocol *that){
  
  return;
}

void com_github_airutech_cnetsTransports_bufferToProtocol_onDestroy(com_github_airutech_cnetsTransports_bufferToProtocol *that){
  
  return;
}


void com_github_airutech_cnetsTransports_bufferToProtocol_onKernels(com_github_airutech_cnetsTransports_bufferToProtocol *that){
  
  return;
}