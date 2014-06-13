/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "../include/sockJS.h"

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_sockJS_getRunnables(com_github_airutech_cnetsTransports_sockJS *that);
void com_github_airutech_cnetsTransports_sockJS_run(void *that);
void com_github_airutech_cnetsTransports_sockJS_onStart(void *that);
void com_github_airutech_cnetsTransports_sockJS_onStop(void *that);

void com_github_airutech_cnetsTransports_sockJS_onCreate(com_github_airutech_cnetsTransports_sockJS *that);
void com_github_airutech_cnetsTransports_sockJS_onDestroy(com_github_airutech_cnetsTransports_sockJS *that);

void com_github_airutech_cnetsTransports_sockJS_initialize(com_github_airutech_cnetsTransports_sockJS *that){
  that->getRunnables = com_github_airutech_cnetsTransports_sockJS_getRunnables;
  that->run = com_github_airutech_cnetsTransports_sockJS_run;
  com_github_airutech_cnetsTransports_sockJS_onCreate(that);
}

void com_github_airutech_cnetsTransports_sockJS_deinitialize(struct com_github_airutech_cnetsTransports_sockJS *that){
  com_github_airutech_cnetsTransports_sockJS_onDestroy(that);
}

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_sockJS_getRunnables(com_github_airutech_cnetsTransports_sockJS *that){
  
    com_github_airutech_cnets_runnablesContainer_create(runnables)
    RunnableStoppable_create(runnableStoppableObj,that, com_github_airutech_cnetsTransports_sockJS_)
    runnables.setCore(&runnables,runnableStoppableObj);
    return runnables;
}
/*[[[end]]] (checksum: 274c5391fba454db82351541d7cdae39)*/

void com_github_airutech_cnetsTransports_sockJS_run(void *t){

}

void com_github_airutech_cnetsTransports_sockJS_onStart(void *t){
  /*com_github_airutech_cnetsTransports_sockJS *that = (com_github_airutech_cnetsTransports_sockJS*)t;*/
}

void com_github_airutech_cnetsTransports_sockJS_onStop(void *that){
  /*com_github_airutech_cnetsTransports_sockJS *that = (com_github_airutech_cnetsTransports_sockJS*)t;*/
}


void com_github_airutech_cnetsTransports_sockJS_onCreate(com_github_airutech_cnetsTransports_sockJS *that){
  
  return;
}

void com_github_airutech_cnetsTransports_sockJS_onDestroy(com_github_airutech_cnetsTransports_sockJS *that){
  
  return;
}


void com_github_airutech_cnetsTransports_sockJS_onKernels(com_github_airutech_cnetsTransports_sockJS *that){
  
  return;
}