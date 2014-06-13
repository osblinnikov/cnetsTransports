

#ifndef com_github_airutech_cnetsTransports_msgpack_H
#define com_github_airutech_cnetsTransports_msgpack_H

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "github.com/airutech/cnetsTransports/types/c/include/types.h"

#undef com_github_airutech_cnetsTransports_msgpack_EXPORT_API
#if defined WIN32 && !defined __MINGW32__ && !defined(CYGWIN) && !defined(com_github_airutech_cnetsTransports_msgpack_STATIC)
  #ifdef com_github_airutech_cnetsTransports_msgpack_EXPORT
    #define com_github_airutech_cnetsTransports_msgpack_EXPORT_API __declspec(dllexport)
  #else
    #define com_github_airutech_cnetsTransports_msgpack_EXPORT_API __declspec(dllimport)
  #endif
#else
  #define com_github_airutech_cnetsTransports_msgpack_EXPORT_API extern
#endif

struct com_github_airutech_cnetsTransports_msgpack;

com_github_airutech_cnetsTransports_msgpack_EXPORT_API
void com_github_airutech_cnetsTransports_msgpack_initialize(struct com_github_airutech_cnetsTransports_msgpack *that);

com_github_airutech_cnetsTransports_msgpack_EXPORT_API
void com_github_airutech_cnetsTransports_msgpack_deinitialize(struct com_github_airutech_cnetsTransports_msgpack *that);

com_github_airutech_cnetsTransports_msgpack_EXPORT_API
void com_github_airutech_cnetsTransports_msgpack_onKernels(struct com_github_airutech_cnetsTransports_msgpack *that);

com_github_airutech_cnetsTransports_msgpack_EXPORT_API
com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_msgpack_getRunnables(struct com_github_airutech_cnetsTransports_msgpack *that);

#undef com_github_airutech_cnetsTransports_msgpack_onCreateMacro
#define com_github_airutech_cnetsTransports_msgpack_onCreateMacro(_NAME_) /**/

#define com_github_airutech_cnetsTransports_msgpack_create(_NAME_)\
    com_github_airutech_cnetsTransports_msgpack _NAME_;\
    com_github_airutech_cnetsTransports_msgpack_onCreateMacro(_NAME_)\
    com_github_airutech_cnetsTransports_msgpack_initialize(&_NAME_);\
    com_github_airutech_cnetsTransports_msgpack_onKernels(&_NAME_);

typedef struct com_github_airutech_cnetsTransports_msgpack{
  
  
  com_github_airutech_cnets_runnablesContainer (*getRunnables)(struct com_github_airutech_cnetsTransports_msgpack *that);
  void (*run)(void *that);
/*[[[end]]] (checksum: 57f0803270777b0d12e70abffc0dadf8)*/

}com_github_airutech_cnetsTransports_msgpack;

#undef com_github_airutech_cnetsTransports_msgpack_onCreateMacro
#define com_github_airutech_cnetsTransports_msgpack_onCreateMacro(_NAME_) /**/

#endif /* com_github_airutech_cnetsTransports_msgpack_H */