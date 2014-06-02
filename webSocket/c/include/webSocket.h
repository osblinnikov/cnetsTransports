

#ifndef com_github_airutech_cnetsTransports_webSocket_H
#define com_github_airutech_cnetsTransports_webSocket_H

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "github.com/airutech/cnetsTransports/types/c/include/types.h"
#include "github.com/airutech/cnets/readerWriter/c/include/readerWriter.h"
#include "github.com/airutech/cnets/runnablesContainer/c/include/runnablesContainer.h"
#include "github.com/airutech/cnets/selector/c/include/selector.h"
#include "github.com/airutech/cnets/queue/c/include/queue.h"
#include "github.com/airutech/cnets/mapBuffer/c/include/mapBuffer.h"

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

#define com_github_airutech_cnetsTransports_webSocket_create(_NAME_,_inBuffersIds,_maxNodesCount,_maxBuffersCount,_initialConnection,_bindPort,_connectionStatus,_sslContext,_w0,_r0,_r1)\
    com_github_airutech_cnetsTransports_webSocket _NAME_;\
    _NAME_.inBuffersIds = _inBuffersIds;\
    _NAME_.maxNodesCount = _maxNodesCount;\
    _NAME_.maxBuffersCount = _maxBuffersCount;\
    _NAME_.initialConnection = _initialConnection;\
    _NAME_.bindPort = _bindPort;\
    _NAME_.connectionStatus = _connectionStatus;\
    _NAME_.sslContext = _sslContext;\
    com_github_airutech_cnetsTransports_webSocket_onCreateMacro(_NAME_)\
    _NAME_.w0 = _w0;\
    _NAME_.r0 = _r0;\
    _NAME_.r1 = _r1;\
    com_github_airutech_cnetsTransports_webSocket_initialize(&_NAME_);\
    com_github_airutech_cnetsTransports_webSocket_onKernels(&_NAME_);

typedef struct com_github_airutech_cnetsTransports_webSocket{
    arrayObject inBuffersIds;int maxNodesCount;int maxBuffersCount;String initialConnection;int bindPort;connectionStatusCallback connectionStatus;SSLContext sslContext;writer w0;reader r0;reader r1;

  
  com_github_airutech_cnets_runnablesContainer (*getRunnables)(struct com_github_airutech_cnetsTransports_webSocket *that);
  void (*run)(void *that);
/*[[[end]]] (checksum: bf905b81e40dc6b5886187a6aee0addc)*/

}com_github_airutech_cnetsTransports_webSocket;

#undef com_github_airutech_cnetsTransports_webSocket_onCreateMacro
#define com_github_airutech_cnetsTransports_webSocket_onCreateMacro(_NAME_) /**/

#endif /* com_github_airutech_cnetsTransports_webSocket_H */