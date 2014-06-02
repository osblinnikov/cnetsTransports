/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "../include/webSocket.h"

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_webSocket_getRunnables(com_github_airutech_cnetsTransports_webSocket *that);
void com_github_airutech_cnetsTransports_webSocket_run(void *that);
void com_github_airutech_cnetsTransports_webSocket_onStart(void *that);
void com_github_airutech_cnetsTransports_webSocket_onStop(void *that);

void com_github_airutech_cnetsTransports_webSocket_onCreate(com_github_airutech_cnetsTransports_webSocket *that);
void com_github_airutech_cnetsTransports_webSocket_onDestroy(com_github_airutech_cnetsTransports_webSocket *that);

void com_github_airutech_cnetsTransports_webSocket_initialize(com_github_airutech_cnetsTransports_webSocket *that){
  that->getRunnables = com_github_airutech_cnetsTransports_webSocket_getRunnables;
  that->run = com_github_airutech_cnetsTransports_webSocket_run;
  com_github_airutech_cnetsTransports_webSocket_onCreate(that);
}

void com_github_airutech_cnetsTransports_webSocket_deinitialize(struct com_github_airutech_cnetsTransports_webSocket *that){
  com_github_airutech_cnetsTransports_webSocket_onDestroy(that);
}

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_webSocket_getRunnables(com_github_airutech_cnetsTransports_webSocket *that){
  
    com_github_airutech_cnets_runnablesContainer_create(runnables)
    RunnableStoppable_create(runnableStoppableObj,that, com_github_airutech_cnetsTransports_webSocket_)
    runnables.setCore(&runnables,runnableStoppableObj);
    return runnables;
}
/*[[[end]]] (checksum: 46725cec27cd897cc0b14dee8c377b65)*/

void com_github_airutech_cnetsTransports_webSocket_run(void *t){

}

void com_github_airutech_cnetsTransports_webSocket_onStart(void *t){
  /*com_github_airutech_cnetsTransports_webSocket *that = (com_github_airutech_cnetsTransports_webSocket*)t;*/
}

void com_github_airutech_cnetsTransports_webSocket_onStop(void *that){
  /*com_github_airutech_cnetsTransports_webSocket *that = (com_github_airutech_cnetsTransports_webSocket*)t;*/
}


void com_github_airutech_cnetsTransports_webSocket_onCreate(com_github_airutech_cnetsTransports_webSocket *that){
  
  return;
}

void com_github_airutech_cnetsTransports_webSocket_onDestroy(com_github_airutech_cnetsTransports_webSocket *that){
  
  return;
}


void com_github_airutech_cnetsTransports_webSocket_onKernels(com_github_airutech_cnetsTransports_webSocket *that){
  
  return;
}