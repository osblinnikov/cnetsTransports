/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "../include/msgpack.h"
void com_github_airutech_cnetsTransports_msgpack_onCreate(com_github_airutech_cnetsTransports_msgpack *that);
void com_github_airutech_cnetsTransports_msgpack_onDestroy(com_github_airutech_cnetsTransports_msgpack *that);
void com_github_airutech_cnetsTransports_msgpack_initialize(com_github_airutech_cnetsTransports_msgpack *that){
  com_github_airutech_cnetsTransports_msgpack_onCreate(that);
}

void com_github_airutech_cnetsTransports_msgpack_deinitialize(struct com_github_airutech_cnetsTransports_msgpack *that){
  com_github_airutech_cnetsTransports_msgpack_onDestroy(that);
}
/*[[[end]]] (checksum: e10a45126aabf43d6680cee9068f4376)*/

void com_github_airutech_cnetsTransports_msgpack_onCreate(com_github_airutech_cnetsTransports_msgpack *that){
  
  return;
}

void com_github_airutech_cnetsTransports_msgpack_onDestroy(com_github_airutech_cnetsTransports_msgpack *that){
  
  return;
}


void com_github_airutech_cnetsTransports_msgpack_onKernels(com_github_airutech_cnetsTransports_msgpack *that){
  
  return;
}