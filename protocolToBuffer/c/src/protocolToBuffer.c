/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "../include/protocolToBuffer.h"

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_protocolToBuffer_getRunnables(com_github_airutech_cnetsTransports_protocolToBuffer *that);
void com_github_airutech_cnetsTransports_protocolToBuffer_run(void *that);
void com_github_airutech_cnetsTransports_protocolToBuffer_onStart(void *that);
void com_github_airutech_cnetsTransports_protocolToBuffer_onStop(void *that);

void com_github_airutech_cnetsTransports_protocolToBuffer_onCreate(com_github_airutech_cnetsTransports_protocolToBuffer *that);
void com_github_airutech_cnetsTransports_protocolToBuffer_onDestroy(com_github_airutech_cnetsTransports_protocolToBuffer *that);

void com_github_airutech_cnetsTransports_protocolToBuffer_initialize(com_github_airutech_cnetsTransports_protocolToBuffer *that){
  that->getRunnables = com_github_airutech_cnetsTransports_protocolToBuffer_getRunnables;
  that->run = com_github_airutech_cnetsTransports_protocolToBuffer_run;
  com_github_airutech_cnetsTransports_protocolToBuffer_onCreate(that);
}

void com_github_airutech_cnetsTransports_protocolToBuffer_deinitialize(struct com_github_airutech_cnetsTransports_protocolToBuffer *that){
  com_github_airutech_cnetsTransports_protocolToBuffer_onDestroy(that);
}

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_protocolToBuffer_getRunnables(com_github_airutech_cnetsTransports_protocolToBuffer *that){
  
    com_github_airutech_cnets_runnablesContainer_create(runnables)
    RunnableStoppable_create(runnableStoppableObj,that, com_github_airutech_cnetsTransports_protocolToBuffer_)
    runnables.setCore(&runnables,runnableStoppableObj);
    return runnables;
}
/*[[[end]]] (checksum: 55661e5804d9928e5542bb63cdd4994e)*/

void com_github_airutech_cnetsTransports_protocolToBuffer_run(void *t){

}

void com_github_airutech_cnetsTransports_protocolToBuffer_onStart(void *t){
  /*com_github_airutech_cnetsTransports_protocolToBuffer *that = (com_github_airutech_cnetsTransports_protocolToBuffer*)t;*/
}

void com_github_airutech_cnetsTransports_protocolToBuffer_onStop(void *that){
  /*com_github_airutech_cnetsTransports_protocolToBuffer *that = (com_github_airutech_cnetsTransports_protocolToBuffer*)t;*/
}


void com_github_airutech_cnetsTransports_protocolToBuffer_onCreate(com_github_airutech_cnetsTransports_protocolToBuffer *that){
  
  return;
}

void com_github_airutech_cnetsTransports_protocolToBuffer_onDestroy(com_github_airutech_cnetsTransports_protocolToBuffer *that){
  
  return;
}


void com_github_airutech_cnetsTransports_protocolToBuffer_onKernels(com_github_airutech_cnetsTransports_protocolToBuffer *that){
  
  return;
}