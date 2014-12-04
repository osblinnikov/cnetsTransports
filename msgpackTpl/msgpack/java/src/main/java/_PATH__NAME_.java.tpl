<%import parsing_java
p = reload(parsing_java)
p.parsingGernet(a)%>${p.importBlocks(a)}
import java.io.IOException;
public class ${a.className}  implements cnetsMessagePackable {
  @Override
  public boolean serializeWith(cnetsSerializeValue s, Object target){
    ${a.targetClassName} that = (${a.targetClassName})target;
    try {
${p.serializeWith(a,'.msgpack.msgpack')}
    } catch (Exception e) {e.printStackTrace();return false;}
    return true;
  }

  @Override
  public boolean deserializeWith(cnetsDeserializeValue d, Object target) {
    ${a.targetClassName} that = (${a.targetClassName})target;
    try {
${p.deserializeWith(a,'.msgpack.msgpack')}
    } catch (Exception e) {e.printStackTrace();return false;}
    return true;
  }