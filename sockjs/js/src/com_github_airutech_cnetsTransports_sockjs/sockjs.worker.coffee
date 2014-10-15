WSNODE = undefined
isNode = typeof module isnt "undefined" and module.exports and process and process.title != 'browser'
console.log "isNode "+isNode
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
  WSServer = undefined
  protocol = if location.protocol == "http:" then "ws:" else "wss:"
  if typeof document isnt "undefined"
    console.log "aaaaaaaaa ++++++="
    importScripts(
      '/bower_components/sockjs/sockjs.js'
    )
    WS = SockJS
    GetURL = (path)-> 
      if path.indexOf("ws") == 0
        return path
      else
        protocol+"//"+location.host+path
  else
    WS = WebSocket
    console.log "aaaaaaaaa"
    GetURL = (path)-> 
      if path.indexOf("ws") == 0
        return path+"/websocket"
      else
        return protocol+"//"+location.host+path+"/websocket"


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

bufW0statuses = new mapBuffer.create()
bufW0statuses.setDispatcher(0, _this)
w0statuses = bufW0statuses.getWriter(onRun)
bufR0toSocket = new mapBuffer.create()
bufR0toSocket.setDispatcher(0, _this)
r0toSocket = bufR0toSocket.getReader(onRun)
bufR1connections = new mapBuffer.create()
bufR1connections.setDispatcher(1, _this)
r1connections = bufR1connections.getReader(onRun)
bufR2receiveRemoteRepository = new mapBuffer.create()
bufR2receiveRemoteRepository.setDispatcher(2, _this)
r2receiveRemoteRepository = bufR2receiveRemoteRepository.getReader(onRun)

#[[[end]]] (checksum: 374b502fcf6073c1002d80050f916cf9) 

client = undefined
httpSrv = undefined
server = undefined

_this.onStart = ->
  console.log "onStart"

  isServer = if typeof props.bindPort != "undefined" && props.bindPort > 0 then true else false
  isClient = if !isServer && typeof props.initialConnection != "undefined" then true else false
  if isServer
    if typeof WSServer == "undefined"
      throw new Exception("typeof WSServer == 'undefined'")
    server = WSServer.createServer()
    server.on "connection", (conn) ->
      console.log "server connection "
      conn.on "data", (message) ->
        console.log "server data"
        conn.write message
        return

      conn.on "close", ->
        console.log "server close"
      return

    static_directory = new node_static.Server(__dirname+"/../..")
    httpSrv = http.createServer()
    
    httpSrv.addListener "request", (req, res) ->
      static_directory.serve req, res
      return

    httpSrv.addListener "upgrade", (req, res) ->
      res.end()
      return

    server.installHandlers httpSrv,
      prefix: "/ws"

    httpSrv.listen props.bindPort, "0.0.0.0"

  if isClient
    console.log GetURL(props.initialConnection+"/ws")
    if WSNODE
      client = WSNODE.create(GetURL(props.initialConnection+"/ws"))
    else
      client = new WS(GetURL(props.initialConnection+"/ws"))

    onopen = ->
      console.log "open"
      return

    onmessage = (e) ->
      console.log "message", e.data
      return

    onclose = ->
      console.log "close"
      return

    if WSNODE
      client.on('connection', onopen)
      client.on('data', onmessage)
      client.on('error', onclose)
    else
      client.onopen = onopen
      client.onmessage = onmessage
      client.onclose = onclose

_this.onStop = ->
  console.log "onStop"

onRun.callback = (mapBufferId, mapBufferObj) ->
  switch mapBufferId
    when 0
      console.log "onRun"
    else
      console.log "onRun, unknown buffer"

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
