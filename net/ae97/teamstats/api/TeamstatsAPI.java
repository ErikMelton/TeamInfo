/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ae97.teamstats.api;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ae97.teamstats.exceptions.TeamstatsException;
import net.ae97.teamstats.network.PacketListener;
import net.ae97.teamstats.network.api.Packet;
import net.ae97.teamstats.network.api.Request;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public final class TeamstatsAPI {

    private static final TeamstatsAPI instance = new TeamstatsAPI();
    private final String SERVER_URL = "teamstats.ae97.net";
    private final int SERVER_PORT = 19767;
    private final byte CLIENT_VERSION = 10;
    private final Map<String, Map<String, Object>> friendData = new ConcurrentHashMap<String, Map<String, Object>>();
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private PacketListener packetListener;

    private TeamstatsAPI() {
    }

    public static TeamstatsAPI getAPI() {
        return instance;
    }

    public void load() throws IOException, TeamstatsException {
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            return;
        }
        socket = new Socket(SERVER_URL, SERVER_PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        Packet versionPacket = new Packet(Request.GET_VERSION);
        versionPacket.set("version", CLIENT_VERSION);
        sendPacket(versionPacket);
        Packet reply = packetListener.waitFor(versionPacket.getID());
        Boolean answer = (Boolean) reply.get("accept");
        if (!answer) {
            packetListener.close();
            out.close();
            in.close();
            socket.close();
        }
    }

    public void sendPacket(Packet packet) throws IOException {
        if (out == null || socket == null || !socket.isConnected()
                || socket.isClosed() || packet == null) {
            return;
        }
        out.writeObject(packet);
        out.flush();
    }

    public void updateStats(Map<String, Object> map) throws IOException {
        HashMap<String, Serializable> data = new HashMap<String, Serializable>();
        for (Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Serializable) {
                data.put(entry.getKey(), (Serializable) entry.getValue());
            } else {
                Logger.getLogger(TeamstatsAPI.class.getName()).log(Level.WARNING, "The key " + entry.getKey() + " stores a non-serializable object: " + entry.getValue().getClass().getSimpleName());
            }
        }
        Packet sendData = new Packet(Request.SEND_STATS);
        sendData.set("data", data);
        sendPacket(sendData);
    }

    public Map<String, Object> getStats(String name) {
        return friendData.get(name);
    }

    public Set<String> getFriends() {
        return friendData.keySet();
    }

    public void addFriend(String name) throws IOException {
        Packet addFriend = new Packet(Request.ADD_FRIEND);
        addFriend.set("name", name);
        sendPacket(addFriend);
    }

    public void removeFriend(String name) {
    }

    public void denyRequest(String name) {
    }

    public void blockUser(String name) {
    }

    public void unblockUser(String name) {
    }
}
