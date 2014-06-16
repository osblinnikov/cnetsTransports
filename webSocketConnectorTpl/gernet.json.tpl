{
"path":"${_JAVA_PATH_}.${_NAME_}",
"ver":"0.0.0",
"type":"com.github.airutech.cnetsTransports.webSocketConnectorTpl",
"args": [{
  "name": "maxNodesCount",
  "type": "int"
},{
  "name": "buffersLengths",
  "type": "int"
},{
  "name": "serverUrl",
  "type": "String"
},{
  "name": "bindPort",
  "type": "int"
},{
  "name": "binBuffersSize",
  "type": "int"
}],
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
 "type":"kernel",
 "name":"_connector",
 "path":"${_JAVA_PATH_}.${_NAME_}.connector",
 "ver":"[0.0.0,)",
 "args": [{
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