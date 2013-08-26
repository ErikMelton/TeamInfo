package net.ae97.teamstats.api.events;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public class StatsUpdatedEvent extends TeamstatsEvent {

    private final Map<String, Map<String, Object>> stats;

    public StatsUpdatedEvent(Map<String, Map<String, Object>> newStats) {
        stats = new ConcurrentHashMap<String, Map<String, Object>>();
        stats.putAll(newStats);
    }

    public String[] getUpdatedList() {
        return stats.keySet().toArray(new String[0]);
    }

    public Map<String, Object> getStats(String userName) {
        return stats.get(userName);
    }
}
