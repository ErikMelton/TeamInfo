package kovu.teamstats.api.exception;

/**
 * @since 0.1
 * @version 0.1
 * @author Lord_Ralex
 */
public class ServerOutdatedException extends ServerException {

    public ServerOutdatedException() {
        super("Server will not support this version of the API");
    }

    public ServerOutdatedException(String message) {
        super(message);
    }
}
