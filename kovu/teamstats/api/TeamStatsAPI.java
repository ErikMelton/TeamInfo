package kovu.ralex.teamstats.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import kovu.ralex.teamstats.api.exception.*;
import kovu.ralex.teamstats.api.list.TSAList;

/**
 * The TeamStats API class. This handles all the server-related requests. This
 * should be used to get info from the server.
 *
 * @author Lord_Ralex
 * @version 0.2
 * @since 0.1
 */
public final class TeamStatsAPI {

    private static String name;
    private static String session;
    private static Socket connection;
    private static DataInputStream input;
    private static DataOutputStream output;
    private static final String MAIN_SERVER_URL;
    private static final int SERVER_PORT;
    private static final List<String> friendList = new TSAList<String>();
    private static final Map<String, Map<String, Object>> friendStats = new ConcurrentHashMap<String, Map<String, Object>>();
    private static final List<String> friendRequests = new TSAList<String>();
    private static final UpdaterThread updaterThread = new UpdaterThread();
    private static final Map<String, Object> stats = new ConcurrentHashMap<String, Object>();
    private static final List<String> newFriends = new TSAList<String>();
    private static final List<String> newRequests = new TSAList<String>();
    private static final List<String> newlyRemovedFriends = new TSAList<String>();
    private static final List<String> onlineFriends = new TSAList<String>();
    private static final int UPDATE_TIMER = (1000 * 60 * 1); //1000ms * 60s * 1 minute = 1 minute update
    private static boolean online = false;
    private static short API_VERSION = 1;
    private static boolean was_set_up = false;

    static {
        //enter the server url here where the main bouncer is
        MAIN_SERVER_URL = "";
        //enter the port the bouncer runs off of here
        SERVER_PORT = 0;
    }

    /**
     * Sets up the connection to the server.
     *
     * @param aName Name of player
     * @param aSession The player's session id
     * @throws UnknownHostException Thrown when attempting to reach server fails
     * @throws IOException Thrown if server closes connection or fails to
     * connect
     */
    public static void setupApi(String aName, String aSession) throws UnknownHostException, IOException {
        name = aName;
        session = aSession;
        connection = new Socket(MAIN_SERVER_URL, SERVER_PORT);
        input = new DataInputStream(connection.getInputStream());
        output = new DataOutputStream(connection.getOutputStream());
        output.writeUTF("GETSERVER");
        String SERVER_URL = input.readUTF();
        connection.close();

        if (SERVER_URL.equalsIgnoreCase("NONODE")) {
            throw new ServerRejectionException("There is no node open");
        }

        String link = SERVER_URL.split(":")[0];
        int port = Integer.parseInt(SERVER_URL.split(":")[1]);
        short server_version = Short.parseShort(SERVER_URL.split(" ")[1]);
        if (server_version != API_VERSION) {
            throw new ServerOutdatedException();

        }
        connection = new Socket(link, port);
        input = new DataInputStream(connection.getInputStream());
        output = new DataOutputStream(connection.getOutputStream());
        output.writeUTF(name + " " + session);
        boolean isAccepted = input.readBoolean();
        if (!isAccepted) {
            throw new ServerRejectionException();
        }
        updaterThread.start();
        online = true;
        was_set_up = true;
    }

    /**
     * Gets the stats for each friend that is registered by the server. This can
     * throw an IOException if the server rejects the client communication or an
     * issue occurs when reading the data.
     *
     * @return Mapping of friends and their stats
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static Map<String, Map<String, Object>> getFriendStats() throws IOException {
        wasSetup();
        return friendStats;
    }

    /**
     * Returns the map's toString form of the friend's stats. THIS IS
     * DEPRECATED, REFER TO NOTES FOR NEW METHOD
     *
     * @param friendName Name of friend
     * @return String version of the stats
     * @throws IOException
     */
    public static String getFriendState(String friendName) throws IOException {
        wasSetup();
        return friendStats.get(friendName).toString();
    }

    /**
     * Gets the stats for a single friend. If the friend requested is not an
     * actual friend, this will return null.
     *
     * @param friendName The friend to get the stats for
     * @return The stats in a map
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static Map<String, Object> getFriendStat(String friendName) throws IOException {
        wasSetup();
        return friendStats.get(friendName);
    }

    /**
     * Gets the specific value for a certain stat for a friend. The key is the
     * stat name.
     *
     * @param friendName Name of friend
     * @param key Key of stat
     * @return Value of the friend's key, or null if not one
     * @throws IOException
     */
    public static Object getFriendStat(String friendName, String key) throws IOException {
        wasSetup();
        key = key.toLowerCase();
        Map<String, Object> stat = friendStats.get(friendName);
        if (stat == null) {
            return null;
        } else {
            return stat.get(key);
        }
    }

