<%import parsing_java, json
p = reload(parsing_java)
p.parsingGernet(a)%>
"props": [],
"depends":[{
  "path":"com.github.airutech.cnets.readerWriter",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnets.runnablesContainer",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnets.selector",
  "ver": "[0.0.0,)"
},{
  "path":"no.eyasys.mobileAlarm.types",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnetsTransports.types",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnets.types",
  "ver": "[0.0.0,)"
}],
"blocks":[{
  "id":0,
  "name": "_protocolToBuffer_0",
  "path":"com.github.airutech.cnetsTransports.protocolToBuffer",
  "ver":"[0.0.0,)",
  "args": [{
    "name": "writers",
    "value": "_protocolToBuffer_0_writers"
  },{
    "name": "callbacks",
    "value": "_protocolToBuffer_0_writers_callbacks"
  },{
    "name": "protocolToBuffersGridSize",
    "value": "_protocolToBuffer_cnt"
  },{
    "name": "maxNodesCount",
    "value": "maxNodesCount"
  }],
  "connection":{
    "writeTo": [{
      "name":"sendRepositoryToDestination",
      "type":"com.github.airutech.cnetsTransports.type.nodeRepositoryProtocol",
      "blockId": 4,
      "pinId": 0
    }],
    "readFrom": [{
      "name":"connectionStatus",
      "type":"com.github.airutech.cnetsTransports.type.connectionStatus"
    },{
      "name":"receiveRemoteRepository",
      "type":"com.github.airutech.cnetsTransports.type.nodeRepositoryProtocol"
    },{
      "name":"input",
      "type":"cnetsProtocol"
    }]
  }
},{
  "id":1,
  "name": "_bufferToProtocol_0",
  "path":"com.github.airutech.cnetsTransports.bufferToProtocol",
  "ver":"[0.0.0,)",
  "args": [{
    "name": "readers",
    "value": "_bufferToProtocol_0_readers"
  },{
    "name": "serializeCallbacks",
    "value": "_bufferToProtocol_0_readers_callbacks"
  },{
    "name": "bufferIndexOffset",
    "value": 0
  },{
    "name": "maxNodesCount",
    "value": "maxNodesCount"
  }],
  "connection":{
    "writeTo": [{
      "name":"output",
      "type":"cnetsProtocol",
      "blockId": 3,
      "pinId": 0
    }],
    "readFrom": [{
       "name":"connectionStatus",
       "type":"com.github.airutech.cnetsTransports.type.connectionStatus"
     },{
      "name":"receiveRemoteRepository",
      "type":"com.github.airutech.cnetsTransports.type.nodeRepositoryProtocol"
    }]
  }
},{
 "id":2,
 "name": "_webSocket",
 "path":"com.github.airutech.cnetsTransports.webSocket",
 "ver":"[0.0.0,)",
 "args": [{
    "name": "maxNodesCount",
    "value":"maxNodesCount"
  },{
    "name": "initialConnection",
    "value":"serverUrl"
  },{
    "name": "bindPort",
    "value": -1
  },{
    "name": "sslContext",
    "value": "null"
  },{
    "name": "nodesReceivers",
    "value": "_webSocket_nodesReceivers_writers"
  },{
    "name": "connectionStatusReceivers",
    "value": "_webSocket_connectionStatusReceivers_writers"
  },{
    "name": "buffersParameters",
    "value": "_webSocket_buffers_readers"
  }],
 "connection":{
   "writeTo": [],
   "readFrom": [{
     "name":"toSocket",
     "type":"com.github.airutech.cnetsTransports.type.cnetsProtocol"
   },{
     "name":"connections",
     "type":"com.github.airutech.cnetsTransports.type.cnetsConnections"
   },{
     "name":"receiveRemoteRepository",
     "type":"com.github.airutech.cnetsTransports.type.nodeRepositoryProtocol"
   }]
 }
},{
 "id":3,
 "type":"buffer",
 "name":"_outputProtocolBuffer_0",
 "path":"com.github.airutech.cnets.mapBuffer",
 "ver":"[0.0.0,)",
 "args": [{
   "name":"_outputProtocolBuffer_0_Arr",
   "value":"_outputProtocolBuffer_0_Arr",
   "type":"Object[]"
 },{
   "name": "timeout_milisec",
   "value": "2000L"
 },{
   "name": "uniqueId",
   "value": "\"0L\""
 },{
   "name": "readers_grid_size",
   "value": 1
 },{
  "name": "statsInterval",
  "value": 1000
 }],
 "connection": {
   "writeTo": [{
     "type": "com.github.airutech.cnetsTransports.types.cnetsProtocol",
     "blockId": 2,
     "pinId": 0
   }],
   "readFrom": [{
     "type": "com.github.airutech.cnetsTransports.types.cnetsProtocol"
   }]
 }
},{
   "id":4,
   "type":"buffer",
   "name":"_nodeRepositoryProtocolBuffer",
   "path":"com.github.airutech.cnets.mapBuffer",
   "ver":"[0.0.0,)",
   "args": [{
     "name":"_nodeRepositoryProtocolBufferArr",
     "value":"_nodeRepositoryProtocolBufferArr",
     "type":"Object[]"
   },{
     "name": "timeout_milisec",
     "value": "2000L"
   },{
     "name": "uniqueId",
     "value": "\"1L\""
   },{
     "name": "readers_grid_size",
     "value": 3
  },{
    "name": "statsInterval",
    "value": 1000
  }],
   "connection": {
      "writeTo": [{
       "type": "com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol",
       "blockId": 0,
       "pinId": 1
      },{
        "type": "com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol",
        "blockId": 1,
        "pinId": 1
      },{
        "type": "com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol",
        "blockId": 2,
        "pinId": 2
      }],
      "readFrom": [{
        "type": "com.github.airutech.cnetsTransports.types.nodeRepositoryProtocol"
      }]
   }
},{
  "id":5,
  "type":"buffer",
  "name":"_connectionsBuffer",
  "path":"com.github.airutech.cnets.mapBuffer",
  "ver":"[0.0.0,)",
  "args": [{
    "name":"_connectionsBufferArr",
    "value":"_connectionsBufferArr",
    "type":"Object[]"
  },{
    "name": "timeout_milisec",
    "value": "2000L"
  },{
    "name": "uniqueId",
    "value": "\"2L\""
  },{
    "name": "readers_grid_size",
    "value": 1
  },{
    "name": "statsInterval",
    "value": 1000
  }],
  "connection": {
    "writeTo": [{
      "type": "com.github.airutech.cnetsTransports.types.cnetsConnections",
      "blockId": 2,
      "pinId": 1
    }],
    "readFrom": [{
      "type": "com.github.airutech.cnetsTransports.types.cnetsConnections",
      "blockId":"export",
      "pinId":0
    }]
  }
},{
  "id":6,
  "type":"buffer",
  "name":"_connectionStatusBuffer_forNodes_0",
  "path":"com.github.airutech.cnets.mapBuffer",
  "ver":"[0.0.0,)",
  "args": [{
    "name":"_connectionStatusBuffer_forNodes_0_Arr",
    "value":"_connectionStatusBuffer_forNodes_0_Arr",
    "type":"Object[]"
  },{
    "name": "timeout_milisec",
    "value": "2000L"
  },{
    "name": "uniqueId",
    "value": "\"3L\""
  },{
    "name": "readers_grid_size",
    "value": 1
  },{
    "name": "statsInterval",
    "value": 1000
  }],
  "connection": {
    "writeTo": [{
      "type": "com.github.airutech.cnetsTransports.types.connectionStatus",
      "blockId": 0,
      "pinId": 0
    },{
      "type": "com.github.airutech.cnetsTransports.types.connectionStatus",
      "blockId": 1,
      "pinId": 0
    }],
    "readFrom": []
  }
},{
 "id":7,
 "type":"buffer",
 "name":"_inputProtocolBuffer_forNodes_0",
 "path":"com.github.airutech.cnets.mapBuffer",
 "ver":"[0.0.0,)",
 "args": [{
   "name":"_inputProtocolBuffer_forNodes_0_Arr",
   "value":"_inputProtocolBuffer_forNodes_0_Arr",
   "type":"Object[]"
 },{
   "name": "timeout_milisec",
   "value": "2000L"
 },{
   "name": "uniqueId",
   "value": "\"4L\""
 },{
   "name": "readers_grid_size",
   "value": 1
 },{
  "name": "statsInterval",
  "value": 1000
 }],
 "connection": {
   "writeTo": [{
     "type": "com.github.airutech.cnetsTransports.types.cnetsProtocol",
     "blockId": 0,
     "pinId": 2
   }],
   "readFrom": []
 }
}]