isNode = typeof module isnt "undefined" and module.exports and process and process.title != 'browser'
if isNode
  s = {}
else
  s = self

if !s.com_github_airutech_cnetsTransports_sockjs
  s.com_github_airutech_cnetsTransports_sockjs = {}

com_github_airutech_cnetsTransports_sockjs = s.com_github_airutech_cnetsTransports_sockjs

s.com_github_airutech_cnetsTransports_sockjs.nodeBufIndex = ->
    this.dstBufferIndex = 0
    this.publishedName = null
    this.nodeUniqueId = 0
    this.isConnected = false
    true


if isNode
  module.exports = s.com_github_airutech_cnetsTransports_sockjs