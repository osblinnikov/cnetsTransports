WSNODE = undefined
WSServer = undefined
WS = undefined
GetURL = undefined
node_static = undefined
http = undefined

importWebSocket = ->
  isNode = typeof module isnt "undefined" and module.exports and process and process.title != 'browser'
  # console.log "isNode "+isNode
  if isNode
    http = require('http')
    node_static = require('node-static')
    WSServer = require(__dirname + "/../../node_modules/sockjs/lib/sockjs.js")
    WSNODE = require(__dirname + "/../../node_modules/sockjs-client/lib/sockjs-client.js")
    GetURL = (path)-> 
      if path.indexOf("ws") == 0
        path = path.replace("ws","http")
      return path
  else
    protocol = if location.protocol == "http:" then "ws:" else "wss:"
    if typeof document isnt "undefined" #&& typeof WebSocket == 'undefined'
      # importScripts(
      #   '/bower_components/sockjs/sockjs.js'
      # )
      # console.log s.SockJS
      WS = SockJS
      GetURL = (path)-> 
        if path.indexOf("ws") == 0
          path = path.replace("ws","http")
          return path
        else
          protocol+"//"+location.host+path
    else
      WS = WebSocket
      GetURL = (path)-> 
        if path.indexOf("ws") == 0
          return path+"/websocket"
        else
          return protocol+"//"+location.host+path+"/websocket"

#do import WS
importWebSocket()

#[[[cog
#import cogging as c
#c.tpl(cog,templateFile,c.a(prefix=configFile))
#]]]


importScripts(
  '/dist/com_github_airutech_cnetsTransports_types/types.js',
  '/dist/com_github_airutech_cnets_selector/selector.js',
  '/dist/com_github_airutech_cnets_queue/queue.js',
  '/dist/com_github_airutech_cnetsTransports_nodeRepositoryProtocol/nodeRepositoryProtocol.js',
  '/dist/com_github_airutech_cnets_readerWriter/readerWriter.js',
  '/dist/com_github_airutech_cnets_runnablesContainer/runnablesContainer.js',
  '/dist/com_github_airutech_cnets_types/types.js',
  '/dist/com_github_airutech_cnets_mapBuffer/mapBuffer.js'
)

onRun =
  callback: ->
    #dummy

_this = new Dispatcher(this)

props = undefined
_this.onCreate = (_props)->
  props = _props
  if initOnCreate
    initOnCreate(_props)

bufW0statuses = new mapBuffer.create()
bufW0statuses.setDispatcher(0, _this)
w0statuses = bufW0statuses.getWriter(onRun)
bufW1fromSocket = new mapBuffer.create()
bufW1fromSocket.setDispatcher(1, _this)
w1fromSocket = bufW1fromSocket.getWriter(onRun)
bufR0toSocket = new mapBuffer.create()
bufR0toSocket.setDispatcher(1, _this)
r0toSocket = bufR0toSocket.getReader(onRun)
bufR1connections = new mapBuffer.create()
bufR1connections.setDispatcher(2, _this)
r1connections = bufR1connections.getReader(onRun)
bufR2receiveRemoteRepository = new mapBuffer.create()
bufR2receiveRemoteRepository.setDispatcher(3, _this)
r2receiveRemoteRepository = bufR2receiveRemoteRepository.getReader(onRun)

#[[[end]]] (checksum: 4c38dd6121a9fa1af4837b28a7746d07) 

importScripts(
  '/dist/com_github_airutech_cnetsTransports_sockjs/nodeBufIndex.js',
  '/dist/com_github_airutech_cnetsTransports_sockjs/connectionsRegistry.js'
)

client = undefined
httpSrv = undefined
server = undefined

nodeBufIndex = ->
  this.dstBufferIndex = 0
  this.publishedName = null
  this.nodeUniqueId = 0
  this.isConnected = false
  true

nodes = undefined
conManager = undefined

makeReconnection = false

