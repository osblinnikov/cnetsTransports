package com.github.airutech.cnetsTransports.protocolToBuffer;

import java.nio.ByteBuffer;

/**
 * Created by oleg on 4/30/14.
 */
public interface deserializeCallback {
    public boolean deserialize(ByteBuffer input, Object bufDataObj, int packetId, int packets_grid_size, boolean isNewBunch, int nodeId);
}
