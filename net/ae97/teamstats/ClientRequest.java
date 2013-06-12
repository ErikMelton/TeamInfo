package net.ae97.teamstats;

/**
 * List of communication calls.
 *
 * @author Lord_Ralex
 */
public enum ClientRequest {

    GETONLINESTATUS((byte) 0x10000000),
    GETVERSION((byte) 0x01000000),
    GETSERVER((byte) 0x00100000),
    GETNODEMUMBER((byte) 0x00010000),
    GETCLIENTCOUNT((byte) 0x00001000),
    HAVEFREESPACE((byte) 0x00000100),
    OPENCONNECTION((byte) 0x00000010),
    GETFRIENDS((byte) 0x20000000),
    UPDATESTATS((byte) 0x02000000),
    ADDFRIEND((byte) 0x00200000),
    REMOVEFRIEND((byte) 0x00020000),
    GETSTATS((byte) 0x00002000),
    GETREQUESTS((byte) 0x00000200),
    CHANGEONLINE((byte) 0x00000020),
    DISCONNECT((byte) 0xFFFFFFFF),
    REQUESTACCEPTED((byte) 0xAAAAAAAA),
    SIMPLEREPLYPACKET((byte) 0xBBBBBBBB),
    NOSUCHREQUESTTYPE((byte) 0x00000000);
    private final byte id;

    private ClientRequest(byte type) {
        id = type;
    }

    public byte getID() {
        return id;
    }

    public static ClientRequest getRequest(String r) {
        for (ClientRequest rq : ClientRequest.values()) {
            if (rq.name().equalsIgnoreCase(r)) {
                return rq;
            }
        }
        return NOSUCHREQUESTTYPE;
    }

    public static ClientRequest getRequest(Byte r) {
        if (r == null) {
            return getRequest((byte) 0x00000000);
        } else {
            return getRequest(r.byteValue());
        }
    }

    public static ClientRequest getRequest(byte r) {
        for (ClientRequest rq : ClientRequest.values()) {
            if (rq.id == r) {
                return rq;
            }
        }
        return NOSUCHREQUESTTYPE;
    }
}
