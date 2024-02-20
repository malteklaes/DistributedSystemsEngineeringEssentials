package Task4_exceptions;

public class SendMessageException extends GenericRemoteException {

	public SendMessageException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}
