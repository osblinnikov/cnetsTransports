import json
import math


def findPropValueByName(a,name):
    for v in a.read_data["props"]:
        if v["name"] == name:
            return v["value"]
    raise Exception(name+" property was not found but required for connector generator")

def getGernetProps(a):
    countNodesProcessors = findPropValueByName(a, "countNodesProcessors")
    countBuffersProcessors = findPropValueByName(a, "countBuffersProcessors")
    maxNodesCount = findPropValueByName(a,"maxNodesCount")
    buffersLengths = findPropValueByName(a,"buffersLengths")
    binBuffersSize = findPropValueByName(a,"binBuffersSize")
    readersCount = len(a.read_data["connection"]["readFrom"]) - 1
    writersCount = len(a.read_data["connection"]["writeTo"]) - 1
    out = []
    nodesLeftCount = maxNodesCount
    nodesPerProcessorCeil = int(math.ceil(float(maxNodesCount)/countNodesProcessors))
    for processorId in range(0, countNodesProcessors):
        if nodesLeftCount <= 0:
            break
        if nodesLeftCount < nodesPerProcessorCeil:
            nodesPerProcessorCeil = nodesLeftCount
        nodesLeftCount = nodesLeftCount - nodesPerProcessorCeil

        out.append(dict(
            name="_connectionStatusBuffer_forNodes_"+str(processorId)+"_Arr",
            type="connectionStatus[]",
            size=buffersLengths
        ))
        out.append(dict(
            name="_inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr",
            type="cnetsProtocol[]",
            size=buffersLengths
        ))
        out.append(dict(
            name="_inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr_BinaryBuffers",
            type="byte[]",
            size=binBuffersSize*buffersLengths
        ))
        out.append(dict(
            name="_protocolToBuffer_"+str(processorId)+"_writers",
            type="writer[]",
            size=nodesPerProcessorCeil
        ))
        out.append(dict(
            name="_protocolToBuffer_"+str(processorId)+"_writers_callbacks",
            type="deserializeStreamCallback[]",
            size=nodesPerProcessorCeil
        ))

    buffersPerProcessorCeil = int(math.ceil(float(readersCount)/countBuffersProcessors))
    buffersLeftCount = readersCount
    for processorId in range(0, countBuffersProcessors):
        if buffersLeftCount <= 0:
            break
        if buffersLeftCount < buffersPerProcessorCeil:
            buffersPerProcessorCeil = buffersLeftCount
        buffersLeftCount = buffersLeftCount - buffersPerProcessorCeil

        out.append(dict(
            name="_bufferToProtocol_"+str(processorId)+"_readers",
            type="reader[]",
            size=buffersPerProcessorCeil
        ))
        out.append(dict(
            name="_bufferToProtocol_"+str(processorId)+"_readers_callbacks",
            type="serializeStreamCallback[]",
            size=buffersPerProcessorCeil
        ))


    out.append(dict(
        name="_nodesReceivers_writers",
        type="writer[]",
        size=countNodesProcessors
    ))
    out.append(dict(
        name="_connectionStatusReceivers_writers",
        type="writer[]",
        size=countNodesProcessors
    ))
    out.append(dict(
        name="_allBuffers_readers",
        type="reader[]",
        size=readersCount
    ))
    out.append(dict(
        name="_nodeRepositoryProtocolBufferArr",
        type="nodeRepositoryProtocol[]",
        size=2
    ))
    out.append(dict(
        name="_outputProtocolBuffer_Arr",
        type="cnetsProtocol[]",
        size=buffersLengths
    ))
    out.append(dict(
        name="_outputProtocolBuffer_Arr_BinaryBuffers",
        type="byte[]",
        size=binBuffersSize*buffersLengths
    ))
    out.append(dict(
        name="_connectionStatusBuffer_publish_Arr",
        type="cnetsProtocol[]",
        size=buffersLengths
    ))
    out.append(dict(
        name="_connectionStatusBuffer_publish_Arr_BinaryBuffers",
        type="byte[]",
        size=binBuffersSize*buffersLengths
    ))

    out.append(dict(
        name="_connectionsBufferArr",
        type="cnetsConnections[]",
        size=buffersLengths
    ))

    return json.dumps(out)


