<%import parsing_java,initProps
initp = reload(initProps)
p = reload(parsing_java)
p.parsingGernet(a)%>
import com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol;
import com.github.airutech.cnetsTransports.types.connectionStatus;
import java.nio.ByteBuffer;
${p.importBlocks(a)}
public class ${a.className} implements RunnableStoppable{
${p.getProps(a)}  
${p.getConstructor(a)}
${p.declareBlocks(a)}
  private void initialize(){
    /*init props*/
    ${initp.initializeProperties(a)}
    /*init buffers*/
    ${p.initializeBuffers(a)}
    /*init props after buffers*/
    ${initp.initializePropertiesAfterBuffers(a)}
    onKernels();
    ${p.initializeKernels(a)}
  }
  public runnablesContainer getRunnables(){
    ${p.getRunnables(a)}
  }