package friendmodapi;

import friendmodapi.exception.ServerConnectionLostException;
import friendmodapi.exception.ServerOutdatedException;
import friendmodapi.exception.ServerRejectionException;
import friendmodapi.list.FMAList;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The FriendMod API class. This handles all the server-related requests. This
 * should be used to get info from the server.
 *
 * @author Lord_Ralex
 * @version 0.1
 * @since 0.1
 */
public final class FriendModApi {

    private static String name;
    private static String session;
    private static Socket connection;
    private static DataInputStream input;
    private static DataOutputStream output;
    private static final String MAIN_SERVER_URL = "friendserver.bouncer.lordralex.us";
    private static final int SERVER_PORT = 1667;
    private static final List<String> friendList = new FMAList<String>();
    private static final Map<String, String> friendStats = new ConcurrentHashMap<String, String>();
    private static final List<String> friendRequests = new FMAList<String>();
    private static final UpdaterThread updaterThread = new UpdaterThread();
    private static String stats;
    private static final List<String> newFriends = new FMAList<String>();
    private static final List<String> newRequests = new FMAList<String>();
    private static final List<String> newlyRemovedFriends = new FMAList<String>();
    private static final List<String> onlineFriends = new FMAList<String>();
    private static final int UPDATE_TIMER = (1000 * 60 * 1); //1000ms * 60s * 1 minute = 1 minute update
    private static boolean online = false;
    private static short API_VERSION = 1;

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
            throw new ServerOutdatedException("Server will not support this version of the API");

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
    public static Map<String, String> getFriendStats() throws IOException {
        return friendStats;
    }

    /**
     * Gets the stats for a single friend. If the friend requested is not an
     * actual friend, this will return an empty String.
     *
     * @param friendName The friend to get the stats for
     * @return The String -form of their stats
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static String getFriendState(String friendName) throws IOException {
        return friendStats.get(friendName);
    }

    /**
     * Gets all accepted friends.
     *
     * @return An array of all friends accepted
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static String[] getFriends() throws IOException {
        return friendList.toArray(new String[0]);
    }

    /**
     * Sends the stats to the server. This will never return false. If the
     * connection is rejected, this will throw an IOException.
     *
     * @param newStats New stats to send
     * @return True if connection was successful.
     * @throws IOException Thrown when server fails to send data or if server
     * rejects communication
     */
    public static boolean updateStats(String newStats) throws IOException {
        stats = newStats;
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
    public static String[] getNewFriendRequests(boolean reset) {
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
    public static String[] getRemovedFriends(boolean reset) {
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
    public static String[] getNewFriends(boolean reset) {
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
    public static String[] getNewFriendRequests() {
        return getNewFriendRequests(true);
    }

    /**
     * Gets the list of removed friends to this user. This will also clear the
     * list.
     *
     * @return Names of newly removed friends
     */
    public static String[] getRemovedFriends() {
        return getRemovedFriends(true);
    }

    /**
     * Gets the list of new friends to this user. This will also clear the list.
     *
     * @return Names of new friends
     */
    public static String[] getNewFriends() {
        return getNewFriends(true);
    }

    /**
     * Returns an array of friends that are online based on the cache.
     *
     * @return Array of friends who are online
     */
    public static String[] getOnlineFriends() {
        return onlineFriends.toArray(new String[0]);
    }

    /**
     * Checks to see if a particular friend is online.
     *
     * @param name Name of friend
     * @return True if they are online, false otherwise
     */
    public static boolean isFriendOnline(String name) {
        return onlineFriends.contains(name);
    }

    /**
     * Forces the client to update the stats and such. This forces the update
     * thread to run.
     *
     * @throws IOException
     */
    public static void forceUpdate() throws IOException {
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
        return changeOnlineStatus(!online);
    }

    private FriendModApi() {
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
                        List<String> addFriend = new FMAList<String>();
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
                        output.writeUTF("UPDATESTATS " + session + " " + stats);
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
                                friendStats.put(friendName, stat);
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
