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

    out.append(dict(name="countNodesProcessors",type="int",value=countNodesProcessors))
    out.append(dict(name="countBuffersProcessors",type="int",value=countBuffersProcessors))
    out.append(dict(name="maxNodesCount",type="int",value=maxNodesCount))
    out.append(dict(name="buffersLengths",type="int",value=buffersLengths))
    out.append(dict(name="binBuffersSize",type="int",value=binBuffersSize))
    out.append(dict(name="readersCount",type="int",value=readersCount))
    out.append(dict(name="writersCount",type="int",value=writersCount))

    if countNodesProcessors > 0:
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
                type="com.github.airutech.cnetsTransports.types.connectionStatus[]",
                size=buffersLengths
            ))
            out.append(dict(
                name="_inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr",
                type="com.github.airutech.cnetsTransports.types.cnetsProtocol[]",
                size=buffersLengths
            ))
            out.append(dict(
                name="_inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr_BinaryBuffers",
                type="byte[]",
                size=binBuffersSize*buffersLengths
            ))

    if countBuffersProcessors > 0:
        buffersPerProcessorCeil = int(math.ceil(float(readersCount)/(countBuffersProcessors + 1))) # + 1 for reader of  Repository Protocol
        buffersLeftCount = readersCount
        for processorId in range(0, countBuffersProcessors + 1):# + 1 for reader of  Repository Protocol
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
                type="com.github.airutech.cnetsTransports.types.serializeStreamCallback[]",
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
        name="_localNodeRepositoryProtocolBufferArr",
        type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol[]",
        size=2
    ))
    out.append(dict(
        name="_dstNodeRepositoryProtocolBufferArr",
        type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol[]",
        size=2
    ))
    out.append(dict(
        name="_outputProtocolBuffer_Arr",
        type="com.github.airutech.cnetsTransports.types.cnetsProtocol[]",
        size=buffersLengths
    ))
    out.append(dict(
        name="_outputProtocolBuffer_Arr_BinaryBuffers",
        type="byte[]",
        size=binBuffersSize*buffersLengths
    ))
    out.append(dict(
        name="_connectionStatusBuffer_publish_Arr",
        type="com.github.airutech.cnetsTransports.types.connectionStatus[]",
        size=buffersLengths
    ))

    # out.append(dict(
    #     name="_connectionsBufferArr",
    #     type="com.github.airutech.cnetsTransports.types.cnetsConnections[]",
    #     size=buffersLengths
    # ))
    
    out.append(dict(
        name="_dispatchConnStatusBuffer_Arr",
        type="com.github.airutech.cnetsTransports.types.connectionStatus[]",
        size=buffersLengths
    ))

    return json.dumps(out)

def initDstNodeRepositoryBuffer(nodeRepositoryProtocolBufferId,transportKernelId, countNodesProcessors, countBuffersProcessors, timeoutInterval):
    #############
    nodeRepositoryProtocolWriteTo = []

    #add transportKernel connection
    nodeRepositoryProtocolWriteTo.append(dict(
        type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol",
        blockId=transportKernelId,
        pinId=2
    ))
    #connect to the next kernel after transport kernel
    for i in range(transportKernelId+1,transportKernelId+1+countNodesProcessors+countBuffersProcessors):
        # print "\n create connection "+str(i)+" "+str(transportKernelId)+" "+str(countNodesProcessors)+" "+str(countBuffersProcessors)
        nodeRepositoryProtocolWriteTo.append(dict(
            type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol",
            blockId=i,
            pinId=1
        ))
    #create buffer for nodeRepository

    return dict(
        id=nodeRepositoryProtocolBufferId,
        type="buffer",
        name="_dstNodeRepositoryProtocolBuffer",
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_dstNodeRepositoryProtocolBufferArr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value=countNodesProcessors + countBuffersProcessors + 1)# +1 for transportKernel
        ],
        connection=dict(
            writeTo=nodeRepositoryProtocolWriteTo,
            readFrom=[
                # dict(type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol")
            ]
        )
    )


