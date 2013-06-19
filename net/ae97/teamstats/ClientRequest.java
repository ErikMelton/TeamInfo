package net.ae97.teamstats;

/**
 * List of communication calls.
 *
 * @author Lord_Ralex
 */
public enum ClientRequest {

    GETONLINESTATUS(1),
    GETVERSION(2),
    GETSERVER(3),
    GETNODEMUMBER(4),
    GETCLIENTCOUNT(5),
    HAVEFREESPACE(6),
    OPENCONNECTION(10),
    GETFRIENDS(11),
    UPDATESTATS(12),
    ADDFRIEND(13),
    REMOVEFRIEND(14),
    GETSTATS(15),
    GETREQUESTS(16),
    CHANGEONLINE(17),
    DISCONNECT(97),
    REQUESTACCEPTED(98),
    SIMPLEREPLYPACKET(99),
    NOSUCHREQUESTTYPE(0);
    private final int id;

    private ClientRequest(int type) {
        id = type;
    }

    public int getID() {
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
            return getRequest(0);
        } else {
            return getRequest(r.byteValue());
        }
    }

    public static ClientRequest getRequest(int r) {
        for (ClientRequest rq : ClientRequest.values()) {
            if (rq.id == r) {
                return rq;
            }
        }
        return NOSUCHREQUESTTYPE;
    }
}
