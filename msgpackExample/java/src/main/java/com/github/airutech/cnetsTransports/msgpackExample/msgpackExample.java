
package com.github.airutech.cnetsTransports.msgpackExample;

/*[[[cog
import cogging as c
c.tpl(cog,templateFile,c.a(prefix=configFile))
]]]*/

public class msgpackExample{
  public int testInt; public float[] testFloatArr; public com.github.airutech.cnetsTransports.msgpackExample.msgpackExample testNestedExample = null; public double[] testDoubleArr; public com.github.airutech.cnetsTransports.msgpackExample.msgpackExample[] testNestedExampleArr;
  public msgpackExample(double[] testDoubleArr,com.github.airutech.cnetsTransports.msgpackExample.msgpackExample[] testNestedExampleArr){
    this.testDoubleArr = testDoubleArr;
    this.testNestedExampleArr = testNestedExampleArr;
    this.testFloatArr = new float[10];
    this.testNestedExample = null;
    onCreate();
    initialize();
  }

  private void initialize(){
    
    onKernels();
    
  }
/*[[[end]]] (checksum: 0722752f03ae0230735cb3ee576aeb1f)*/

  public msgpackExample(){}
  private void onCreate(){

  }

  private void onKernels(){

  }


}

