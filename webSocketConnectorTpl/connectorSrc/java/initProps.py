import math


def findPropValueByName(a,name):
    for v in a.read_data["props"]:
        if v["name"] == name:
            return v["value"]
    raise Exception(name+" property was not found but required for connector generator")

def initializeProperties(a):

    countNodesProcessors = findPropValueByName(a, "countNodesProcessors")
    countBuffersProcessors = findPropValueByName(a, "countBuffersProcessors")
    maxNodesCount = findPropValueByName(a,"maxNodesCount")
    buffersLengths = findPropValueByName(a,"buffersLengths")
    binBuffersSize = findPropValueByName(a,"binBuffersSize")
    readersCount = findPropValueByName(a,"readersCount")
    writersCount = findPropValueByName(a,"writersCount")

    out = []
    out.append("int i,l,rIter;")

    nodesLeftCount = maxNodesCount
    nodesPerProcessorCeil = int(math.ceil(float(maxNodesCount)/countNodesProcessors))
    for processorId in range(0, countNodesProcessors):
        if nodesLeftCount <= 0:
            break
        if nodesLeftCount < nodesPerProcessorCeil:
            nodesPerProcessorCeil = nodesLeftCount
        nodesLeftCount = nodesLeftCount - nodesPerProcessorCeil

        out.append("for(i=0; i<_connectionStatusBuffer_forNodes_"+str(processorId)+"_Arr.length; i++){")
        out.append("  _connectionStatusBuffer_forNodes_"+str(processorId)+"_Arr[i] = new connectionStatus();")
        out.append("}")

        out.append("l = _inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr_BinaryBuffers.length/_inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr.length;")
        out.append("for(i=0; i<_inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr.length; i++){")
        out.append("  _inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr[i] = new cnetsProtocol();")
        out.append("  _inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr[i].setData(ByteBuffer.wrap(_inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr_BinaryBuffers, i*l, l));")
        out.append("}")

    # this._bufferToProtocol_0_readers = new reader[2];
    # this._bufferToProtocol_0_readers_callbacks = new serializeStreamCallback[2];
    # this._bufferToProtocol_1_readers = new reader[1];
    # this._bufferToProtocol_1_readers_callbacks = new serializeStreamCallback[1];
    buffersPerProcessorCeil = int(math.ceil(float(readersCount)/countBuffersProcessors))
    buffersLeftCount = readersCount
    out.append("rIter = 0;")
    for processorId in range(0, countBuffersProcessors):
        if buffersLeftCount <= 0:
            break
        if buffersLeftCount < buffersPerProcessorCeil:
            buffersPerProcessorCeil = buffersLeftCount
        buffersLeftCount = buffersLeftCount - buffersPerProcessorCeil

        out.append("for(i=0; i<_bufferToProtocol_"+str(processorId)+"_readers.length; i++){")
        out.append("  _bufferToProtocol_"+str(processorId)+"_readers[i] = allReaders[rIter];")
        out.append("  _bufferToProtocol_"+str(processorId)+"_readers_callbacks[i] = allReaders_callbacks[rIter];")
        out.append("  rIter++;")
        out.append("}")

    out.append("for(i=0; i<_nodeRepositoryProtocolBufferArr.length; i++){")
    out.append("  _nodeRepositoryProtocolBufferArr[i] = new nodeRepositoryProtocol();")
    out.append("}")

    out.append("for(i=0; i<_connectionsBufferArr.length; i++){")
    out.append("  _connectionsBufferArr[i] = new cnetsConnections();")
    out.append("}")

    out.append("l = _connectionStatusBuffer_publish_Arr_BinaryBuffers.length/_connectionStatusBuffer_publish_Arr.length;")
    out.append("for(i=0; i<_connectionStatusBuffer_publish_Arr.length; i++){")
    out.append("  _connectionStatusBuffer_publish_Arr[i] = new cnetsProtocol();")
    out.append("  _connectionStatusBuffer_publish_Arr[i].setData(ByteBuffer.wrap(_connectionStatusBuffer_publish_Arr_BinaryBuffers, i*l, l));")
    out.append("}")

    out.append("l = _outputProtocolBuffer_Arr_BinaryBuffers.length/_outputProtocolBuffer_Arr.length;")
    out.append("for(i=0; i<_outputProtocolBuffer_Arr.length; i++){")
    out.append("  _outputProtocolBuffer_Arr[i] = new cnetsProtocol();")
    out.append("  _outputProtocolBuffer_Arr[i].setData(ByteBuffer.wrap(_outputProtocolBuffer_Arr_BinaryBuffers, i*l, l));")
    out.append("}")

    return '\n    '.join(out)


def initializePropertiesAfterBuffers(a):
    countNodesProcessors = findPropValueByName(a, "countNodesProcessors")
    maxNodesCount = findPropValueByName(a,"maxNodesCount")

    out = []
    nodesLeftCount = maxNodesCount
    nodesPerProcessorCeil = int(math.ceil(float(maxNodesCount)/countNodesProcessors))
    for processorId in range(0, countNodesProcessors):
        if nodesLeftCount <= 0:
            break
        if nodesLeftCount < nodesPerProcessorCeil:
            nodesPerProcessorCeil = nodesLeftCount
        nodesLeftCount = nodesLeftCount - nodesPerProcessorCeil
        out.append("_nodesReceivers_writers["+str(processorId)+"] = _inputProtocolBuffer_forNodes_"+str(processorId)+".getWriter(0);")
        out.append("_connectionStatusReceivers_writers["+str(processorId)+"] = _connectionStatusBuffer_forNodes_"+str(processorId)+".getWriter(0);")

    return '\n    '.join(out)