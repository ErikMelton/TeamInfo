package kovu.teamstats.api.list;

import java.util.ArrayList;

/**
 * @since 0.1
 * @version 0.2
 * @author Lord_Ralex
 */
public class TSAList<T extends String> extends ArrayList<T> {

    @Override
    public synchronized boolean contains(Object obj) {
        if (super.contains(obj)) {
            return true;
        }

        String t = (String) obj;

        for (String val : this) {
            if (t.equalsIgnoreCase(val)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized Object[] toArray() {
        return super.toArray();
    }

    @Override
    public synchronized <T> T[] toArray(T[] ts) {
        return super.toArray(ts);
    }
}
