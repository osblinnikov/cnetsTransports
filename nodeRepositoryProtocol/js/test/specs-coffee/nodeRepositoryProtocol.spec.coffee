

#[[[cog
#import cogging as c
#c.tpl(cog,templateFile,c.a(prefix=configFile))
#]]]

isNode = typeof module isnt "undefined" and module.exports and process and process.title != 'browser'
if isNode
  s = {}
else
  s = self

  s.nodeRepositoryProtocol = s.com_github_airutech_cnetsTransports_nodeRepositoryProtocol = require(__dirname + "/../../dist/com_github_airutech_cnetsTransports_nodeRepositoryProtocol/nodeRepositoryProtocol.js")
#[[[end]]] (checksum: e5e9b32b20c732319760f740d13bcc08)