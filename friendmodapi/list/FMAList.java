/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package friendmodapi.list;

import java.util.ArrayList;

/**
 *
 * @author Joshua
 */
public class FMAList<T> extends ArrayList {

    @Override
    public boolean contains(Object obj) {
        if (super.contains(obj)) {
            return true;
        }

        if (!(obj instanceof String)) {
            return false;
        }

        String name = (String) obj;

        for (Object val : this) {
            if (val instanceof String) {
                String t = (String) val;
                if (name.equalsIgnoreCase(t)) {
                    return true;
                }
            }
        }
        return false;
    }
}
