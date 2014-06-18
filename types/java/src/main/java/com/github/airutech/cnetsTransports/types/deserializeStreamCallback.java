package com.github.airutech.cnetsTransports.types;


import com.github.airutech.cnetsTransports.types.*;
import java.nio.ByteBuffer;

public interface deserializeStreamCallback {
    public boolean deserializeNext(Object bufDataObj, cnetsProtocol input);
}