initOnCreate = (p)->
  console.log "initOnCreate"
  conManager = new connectionsRegistry.create(p.maxNodesCount)
  #local storage for all nodes and all localBuffers
  if p.publishedBuffersNames == null
    return
  nodes = []
  nodes.length = p.maxNodesCount*p.publishedBuffersNames.length
  for i in [0...nodes.length]
    nodes[i] = new s.com_github_airutech_cnetsTransports_sockjs.nodeBufIndex()

  for i in [0...p.maxNodesCount]
    for  bufferIndex in [0...p.publishedBuffersNames.length]
      node = nodes[i * p.publishedBuffersNames.length + bufferIndex]
      node.connected = false
      node.dstBufferIndex = -1
      node.publishedName = p.publishedBuffersNames[bufferIndex]

onOpen = (hashKey, webSocket)->
  console.log "onOpen"
  conManager.addConnection(hashKey, webSocket)
  id = conManager.findUniqueConnectionId(hashKey)
  if id < 0
    webSocket.close()
    return

  if props.publishedBuffersNames == null
    return

  for bufferIndex in [0...props.publishedBuffersNames.length]
    node = nodes[(id%props.maxNodesCount) * props.publishedBuffersNames.length + bufferIndex]
    node.nodeUniqueId = id 
    node.connected = true

  sendConnStatus(id, true, false)

onClose = (hashKey)->
  console.log "onClose"
  id = conManager.findUniqueConnectionId(hashKey)
  if id < 0
    return
  conManager.removeConnection(hashKey)
  if props.publishedBuffersNames == null
    return
  for bufferIndex in [0...props.publishedBuffersNames.length]
    node = nodes[(id%props.maxNodesCount) * props.publishedBuffersNames.length+bufferIndex]
    if node.connected
      node.dstBufferIndex = -1
    node.connected = false
  sendConnStatus(id, false, false)

onMessage = (hashKey, msg) ->
    id = conManager.findUniqueConnectionId(hashKey)
    if id<0
      console.error("sockjs: onMessage: connection for "+hashKey+" was not found")
      return
    
    if w1fromSocket == null
      return
    receivedProtocol = null
    getReceivedProtocol = ->
      receivedProtocol = w1fromSocket.writeNext(-1)
      if receivedProtocol == null
        setTimeout getReceivedProtocol, 1000

    getReceivedProtocol()
    receivedProtocol.obj.data = msg

    receivedProtocol.obj.nodeUniqueIds = [id]
    # receivedProtocol.obj.nodeUniqueIds[0] = id

    w1fromSocket.writeFinished()


connect = ->
  isServer = if typeof props.bindPort != "undefined" && props.bindPort > 0 then true else false
  isClient = if !isServer && typeof props.initialConnection != "undefined" then true else false

  if isServer && !server
    if typeof WSServer == "undefined"
      throw new Exception("typeof WSServer == 'undefined'")
    server = WSServer.createServer()
    server.on "connection", (conn) ->
      onOpen("connString",conn)

      console.log "server connection "
      conn.on "data", (message) ->
        console.log "server data "
        console.log message
        conn.write message
        return

      conn.on "close", ->
        # console.log "server close"
        onClose("connString")
      return

    static_directory = new node_static.Server(__dirname+"/../..")
    httpSrv = http.createServer()

    httpSrv.addListener "request", (req, res) ->
      static_directory.serve req, res
      return

    httpSrv.addListener "upgrade", (req, res) ->
      console.log "upgrade"
      console.log req
      res.end()
      return

    server.installHandlers httpSrv,
      prefix: "/ws"

    httpSrv.listen props.bindPort, "0.0.0.0"
    
  if isClient && !client
    console.log GetURL(props.initialConnection+"/ws")
    if WSNODE
      client = WSNODE.create(GetURL(props.initialConnection+"/ws"))
    else
      client = new WS(GetURL(props.initialConnection+"/ws"))
    onopen = ->
      console.log "open in client"
      onOpen("connString",client)
      # if client.write
      #   client.write("test")
      # else if client.send
      #   client.send("test")
      # else
      #   console.error "sending function not found"
      return

    onmessage = (e) ->
      console.log "message in client"
      msg = e
      if typeof e == 'object' && e.data
        msg = e.data
      # console.log msg
      onMessage("connString",msg)
      return

    onclose = (e)->
      console.log "close in client"
      console.log e
      onClose("connString")
      makeReconnection = true
      return

    if WSNODE
      client.on('connection', onopen)
      client.on('data', onmessage)
      client.on('error', onclose)
    else
      client.onopen = onopen
      client.onmessage = onmessage
      client.onclose = onclose

