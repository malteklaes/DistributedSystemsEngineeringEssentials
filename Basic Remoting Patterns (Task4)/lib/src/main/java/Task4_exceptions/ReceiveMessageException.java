package Task4_exceptions;

public class ReceiveMessageException extends GenericRemoteException {

	public ReceiveMessageException(String errorName, String errorMessage) {
		super(errorName, errorMessage);
	}

}