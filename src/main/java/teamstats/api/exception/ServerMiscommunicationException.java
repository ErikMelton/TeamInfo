package teamstats.api.exception;

/**
 * @since 0.1
 * @version 0.1
 * @author Lord_Ralex
 */
public class ServerMiscommunicationException extends ServerException {

    public ServerMiscommunicationException() {
        super("Server sent an invalid command");
    }

    public ServerMiscommunicationException(String message) {
        super(message);
    }
}
