(function() {
  var isNode, s;

  isNode = typeof module !== "undefined" && module.exports && process && process.title !== 'browser';

  if (isNode) {
    s = {};
  } else {
    s = self;
    s.types = s.com_github_airutech_cnetsTransports_types = require(__dirname + "/../../dist/com_github_airutech_cnetsTransports_types/types.js");
  }

}).call(this);
