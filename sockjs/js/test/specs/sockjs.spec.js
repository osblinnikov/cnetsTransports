(function() {
  var isNode, s;

  isNode = typeof module !== "undefined" && module.exports && process && process.title !== 'browser';

  if (isNode) {
    s = {};
  } else {
    s = self;
  }

  if (isNode) {
    s.types = s.com_github_airutech_cnetsTransports_types = require(__dirname + "/../../dist/com_github_airutech_cnetsTransports_types/types.js");
    s.selector = s.com_github_airutech_cnets_selector = require(__dirname + "/../../dist/com_github_airutech_cnets_selector/selector.js");
    s.queue = s.com_github_airutech_cnets_queue = require(__dirname + "/../../dist/com_github_airutech_cnets_queue/queue.js");
    s.nodeRepositoryProtocol = s.com_github_airutech_cnetsTransports_nodeRepositoryProtocol = require(__dirname + "/../../dist/com_github_airutech_cnetsTransports_nodeRepositoryProtocol/nodeRepositoryProtocol.js");
    s.readerWriter = s.com_github_airutech_cnets_readerWriter = require(__dirname + "/../../dist/com_github_airutech_cnets_readerWriter/readerWriter.js");
    s.runnablesContainer = s.com_github_airutech_cnets_runnablesContainer = require(__dirname + "/../../dist/com_github_airutech_cnets_runnablesContainer/runnablesContainer.js");
    s.types = s.com_github_airutech_cnets_types = require(__dirname + "/../../dist/com_github_airutech_cnets_types/types.js");
    s.mapBuffer = s.com_github_airutech_cnets_mapBuffer = require(__dirname + "/../../dist/com_github_airutech_cnets_mapBuffer/mapBuffer.js");
    s.sockjs = s.com_github_airutech_cnetsTransports_sockjs = require(__dirname + "/../../dist/com_github_airutech_cnetsTransports_sockjs/sockjs.js");
  }

  describe("sockjs-send-receive", function() {
    return it("should send the given message", function() {
      var done;
      expect(s.mapBuffer.create).toEqual(jasmine.any(Function));
      expect(s.com_github_airutech_cnets_types.Worker).toEqual(jasmine.any(Function));
      done = false;
      runs(function() {
        var bindPort, initialConnection, maxNodesCount, nodesReceivers, publishedBuffersNames, r0toSocket, r1connections, r2receiveRemoteRepository, sockKernel, sslContext, w0statuses;
        publishedBuffersNames = [];
        maxNodesCount = 1;
        initialConnection = "ws://localhost:9911/ws";
        bindPort = 0;
        sslContext = void 0;
        nodesReceivers = [];
        w0statuses = void 0;
        r0toSocket = void 0;
        r1connections = void 0;
        r2receiveRemoteRepository = void 0;
        sockKernel = new s.com_github_airutech_cnetsTransports_sockjs.create(publishedBuffersNames, maxNodesCount, initialConnection, bindPort, sslContext, nodesReceivers, w0statuses, r0toSocket, r1connections, r2receiveRemoteRepository);
        return sockKernel.onStart();
      });
      return waitsFor((function() {
        return done;
      }), "should finish", 1000);
    });
  });

}).call(this);
