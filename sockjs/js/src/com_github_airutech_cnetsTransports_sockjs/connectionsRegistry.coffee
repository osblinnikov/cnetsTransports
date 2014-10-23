isNode = typeof module isnt "undefined" and module.exports and process and process.title != 'browser'
if isNode
  s = {}
else
  s = self

conContainer = ->
  this.connection = null
  this.keyCode = null
  this.uniqueId = 0


s.connectionsRegistry = {}
connectionsRegistry = {}

connectionsRegistry.create = s.connectionsRegistry.create = (capacity)->
  connectionsIdsQueue = []
  arrContainers = new Array(capacity)
  for i in [0...capacity]
    connectionsIdsQueue.push(i)
    arrContainers[i] = new conContainer()
    arrContainers[i].uniqueId = i - arrContainers.length
  
  

  this.sendToNode = (nodeId, bb)->
    if nodeId >= 0
      nodeIndx = nodeId%arrContainers.length
      if arrContainers[nodeIndx].uniqueId != nodeId
        return
      if arrContainers[nodeIndx].connection != null
        if arrContainers[nodeIndx].connection.send
          arrContainers[nodeIndx].connection.send(bb)
        else if arrContainers[nodeIndx].connection.write
          arrContainers[nodeIndx].connection.write(bb)
        else
          console.error "connectionsRegistry: neither send nor write methods found"

  this.addConnection = (keyCode, connection)->
    id = this.findConnectionId(keyCode)
    res = false
    if id < 0 && connectionsIdsQueue.length > 0
      id = connectionsIdsQueue.shift()
      arrContainers[id].connection = connection
      arrContainers[id].keyCode = keyCode
      arrContainers[id].uniqueId += arrContainers.length
      if arrContainers[id].uniqueId < 0
        arrContainers[id].uniqueId = id
      res = true
    else
      res = false
    return res

  this.findConnectionId = (keyCode)->
    if keyCode == null
      return -1
    for i in [0...arrContainers.length]
      if arrContainers[i].keyCode != null && arrContainers[i].keyCode == keyCode
        return i
    return -1

  this.findUniqueConnectionId = (hashKey)->
    id = this.findConnectionId(hashKey)
    if id<0
      return -1
    uid = arrContainers[id].uniqueId
    return uid

  this.removeConnection = (keyCode)->
    id = this.findConnectionId(keyCode)
    if id < 0
      console.error("removeConnection: socket container was not registered\n")
      return false
    arrContainers[id].keyCode = null
    arrContainers[id].connection = null
    connectionsIdsQueue.push(id)
    return true

  true

if isNode
  module.exports = s.connectionsRegistry
