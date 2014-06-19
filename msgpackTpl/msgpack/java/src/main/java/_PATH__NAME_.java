<%
import sys
sys.path.insert(0, a.parserPath)

import parsing_java
p = reload(parsing_java)
p.parsingGernet(a)

%>
package ${a.read_data["path"]};

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/
/*[[[end]]]*/

  @Override
  public void fillNodeIds(cnetsProtocol outputMetaData, Object target) {
    //${a.targetClassName} that = (${a.targetClassName})target;
    //outputMetaData.setNodeUniqueIds(new int[]{});
    outputMetaData.setPublished(true);
  }

  @Override
  public void fromNodeId(int id, Object target) {
    //${a.targetClassName} that = (${a.targetClassName})target;
  }
}