    /**
     * Gets all accepted friends.
     *
     * @return An array of all friends accepted
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static String[] getFriends() throws IOException {
        wasSetup();
        return friendList.toArray(new String[0]);
    }

    /**
     * Sends the stats to the server. This will never return false. If the
     * connection is rejected, this will throw an IOException.
     *
     * @param key Key to set
     * @param value The value for this key
     * @return True if connection was successful.
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static boolean updateStats(String key, Object value) throws IOException {
        wasSetup();
        stats.put(key.toLowerCase().trim(), value);
        return true;
    }

    /**
     * Sends the stats to the server. This will never return false. If the
     * connection is rejected, this will throw an IOException.
     *
     * @param map Map of values to set
     * @return True if connection was successful.
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static boolean updateStats(Map<String, ? extends Object> map) throws IOException {
        for (String key : map.keySet()) {
            updateStats(key, map.get(key));
        }
        return true;
    }

    /**
     * Gets a list of friend requests the user has. This will return names of
     * those that want to friend this user.
     *
     * @return Array of friend requests to the user
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static String[] getFriendRequests() throws IOException {
        wasSetup();
        return friendRequests.toArray(new String[0]);
    }

    /**
     * Requests a friend addition. This will not add them, just request that the
     * person add them. The return is just for the connection, not for the
     * friend request.
     *
     * @param name Name of friend to add/request
     * @return True if request was successful
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static boolean addFriend(String name) throws IOException {
        wasSetup();
        return friendList.add(name);
    }

    /**
     * Removes a friend. This will take place once used and any friend list will
     * be updated.
     *
     * @param name Name of friend to remove
     * @return True if connection was successful
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static boolean removeFriend(String name) throws IOException {
        wasSetup();
        return friendList.remove(name);
    }

    /**
     * Gets the list of new requests to this user. This will also clear the list
     * if true is passed.
     *
     * @param reset Whether to clear the list. True will remove the list after
     * returning it.
     * @return Names of new friend requests
     */
    public static String[] getNewFriendRequests(boolean reset) throws IOException {
        wasSetup();
        String[] newFriendsToReturn = newRequests.toArray(new String[0]);
        if (reset) {
            newRequests.clear();
        }
        return newFriendsToReturn;
    }

    /**
     * Gets the list of removed friends to this user. This will also clear the
     * list if true is passed.
     *
     * @param reset Whether to clear the list. True will remove the list after
     * returning it.
     * @return Names of newly removed friends
     */
    public static String[] getRemovedFriends(boolean reset) throws IOException {
        wasSetup();
        String[] newFriendsToReturn = newlyRemovedFriends.toArray(new String[0]);
        if (reset) {
            newlyRemovedFriends.clear();
        }
        return newFriendsToReturn;
    }

    /**
     * Gets the list of new friends to this user. This will also clear the list
     * if true is passed.
     *
     * @param reset Whether to clear the list. True will remove the list after
     * returning it.
     * @return Names of new friends
     */
    public static String[] getNewFriends(boolean reset) throws IOException {
        wasSetup();
        String[] newFriendsToReturn = newFriends.toArray(new String[0]);
        if (reset) {
            newFriends.clear();
        }
        return newFriendsToReturn;
    }

    /**
     * Gets the list of new requests to this user. This will also clear the
     * list.
     *
     * @return Names of new friend requests
     */
    public static String[] getNewFriendRequests() throws IOException {
        wasSetup();
        return getNewFriendRequests(true);
    }

    /**
     * Gets the list of removed friends to this user. This will also clear the
     * list.
     *
     * @return Names of newly removed friends
     */
    public static String[] getRemovedFriends() throws IOException {
        wasSetup();
        return getRemovedFriends(true);
    }

    /**
     * Gets the list of new friends to this user. This will also clear the list.
     *
     * @return Names of new friends
     */
    public static String[] getNewFriends() throws IOException {
        wasSetup();
        return getNewFriends(true);
    }

    /**
     * Returns an array of friends that are online based on the cache.
     *
     * @return Array of friends who are online
     */
    public static String[] getOnlineFriends() throws IOException {
        wasSetup();
        return onlineFriends.toArray(new String[0]);
    }

    /**
     * Checks to see if a particular friend is online.
     *
     * @param name Name of friend
     * @return True if they are online, false otherwise
     */
    public static boolean isFriendOnline(String name) throws IOException {
        wasSetup();
        return onlineFriends.contains(name);
    }

    /**
     * Forces the client to update the stats and such. This forces the update
     * thread to run.
     *
     * @throws IOException
     */
    public static void forceUpdate() throws IOException {
        wasSetup();
        synchronized (updaterThread) {
            if (updaterThread.isAlive()) {
                updaterThread.notify();
            } else {
                throw new ServerConnectionLostException();
            }
        }
    }

    /**
     * Checks to see if the client is still connected to the server and if the
     * update thread is running.
     *
     * @return True if the update thread is alive, false otherwise.
     * @throws IOException
     */
    public static boolean isChecking() throws IOException {
        wasSetup();
        return updaterThread.isAlive();
    }

    /**
     * Changes the online status of the client. This is instant to the server
     * and tells the server to turn the client offline.
     *
     * @param newStatus New online status
     * @return The new online status
     * @throws IOException
     */
    public static boolean changeOnlineStatus(boolean newStatus) throws IOException {
        wasSetup();
        online = newStatus;
        output.writeUTF("CHANGEONLINE " + session + " " + (online ? "1" : "0"));
        if (input.readBoolean()) {
            return online;
        } else {
            throw new ServerRejectionException();
        }
    }

