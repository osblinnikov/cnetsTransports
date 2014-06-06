
package com.github.airutech.cnetsTransports.webSocket;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import org.junit.Test;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.mapBuffer.*;
import com.github.airutech.cnetsTransports.types.*;
/*[[[end]]] (checksum: 3fdebff6d5eb746dbf117f8b0f3ebaeb) */
public class webSocketTest {
  @Test
  public void webSocketTest() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {
    SSLContext sslContext = null;
// load up the key store
//    String STORETYPE = "JKS";
//    String KEYSTORE = "keystore.jks";
//    String STOREPASSWORD = "storepassword";
//    String KEYPASSWORD = "keypassword";
//
//    KeyStore ks = KeyStore.getInstance( STORETYPE );
//    File kf = new File( KEYSTORE );
//    ks.load( new FileInputStream( kf ), STOREPASSWORD.toCharArray() );
//
//    KeyManagerFactory kmf = KeyManagerFactory.getInstance( "SunX509" );
//    kmf.init( ks, KEYPASSWORD.toCharArray() );
//    TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
//    tmf.init( ks );
//
//
//    sslContext = SSLContext.getInstance( "TLS" );
//    sslContext.init( kmf.getKeyManagers(), tmf.getTrustManagers(), null );


////////////////SERVER//////////////////
    int bufSizes = 1000;
/*outgoing protocol Buffer**/
    Object buffersProtocolOutgoing[] = new Object[bufSizes];
    for(int i=0; i<buffersProtocolOutgoing.length; i++){
      buffersProtocolOutgoing[i] = new cnetsProtocol(100);
    }
    mapBuffer mOut = new mapBuffer(buffersProtocolOutgoing, 1000, "1", 1, 1000);
    reader outpr0 = mOut.getReader(0);
    final writer outpw0 = mOut.getWriter(0);

/*incoming protocol Buffer**/
    Object buffersProtocolIncoming[] = new Object[bufSizes];
    for(int i=0; i<buffersProtocolIncoming.length; i++){
      buffersProtocolIncoming[i] = new cnetsProtocol(100);
    }
    mapBuffer mIn = new mapBuffer(buffersProtocolIncoming, 1000, "2", 1, 1000);
    reader inpr0 = mIn.getReader(0);
    final writer inpw0 = mIn.getWriter(0);

/*Configuration Buffer**/
    Object buffersConfig[] = new Object[bufSizes];
    for(int i=0; i<buffersConfig.length; i++){
      buffersConfig[i] = new cnetsProtocol(100);
    }
    mapBuffer mConfig = new mapBuffer(buffersConfig, 1000, "3", 1, 1000);
    reader cr0 = mConfig.getReader(0);
    final writer cw0 = mConfig.getWriter(0);



////////////////////CLIENT/////////////////////

/*outgoing protocol Buffer**/
    Object clientbuffersProtocolOutgoing[] = new Object[bufSizes];
    for(int i=0; i<clientbuffersProtocolOutgoing.length; i++){
      clientbuffersProtocolOutgoing[i] = new cnetsProtocol(100);
    }
    mapBuffer clientmOut = new mapBuffer(clientbuffersProtocolOutgoing, 1000, "1", 1, 1000);
    reader clientoutpr0 = clientmOut.getReader(0);
    final writer clientoutpw0 = clientmOut.getWriter(0);


/*connection status buffer**/
    Object connStatus[] = new Object[bufSizes];
    for(int i=0; i<connStatus.length; i++){
      connStatus[i] = new connectionStatus();
    }
    mapBuffer clientmConn = new mapBuffer(connStatus, 1000, "2", 1, 1000);
    reader clientConnr0 = clientmOut.getReader(0);
    final writer clientConnw0 = clientmOut.getWriter(0);

/*incoming protocol Buffer**/
    Object clientbuffersProtocolIncoming[] = new Object[bufSizes];
    for(int i=0; i<clientbuffersProtocolIncoming.length; i++){
      clientbuffersProtocolIncoming[i] = new cnetsProtocol(100);
    }
    mapBuffer clientmIn = new mapBuffer(clientbuffersProtocolIncoming, 1000, "3", 1, 1000);
    reader clientinpr0 = clientmIn.getReader(0);
    final writer clientinpw0 = clientmIn.getWriter(0);

/*Configuration Buffer**/
    Object clientbuffersConfig[] = new Object[bufSizes];
    for(int i=0; i<clientbuffersConfig.length; i++){
      clientbuffersConfig[i] = new cnetsProtocol(100);
    }
    mapBuffer clientmConfig = new mapBuffer(clientbuffersConfig, 1000, "4", 1, 1000);
    reader clientcr0 = clientmConfig.getReader(0);
    final writer clientcw0 = clientmConfig.getWriter(0);

/*Initialize Kernels**/
    Long[] clientOutBufferIds = new Long[]{4L};
    Long[] clientInBufferIds = new Long[]{5L};

//    WebSocketImpl.DEBUG = true;
    webSocket serverObj = new webSocket(100, null, 10001, sslContext, null, null, null, outpr0, cr0, null);
    webSocket clientObj = new webSocket(1, "ws://127.0.0.1:10001", -1, sslContext, null, null, null,clientoutpr0, clientcr0, null);

/*running kernels*/
    runnablesContainer runnables = new runnablesContainer();
    runnablesContainer[] arrContainers = new runnablesContainer[2];
    arrContainers[0] = serverObj.getRunnables();
    arrContainers[1] = clientObj.getRunnables();
    runnables.setContainers(arrContainers);
    runnables.launch(false);

/*waiting*/
    try {
      Thread.sleep(3000);
      runnables.stop();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

