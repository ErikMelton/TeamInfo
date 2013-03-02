package teamstats.api.exception;

/**
 * @since 0.1
 * @version 0.1
 * @author Lord_Ralex
 */
public class ServerRejectionException extends ServerException {

    public ServerRejectionException() {
        super("Server rejected the communication");
    }

    public ServerRejectionException(String message) {
        super(message);
    }
}
