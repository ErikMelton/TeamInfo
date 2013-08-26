package net.ae97.teamstats.api.events;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public class NewFriendEvent extends TeamstatsEvent {

    private final String newFriendName;

    public NewFriendEvent(String name) {
        newFriendName = name;
    }

    public String getName() {
        return newFriendName;
    }
}