def initNodeRepositoryBuffer(nodeRepositoryProtocolBufferId,transportKernelId, countNodesProcessors, countBuffersProcessors, timeoutInterval):
    #############
    nodeRepositoryProtocolWriteTo = []

    #add transportKernel connection
    nodeRepositoryProtocolWriteTo.append(dict(
        type="com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol",
        blockId=transportKernelId,
        pinId=2
    ))
    #connect to the next kernel after transport kernel
    for i in range(transportKernelId+1,transportKernelId+1+countNodesProcessors+countBuffersProcessors):
        nodeRepositoryProtocolWriteTo.append(dict(
            type="com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol",
            blockId=i,
            pinId=1
        ))
    #create buffer for nodeRepository

    return dict(
        id=nodeRepositoryProtocolBufferId,
        type="buffer",
        name="_nodeRepositoryProtocolBuffer",
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_nodeRepositoryProtocolBufferArr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value="moduleUniqueName+\"_nodeRepositoryProtocolBuffer\""),
            dict(value=countNodesProcessors + countBuffersProcessors + 1),# +1 for transportKernel
            dict(value="statsInterval")#as module argument
        ],
        connection=dict(
            writeTo=nodeRepositoryProtocolWriteTo,
            readFrom=[dict(type="com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol")]
        )
    )

def initConnectionsBuffer(connectionsBufferId, transportKernelId, timeoutInterval):
    return dict(
        id=connectionsBufferId,
        type="buffer",
        name="_connectionsBuffer",
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_connectionsBufferArr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value="moduleUniqueName+\"_connectionsBuffer\""),
            dict(value=1),# configure connections only in transport
            dict(value="statsInterval")#as module argument
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.cnetsConnections",
                blockId=transportKernelId,
                pinId=1
            )],
            readFrom=[dict(
                type="com.github.airutech.cnetsTransports.types.cnetsConnections",
                blockId="export",
                pinId=0
            )]
        )
    )

def initSendProtocolsBuffer(sendProtocolsBufferId, countBuffersProcessors, transportKernelId, timeoutInterval):
    readFromList = []
    for i in range(0,countBuffersProcessors):
        readFromList.append(dict(type="com.github.airutech.cnetsTransports.types.cnetsProtocol"))
    return dict(
        id=sendProtocolsBufferId,
        type="buffer",
        name="_outputProtocolBuffer",
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_outputProtocolBuffer_Arr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value="moduleUniqueName+\"_outputProtocolBuffer\""),
            dict(value=1),# configure connections only in transport
            dict(value="statsInterval")#as module argument
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.cnetsProtocol",
                blockId=transportKernelId, #after transport kernel will be the protocolToBuffer
                pinId=0
            )],
            readFrom=readFromList,
        )
    )

def initConnStatusBuffer(pubConnStatusBufferId,transportKernelId, timeoutInterval, processorId):
    return dict(
        id=pubConnStatusBufferId+1+processorId,
        type="buffer",
        name="_connectionStatusBuffer_forNodes_"+str(processorId),
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_connectionStatusBuffer_forNodes_"+str(processorId)+"_Arr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value="moduleUniqueName+\"_connectionStatusBuffer_forNodes_"+str(processorId)+"\""),
            dict(value=1),# configure connections only in transport
            dict(value="statsInterval")#as module argument
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.connectionStatus",
                blockId=transportKernelId+1+processorId, #after transport kernel will be the protocolToBuffer
                pinId=0
            )],
            readFrom=[], # dict(type="com.github.airutech.cnetsTransports.types.connectionStatus") , need to get it manually in source code
            comment="need to get readers manually in source code"
        )
    )

