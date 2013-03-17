package kovu.ralex.teamstats.api.exception;

/**
 *
 * @author Joshua
 */
public class CreationNotCompleteException extends ServerException {

    public CreationNotCompleteException() {
        super("API failed to completely set up");
    }

    public CreationNotCompleteException(String message) {
        super(message);
    }
}
