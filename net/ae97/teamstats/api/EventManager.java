package net.ae97.teamstats.api;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ae97.teamstats.api.events.TeamstatsEvent;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public class EventManager {

    private Map<Class<? extends TeamstatsEvent>, Map<TeamstatListener, Method>> eventList = new ConcurrentHashMap<Class<? extends TeamstatsEvent>, Map<TeamstatListener, Method>>();

    public void registerListener(TeamstatListener listener) {
        Class cl = listener.getClass();
        for (Method method : cl.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(TeamstatHandler.class)) {
                continue;
            }
            Class[] params = method.getParameterTypes();
            if (params.length != 1) {
                continue;
            }
            if (TeamstatsEvent.class.isAssignableFrom(params[0])) {
                Map<TeamstatListener, Method> map = eventList.get(params[0]);
                if (map == null) {
                    System.err.println("The event list does not contain the event " + params[0].getName());
                    continue;
                }
                map.put(listener, method);
                eventList.put(params[0], map);
                System.out.println("The listener " + listener.getClass().getName() + " has registered " + params[0].getName());
            }
        }
    }

    public void registerEvent(Class<? extends TeamstatsEvent> event) {
        System.out.println("Registering event class: " + event.getName());
        eventList.put(event, new ConcurrentHashMap<TeamstatListener, Method>());
    }

    public void invokeEvent(TeamstatsEvent event) {
        Map<TeamstatListener, Method> listeners = eventList.get(event.getClass());
        for (Entry<TeamstatListener, Method> entry : listeners.entrySet()) {
            try {
                entry.getValue().invoke(entry.getKey(), event);
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error on handling event (" + event.getClass().getName() + ") in " + entry.getKey().getClass().getName(), e);
            }
        }
    }
}
