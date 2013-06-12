package net.ae97.teamstats.networking;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import net.ae97.teamstats.ClientRequest;

/**
 * @version 0.1
 * @author Lord_Ralex
 */
public class Packet implements Serializable {

    private final byte id;
    private final Map<String, Object> data = new HashMap<String, Object>();
    private boolean isAlive = true;

    public Packet(byte i) {
        id = i;
    }

    public Packet(ClientRequest reg) {
        if (reg == null) {
            reg = ClientRequest.NOSUCHREQUESTTYPE;
        }
        id = reg.getID();
    }

    public Packet addData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public byte getID() {
        return id;
    }

    public void kill() {
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