    /**
     * Changes the online status of the client. This is instant to the server
     * and tells the server to turn the client offline.
     *
     * @return The new online status
     * @throws IOException
     */
    public static boolean changeOnlineStatus() throws IOException {
        wasSetup();
        return changeOnlineStatus(!online);
    }

    /**
     * Returns a boolean where true means the API was completely setup and
     * connections were successful, otherwise an exception is thrown. This only
     * checks the initial connection, not the later connections. Use
     * isChecking() for that.
     *
     * @return True if API was set up.
     * @throws IOException If api was not created right, exception thrown
     */
    public static boolean wasSetup() throws IOException {
        if (was_set_up) {
            return true;
        } else {
            throw new CreationNotCompleteException();
        }
    }

    protected TeamStatsAPI() {
    }

    private static class UpdaterThread extends Thread {

        @Override
        public void run() {
            boolean isGood = true;
            while (isGood) {
                synchronized (this) {
                    try {
                        wait(UPDATE_TIMER);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace(System.out);
                    }
                }
                if (online) {
                    try {
                        output.writeUTF("GETFRIENDS " + session);
                        String[] friends;
                        if (input.readBoolean()) {
                            friends = input.readUTF().split(" ");
                        } else {
                            throw new ServerRejectionException();
                        }

                        //check current friend list, removing and adding name differences
                        List<String> addFriend = new TSAList<String>();
                        addFriend.addAll(friendList);
                        for (String existing : friends) {
                            addFriend.remove(existing);
                        }
                        for (String name : addFriend) {
                            output.writeUTF("ADDFRIEND " + session + " " + name);
                            if (input.readBoolean()) {
                            } else {
                                throw new ServerRejectionException();
                            }
                        }

                        List<String> removeFriend = new ArrayList<String>();
                        removeFriend.addAll(Arrays.asList(friends));
                        for (String existing : friendList) {
                            removeFriend.remove(existing);
                        }
                        for (String name : removeFriend) {
                            output.writeUTF("REMOVEFRIEND " + session + " " + name);
                            if (input.readBoolean()) {
                            } else {
                                throw new ServerRejectionException();
                            }
                        }

                        //send new stats for this person
                        String pStats = "";
                        for (String key : stats.keySet()) {
                            pStats += key + ":" + stats.get(key) + " ";
                        }
                        output.writeUTF("UPDATESTATS " + session + " " + pStats);
                        if (input.readBoolean()) {
                        } else {
                            throw new ServerRejectionException();
                        }

                        //check friend requests
                        output.writeUTF("GETREQUESTS " + session);
                        if (input.readBoolean()) {
                            String names = input.readUTF();
                            String[] old = friendRequests.toArray(new String[0]);
                            friendRequests.clear();
                            friendRequests.addAll(Arrays.asList(names.split(" ")));
                            if (newRequests.containsAll(Arrays.asList(old))) {
                                break;
                            }
                            for (String name : old) {
                                if (!newRequests.contains(name)) {
                                    newRequests.add(name);
                                }
                            }
                        } else {
                            throw new ServerRejectionException();
                        }

                        output.writeUTF("GETFRIENDS " + session);
                        if (input.readBoolean()) {
                            List<String> updateFriends = Arrays.asList(input.readUTF().split(" "));
                            for (String name : updateFriends) {
                                if (friendList.contains(name)) {
                                    continue;
                                }
                                newFriends.add(name);
                            }
                            for (String name : friendList) {
                                if (updateFriends.contains(name)) {
                                    continue;
                                }
                                newlyRemovedFriends.add(name);
                            }
                            friendList.clear();
                            friendList.addAll(updateFriends);
                        } else {
                            throw new ServerRejectionException();
                        }

                        //get stats for friends in list
                        friendStats.clear();
                        onlineFriends.clear();
                        for (String friendName : friendList) {
                            output.writeUTF("GETSTATS " + session + " " + friendName);
                            if (input.readBoolean()) {
                                String stat = input.readUTF();
                                Map<String, Object> friendS = new HashMap<String, Object>();
                                String[] parts = stat.split(" ");
                                for (String string : parts) {
                                    friendS.put(string.split(":")[0].toLowerCase().trim(), string.split(":")[1]);
                                }
                                friendStats.put(friendName, friendS);
                            } else {
                                throw new ServerRejectionException();
                            }
                            output.writeUTF("GETONLINESTATUS " + session + " " + friendName);
                            if (input.readBoolean()) {
                                boolean isOnline = input.readBoolean();
                                if (isOnline) {
                                    onlineFriends.add(friendName);
                                }
                            } else {
                                throw new ServerRejectionException();
                            }
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace(System.err);
                        isGood = false;
                        online = false;
                    }
                } else {
                    new ServerConnectionLostException().printStackTrace(System.err);
                    isGood = false;
                    online = false;
                }
            }
        }
    }
}
