/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package friendmodapi.exception;

import java.io.IOException;

public class ServerRejectionException extends IOException {

    public ServerRejectionException(String message) {
        super(message);
    }

    public ServerRejectionException() {
        super("Server rejected the communication");
    }
}
