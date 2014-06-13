

#ifndef com_github_airutech_cnetsTransports_webSocket_H
#define com_github_airutech_cnetsTransports_webSocket_H

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "github.com/airutech/cnets/readerWriter/c/include/readerWriter.h"
#include "github.com/airutech/cnets/runnablesContainer/c/include/runnablesContainer.h"
#include "github.com/airutech/cnets/selector/c/include/selector.h"
#include "github.com/airutech/cnets/queue/c/include/queue.h"
#include "github.com/airutech/cnets/mapBuffer/c/include/mapBuffer.h"
#include "github.com/airutech/cnetsTransports/types/c/include/types.h"

#undef com_github_airutech_cnetsTransports_webSocket_EXPORT_API
#if defined WIN32 && !defined __MINGW32__ && !defined(CYGWIN) && !defined(com_github_airutech_cnetsTransports_webSocket_STATIC)
  #ifdef com_github_airutech_cnetsTransports_webSocket_EXPORT
    #define com_github_airutech_cnetsTransports_webSocket_EXPORT_API __declspec(dllexport)
  #else
    #define com_github_airutech_cnetsTransports_webSocket_EXPORT_API __declspec(dllimport)
  #endif
#else
  #define com_github_airutech_cnetsTransports_webSocket_EXPORT_API extern
#endif

struct com_github_airutech_cnetsTransports_webSocket;

com_github_airutech_cnetsTransports_webSocket_EXPORT_API
void com_github_airutech_cnetsTransports_webSocket_initialize(struct com_github_airutech_cnetsTransports_webSocket *that);

com_github_airutech_cnetsTransports_webSocket_EXPORT_API
void com_github_airutech_cnetsTransports_webSocket_deinitialize(struct com_github_airutech_cnetsTransports_webSocket *that);

com_github_airutech_cnetsTransports_webSocket_EXPORT_API
void com_github_airutech_cnetsTransports_webSocket_onKernels(struct com_github_airutech_cnetsTransports_webSocket *that);

com_github_airutech_cnetsTransports_webSocket_EXPORT_API
com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_webSocket_getRunnables(struct com_github_airutech_cnetsTransports_webSocket *that);

#undef com_github_airutech_cnetsTransports_webSocket_onCreateMacro
#define com_github_airutech_cnetsTransports_webSocket_onCreateMacro(_NAME_) /**/

#define com_github_airutech_cnetsTransports_webSocket_create(_NAME_,_maxNodesCount,_initialConnection,_bindPort,_sslContext,_nodesReceivers,_connectionStatusReceivers,_buffersParameters,_r0,_r1,_r2)\
    com_github_airutech_cnetsTransports_webSocket _NAME_;\
    _NAME_.maxNodesCount = _maxNodesCount;\
    _NAME_.initialConnection = _initialConnection;\
    _NAME_.bindPort = _bindPort;\
    _NAME_.sslContext = _sslContext;\
    _NAME_.nodesReceivers = _nodesReceivers;\
    _NAME_.connectionStatusReceivers = _connectionStatusReceivers;\
    _NAME_.buffersParameters = _buffersParameters;\
    com_github_airutech_cnetsTransports_webSocket_onCreateMacro(_NAME_)\
    _NAME_.r0 = _r0;\
    _NAME_.r1 = _r1;\
    _NAME_.r2 = _r2;\
    com_github_airutech_cnetsTransports_webSocket_initialize(&_NAME_);\
    com_github_airutech_cnetsTransports_webSocket_onKernels(&_NAME_);

typedef struct com_github_airutech_cnetsTransports_webSocket{
    int maxNodesCount;String initialConnection;int bindPort;SSLContext sslContext;arrayObject nodesReceivers;arrayObject connectionStatusReceivers;arrayObject buffersParameters;reader r0;reader r1;reader r2;

  
  com_github_airutech_cnets_runnablesContainer (*getRunnables)(struct com_github_airutech_cnetsTransports_webSocket *that);
  void (*run)(void *that);
/*[[[end]]] (checksum: 8a4f2bb124527a8e2a1b991cbb0383a2)*/

}com_github_airutech_cnetsTransports_webSocket;

#undef com_github_airutech_cnetsTransports_webSocket_onCreateMacro
#define com_github_airutech_cnetsTransports_webSocket_onCreateMacro(_NAME_) /**/

#endif /* com_github_airutech_cnetsTransports_webSocket_H */