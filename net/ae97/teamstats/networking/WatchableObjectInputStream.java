package net.ae97.teamstats.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * @version 0.1
 * @author Lord_Ralex
 */
public class WatchableObjectInputStream extends ObjectInputStream {

    private boolean isClosed = false;

    public WatchableObjectInputStream() throws IOException {
        super();
    }

    public WatchableObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    public void close() throws IOException {
        isClosed = true;
        super.close();
    }

    public boolean isClosed() {
        return isClosed;
    }
}
