

#ifndef com_github_airutech_cnetsTransports_bufferToProtocol_H
#define com_github_airutech_cnetsTransports_bufferToProtocol_H

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "github.com/airutech/cnetsTransports/types/c/include/types.h"
#include "github.com/airutech/cnets/types/c/include/types.h"
#include "github.com/airutech/cnets/readerWriter/c/include/readerWriter.h"
#include "github.com/airutech/cnets/queue/c/include/queue.h"
#include "github.com/airutech/cnets/runnablesContainer/c/include/runnablesContainer.h"
#include "github.com/airutech/cnets/selector/c/include/selector.h"
#include "github.com/airutech/cnets/mapBuffer/c/include/mapBuffer.h"

#undef com_github_airutech_cnetsTransports_bufferToProtocol_EXPORT_API
#if defined WIN32 && !defined __MINGW32__ && !defined(CYGWIN) && !defined(com_github_airutech_cnetsTransports_bufferToProtocol_STATIC)
  #ifdef com_github_airutech_cnetsTransports_bufferToProtocol_EXPORT
    #define com_github_airutech_cnetsTransports_bufferToProtocol_EXPORT_API __declspec(dllexport)
  #else
    #define com_github_airutech_cnetsTransports_bufferToProtocol_EXPORT_API __declspec(dllimport)
  #endif
#else
  #define com_github_airutech_cnetsTransports_bufferToProtocol_EXPORT_API extern
#endif

struct com_github_airutech_cnetsTransports_bufferToProtocol;

com_github_airutech_cnetsTransports_bufferToProtocol_EXPORT_API
void com_github_airutech_cnetsTransports_bufferToProtocol_initialize(struct com_github_airutech_cnetsTransports_bufferToProtocol *that);

com_github_airutech_cnetsTransports_bufferToProtocol_EXPORT_API
void com_github_airutech_cnetsTransports_bufferToProtocol_deinitialize(struct com_github_airutech_cnetsTransports_bufferToProtocol *that);

com_github_airutech_cnetsTransports_bufferToProtocol_EXPORT_API
void com_github_airutech_cnetsTransports_bufferToProtocol_onKernels(struct com_github_airutech_cnetsTransports_bufferToProtocol *that);

com_github_airutech_cnetsTransports_bufferToProtocol_EXPORT_API
com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_bufferToProtocol_getRunnables(struct com_github_airutech_cnetsTransports_bufferToProtocol *that);

#undef com_github_airutech_cnetsTransports_bufferToProtocol_onCreateMacro
#define com_github_airutech_cnetsTransports_bufferToProtocol_onCreateMacro(_NAME_) /**/

#define com_github_airutech_cnetsTransports_bufferToProtocol_create(_NAME_,_readers,_callbacks,_bufferIndexOffset,_maxNodesCount,_w0,_r0,_r1)\
    com_github_airutech_cnetsTransports_bufferToProtocol _NAME_;\
    _NAME_.readers = _readers;\
    _NAME_.callbacks = _callbacks;\
    _NAME_.bufferIndexOffset = _bufferIndexOffset;\
    _NAME_.maxNodesCount = _maxNodesCount;\
    com_github_airutech_cnetsTransports_bufferToProtocol_onCreateMacro(_NAME_)\
    _NAME_.w0 = _w0;\
    _NAME_.r0 = _r0;\
    _NAME_.r1 = _r1;\
    com_github_airutech_cnetsTransports_bufferToProtocol_initialize(&_NAME_);\
    com_github_airutech_cnetsTransports_bufferToProtocol_onKernels(&_NAME_);

typedef struct com_github_airutech_cnetsTransports_bufferToProtocol{
    arrayObject readers;arrayObject callbacks;int bufferIndexOffset;int maxNodesCount;writer w0;reader r0;reader r1;

  
  com_github_airutech_cnets_runnablesContainer (*getRunnables)(struct com_github_airutech_cnetsTransports_bufferToProtocol *that);
  void (*run)(void *that);
/*[[[end]]] (checksum: 5de1d8a1b3c4a7e4c9f6fb665f61e1ab)*/

}com_github_airutech_cnetsTransports_bufferToProtocol;

#undef com_github_airutech_cnetsTransports_bufferToProtocol_onCreateMacro
#define com_github_airutech_cnetsTransports_bufferToProtocol_onCreateMacro(_NAME_) /**/

#endif /* com_github_airutech_cnetsTransports_bufferToProtocol_H */