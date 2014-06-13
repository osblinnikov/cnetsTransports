/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "../include/webSocketConnector.h"

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_webSocketConnector_getRunnables(com_github_airutech_cnetsTransports_webSocketConnector *that);
void com_github_airutech_cnetsTransports_webSocketConnector_run(void *that);
void com_github_airutech_cnetsTransports_webSocketConnector_onStart(void *that);
void com_github_airutech_cnetsTransports_webSocketConnector_onStop(void *that);

void com_github_airutech_cnetsTransports_webSocketConnector_onCreate(com_github_airutech_cnetsTransports_webSocketConnector *that);
void com_github_airutech_cnetsTransports_webSocketConnector_onDestroy(com_github_airutech_cnetsTransports_webSocketConnector *that);

void com_github_airutech_cnetsTransports_webSocketConnector_initialize(com_github_airutech_cnetsTransports_webSocketConnector *that){
  that->getRunnables = com_github_airutech_cnetsTransports_webSocketConnector_getRunnables;
  that->run = com_github_airutech_cnetsTransports_webSocketConnector_run;
  com_github_airutech_cnetsTransports_webSocketConnector_onCreate(that);
}

void com_github_airutech_cnetsTransports_webSocketConnector_deinitialize(struct com_github_airutech_cnetsTransports_webSocketConnector *that){
  com_github_airutech_cnetsTransports_webSocketConnector_onDestroy(that);
}

com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_webSocketConnector_getRunnables(com_github_airutech_cnetsTransports_webSocketConnector *that){
  
    com_github_airutech_cnets_runnablesContainer_create(runnables)
    
    that->arrContainers[0] = that->_protocolToBuffer_0.getRunnables(&that->_protocolToBuffer_0);
    that->arrContainers[1] = that->_bufferToProtocol_0.getRunnables(&that->_bufferToProtocol_0);
    that->arrContainers[2] = that->_webSocket.getRunnables(&that->_webSocket);

    arrayObject arr;
    arr.array = (void*)&that->arrContainers;
    arr.length = 3;
    arr.itemSize = sizeof(com_github_airutech_cnets_runnablesContainer);
    runnables.setContainers(&runnables,arr);
    return runnables;
}
/*[[[end]]] (checksum: 92f511b43fc9bcce103eacbee21f1bd8)*/

void com_github_airutech_cnetsTransports_webSocketConnector_run(void *t){
    com_github_airutech_cnetsTransports_webSocketConnector *that = (com_github_airutech_cnetsTransports_webSocketConnector*)t;
    that->_protocolToBuffer.run(&that->_protocolToBuffer);
    that->_bufferToProtocol.run(&that->_bufferToProtocol);
    that->_webSocket.run(&that->_webSocket);
}

void com_github_airutech_cnetsTransports_webSocketConnector_onStart(void *t){
  /*com_github_airutech_cnetsTransports_webSocketConnector *that = (com_github_airutech_cnetsTransports_webSocketConnector*)t;*/
}

void com_github_airutech_cnetsTransports_webSocketConnector_onStop(void *that){
  /*com_github_airutech_cnetsTransports_webSocketConnector *that = (com_github_airutech_cnetsTransports_webSocketConnector*)t;*/
}


void com_github_airutech_cnetsTransports_webSocketConnector_onCreate(com_github_airutech_cnetsTransports_webSocketConnector *that){
  
  return;
}

void com_github_airutech_cnetsTransports_webSocketConnector_onDestroy(com_github_airutech_cnetsTransports_webSocketConnector *that){
  
  return;
}


void com_github_airutech_cnetsTransports_webSocketConnector_onKernels(com_github_airutech_cnetsTransports_webSocketConnector *that){
  
  return;
}