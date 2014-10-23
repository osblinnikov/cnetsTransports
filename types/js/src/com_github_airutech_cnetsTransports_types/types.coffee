

#[[[cog
#import cogging as c
#c.tpl(cog,templateFile,c.a(prefix=configFile))
#]]]

isNode = typeof module isnt "undefined" and module.exports and process and process.title != 'browser'
if isNode
  s = {}
else
  s = self



s.com_github_airutech_cnetsTransports_types =
  create: ()->
    that = this
    
    #dummy
#[[[end]]] (checksum: ea080e43bb50adfc812c4259ccdfb6c7)
    true

#IMPLEMENTATION GOES HERE
s.com_github_airutech_cnetsTransports_types.connectionStatus = ->
  that = this
  that.id = 0
  that.on = false
  that.receivedRepo = false
  true

s.com_github_airutech_cnetsTransports_types.cnetsProtocol = ->
  that = this
  that.bb = null
  that.bufferIndex = 0
  that.timeStart = 0
  that.bunchId = 0
  that.packet = 0
  that.packets_grid_size = 0
  that.nodeUniqueIds_ = [] #it's not going to be serialized
  that.published_ = false #it's not going to be serialized
  true

s.com_github_airutech_cnetsTransports_types.cnetsConnections = ->
  that = this
  that.connections = []
  true


if isNode
  module.exports = s.com_github_airutech_cnetsTransports_types