<%import parsing_java
p = reload(parsing_java)
p.parsingGernet(a)%>${p.importBlocks(a)}
import java.io.IOException;
public class ${a.className}  implements cnetsMessagePackable {
${p.getProps(a)}  
${p.getConstructor(a)}
 @Override
  public boolean serializeWith(msgPackSerializer s){
${p.serializeWith(a)}
    return true;
  }

  @Override
  public boolean deserializeWith(msgPackDeserializer d) {
    try {
${p.deserializeWith(a)}
    } catch (IOException e) {return false;}
    return true;
  }