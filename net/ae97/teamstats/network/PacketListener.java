package net.ae97.teamstats.network;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ae97.teamstats.network.api.Packet;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public class PacketListener extends Thread implements Closeable {

    private final Queue<Packet> packetQueue = new ConcurrentLinkedQueue<Packet>();
    private final ObjectInputStream in;
    private final Socket socket;
    private final ScheduledExecutorService es = Executors.newScheduledThreadPool(10);

    public PacketListener(Socket s) throws IOException {
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (socket.isConnected() && !socket.isClosed() && !isInterrupted()) {
            try {
                Object input = in.readObject();
                if (!(input instanceof Packet)) {
                    continue;
                }
                synchronized (packetQueue) {
                    packetQueue.offer((Packet) input);
                }
            } catch (Exception ex) {
                Logger.getLogger(PacketListener.class.getName()).log(Level.SEVERE, "Exception from PacketListener", ex);
            }
        }
    }

    public Packet waitFor(final int id) {
        synchronized (packetQueue) {
            Iterator<Packet> itt = packetQueue.iterator();
            while (itt.hasNext()) {
                Packet next = itt.next();
                if (next.getID() == id) {
                    itt.remove();
                    packetQueue.remove(next);
                    return next;
                }
            }
        }
        ScheduledFuture<Packet> future = es.schedule(new Recheck(id), 50, TimeUnit.MILLISECONDS);
        try {
            return future.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(PacketListener.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ExecutionException ex) {
            Logger.getLogger(PacketListener.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        this.interrupt();
    }

    private class Recheck implements Callable<Packet> {

        private final int id;

        protected Recheck(int i) {
            id = i;
        }

        @Override
        public Packet call() throws Exception {
            return waitFor(id);
        }
    }
}
