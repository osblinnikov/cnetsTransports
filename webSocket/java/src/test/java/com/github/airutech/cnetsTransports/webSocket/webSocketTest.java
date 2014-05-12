
package com.github.airutech.cnetsTransports.webSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import org.junit.Test;
/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/


import com.github.airutech.cnetsTransports.types.*;
import com.github.airutech.cnets.readerWriter.*;
import com.github.airutech.cnets.runnablesContainer.*;
import com.github.airutech.cnets.selector.*;
import com.github.airutech.cnets.queue.*;
import com.github.airutech.cnets.mapBuffer.*;
/*[[[end]]] (checksum: 2efeebb03bb75b35280c2db6e0fdfea7) */
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
      buffersProtocolOutgoing[i] = new cnetsProtocolBinary();
    }
    mapBuffer mOut = new mapBuffer(buffersProtocolOutgoing, 1000, 1, 1);
    reader outpr0 = mOut.getReader(0);
    final writer outpw0 = mOut.getWriter(0);

/*incoming protocol Buffer**/
    Object buffersProtocolIncoming[] = new Object[bufSizes];
    for(int i=0; i<buffersProtocolIncoming.length; i++){
      buffersProtocolIncoming[i] = new cnetsProtocolBinary();
    }
    mapBuffer mIn = new mapBuffer(buffersProtocolIncoming, 1000, 2, 1);
    reader inpr0 = mIn.getReader(0);
    final writer inpw0 = mIn.getWriter(0);

/*Configuration Buffer**/
    Object buffersConfig[] = new Object[bufSizes];
    for(int i=0; i<buffersConfig.length; i++){
      buffersConfig[i] = new cnetsProtocolBinary();
    }
    mapBuffer mConfig = new mapBuffer(buffersConfig, 1000, 3, 1);
    reader cr0 = mConfig.getReader(0);
    final writer cw0 = mConfig.getWriter(0);



////////////////////CLIENT/////////////////////

/*outgoing protocol Buffer**/
    Object clientbuffersProtocolOutgoing[] = new Object[bufSizes];
    for(int i=0; i<clientbuffersProtocolOutgoing.length; i++){
      clientbuffersProtocolOutgoing[i] = new cnetsProtocolBinary();
    }
    mapBuffer clientmOut = new mapBuffer(clientbuffersProtocolOutgoing, 1000, 1, 1);
    reader clientoutpr0 = clientmOut.getReader(0);
    final writer clientoutpw0 = clientmOut.getWriter(0);

/*incoming protocol Buffer**/
    Object clientbuffersProtocolIncoming[] = new Object[bufSizes];
    for(int i=0; i<clientbuffersProtocolIncoming.length; i++){
      clientbuffersProtocolIncoming[i] = new cnetsProtocolBinary();
    }
    mapBuffer clientmIn = new mapBuffer(clientbuffersProtocolIncoming, 1000, 2, 1);
    reader clientinpr0 = clientmIn.getReader(0);
    final writer clientinpw0 = clientmIn.getWriter(0);

/*Configuration Buffer**/
    Object clientbuffersConfig[] = new Object[bufSizes];
    for(int i=0; i<clientbuffersConfig.length; i++){
      clientbuffersConfig[i] = new cnetsProtocolBinary();
    }
    mapBuffer clientmConfig = new mapBuffer(clientbuffersConfig, 1000, 3, 1);
    reader clientcr0 = clientmConfig.getReader(0);
    final writer clientcw0 = clientmConfig.getWriter(0);




/*Initialize Kernels**/
    Long[] clientOutBufferIds = new Long[]{4L};
    Long[] clientInBufferIds = new Long[]{5L};

//    WebSocketImpl.DEBUG = true;
    webSocket serverObj = new webSocket(clientOutBufferIds,100,10,null,10001,new conState(), sslContext, inpw0,outpr0,cr0);
    webSocket clientObj = new webSocket(clientInBufferIds,1,10,"ws://127.0.0.1:10001",-1, new conState(), sslContext, clientinpw0,clientoutpr0,clientcr0);

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
      runnables.stop(true);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

