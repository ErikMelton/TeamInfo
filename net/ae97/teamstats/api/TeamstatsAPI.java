package net.ae97.teamstats.api;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ae97.teamstats.api.events.NewFriendEvent;
import net.ae97.teamstats.api.events.NewRequestEvent;
import net.ae97.teamstats.api.events.StatsUpdatedEvent;
import net.ae97.teamstats.exceptions.TeamstatsException;
import net.ae97.teamstats.network.PacketListener;
import net.ae97.teamstats.network.PacketSender;
import net.ae97.teamstats.network.api.Packet;
import net.ae97.teamstats.network.api.Request;
import net.minecraftforge.common.MinecraftForge;

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
    private volatile Socket socket;
    private volatile PacketListener packetListener;
    private volatile PacketSender packetSender;
    private volatile Thread updateThread;

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
        packetSender = new PacketSender(socket);
        packetListener = new PacketListener(socket);
        Packet versionPacket = new Packet(Request.GET_VERSION);
        versionPacket.set("version", CLIENT_VERSION);
        sendPacket(versionPacket);
        Packet reply = packetListener.waitFor(versionPacket.getID());
        Boolean answer = (Boolean) reply.get("accept");
        if (!answer) {
            packetListener.close();
            socket.close();
        }
        updateThread = new Thread(new UpdateDataThread());
        updateThread.start();
    }

    public void sendPacket(Packet packet) throws IOException {
        if (socket == null || !socket.isConnected()
                || socket.isClosed() || packet == null) {
            return;
        }
        packetSender.sendPacket(packet);
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

    public void removeFriend(String name) throws IOException {
        friendData.remove(name);
        Packet removeFriend = new Packet(Request.REMOVE_FRIEND);
        removeFriend.set("name", name);
        sendPacket(removeFriend);
    }

    public void denyRequest(String name) throws IOException {
        Packet denyRequest = new Packet(Request.DENY_REQUEST);
        denyRequest.set("name", name);
        sendPacket(denyRequest);
    }

    public void blockUser(String name) throws IOException {
        Packet blockUser = new Packet(Request.BLOCK_USER);
        blockUser.set("name", name);
        sendPacket(blockUser);
    }

    public void unblockUser(String name) throws IOException {
        Packet unblockUser = new Packet(Request.UNBLOCK_USER);
        unblockUser.set("name", name);
        sendPacket(unblockUser);
    }

    public void forceUpdate() {
        synchronized (updateThread) {
            updateThread.notify();
        }
    }

    private class UpdateDataThread implements Runnable {

        private final int UPDATE_DELAY = 60000;

        @Override
        public void run() {
            boolean canRun = true;
            while (canRun) {
                try {
                    synchronized (this) {
                        this.wait(UPDATE_DELAY);
                    }
                } catch (InterruptedException ex) {
                }
                if (socket.isClosed() || !socket.isConnected()) {
                    break;
                }
                NewFriendEvent asdf = new NewFriendEvent("Billy Bob Joe");
                try {
                    System.out.println("Test event firing");
                    MinecraftForge.EVENT_BUS.post(asdf);
                    System.out.println("Test event fired");
                } catch (Exception e) {
                    Logger.getLogger(TeamstatsAPI.class.getName()).log(Level.SEVERE, "Error running NewFriendEvent for " + asdf, e);
                }
                try {
                    Packet newFriendPacket = new Packet(Request.GET_NEW_FRIENDS);
                    sendPacket(newFriendPacket);
                    Packet reply = packetListener.waitFor(newFriendPacket.getID());
                    Set<String> names = (Set<String>) reply.get("reply");
                    for (String name : names) {
                        NewFriendEvent newFriendEvent = new NewFriendEvent(name);
                        try {
                            MinecraftForge.EVENT_BUS.post(newFriendEvent);
                        } catch (Exception e) {
                            Logger.getLogger(TeamstatsAPI.class.getName()).log(Level.SEVERE, "Error running NewFriendEvent for " + name, e);
                        }
                    }

                    Packet newReqPacket = new Packet(Request.GET_NEW_REQUESTS);
                    sendPacket(newReqPacket);
                    reply = packetListener.waitFor(newReqPacket.getID());
                    names = (Set<String>) reply.get("reply");
                    for (String name : names) {
                        NewRequestEvent newRequestEvent = new NewRequestEvent(name);
                        try {
                            MinecraftForge.EVENT_BUS.post(newRequestEvent);
                        } catch (Exception e) {
                            Logger.getLogger(TeamstatsAPI.class.getName()).log(Level.SEVERE, "Error running NewRequestEvent for " + name, e);
                        }
                    }

                    Packet getFriends = new Packet(Request.GET_FRIENDS);
                    sendPacket(getFriends);
                    reply = packetListener.waitFor(getFriends.getID());
                    List<String> friendList = (List<String>) reply.get("reply");
                    synchronized (friendData) {
                        friendData.clear();
                    }
                    for (String friend : friendList) {
                        Packet getStats = new Packet(Request.GET_STATS);
                        getStats.set("name", friend);
                        sendPacket(getStats);
                        reply = packetListener.waitFor(getStats.getID());
                        HashMap<String, Serializable> stats = (HashMap<String, Serializable>) reply.get("stats");
                        Map<String, Object> newStatData = new ConcurrentHashMap<String, Object>();
                        for (Entry<String, Serializable> entry : stats.entrySet()) {
                            newStatData.put(entry.getKey(), entry.getValue());
                        }
                        Map<String, Object> oldStats;
                        synchronized (friendData) {
                            oldStats = friendData.remove(friend);
                            if (oldStats == null) {
                                oldStats = new HashMap<String, Object>();
                            }
                            friendData.put(friend, newStatData);
                        }
                        StatsUpdatedEvent updateEvent = new StatsUpdatedEvent(friend, oldStats, newStatData);
                        try {
                            MinecraftForge.EVENT_BUS.post(updateEvent);
                        } catch (Exception e) {
                            Logger.getLogger(TeamstatsAPI.class.getName()).log(Level.SEVERE, "Error running StatsUpdatedEvent for " + friend, e);
                        }
                    }
                } catch (IOException e) {
                    Logger.getLogger(TeamstatsAPI.class.getName()).log(Level.SEVERE, "Error updating stats", e);
                }

            }
        }
    }
}
