package net.ae97.teamstats.api.events;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public class NewRequestEvent extends TeamstatsEvent {

    private final String newRequestName;

    public NewRequestEvent(String name) {
        newRequestName = name;
    }

    public String getName() {
        return newRequestName;
    }
}