disconnect = ->
  if client
    onClose("connString")
    client.close()
    client = undefined

_this.onStart = ->
  console.log "onStart"
  connect()

_this.onStop = ->
  console.log "onStop"
  disconnect()

  # if server
  # if httpSrv

sendToNodes = (writeProtocol)->
  if writeProtocol == null
    console.error "sockjs: sendToNodes: writeProtocol is null"
    return
  if writeProtocol.nodeUniqueIds == null
    writeProtocol.nodeUniqueIds = []
    writeProtocol.nodeUniqueIds.length = 1
  console.log "sockjs send from "+writeProtocol.bufferIndex+" to "+writeProtocol.published+" with ids:"
  console.log writeProtocol.nodeUniqueIds

  if writeProtocol.published
    for i in [0...props.maxNodesCount]
      node = nodes[i * props.publishedBuffersNames.length + writeProtocol.bufferIndex]
      if writeProtocol.bufferIndex == 0 || node.dstBufferIndex>=0
        conManager.sendToNode(node.nodeUniqueId, writeProtocol.data)
  else
    for i in [0...writeProtocol.nodeUniqueIds.length]
      if writeProtocol.nodeUniqueIds[i] < 0
        break
      nodeIndex = writeProtocol.nodeUniqueIds[i] % props.maxNodesCount
      node = nodes[nodeIndex * props.publishedBuffersNames.length + writeProtocol.bufferIndex]
      if writeProtocol.bufferIndex == 0 || node.dstBufferIndex >= 0
        conManager.sendToNode(writeProtocol.nodeUniqueIds[i], writeProtocol.data)
      else
        console.warn ("sockjs: sendToNode: sending to node "+writeProtocol.nodeUniqueIds[i]+" of "+props.maxNodesCount+" nodes with buffer index "+writeProtocol.bufferIndex+" FAILED, " +
                "because destination doesn't have this buffer entry (strange error, packet should be filtered out in " +
                "bufferToProtocol module)\n")

processConnectionsConfig = ->


processRepositoryUpdate = (update)->
  if typeof update != 'object'
    console.error "socksjs: processRepositoryUpdate: update is not an object"
    return
  console.log "sockjs: processRepositoryUpdate"
  if update.subscribedNames == null 
    console.error "socksjs: processRepositoryUpdate: bufferNames are null"
    return
  isNotLateRepoUpdate = false
  for i in [0...update.subscribedNames.length]
    for bufferIndx in [0...publishedBuffersNames.length]
      nodeBufIndex node = nodes[(update.nodeId%props.maxNodesCount) * props.publishedBuffersNames.length + bufferIndx]
      if node.connected && node.nodeUniqueId == update.nodeId && node.publishedName = update.subscribedNames[i]
        node.dstBufferIndex = i
        isNotLateRepoUpdate = true
        break
  if isNotLateRepoUpdate
    sendConnStatus(update.nodeId, true, true)

sendConnStatus = (id, status, receivedRepo)->
    if w0statuses != null
      getConnStatus = ->
        conStatus = w0statuses.writeNext(-1)
        if conStatus == null
          setTimeout(getConnStatus, 1000)
        else
          conStatus.obj.id = id
          conStatus.obj.on = status
          conStatus.obj.receivedRepo = receivedRepo
          w0statuses.writeFinished()

      getConnStatus()

onRun.callback = (mapBufferId, mapBufferObj) ->

  if makeReconnection
    makeReconnection = false
    disconnect()
    connect()

  if mapBufferObj != null
    r = mapBufferObj.readNext()
    if r == null then return
  else
    r = {}

  switch mapBufferId
    when 0
      sendToNodes(r.obj)
    when 1
      processConnectionsConfig(r.obj)
    when 2
      processRepositoryUpdate(r.obj)
    else
      console.error "onRun, unknown buffer"

  if mapBufferObj != null
    mapBufferObj.readFinished()


#sendMessageExample = ->
#  r = w0.writeNext()
#  if r!=null
#    r.obj = {someData:"data"}
#    w0.writeFinished()
#    receivedData = null
#  # else
#    # setTimeout(sendMessageExample,100)

#readMessageExample = ->
#  r = r0.readNext()
#  if r != null
#    receivedData = r.obj
#    r0.readFinished()
