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
public class ServerConnectionLostException extends IOException {

    public ServerConnectionLostException(String message) {
        super(message);
    }

    public ServerConnectionLostException() {
        super("Server rejected the communication");
    }
}