def initPublishConnStatusBuffer(pubConnStatusBufferId, transportKernelId, countBuffersProcessors, countNodesProcessors, timeoutInterval):
    writeToList = []
    for processorId in range(countNodesProcessors, countBuffersProcessors+countNodesProcessors):
        writeToList.append(dict(
            type="com.github.airutech.cnetsTransports.types.connectionStatus",
            blockId=transportKernelId+1+processorId, #after transport kernel will be the protocolToBuffer
            pinId=0
        ))
    return dict(
        id=pubConnStatusBufferId,
        type="buffer",
        name="_connectionStatusBuffer_publish",
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_connectionStatusBuffer_publish_Arr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value="moduleUniqueName+\"_connectionStatusBuffer_publish\""),
            dict(value=countBuffersProcessors),# configure connections only in transport
            dict(value="statsInterval")#as module argument
        ],
        connection=dict(
            writeTo=writeToList,
            readFrom=[dict(type="com.github.airutech.cnetsTransports.types.connectionStatus")]
        )
    )

def initRecvProtocolsBuffer(pubConnStatusBufferId, transportKernelId, countNodesProcessors, timeoutInterval, processorId):
    return dict(
        id=pubConnStatusBufferId+1+countNodesProcessors+processorId,
        type="buffer",
        name="_inputProtocolBuffer_forNodes"+str(processorId),
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_connectionStatusBuffer_forNodes_"+str(processorId)+"_Arr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value="moduleUniqueName+\"_inputProtocolBuffer_forNodes"+str(processorId)+"\""),
            dict(value=1),# configure connections only in transport
            dict(value="statsInterval")#as module argument
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.cnetsProtocol",
                blockId=transportKernelId+1+processorId, #after transport kernel will be the protocolToBuffer
                pinId=2
            )],
            readFrom=[], # dict(type="com.github.airutech.cnetsTransports.types.cnetsProtocol") , need to get it manually in source code
            comment="need to get readers manually in source code"
        )
    )

def initTransportKernel(transportKernelId,pubConnStatusBufferId):
    return dict(
        id=transportKernelId,
        type="kernel",
        name="_webSocket",
        path="com.github.airutech.cnetsTransports.webSocket",
        ver="[0.0.0,)",
        args=[
            dict(value="maxNodesCount"),
            dict(value="serverUrl"),
            dict(value="bindPort"),
            dict(value="null", name="sslContext"),
            dict(value="_nodesReceivers_writers"),
            dict(value="_connectionStatusReceivers_writers"),
            dict(value="_allBuffers_readers")
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.connectionStatus",
                blockId=pubConnStatusBufferId,
                pinId=0
            )],
            readFrom=[
                dict(type="com.github.airutech.cnetsTransports.types.cnetsProtocol"),
                dict(type="com.github.airutech.cnetsTransports.types.cnetsConnections"),
                dict(type="com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol")
            ]
        )
    )

def initProtocolToBufferKernel(transportKernelId, countNodesProcessors, processorId, maxNodesCount, nodeRepositoryProtocolBufferId):
    nodesIndexOffset = int(math.ceil(float(maxNodesCount)/countNodesProcessors))*processorId
    return dict(
        id=transportKernelId+1+processorId,
        type="kernel",
        name="_protocolToBuffer_"+str(processorId),
        path="com.github.airutech.cnetsTransports.protocolToBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_protocolToBuffer_"+str(processorId)+"_writers"),
            dict(value="_protocolToBuffer_"+str(processorId)+"_writers_callbacks"),
            dict(value=nodesIndexOffset),
            dict(value=countNodesProcessors),
            dict(value=maxNodesCount)
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol",
                blockId=nodeRepositoryProtocolBufferId,
                pinId=0
            )],
            readFrom=[
                dict(type="com.github.airutech.cnetsTransports.types.connectionStatus"),
                dict(type="com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol"),
                dict(type="com.github.airutech.cnetsTransports.types.cnetsProtocol")
            ]
        )
    )

