package kovu.ralex.teamstats.api.exception;

/**
 * @since 0.1
 * @version 0.1
 * @author Lord_Ralex
 */
public class ServerConnectionLostException extends ServerException {

    public ServerConnectionLostException() {
        super("Server connection was lost");
    }

    public ServerConnectionLostException(String message) {
        super(message);
    }
}
