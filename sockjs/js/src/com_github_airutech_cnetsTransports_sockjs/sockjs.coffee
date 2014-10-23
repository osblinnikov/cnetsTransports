

#[[[cog
#import cogging as c
#c.tpl(cog,templateFile,c.a(prefix=configFile))
#]]]

isNode = typeof module isnt "undefined" and module.exports and process and process.title != 'browser'
if isNode
  s = {}
else
  s = self

if isNode
  s.types = s.com_github_airutech_cnetsTransports_types = require(__dirname + "/../../dist/com_github_airutech_cnetsTransports_types/types.js")
  s.selector = s.com_github_airutech_cnets_selector = require(__dirname + "/../../dist/com_github_airutech_cnets_selector/selector.js")
  s.queue = s.com_github_airutech_cnets_queue = require(__dirname + "/../../dist/com_github_airutech_cnets_queue/queue.js")
  s.nodeRepositoryProtocol = s.com_github_airutech_cnetsTransports_nodeRepositoryProtocol = require(__dirname + "/../../dist/com_github_airutech_cnetsTransports_nodeRepositoryProtocol/nodeRepositoryProtocol.js")
  s.readerWriter = s.com_github_airutech_cnets_readerWriter = require(__dirname + "/../../dist/com_github_airutech_cnets_readerWriter/readerWriter.js")
  s.runnablesContainer = s.com_github_airutech_cnets_runnablesContainer = require(__dirname + "/../../dist/com_github_airutech_cnets_runnablesContainer/runnablesContainer.js")
  s.types = s.com_github_airutech_cnets_types = require(__dirname + "/../../dist/com_github_airutech_cnets_types/types.js")
  s.mapBuffer = s.com_github_airutech_cnets_mapBuffer = require(__dirname + "/../../dist/com_github_airutech_cnets_mapBuffer/mapBuffer.js")

customCallbacks = {}

s.com_github_airutech_cnetsTransports_sockjs =
  create: (publishedBuffersNames,maxNodesCount,initialConnection,bindPort,sslContext,w0statuses,w1fromSocket,r0toSocket,r1connections,r2receiveRemoteRepository)->
    self = this
    #constructor
    
    wrk = new s.com_github_airutech_cnets_types.Worker('/dist/com_github_airutech_cnetsTransports_sockjs/sockjs.worker.js')
    if w0statuses
      w0statuses.registerSrc(wrk,0)
    if w1fromSocket
      w1fromSocket.registerSrc(wrk,1)
    if r0toSocket
      r0toSocket.registerDst(wrk,0)
    if r1connections
      r1connections.registerDst(wrk,1)
    if r2receiveRemoteRepository
      r2receiveRemoteRepository.registerDst(wrk,2)
    
    wrk.postMessage({type:'props',publishedBuffersNames:publishedBuffersNames,maxNodesCount:maxNodesCount,initialConnection:initialConnection,bindPort:bindPort,sslContext:sslContext})
    
    self.onStart = ->
      if customCallbacks.onStart
        customCallbacks.onStart()
      
      wrk.postMessage({type:'start'})
      

    self.onStop = ->
      wrk.postMessage({type:'stop'})
      
      if customCallbacks.onStop
        customCallbacks.onStop()
#[[[end]]] (checksum: 9940507f0480aacfbe9fd165ddb72e51)
    true

#IMPLEMENTATION GOES HERE


if isNode
  module.exports = s.com_github_airutech_cnetsTransports_sockjs