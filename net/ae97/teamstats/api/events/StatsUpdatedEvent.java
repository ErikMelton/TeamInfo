package net.ae97.teamstats.api.events;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public class StatsUpdatedEvent extends TeamstatsEvent {

    private final Map<String, Object> oldStats;
    private final Map<String, Object> newStats;
    private final String friendName;

    public StatsUpdatedEvent(String name, Map<String, Object> old, Map<String, Object> newS) {
        oldStats = new ConcurrentHashMap<String, Object>();
        newStats = new ConcurrentHashMap<String, Object>();
        friendName = name;
        oldStats.putAll(old);
        newStats.putAll(newS);
    }

    public Map<String, Object> getOldStats() {
        return oldStats;
    }

    public Map<String, Object> getNewStats() {
        return oldStats;
    }

    public String getFriendName() {
        return friendName;
    }
}