def initLocalNodeRepositoryBuffer(nodeRepositoryProtocolBufferId,transportKernelId, countNodesProcessors, countBuffersProcessors, timeoutInterval):
    #############
    nodeRepositoryProtocolWriteTo = []

    # #add transportKernel connection
    # nodeRepositoryProtocolWriteTo.append(dict(
    #     type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol",
    #     blockId=transportKernelId,
    #     pinId=2
    # ))
    # #connect to the next kernel after transport kernel
    # for i in range(transportKernelId+1,transportKernelId+1+countNodesProcessors+countBuffersProcessors):
    #     # print "\n create connection "+str(i)+" "+str(transportKernelId)+" "+str(countNodesProcessors)+" "+str(countBuffersProcessors)
    #     nodeRepositoryProtocolWriteTo.append(dict(
    #         type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol",
    #         blockId=i,
    #         pinId=1
    #     ))
    # #create buffer for nodeRepository

    return dict(
        id=nodeRepositoryProtocolBufferId,
        type="buffer",
        name="_localNodeRepositoryProtocolBuffer",
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_localNodeRepositoryProtocolBufferArr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value=1)# +1 for transportKernel        //countNodesProcessors + countBuffersProcessors +
        ],
        connection=dict(
            writeTo=nodeRepositoryProtocolWriteTo,
            readFrom=[dict(type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol")]
        )
    )

# def initConnectionsBuffer(connectionsBufferId, transportKernelId, timeoutInterval):
#     return dict(
#         id=connectionsBufferId,
#         type="buffer",
#         name="_connectionsBuffer",
#         path="com.github.airutech.cnets.mapBuffer",
#         ver="[0.0.0,)",
#         args=[
#             dict(value="_connectionsBufferArr",type="Object[]"),
#             dict(value=timeoutInterval),#as module property
#             dict(value=1)# configure connections only in transport
#         ],
#         connection=dict(
#             writeTo=[dict(
#                 type="com.github.airutech.cnetsTransports.types.cnetsConnections",
#                 blockId=transportKernelId,
#                 pinId=1
#             )],
#             readFrom=[dict(
#                 type="com.github.airutech.cnetsTransports.types.cnetsConnections"
#             )]
#         )
#     )

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
            dict(value=1)# configure connections only in transport
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
            dict(value=1)# configure connections only in transport
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

def initPublishConnStatusBuffer(pubConnStatusBufferId, transportKernelId, countBuffersProcessors, countNodesProcessors, timeoutInterval, repSourceKernelId):
    writeToList = []
    for processorId in range(countNodesProcessors, countBuffersProcessors+countNodesProcessors):
        writeToList.append(dict(
            type="com.github.airutech.cnetsTransports.types.connectionStatus",
            blockId=transportKernelId+1+processorId,
            pinId=0
        ))
    writeToList.append(dict(
        type="com.github.airutech.cnetsTransports.types.connectionStatus",
        blockId=repSourceKernelId,
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
            dict(value=countBuffersProcessors + 1)# configure connections only in transport, + 1 for repSourceKernelId
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
        name="_inputProtocolBuffer_forNodes_"+str(processorId),
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_inputProtocolBuffer_forNodes_"+str(processorId)+"_Arr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value=1)# configure connections only in transport
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

def initConStatusDispatcherBuffer(dispatchConnStatusBufferId,dispatchConnStatusKernelId,timeoutInterval):
    return dict(
        id=dispatchConnStatusBufferId,
        type="buffer",
        name="_dispatchConnStatusBuffer",
        path="com.github.airutech.cnets.mapBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="_dispatchConnStatusBuffer_Arr",type="Object[]"),
            dict(value=timeoutInterval),#as module property
            dict(value=1)# configure connections only in transport
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.connectionStatus",
                blockId=dispatchConnStatusKernelId, #after transport kernel will be the protocolToBuffer
                pinId=0
            )],
            readFrom=[dict(type="com.github.airutech.cnetsTransports.types.connectionStatus")]
        )
    )

