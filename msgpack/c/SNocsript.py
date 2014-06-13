
from helper import *
#           Environment
Import( 'env', 'args' )

def add_dependencies(env, args):
    '''[[[cog
    import cogging as c
    c.tpl(cog,templateFile,c.a(prefix=configFile))
    ]]]'''

    AddDependency(args,'com_github_airutech_cnetsTransports_types',join(args['PROJECTS_ROOT_PATH'],'src/github.com/airutech/cnetsTransports/types/c'))
    '''[[[end]]] (checksum: 665d0c1bdec12fcf32b6c6f1329395f8)'''
    # AddPthreads(env, args)
    # AddNetwork(args)

c = {}
c['PROG_NAME'] = 'com_github_airutech_cnetsTransports_msgpack'
c['sourceFiles'] = ['msgpack.c']
c['testFiles'] = ['msgpackTest.c']
c['runFiles'] = ['main.c']
c['defines'] = []
c['inclDepsDynamic'] = add_dependencies
c['inclDepsStatic'] = add_dependencies
DefaultLibraryConfig(c, env, args)