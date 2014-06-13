
from helper import *
#           Environment
Import( 'env', 'args' )

def add_dependencies(env, args):
  '''[[[cog
  import cogging as c
  c.tpl(cog,templateFile,c.a(prefix=configFile))
  ]]]'''

  AddDependency(args,'com_github_airutech_cnetsTransports_types',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnetsTransports/types/c'))
  AddDependency(args,'com_github_airutech_cnets_readerWriter',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/readerWriter/c'))
  AddDependency(args,'com_github_airutech_cnets_runnablesContainer',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/runnablesContainer/c'))
  AddDependency(args,'com_github_airutech_cnets_selector',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/selector/c'))
  AddDependency(args,'com_github_airutech_cnets_queue',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/queue/c'))
  AddDependency(args,'com_github_airutech_cnets_mapBuffer',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/mapBuffer/c'))
  '''[[[end]]] (checksum: a14217f9420594f879197800a8295ab7)'''
  AddPthreads(env, args)
  # AddNetwork(args) 

c = {}
c['PROG_NAME'] = 'com_github_airutech_cnetsTransports_sockJS'
c['sourceFiles'] = ['sockJS.c']
c['testFiles'] = ['sockJSTest.c']
c['runFiles'] = ['main.c']
c['defines'] = []
c['inclDepsDynamic'] = add_dependencies
c['inclDepsStatic'] = add_dependencies
DefaultLibraryConfig(c, env, args)