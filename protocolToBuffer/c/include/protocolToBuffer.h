

#ifndef com_github_airutech_cnetsTransports_protocolToBuffer_H
#define com_github_airutech_cnetsTransports_protocolToBuffer_H

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "github.com/airutech/cnets/types/c/include/types.h"
#include "github.com/airutech/cnetsTransports/types/c/include/types.h"
#include "github.com/airutech/cnets/readerWriter/c/include/readerWriter.h"
#include "github.com/airutech/cnets/queue/c/include/queue.h"
#include "github.com/airutech/cnets/runnablesContainer/c/include/runnablesContainer.h"
#include "github.com/airutech/cnets/selector/c/include/selector.h"
#include "github.com/airutech/cnets/mapBuffer/c/include/mapBuffer.h"

#undef com_github_airutech_cnetsTransports_protocolToBuffer_EXPORT_API
#if defined WIN32 && !defined __MINGW32__ && !defined(CYGWIN) && !defined(com_github_airutech_cnetsTransports_protocolToBuffer_STATIC)
  #ifdef com_github_airutech_cnetsTransports_protocolToBuffer_EXPORT
    #define com_github_airutech_cnetsTransports_protocolToBuffer_EXPORT_API __declspec(dllexport)
  #else
    #define com_github_airutech_cnetsTransports_protocolToBuffer_EXPORT_API __declspec(dllimport)
  #endif
#else
  #define com_github_airutech_cnetsTransports_protocolToBuffer_EXPORT_API extern
#endif

struct com_github_airutech_cnetsTransports_protocolToBuffer;

com_github_airutech_cnetsTransports_protocolToBuffer_EXPORT_API
void com_github_airutech_cnetsTransports_protocolToBuffer_initialize(struct com_github_airutech_cnetsTransports_protocolToBuffer *that);

com_github_airutech_cnetsTransports_protocolToBuffer_EXPORT_API
void com_github_airutech_cnetsTransports_protocolToBuffer_deinitialize(struct com_github_airutech_cnetsTransports_protocolToBuffer *that);

com_github_airutech_cnetsTransports_protocolToBuffer_EXPORT_API
void com_github_airutech_cnetsTransports_protocolToBuffer_onKernels(struct com_github_airutech_cnetsTransports_protocolToBuffer *that);

com_github_airutech_cnetsTransports_protocolToBuffer_EXPORT_API
com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_protocolToBuffer_getRunnables(struct com_github_airutech_cnetsTransports_protocolToBuffer *that);

#undef com_github_airutech_cnetsTransports_protocolToBuffer_onCreateMacro
#define com_github_airutech_cnetsTransports_protocolToBuffer_onCreateMacro(_NAME_) /**/

#define com_github_airutech_cnetsTransports_protocolToBuffer_create(_NAME_,_writers,_callbacks,_protocolToBuffersGridSize,_maxNodesCount,_w0,_r0,_r1,_r2)\
    com_github_airutech_cnetsTransports_protocolToBuffer _NAME_;\
    _NAME_.writers = _writers;\
    _NAME_.callbacks = _callbacks;\
    _NAME_.protocolToBuffersGridSize = _protocolToBuffersGridSize;\
    _NAME_.maxNodesCount = _maxNodesCount;\
    com_github_airutech_cnetsTransports_protocolToBuffer_onCreateMacro(_NAME_)\
    _NAME_.w0 = _w0;\
    _NAME_.r0 = _r0;\
    _NAME_.r1 = _r1;\
    _NAME_.r2 = _r2;\
    com_github_airutech_cnetsTransports_protocolToBuffer_initialize(&_NAME_);\
    com_github_airutech_cnetsTransports_protocolToBuffer_onKernels(&_NAME_);

typedef struct com_github_airutech_cnetsTransports_protocolToBuffer{
    arrayObject writers;arrayObject callbacks;int protocolToBuffersGridSize;int maxNodesCount;writer w0;reader r0;reader r1;reader r2;

  
  com_github_airutech_cnets_runnablesContainer (*getRunnables)(struct com_github_airutech_cnetsTransports_protocolToBuffer *that);
  void (*run)(void *that);
/*[[[end]]] (checksum: 5df03fd27c8c757e30c86ffbe70f0d9a)*/

}com_github_airutech_cnetsTransports_protocolToBuffer;

#undef com_github_airutech_cnetsTransports_protocolToBuffer_onCreateMacro
#define com_github_airutech_cnetsTransports_protocolToBuffer_onCreateMacro(_NAME_) /**/

#endif /* com_github_airutech_cnetsTransports_protocolToBuffer_H */