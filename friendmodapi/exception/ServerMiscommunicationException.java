/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package friendmodapi.exception;

import java.io.IOException;

/**
 *
 * @author Joshua
 */
public class ServerMiscommunicationException extends IOException {

    public ServerMiscommunicationException(String message) {
        super(message);
    }

    public ServerMiscommunicationException() {
        super("Server rejected the communication");
    }
}
