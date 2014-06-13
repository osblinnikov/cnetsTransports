

#ifndef com_github_airutech_cnetsTransports_sockJS_H
#define com_github_airutech_cnetsTransports_sockJS_H

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

#undef com_github_airutech_cnetsTransports_sockJS_EXPORT_API
#if defined WIN32 && !defined __MINGW32__ && !defined(CYGWIN) && !defined(com_github_airutech_cnetsTransports_sockJS_STATIC)
  #ifdef com_github_airutech_cnetsTransports_sockJS_EXPORT
    #define com_github_airutech_cnetsTransports_sockJS_EXPORT_API __declspec(dllexport)
  #else
    #define com_github_airutech_cnetsTransports_sockJS_EXPORT_API __declspec(dllimport)
  #endif
#else
  #define com_github_airutech_cnetsTransports_sockJS_EXPORT_API extern
#endif

struct com_github_airutech_cnetsTransports_sockJS;

com_github_airutech_cnetsTransports_sockJS_EXPORT_API
void com_github_airutech_cnetsTransports_sockJS_initialize(struct com_github_airutech_cnetsTransports_sockJS *that);

com_github_airutech_cnetsTransports_sockJS_EXPORT_API
void com_github_airutech_cnetsTransports_sockJS_deinitialize(struct com_github_airutech_cnetsTransports_sockJS *that);

com_github_airutech_cnetsTransports_sockJS_EXPORT_API
void com_github_airutech_cnetsTransports_sockJS_onKernels(struct com_github_airutech_cnetsTransports_sockJS *that);

com_github_airutech_cnetsTransports_sockJS_EXPORT_API
com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_sockJS_getRunnables(struct com_github_airutech_cnetsTransports_sockJS *that);

#undef com_github_airutech_cnetsTransports_sockJS_onCreateMacro
#define com_github_airutech_cnetsTransports_sockJS_onCreateMacro(_NAME_) /**/

#define com_github_airutech_cnetsTransports_sockJS_create(_NAME_,_inBuffersIds,_maxNodesCount,_maxBuffersCount,_initialConnection,_bindPort,_w0,_r0,_r1)\
    com_github_airutech_cnetsTransports_sockJS _NAME_;\
    _NAME_.inBuffersIds = _inBuffersIds;\
    _NAME_.maxNodesCount = _maxNodesCount;\
    _NAME_.maxBuffersCount = _maxBuffersCount;\
    _NAME_.initialConnection = _initialConnection;\
    _NAME_.bindPort = _bindPort;\
    com_github_airutech_cnetsTransports_sockJS_onCreateMacro(_NAME_)\
    _NAME_.w0 = _w0;\
    _NAME_.r0 = _r0;\
    _NAME_.r1 = _r1;\
    com_github_airutech_cnetsTransports_sockJS_initialize(&_NAME_);\
    com_github_airutech_cnetsTransports_sockJS_onKernels(&_NAME_);

typedef struct com_github_airutech_cnetsTransports_sockJS{
    arrayObject inBuffersIds;int maxNodesCount;int maxBuffersCount;String initialConnection;int bindPort;writer w0;reader r0;reader r1;

  
  com_github_airutech_cnets_runnablesContainer (*getRunnables)(struct com_github_airutech_cnetsTransports_sockJS *that);
  void (*run)(void *that);
/*[[[end]]] (checksum: ef925c74c9fe250f2a261c583e7b012e)*/

}com_github_airutech_cnetsTransports_sockJS;

#undef com_github_airutech_cnetsTransports_sockJS_onCreateMacro
#define com_github_airutech_cnetsTransports_sockJS_onCreateMacro(_NAME_) /**/

#endif /* com_github_airutech_cnetsTransports_sockJS_H */