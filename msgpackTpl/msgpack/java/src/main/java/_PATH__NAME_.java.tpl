<%import parsing_java
p = reload(parsing_java)
p.parsingGernet(a)%>${p.importBlocks(a)}
import java.io.IOException;
public class ${a.className}  implements cnetsMessagePackable {
  @Override
  public boolean serializeWith(cnetsSerializeValue s, Object target){
    ${a.targetClassName} that = (${a.targetClassName})target;
${p.serializeWith(a)}
    return true;
  }

  @Override
  public boolean deserializeWith(cnetsDeserializeValue d, Object target) {
    ${a.targetClassName} that = (${a.targetClassName})target;
    try {
${p.deserializeWith(a)}
    } catch (IOException e) {return false;}
    return true;
  }