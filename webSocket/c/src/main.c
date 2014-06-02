
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "../include/webSocket.h"
/*[[[end]]] (checksum: ed7121d455934f11b3aba9286e34361c)*/
int main(int argc, char* argv[]){
  com_github_airutech_cnetsTransports_webSocket_create(classObj,arrayObjectNULL(),0,0,0,0,0,0,writerNULL(),readerNULL(),readerNULL());
    com_github_airutech_cnets_runnablesContainer runnables = classObj.getRunnables(&classObj);
    runnables.launch(&runnables,TRUE);
    
  return 0;
}
