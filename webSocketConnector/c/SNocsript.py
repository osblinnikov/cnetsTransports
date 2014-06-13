
from helper import *
#           Environment
Import( 'env', 'args' )

def add_dependencies(env, args):
  '''[[[cog
  import cogging as c
  c.tpl(cog,templateFile,c.a(prefix=configFile))
  ]]]'''

  AddDependency(args,'com_github_airutech_cnetsTransports_protocolToBuffer',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnetsTransports/protocolToBuffer/c'))
  AddDependency(args,'com_github_airutech_cnetsTransports_bufferToProtocol',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnetsTransports/bufferToProtocol/c'))
  AddDependency(args,'com_github_airutech_cnetsTransports_webSocket',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnetsTransports/webSocket/c'))
  AddDependency(args,'com_github_airutech_cnets_mapBuffer',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/mapBuffer/c'))
  AddDependency(args,'com_github_airutech_cnets_mapBuffer',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/mapBuffer/c'))
  AddDependency(args,'com_github_airutech_cnets_mapBuffer',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/mapBuffer/c'))
  AddDependency(args,'com_github_airutech_cnets_mapBuffer',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/mapBuffer/c'))
  AddDependency(args,'com_github_airutech_cnets_mapBuffer',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/mapBuffer/c'))
  AddDependency(args,'com_github_airutech_cnets_readerWriter',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/readerWriter/c'))
  AddDependency(args,'com_github_airutech_cnets_runnablesContainer',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/runnablesContainer/c'))
  AddDependency(args,'com_github_airutech_cnets_selector',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/selector/c'))
  AddDependency(args,'no_eyasys_mobileAlarm_types',join(args['PROJECTS_ROOT_PATH'],'src/eyasys.no/mobileAlarm/types/c'))
  AddDependency(args,'com_github_airutech_cnetsTransports_types',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnetsTransports/types/c'))
  AddDependency(args,'com_github_airutech_cnets_types',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnets/types/c'))
  '''[[[end]]] (checksum: 9d8fd6ee448698c46076ad55b071a3f4)'''
  AddPthreads(env, args)
  # AddNetwork(args) 

c = {}
c['PROG_NAME'] = 'com_github_airutech_cnetsTransports_webSocketConnector'
c['sourceFiles'] = ['webSocketConnector.c']
c['testFiles'] = ['webSocketConnectorTest.c']
c['runFiles'] = ['main.c']
c['defines'] = []
c['inclDepsDynamic'] = add_dependencies
c['inclDepsStatic'] = add_dependencies
DefaultLibraryConfig(c, env, args)