

#ifndef com_github_airutech_cnetsTransports_types_H
#define com_github_airutech_cnetsTransports_types_H

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


#undef com_github_airutech_cnetsTransports_types_EXPORT_API
#if defined WIN32 && !defined __MINGW32__ && !defined(CYGWIN) && !defined(com_github_airutech_cnetsTransports_types_STATIC)
  #ifdef com_github_airutech_cnetsTransports_types_EXPORT
    #define com_github_airutech_cnetsTransports_types_EXPORT_API __declspec(dllexport)
  #else
    #define com_github_airutech_cnetsTransports_types_EXPORT_API __declspec(dllimport)
  #endif
#else
  #define com_github_airutech_cnetsTransports_types_EXPORT_API extern
#endif

struct com_github_airutech_cnetsTransports_types;

com_github_airutech_cnetsTransports_types_EXPORT_API
void com_github_airutech_cnetsTransports_types_initialize(struct com_github_airutech_cnetsTransports_types *that);

com_github_airutech_cnetsTransports_types_EXPORT_API
void com_github_airutech_cnetsTransports_types_deinitialize(struct com_github_airutech_cnetsTransports_types *that);

com_github_airutech_cnetsTransports_types_EXPORT_API
void com_github_airutech_cnetsTransports_types_onKernels(struct com_github_airutech_cnetsTransports_types *that);

com_github_airutech_cnetsTransports_types_EXPORT_API
com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_types_getRunnables(struct com_github_airutech_cnetsTransports_types *that);

#undef com_github_airutech_cnetsTransports_types_onCreateMacro
#define com_github_airutech_cnetsTransports_types_onCreateMacro(_NAME_) /**/

#define com_github_airutech_cnetsTransports_types_create(_NAME_)\
    com_github_airutech_cnetsTransports_types _NAME_;\
    com_github_airutech_cnetsTransports_types_onCreateMacro(_NAME_)\
    com_github_airutech_cnetsTransports_types_initialize(&_NAME_);\
    com_github_airutech_cnetsTransports_types_onKernels(&_NAME_);

typedef struct com_github_airutech_cnetsTransports_types{
  
  
  com_github_airutech_cnets_runnablesContainer (*getRunnables)(struct com_github_airutech_cnetsTransports_types *that);
  void (*run)(void *that);
/*[[[end]]] (checksum: 2b66377027e8dd18f3ef33e72b5ad839)*/

}com_github_airutech_cnetsTransports_types;

#undef com_github_airutech_cnetsTransports_types_onCreateMacro
#define com_github_airutech_cnetsTransports_types_onCreateMacro(_NAME_) /**/

#endif /* com_github_airutech_cnetsTransports_types_H */