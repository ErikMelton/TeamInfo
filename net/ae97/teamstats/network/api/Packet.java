/*
 * Copyright (C) 2013 Lord_Ralex
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.ae97.teamstats.network.api;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public final class Packet implements Serializable, Cloneable {

    private static volatile Integer UUID = 001;
    private final int id;
    private final Request type;
    private final Map<String, Object> data = new ConcurrentHashMap<String, Object>();

    public Packet(Request req) {
        synchronized (UUID) {
            id = UUID.intValue();
            UUID++;
        }
        type = req;
    }

    public Packet(int i, Request req) {
        id = i;
        type = req;
    }

    @Override
    public Packet clone() throws CloneNotSupportedException {
        return (Packet) super.clone();
    }

    public int getID() {
        return id;
    }

    public Request getRequest() {
        return type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> newData) {
        data.clear();
        data.putAll(newData);
    }

    public Object get(String key) {
        return data.get(key.toLowerCase());
    }

    public void set(String key, Object info) {
        data.put(key.toLowerCase(), info);
    }
}
