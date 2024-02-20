package Task5_exceptions;

/**
 * is thrown, if a message, which should be send from client to server, has the
 * WRONG FORMAT, right format: methodname;parameter
 * 
 * @author Malte
 *
 */
public class MessageWrongFormatException extends GenericRemoteException {

	public MessageWrongFormatException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