def initConStatusDispatcherKernel(dispatchConnStatusKernelId,pubConnStatusBufferId):
    return dict(
        id=dispatchConnStatusKernelId,
        type="kernel",
        name="_connStatusDispatcher",
        path="com.github.airutech.cnetsTransports.connectionStatusDispatcher",
        ver="[0.0.0,)",
        args=[dict(value="_connectionStatusReceivers_writers"), dict(value="maxNodesCount")],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.connectionStatus",
                blockId=pubConnStatusBufferId,
                pinId=0
            ),dict(
                type="com.github.airutech.cnetsTransports.types.connectionStatus",
                blockId="export",
                pinId=0
            )],
            readFrom=[dict(type="com.github.airutech.cnetsTransports.types.connectionStatus")]
        )
    )

def initNodeRepSourceKernel(repSourceKernelId,nodeRepositoryProtocolBufferId):
    return dict(
        id=repSourceKernelId,
        type="kernel",
        name="_localNodeRepositoryProtocolSource",
        path="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.source",
        ver="[0.0.0,)",
        args=[dict(value="publishedBuffersNames"),dict(value="subscribedBuffersNames")],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol",
                blockId=nodeRepositoryProtocolBufferId,
                pinId=0
            )],
            readFrom=[dict(type="com.github.airutech.cnetsTransports.types.cnetsConnections")]
        )
    )

def initTransportKernel(transportKernelId,dispatchConnStatusKernelId):
    return dict(
        id=transportKernelId,
        type="kernel",
        name="_webSocket",
        path="com.github.airutech.cnetsTransports.webSocket",
        ver="[0.0.0,)",
        args=[
            dict(value="publishedBuffersNames"),
            dict(value="maxNodesCount"),
            dict(value="serverUrl"),
            dict(value="bindPort"),
            dict(value="null", name="sslContext"),
            dict(value="_nodesReceivers_writers")
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.connectionStatus",
                blockId=dispatchConnStatusKernelId,
                pinId=0
            )],
            readFrom=[
                dict(type="com.github.airutech.cnetsTransports.types.cnetsProtocol"),
                dict(type="com.github.airutech.cnetsTransports.types.cnetsConnections",blockId="export",pinId=0),
                dict(type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol")
            ]
        )
    )

