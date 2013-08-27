package net.ae97.teamstats.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ae97.teamstats.network.api.Packet;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public class PacketSender extends Thread {

    private final Queue<Packet> outgoing = new ConcurrentLinkedQueue<Packet>();
    private final Socket socket;
    private final ObjectOutputStream out;

    public PacketSender(Socket s) throws IOException {
        socket = s;
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (socket.isConnected() && !socket.isClosed()) {
            boolean isEmpty;
            synchronized (outgoing) {
                isEmpty = outgoing.isEmpty();
            }
            if (isEmpty) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                    }
                }
            } else {
                Packet next;
                synchronized (outgoing) {
                    next = outgoing.poll();

                }
                if (next == null) {
                    continue;
                }
                try {
                    out.writeObject(next);
                } catch (IOException ex) {
                    Logger.getLogger(PacketSender.class.getName()).log(Level.SEVERE, "Packet sending failed", ex);
                }
            }
        }
    }

    public void sendPacket(Packet packet) {
        synchronized (outgoing) {
            outgoing.offer(packet);
        }
        synchronized (this) {
            this.notify();
        }
    }
}
