

#ifndef com_github_airutech_cnetsTransports_webSocketConnector_H
#define com_github_airutech_cnetsTransports_webSocketConnector_H

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

#include "github.com/airutech/cnetsTransports/protocolToBuffer/c/include/protocolToBuffer.h"
#include "github.com/airutech/cnetsTransports/bufferToProtocol/c/include/bufferToProtocol.h"
#include "github.com/airutech/cnetsTransports/webSocket/c/include/webSocket.h"
#include "github.com/airutech/cnets/mapBuffer/c/include/mapBuffer.h"
#include "github.com/airutech/cnets/mapBuffer/c/include/mapBuffer.h"
#include "github.com/airutech/cnets/mapBuffer/c/include/mapBuffer.h"
#include "github.com/airutech/cnetsTransports/types/c/include/types.h"
#include "github.com/airutech/cnets/readerWriter/c/include/readerWriter.h"
#include "github.com/airutech/cnets/runnablesContainer/c/include/runnablesContainer.h"
#include "github.com/airutech/cnets/selector/c/include/selector.h"

#undef com_github_airutech_cnetsTransports_webSocketConnector_EXPORT_API
#if defined WIN32 && !defined __MINGW32__ && !defined(CYGWIN) && !defined(com_github_airutech_cnetsTransports_webSocketConnector_STATIC)
  #ifdef com_github_airutech_cnetsTransports_webSocketConnector_EXPORT
    #define com_github_airutech_cnetsTransports_webSocketConnector_EXPORT_API __declspec(dllexport)
  #else
    #define com_github_airutech_cnetsTransports_webSocketConnector_EXPORT_API __declspec(dllimport)
  #endif
#else
  #define com_github_airutech_cnetsTransports_webSocketConnector_EXPORT_API extern
#endif

struct com_github_airutech_cnetsTransports_webSocketConnector;

com_github_airutech_cnetsTransports_webSocketConnector_EXPORT_API
void com_github_airutech_cnetsTransports_webSocketConnector_initialize(struct com_github_airutech_cnetsTransports_webSocketConnector *that);

com_github_airutech_cnetsTransports_webSocketConnector_EXPORT_API
void com_github_airutech_cnetsTransports_webSocketConnector_deinitialize(struct com_github_airutech_cnetsTransports_webSocketConnector *that);

com_github_airutech_cnetsTransports_webSocketConnector_EXPORT_API
void com_github_airutech_cnetsTransports_webSocketConnector_onKernels(struct com_github_airutech_cnetsTransports_webSocketConnector *that);

com_github_airutech_cnetsTransports_webSocketConnector_EXPORT_API
com_github_airutech_cnets_runnablesContainer com_github_airutech_cnetsTransports_webSocketConnector_getRunnables(struct com_github_airutech_cnetsTransports_webSocketConnector *that);

#undef com_github_airutech_cnetsTransports_webSocketConnector_onCreateMacro
#define com_github_airutech_cnetsTransports_webSocketConnector_onCreateMacro(_NAME_) /**/

