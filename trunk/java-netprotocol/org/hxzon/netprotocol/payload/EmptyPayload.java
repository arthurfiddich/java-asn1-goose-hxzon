package org.hxzon.netprotocol.payload;

public class EmptyPayload extends DataPayload {
    public String getProtocolTypeDesc() {
        return getSrcPacket().getProtocolTypeDesc();
    }

    public String getName() {
        return "empty";
    }
}