def initBufferToProtocolKernel(transportKernelId, countNodesProcessors, processorId, maxNodesCount, sendProtocolsBufferId, bufferIndexOffset):
    return dict(
        id=transportKernelId+countNodesProcessors+1+processorId,
        type="kernel",
        name="_bufferToProtocol_"+str(processorId),
        path="com.github.airutech.cnetsTransports.bufferToProtocol",
        ver="[0.0.0,)",
        args=[
            dict(value="_bufferToProtocol_"+str(processorId)+"_readers"),
            dict(value="_bufferToProtocol_"+str(processorId)+"_readers_callbacks"),
            dict(value=bufferIndexOffset),
            dict(value=maxNodesCount)
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.cnetsProtocol",
                blockId=sendProtocolsBufferId,
                pinId=0
            )],
            readFrom=[
                dict(type="com.github.airutech.cnetsTransports.types.connectionStatus"),
                dict(type="com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol"),
            ]
        )
    )

def getGernetBlocks(a):
    out = []
    timeoutInterval = findPropValueByName(a, "timeoutInterval")
    countNodesProcessors = findPropValueByName(a, "countNodesProcessors")
    countBuffersProcessors = findPropValueByName(a, "countBuffersProcessors")
    maxNodesCount = findPropValueByName(a,"maxNodesCount")
    buffersLengths = findPropValueByName(a,"buffersLengths")
    binBuffersSize = findPropValueByName(a,"binBuffersSize")
    readersCount = len(a.read_data["connection"]["readFrom"]) - 1
    writersCount = len(a.read_data["connection"]["writeTo"]) - 1

    ##############################################
    ### Forecast of the first transport kernel ###

    nodeRepositoryProtocolBufferId = 0
    connectionsBufferId = 1
    sendProtocolsBufferId = 2
    pubConnStatusBufferId = 3
    transportKernelId = pubConnStatusBufferId+1 + 2*countNodesProcessors # id of first kernel in blocks, after all buffers
    ##############################################

    out.append(initNodeRepositoryBuffer(nodeRepositoryProtocolBufferId,transportKernelId, countNodesProcessors, countBuffersProcessors, timeoutInterval))
    out.append(initConnectionsBuffer(connectionsBufferId,transportKernelId, timeoutInterval))
    out.append(initSendProtocolsBuffer(sendProtocolsBufferId,countBuffersProcessors, transportKernelId, timeoutInterval))
    out.append(initPublishConnStatusBuffer(pubConnStatusBufferId, transportKernelId, countBuffersProcessors, countNodesProcessors, timeoutInterval))
    for processorId in range(0, countNodesProcessors):
        out.append(initConnStatusBuffer(pubConnStatusBufferId, transportKernelId, timeoutInterval, processorId))
    for processorId in range(0, countNodesProcessors):
        out.append(initRecvProtocolsBuffer(pubConnStatusBufferId,transportKernelId, countNodesProcessors, timeoutInterval, processorId))

    #first kernel
    out.append(initTransportKernel(transportKernelId,pubConnStatusBufferId))

    leftCount = maxNodesCount
    perProcessorCeil = int(math.ceil(float(maxNodesCount)/countNodesProcessors))
    for processorId in range(0, countNodesProcessors):
        if leftCount <= 0:
            break
        if leftCount < perProcessorCeil:
            perProcessorCeil = leftCount
        leftCount = leftCount - perProcessorCeil
        out.append(initProtocolToBufferKernel(transportKernelId, countNodesProcessors, processorId, maxNodesCount, nodeRepositoryProtocolBufferId))


    leftCount = readersCount
    perProcessorCeil = int(math.ceil(float(readersCount)/countBuffersProcessors))
    bufferIndexOffset = 0
    for processorId in range(0, countBuffersProcessors):
        if leftCount <= 0:
            break
        if leftCount < perProcessorCeil:
            perProcessorCeil = leftCount
        leftCount = leftCount - perProcessorCeil
        out.append(initBufferToProtocolKernel(transportKernelId, countNodesProcessors, processorId, maxNodesCount, sendProtocolsBufferId,bufferIndexOffset))
        bufferIndexOffset += perProcessorCeil

    return json.dumps(out)