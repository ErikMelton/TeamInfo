package net.ae97.teamstats.networking;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 0.1
 * @author Lord_Ralex
 */
public class TimeoutThread extends Thread {

    private final int delay;
    private final Thread parent;

    public TimeoutThread(Thread p, int d) {
        parent = p;
        delay = d;
    }

    public void run() {
        synchronized (this) {
            try {
                this.wait(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimeoutThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        synchronized (parent) {
            if (parent.isAlive()) {
                parent.notify();
            }
        }
    }
}
