package com.github.airutech.cnetsTransports.protocolToBuffer;

import java.nio.ByteBuffer;

public interface deserializeCallback {
    public boolean deserialize(ByteBuffer input, Object bufDataObj, int packetId, int packets_grid_size, boolean isNewBunch);
}
