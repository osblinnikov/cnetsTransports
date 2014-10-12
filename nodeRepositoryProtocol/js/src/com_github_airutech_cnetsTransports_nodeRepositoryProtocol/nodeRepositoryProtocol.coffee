

#[[[cog
#import cogging as c
#c.tpl(cog,templateFile,c.a(prefix=configFile))
#]]]

isNode = typeof module isnt "undefined" and module.exports and process and process.title != 'browser'
if isNode
  s = {}
else
  s = self



s.com_github_airutech_cnetsTransports_nodeRepositoryProtocol =
  create: (subscribedNames,publishedNames)->
    that = this
    that.subscribedNames = subscribedNames
    that.publishedNames = publishedNames
    #dummy
#[[[end]]] (checksum: b53acf79ccdd4fc5e98f6919a2cd521a)
    true

#IMPLEMENTATION GOES HERE


if isNode
  module.exports = s.com_github_airutech_cnetsTransports_nodeRepositoryProtocol