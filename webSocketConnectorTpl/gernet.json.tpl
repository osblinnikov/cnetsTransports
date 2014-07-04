{
"path":"${_JAVA_PATH_}.${_NAME_}",
"ver":"0.0.0",
"type":"com.github.airutech.cnetsTransports.webSocketConnectorTpl",
"args": [{
  "name": "serverUrl",
  "type": "String"
},{
  "name": "bindPort",
  "type": "int"
}],
"props": [{
  "name": "countNodesProcessors",
  "type": "int",
  "value": 2
},{
  "name": "countBuffersProcessors",
  "type": "int",
  "value": 2
},{
  "name": "maxNodesCount",
  "type": "int",
  "value": 5
},{
  "name": "buffersLengths",
  "type": "int",
  "value": 8
},{
  "name": "binBuffersSize",
  "type": "int",
  "value": 128
},{
  "name": "timeoutInterval",
  "type": "long",
  "value": "1000L"
}],
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
  "path":"com.github.airutech.cnetsTransports.types",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnets.types",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnetsTransports.msgpackExample.msgpack",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnetsTransports.msgpack",
  "ver":"0.0.0"
},{
  "path":"com.github.airutech.cnetsTransports.nodeRepositoryProtocol.msgpack",
  "ver":"0.0.0"
}],
"blocks":[{
 "id":0,
 "type":"kernel",
 "name":"_connector",
 "path":"${_JAVA_PATH_}.${_NAME_}.connector",
 "ver":"[0.0.0,)",
 "args": [{
    "name": "publishedBuffersNames",
    "value": "publishedBuffersNames",
    "comment": "this variable should be declared and initialized in the generator"
  },{
    "name": "subscribedBuffersNames",
    "value": "subscribedBuffersNames",
    "comment": "this variable should be declared and initialized in the generator"
  },{
     "name": "allWriters",
     "value": "allWriters",
     "comment": "this variable should be declared and initialized in the generator"
  },{
    "name": "allReaders",
    "value": "allReaders",
    "comment": "this variable should be declared and initialized in the generator"
  },{
    "name": "allWriters_callbacks",
    "value": "allWriters_callbacks",
    "comment": "this variable should be declared and initialized in the generator"
  },{
    "name": "allReaders_callbacks",
    "value": "allReaders_callbacks",
    "comment": "this variable should be declared and initialized in the generator"
  },{
    "name": "serverUrl",
    "value": "serverUrl"
  },{
    "name": "bindPort",
    "value": "bindPort"
  }],
 "connection":{
    "writeTo": [{
      "type": "com.github.airutech.cnetsTransports.types.connectionStatus",
      "name": "currentConnectionsStatus",
      "blockId":"export",
      "pinId":0
    }],
    "readFrom": [{
      "type": "com.github.airutech.cnetsTransports.types.cnetsConnections",
      "name": "connectionConfig",
      "blockId":"export",
      "pinId":0
    }]
  }
}],
"connection":{
  "writeTo": [{
    "type": "com.github.airutech.cnetsTransports.types.connectionStatus",
    "name": "currentConnectionsStatus"
  }],
  "readFrom": [{
    "type": "com.github.airutech.cnetsTransports.types.cnetsConnections",
    "name": "connectionConfig"
  }],
  "noSelectors": true
}
}