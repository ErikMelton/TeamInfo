package kovu.teamstats.api.exception;

import java.io.IOException;

/**
 * @since 0.1
 * @version 0.1
 * @author Lord_Ralex
 * @see IOException
 */
public abstract class ServerException extends IOException {

    public ServerException() {
        super("An exception has occurred");
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable e) {
        super(e);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
