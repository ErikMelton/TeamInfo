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
public class ServerOutdatedException extends IOException {

    public ServerOutdatedException(String message) {
        super(message);
    }

    public ServerOutdatedException() {
        super("Server rejected the communication");
    }
}