def initProtocolToBufferKernel(transportKernelId, countNodesProcessors, processorId, maxNodesCount):
    nodesIndexOffset = int(math.floor(float(maxNodesCount)/countNodesProcessors))*processorId
    return dict(
        id=transportKernelId+1+processorId,
        type="kernel",
        name="_protocolToBuffer_"+str(processorId),
        path="com.github.airutech.cnetsTransports.protocolToBuffer",
        ver="[0.0.0,)",
        args=[
            dict(value="subscribedBuffersNames"),
            dict(value="allWriters"),
            dict(value="allWriters_callbacks"),
            dict(value=nodesIndexOffset),
            dict(value=countNodesProcessors),
            dict(value="maxNodesCount")
        ],
        connection=dict(
            writeTo=[],
            readFrom=[
                dict(type="com.github.airutech.cnetsTransports.types.connectionStatus"),
                dict(type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol"),
                dict(type="com.github.airutech.cnetsTransports.types.cnetsProtocol")
            ]
        )
    )

def initBufferToProtocolKernel(transportKernelId, countNodesProcessors, processorId, sendProtocolsBufferId, bufferIndexOffset):
    return dict(
        id=transportKernelId+countNodesProcessors+1+processorId,
        type="kernel",
        name="_bufferToProtocol_"+str(processorId),
        path="com.github.airutech.cnetsTransports.bufferToProtocol",
        ver="[0.0.0,)",
        args=[
            dict(value="publishedBuffersNames"),
            dict(value="_bufferToProtocol_"+str(processorId)+"_readers"),
            dict(value="_bufferToProtocol_"+str(processorId)+"_readers_callbacks"),
            dict(value=bufferIndexOffset),
            dict(value="maxNodesCount")
        ],
        connection=dict(
            writeTo=[dict(
                type="com.github.airutech.cnetsTransports.types.cnetsProtocol",
                blockId=sendProtocolsBufferId,
                pinId=0
            )],
            readFrom=[
                dict(type="com.github.airutech.cnetsTransports.types.connectionStatus"),
                dict(type="com.github.airutech.cnetsTransports.nodeRepositoryProtocol.nodeRepositoryProtocol"),
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

    localNodeRepositoryProtocolBufferId = 0
    dstNodeRepositoryProtocolBufferId = 1
    # connectionsBufferId = 1
    sendProtocolsBufferId = 2
    pubConnStatusBufferId = 3
    dispatchConnStatusBufferId = pubConnStatusBufferId+1 + 2*countNodesProcessors
    dispatchConnStatusKernelId = dispatchConnStatusBufferId+1
    repSourceKernelId = dispatchConnStatusKernelId+1
    transportKernelId = repSourceKernelId+1 # id of first kernel in blocks, after all buffers
    ##############################################

    out.append(initLocalNodeRepositoryBuffer(localNodeRepositoryProtocolBufferId,transportKernelId, countNodesProcessors, countBuffersProcessors, timeoutInterval))
    out.append(initDstNodeRepositoryBuffer(dstNodeRepositoryProtocolBufferId,transportKernelId, countNodesProcessors, countBuffersProcessors, timeoutInterval))
    # out.append(initConnectionsBuffer(connectionsBufferId,transportKernelId, timeoutInterval))
    out.append(initSendProtocolsBuffer(sendProtocolsBufferId,countBuffersProcessors, transportKernelId, timeoutInterval))
    out.append(initPublishConnStatusBuffer(pubConnStatusBufferId, transportKernelId, countBuffersProcessors, countNodesProcessors, timeoutInterval,repSourceKernelId))
    for processorId in range(0, countNodesProcessors):
        out.append(initConnStatusBuffer(pubConnStatusBufferId, transportKernelId, timeoutInterval, processorId))
    for processorId in range(0, countNodesProcessors):
        out.append(initRecvProtocolsBuffer(pubConnStatusBufferId,transportKernelId, countNodesProcessors, timeoutInterval, processorId))

    out.append(initConStatusDispatcherBuffer(dispatchConnStatusBufferId,dispatchConnStatusKernelId,timeoutInterval))
    out.append(initConStatusDispatcherKernel(dispatchConnStatusKernelId,pubConnStatusBufferId))
    out.append(initNodeRepSourceKernel(repSourceKernelId,localNodeRepositoryProtocolBufferId))
    #transport kernel
    out.append(initTransportKernel(transportKernelId,dispatchConnStatusBufferId))

    if countNodesProcessors>0:
        leftCount = maxNodesCount
        perProcessorCeil = int(math.ceil(float(maxNodesCount)/countNodesProcessors))
        for processorId in range(0, countNodesProcessors):
            if leftCount <= 0:
                break
            if leftCount < perProcessorCeil:
                perProcessorCeil = leftCount
            leftCount = leftCount - perProcessorCeil
            out.append(initProtocolToBufferKernel(transportKernelId, countNodesProcessors, processorId, maxNodesCount))


    if countBuffersProcessors> 0:
        leftCount = readersCount
        perProcessorCeil = int(math.ceil(float(readersCount)/countBuffersProcessors))
        bufferIndexOffset = 0
        for processorId in range(0, countBuffersProcessors):
            if leftCount <= 0:
                break
            if leftCount < perProcessorCeil:
                perProcessorCeil = leftCount
            leftCount = leftCount - perProcessorCeil
            out.append(initBufferToProtocolKernel(transportKernelId, countNodesProcessors, processorId, sendProtocolsBufferId, bufferIndexOffset))
            bufferIndexOffset += perProcessorCeil

    return json.dumps(out)