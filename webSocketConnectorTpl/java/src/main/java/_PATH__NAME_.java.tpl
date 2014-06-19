<%import parsing_java
p = reload(parsing_java)
p.parsingGernet(a)%>${p.importBlocks(a)}
public class ${a.className} implements RunnableStoppable{
${p.getProps(a)}
  private String[] subscribedBuffersNames = null;
  private writer[] allWriters = null;
  private reader[] allReaders = null;
  private deserializeStreamCallback[] allWriters_callbacks = null;
  private serializeStreamCallback[] allReaders_callbacks = null;
${p.getConstructor(a)}
${p.declareBlocks(a)}
  private void initialize(){
    ${p.fillConnectorsNames(a)}
    ${p.initializeBuffers(a)}
    onKernels();
    ${p.initializeKernels(a)}
  }
  public runnablesContainer getRunnables(){
    ${p.getRunnables(a)}
  }