{
"path":"${_JAVA_PATH_}.${_NAME_}",
"ver":"0.0.0",
"type":"com.github.airutech.cnetsTransports.webSocketConnectorTpl",
"args": [{
  "name": "moduleUniqueName",
  "type": "string"
},{
  "name": "serverUrl",
  "type": "String"
},{
  "name": "bindPort",
  "type": "int"
},{
  "name": "statsInterval",
  "type": "int"
}],
"props": [{
  "name": "countNodesProcessors",
  "type": "int",
  "value": 2
},{
  "name": "maxNodesCount",
  "type": "int",
  "value": 5
},{
  "name": "buffersLengths",
  "type": "int",
  "value": 5
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
 "type":"kernel",
 "name":"_connector",
 "path":"${_JAVA_PATH_}.${_NAME_}.connector",
 "ver":"[0.0.0,)",
 "args": [{
    "name": "moduleUniqueName",
    "value": "moduleUniqueName+\"._connector\""
  },{
    "name": "maxNodesCount",
    "value": "maxNodesCount"
  },{
    "name": "buffersLengths",
    "value": "buffersLengths"
  },{
    "name": "serverUrl",
    "value": "serverUrl"
  },{
    "name": "bindPort",
    "value": "bindPort"
  },{
    "name": "binBuffersSize",
    "value": "binBuffersSize"
  },{
    "name": "statsInterval",
    "type": "statsInterval"
  }],
 "connection":{
    "writeTo": [{
      "type": "no.eyasys.mobileAlarm.types.connectionStatus",
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
    "type": "no.eyasys.mobileAlarm.types.connectionStatus",
    "name": "currentConnectionsStatus"
  }],
  "readFrom": [{
    "type": "com.github.airutech.cnetsTransports.types.cnetsConnections",
    "name": "connectionConfig"
  }]
}
}