#define com_github_airutech_cnetsTransports_webSocketConnector_create(_NAME_,_binBuffersSize,_initialConnection,_startingBufferId,_w0,_w1,_w2,_r0,_r1)\
    com_github_airutech_cnetsTransports_webSocketConnector _NAME_;\
    _NAME_.binBuffersSize = _binBuffersSize;\
    _NAME_.initialConnection = _initialConnection;\
    _NAME_.startingBufferId = _startingBufferId;\
    com_github_airutech_cnetsTransports_webSocketConnector_onCreateMacro(_NAME_)\
    arrayObject_create(_NAME_##_buffersWriters_, writer, 2)\
    _NAME_.buffersWriters = _NAME_##_buffersWriters_;\
    arrayObject_create(_NAME_##_deserializeCallbacks_, deserializeStreamCallback, 2)\
    _NAME_.deserializeCallbacks = _NAME_##_deserializeCallbacks_;\
    arrayObject_create(_NAME_##_buffersReaders_, reader, 2)\
    _NAME_.buffersReaders = _NAME_##_buffersReaders_;\
    arrayObject_create(_NAME_##_serializeCallbacks_, serializeStreamCallback, 2)\
    _NAME_.serializeCallbacks = _NAME_##_serializeCallbacks_;\
    arrayObject_create(_NAME_##_localBuffersIds_, long, 2)\
    _NAME_.localBuffersIds = _NAME_##_localBuffersIds_;\
    arrayObject_create(_NAME_##_outputProtocolArr_, cnetsProtocol, 2)\
    _NAME_.outputProtocolArr = _NAME_##_outputProtocolArr_;\
    arrayObject_create(_NAME_##_inputProtocolArr_, cnetsProtocol, 2)\
    _NAME_.inputProtocolArr = _NAME_##_inputProtocolArr_;\
    arrayObject_create(_NAME_##_connectionsArr_, cnetsConnections, 2)\
    _NAME_.connectionsArr = _NAME_##_connectionsArr_;\
    _NAME_.w0 = _w0;\
    _NAME_.w1 = _w1;\
    _NAME_.w2 = _w2;\
    _NAME_.r0 = _r0;\
    _NAME_.r1 = _r1;\
    com_github_airutech_cnetsTransports_webSocketConnector_initialize(&_NAME_);\
    com_github_airutech_cnets_mapBuffer_create(_outputProtocolBuffer,_NAME_.outputProtocolArr,2000L,"startingBufferId+0L",1,1000)\
    _NAME_._outputProtocolBuffer = _outputProtocolBuffer;\
    com_github_airutech_cnets_mapBuffer_createReader(_NAME_##_outputProtocolBufferr0,&_NAME_._outputProtocolBuffer,0)\
    com_github_airutech_cnets_mapBuffer_createWriter(_NAME_##_outputProtocolBufferw0,&_NAME_._outputProtocolBuffer,0)\
    com_github_airutech_cnets_mapBuffer_create(_inputProtocolBuffer,_NAME_.inputProtocolArr,2000L,"startingBufferId+1L",1,1000)\
    _NAME_._inputProtocolBuffer = _inputProtocolBuffer;\
    com_github_airutech_cnets_mapBuffer_createReader(_NAME_##_inputProtocolBufferr0,&_NAME_._inputProtocolBuffer,0)\
    com_github_airutech_cnets_mapBuffer_createWriter(_NAME_##_inputProtocolBufferw0,&_NAME_._inputProtocolBuffer,0)\
    com_github_airutech_cnets_mapBuffer_create(_connectionsBuffer,_NAME_.connectionsArr,2000L,"startingBufferId+2L",1,1000)\
    _NAME_._connectionsBuffer = _connectionsBuffer;\
    com_github_airutech_cnets_mapBuffer_createReader(_NAME_##_connectionsBufferr0,&_NAME_._connectionsBuffer,0)\
    com_github_airutech_cnetsTransports_webSocketConnector_onKernels(&_NAME_);\
    com_github_airutech_cnetsTransports_protocolToBuffer_create(_protocolToBuffer,1,buffersWriters,deserializeCallbacks,_NAME_##_inputProtocolBufferr0);\
    _NAME_._protocolToBuffer = _protocolToBuffer;\
    com_github_airutech_cnetsTransports_bufferToProtocol_create(_bufferToProtocol,buffersReaders,serializeCallbacks,_NAME_##_outputProtocolBufferw0);\
    _NAME_._bufferToProtocol = _bufferToProtocol;\
    com_github_airutech_cnetsTransports_webSocket_create(_webSocket,localBuffersIds,1,2,initialConnection,-1,connectionStatus,sslContext,_NAME_##_inputProtocolBufferw0,_NAME_##_outputProtocolBufferr0,_NAME_##_connectionsBufferr0);\
    _NAME_._webSocket = _webSocket;

typedef struct com_github_airutech_cnetsTransports_webSocketConnector{
    int binBuffersSize;String initialConnection;long startingBufferId;arrayObject buffersWriters;arrayObject deserializeCallbacks;arrayObject buffersReaders;arrayObject serializeCallbacks;arrayObject localBuffersIds;connectionStatusCallback connectionStatus;SSLContext sslContext;arrayObject outputProtocolArr;arrayObject inputProtocolArr;arrayObject connectionsArr;writer w0;writer w1;writer w2;reader r0;reader r1;

  com_github_airutech_cnetsTransports_protocolToBuffer _protocolToBuffer;com_github_airutech_cnetsTransports_bufferToProtocol _bufferToProtocol;com_github_airutech_cnetsTransports_webSocket _webSocket;com_github_airutech_cnets_mapBuffer _outputProtocolBuffer;com_github_airutech_cnets_mapBuffer _inputProtocolBuffer;com_github_airutech_cnets_mapBuffer _connectionsBuffer;
com_github_airutech_cnets_runnablesContainer arrContainers[3];
  com_github_airutech_cnets_runnablesContainer (*getRunnables)(struct com_github_airutech_cnetsTransports_webSocketConnector *that);
  void (*run)(void *that);
/*[[[end]]] (checksum: 53cde501baceb351a791d78b4e3c91c6)*/

}com_github_airutech_cnetsTransports_webSocketConnector;

#undef com_github_airutech_cnetsTransports_webSocketConnector_onCreateMacro
#define com_github_airutech_cnetsTransports_webSocketConnector_onCreateMacro(_NAME_) /**/

#endif /* com_github_airutech_cnetsTransports_webSocketConnector_H */