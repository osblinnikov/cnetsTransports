

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
  s.sockjs = s.com_github_airutech_cnetsTransports_sockjs = require(__dirname + "/../../dist/com_github_airutech_cnetsTransports_sockjs/sockjs.js")
#[[[end]]] (checksum: 9dc9f8550ed14cbc63258004076376b3)

describe "sockjs-send-receive", ->
  it "should send the given message", ->
    expect(s.mapBuffer.create).toEqual jasmine.any(Function)
    expect(s.com_github_airutech_cnets_types.Worker).toEqual jasmine.any(Function)

    done = false
    runs ->
      publishedBuffersNames = []
      maxNodesCount = 1
      initialConnection = "ws://localhost:9911/ws"
      bindPort = 0
      sslContext = undefined
      nodesReceivers = []
      w0statuses = undefined 
      r0toSocket = undefined 
      r1connections = undefined 
      r2receiveRemoteRepository = undefined
      sockKernel = new s.com_github_airutech_cnetsTransports_sockjs.create(
        publishedBuffersNames,
        maxNodesCount,
        initialConnection,
        bindPort,
        sslContext,
        nodesReceivers,
        w0statuses,
        r0toSocket,
        r1connections,
        r2receiveRemoteRepository
      )
      sockKernel.onStart()

    waitsFor (->
      done
    ), "should finish", 1000